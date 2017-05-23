package com.yxtec.t8;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerrie on 17-5-23.
 */
public class MemoryJavaFileManager extends ForwardingJavaFileManager {

    private String name;

    private MemoryJavaCodeObject code;

    private MemoryJavaClassObject clazz;

    private List<JavaFileObject> javaFileObjects = new ArrayList<>();

    public MemoryJavaFileManager(String name, String source, OutputStream out) {
        super(null);


        MemoryJavaCodeObject code = new MemoryJavaCodeObject(name, source);
        MemoryJavaClassObject clazz = new MemoryJavaClassObject(name, out);

        this.name = name;
        this.code = code;
        this.clazz = clazz;

        javaFileObjects.add(this.code);
        javaFileObjects.add(this.clazz);
    }

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    protected MemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }
}
