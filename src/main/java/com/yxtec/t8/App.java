package com.yxtec.t8;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
        System.out.println("V1.0");
        System.out.println("\n\n");

        int refCount = 0;
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("===> ");
            String line = scanner.next().trim();

            if (StringUtils.isNoneBlank(line)) {
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
                }
            }
        } while (scanner.hasNext());
    }

    public static void jirbRunn(String source) throws IOException, InterruptedException {
        JirbRunnerProxy proxy = new JirbRunnerProxy();
        JirbRunner jirbRunner = (JirbRunner) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{JirbRunner.class}, proxy);
        String result = jirbRunner.run(source);
        System.out.println("<=== " + result);
        System.out.println();
    }
}
