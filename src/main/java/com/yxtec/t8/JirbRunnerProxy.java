package com.yxtec.t8;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by jerrie on 17-5-23.
 */
public class JirbRunnerProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String source = (String) args[0];

        String code = "import java.util.*; \n" +
                "import java.io.*; \n" +
                "import java.math.*; \n" +
                "import java.awt.*; \n" +
                "import javax.swing.*; \n" +
                "public final class JirbRunnerImpl { \n" +
                "public static void run() { \n" +
                source +
                "} \n" +
                "} \n";

        ByteArrayOutputStream runOut = new ByteArrayOutputStream();

        MemoryJavaCompiler compiler = new MemoryJavaCompiler();
        Method runMethod = compiler.compileStaticMethod("run", "JirbRunnerImpl", code);

        String result = null;
        PrintStream standardOut = System.out;
        try {
            System.setOut(new PrintStream(runOut));
            runMethod.invoke(null);

            result = new String(runOut.toByteArray(), "UTF-8");
        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            System.setOut(standardOut);
        }
        return result;
    }
}
