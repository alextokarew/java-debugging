package com.github.alextokarew.javadebugging.ffm;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class FibFFM {
    static void main(String[] args) throws Throwable {
        try (Arena arena = Arena.ofConfined()) {
            IO.println("Working dir: " + System.getProperty("user.dir"));
            var sl = SymbolLookup.libraryLookup("../cpp-fib/build/lib/main/debug/libcpp-fib.so", arena);
            MemorySegment memSeg = sl.find("fibonacci").orElseThrow();
            Linker linker = Linker.nativeLinker();

            var desc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT);

            MethodHandle fibonacci = linker.downcallHandle(memSeg, desc);
            var result = fibonacci.invoke(12);
            IO.println("Result: " + result);
        }
    }
}
