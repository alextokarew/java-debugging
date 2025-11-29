package com.github.alextokarew.joker.jvmdebug;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;



public class FibFFM {
    public static void main(String[] args) throws Throwable {
        try (Arena arena = Arena.ofConfined()) {
            var sl = SymbolLookup.libraryLookup("/home/atokarew/Work/talks/jvmdebug/jni_example/libfibffm.so", arena);
            MemorySegment memSeg = sl.find("fibonacci").orElseThrow();
            Linker linker = Linker.nativeLinker();

            var desc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT);

            MethodHandle fibonacci = linker.downcallHandle(memSeg, desc);
            var result = fibonacci.invoke(12);
	    System.out.println("Result: " + result);
        }
    }
}
