package com.yxtec.t8;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jerrie on 17-5-23.
 */
public class JirbRunnerProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String source = (String) args[0];

        StringBuilder code = new StringBuilder();
//        code.append("package com.yxtec.t8; \n");
        code.append("import java.util.*; \n");
        code.append("import java.io.*; \n");
        code.append("import java.math.*; \n");
        code.append("public class JirbRunnerImpl { \n");
        code.append("public void run() { \n");

        code.append(source);

        code.append("} \n");
        code.append("} \n");


        ByteArrayOutputStream runOut = new ByteArrayOutputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager javaFileManager = compiler.getStandardFileManager(null, null, null);

        MemoryJavaCodeObject javaCodeObject = new MemoryJavaCodeObject("JirbRunnerImpl", code.toString());
        MemoryJavaClassObject javaClassObject = new MemoryJavaClassObject("JirbRunnerImpl", out);

//        MemoryJavaFileManager javaFileManager = new MemoryJavaFileManager("JirbRunnerImpl", code.toString(), runOut);

        Set<JavaFileObject.Kind> kinds = new HashSet<>();
        kinds.add(JavaFileObject.Kind.SOURCE);
//        kinds.add(JavaFileObject.Kind.CLASS);

        List<JavaFileObject> javaFileObjects = new ArrayList<>();
        javaFileObjects.add(javaCodeObject);

        JavaCompiler.CompilationTask task = compiler.getTask(writer, javaFileManager, null, null, null, javaFileObjects);

        boolean success = task.call();
        if (!success) {
            return new String(out.toByteArray(), "UTF-8");
        } else {
            String result = null;

            JirbClassLoader loader = new JirbClassLoader(Thread.currentThread().getContextClassLoader());
            Class<?> jirbRunnerImplClazz = loader.loadClass("JirbRunnerImpl");
            Object jirbRunnerImpl = jirbRunnerImplClazz.newInstance();
            Method runMethod = jirbRunnerImplClazz.getMethod("run", String.class);

            PrintStream standardOut = System.out;
            try {
                System.setOut(new PrintStream(runOut));
                runMethod.invoke(jirbRunnerImpl, code.toString());

                result = new String(runOut.toByteArray(), "UTF-8");
            } catch (Exception e) {
                result = e.getMessage();
            } finally {
                System.setOut(standardOut);
            }
            return result;
        }
    }
}
