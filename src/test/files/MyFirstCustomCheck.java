import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MyClass {
    public void run() {
        ClassWriter w = new ClassWriter(0);
        w.visit(Opcodes.V1_8, Opcodes.ACC_ABSTRACT | Opcodes.ACC_PUBLIC, "com/sun/tools/javac/code/Scope$WriteableScope", null, "com/sun/tools/javac/code/Scope", null);
        byte[] classData = w.toByteArray();
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) ((Field) theUnsafe).get(null);
            Class scopeClass = Class.forName("com.sun.tools.javac.code.Scope");
            // Noncompliant@+1 {{sun.misc.Unsafe.defineClass is deprecated use java.lang.invoke.MethodHandles.Lookup.defineClass instead}}
            unsafe.defineClass("com.sun.tools.javac.code.Scope$WriteableScope", classData, 0, classData.length, scopeClass.getClassLoader(), scopeClass.getProtectionDomain());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
