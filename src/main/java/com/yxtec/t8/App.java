package com.yxtec.t8;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;

/**
 * Hello world!
 */
public class App {
    static String banner = "       ___                     ___           ___\n" +
            "      /\\  \\        ___        /\\  \\         /\\  \\\n" +
            "      \\:\\  \\      /\\  \\      /::\\  \\       /::\\  \\\n" +
            "  ___ /::\\__\\     \\:\\  \\    /:/\\:\\  \\     /:/\\:\\  \\\n" +
            " /\\  /:/\\/__/     /::\\__\\  /::\\~\\:\\  \\   /::\\~\\:\\__\\\n" +
            " \\:\\/:/  /     __/:/\\/__/ /:/\\:\\ \\:\\__\\ /:/\\:\\ \\:|__|\n" +
            "  \\::/  /     /\\/:/  /    \\/_|::\\/:/  / \\:\\~\\:\\/:/  /\n" +
            "   \\/__/      \\::/__/        |:|::/  /   \\:\\ \\::/  /\n" +
            "               \\:\\__\\        |:|\\/__/     \\:\\/:/  /\n" +
            "                \\/__/        |:|  |        \\::/__/\n" +
            "                              \\|__|         ~~\n";


    public static void main(String[] args) throws Exception {
        System.out.println(banner);
        System.out.println("V1.0 - Power By Jerrie. ");
        System.out.println("Usage: ");
        System.out.println("\t1. multiple line java code must be in code block, like \r\n\t{\r\n\t\tint a = 10;\r\n\t\tint b = 20;\r\n\t\tSystem.out.println(a + b);\r\n\t}");
        System.out.println("\t2. if you wanna quit the program, please input #exit ");
        System.out.println("\t3. if you on windows, please execute %JDK_HOME%\\bin\\java.exe -jar jirb.jar ");

        System.out.println("\n\n");

        int refCount = 0;
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("===> ");
            do {
                try {
                    String line = reader.readLine();

                    if (StringUtils.isNoneBlank(line)) {
                        if(line.equalsIgnoreCase("#exit")) {
                            break;
                        }

                        builder.append(line);

                        if (line.endsWith("{")) {
                            refCount++; //引用加1
                        }

                        if (line.endsWith("}")) {
                            refCount--; //引用减1
                        }

                        builder.append("\n");

                        if (refCount == 0) {
                            jirbRunn(builder.toString());
                            builder = new StringBuilder();
                        } else {
                            System.out.print("======> ");
                        }
                    } else if (refCount > 0) {
                        builder = new StringBuilder();
                        System.out.print("===> ");
                    }
                } catch (Exception e) {
                }
            } while (true);

            System.out.println("Bye.");
            System.exit(0);
        }
    }

    public static void jirbRunn(String source) throws IOException, InterruptedException {
        JirbRunnerProxy proxy = new JirbRunnerProxy();
        JirbRunner jirbRunner = (JirbRunner) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{JirbRunner.class}, proxy);
        String result = jirbRunner.run(source);
        System.out.println("<=== " + result);
        System.out.print("===> ");
    }
}
