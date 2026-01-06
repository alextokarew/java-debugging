package com.github.alextokarew.javadebugging.codegen;

import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.lang.classfile.attribute.SourceFileAttribute;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.ConstantDescs.CD_int;
import static java.lang.constant.ConstantDescs.INIT_NAME;
import static java.lang.constant.ConstantDescs.MTD_void;

public class BytecodeDebugging {

    static void main(String[] args) throws Exception {
        IO.println("Let's generate some bytecode via ClassFile API");
        IO.println("Entrypoint class loader " + Entrypoint.class.getClassLoader());

        var classDesc = ClassDesc.of(SAMPLE_CALCULATOR_CLASS_NAME);
        byte[] classFileBytes = ClassFile.of().build(classDesc, BytecodeDebugging::buildClass);
        IO.println("SampleCalculator bytes length: " + classFileBytes.length);

        Files.write(Path.of("SampleCalculator.class"), classFileBytes);

        MyClassLoader myClassLoader = new MyClassLoader(Entrypoint.class.getClassLoader(), classFileBytes);
        myClassLoader.loadClass(Entrypoint.class.getName());
        Class<?> cls = myClassLoader.defineClass();
        Entrypoint instance = (Entrypoint) cls.getDeclaredConstructor().newInstance();
        int result = instance.calculate(4, 6);
        IO.println("Result of invoking calculate(4,6) is " + result);
    }

    private static void buildClass(ClassBuilder classBuilder) {
        classBuilder
                .withFlags(ClassFile.ACC_PUBLIC)
                .withInterfaceSymbols(ClassDesc.of(Entrypoint.class.getName()))
                .withMethodBody(
                        ConstantDescs.INIT_NAME,
                        ConstantDescs.MTD_void,
                        ClassFile.ACC_PUBLIC,
                        BytecodeDebugging::buildConstructor)
                .withMethodBody(
                    "calculate",
                    MethodTypeDesc.of(CD_int, List.of(CD_int, CD_int)),
                    ClassFile.ACC_PUBLIC,
                    BytecodeDebugging::buildMethodBody)
                .with(SourceFileAttribute.of("expression.expr"));
    }

    private static void buildConstructor(CodeBuilder codeBuilder) {
        codeBuilder
                .aload(0)
                .invokespecial(CD_Object, INIT_NAME, MTD_void)
                .return_();
    }

    private static void buildMethodBody(CodeBuilder codeBuilder) {
        codeBuilder
                .localVariable(0, "this", ClassDesc.of(SAMPLE_CALCULATOR_CLASS_NAME), codeBuilder.startLabel(), codeBuilder.endLabel())
        		.localVariable(1, "a", CD_int, codeBuilder.startLabel(), codeBuilder.endLabel())
                .localVariable(2, "b", CD_int, codeBuilder.startLabel(), codeBuilder.endLabel())
                .lineNumber(1)
                .iload(1)
                .iload(2)
                .lineNumber(2)
                .imul()
                .return_(TypeKind.INT);
    }

    private static class MyClassLoader extends ClassLoader {
        private final byte[] classFileBytes;

        private MyClassLoader(ClassLoader parent, byte[] classFileBytes) {
            super(parent);
            this.classFileBytes = classFileBytes;
        }

        Class<?> defineClass() {
            return super.defineClass(SAMPLE_CALCULATOR_CLASS_NAME, classFileBytes, 0, classFileBytes.length);
        }
    }

    public static final String SAMPLE_CALCULATOR_CLASS_NAME = "com.github.alextokarew.javadebugging.codegen.SampleCalculator";
}
