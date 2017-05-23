package com.yxtec.t8;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class MemoryJavaCodeObject extends SimpleJavaFileObject {

    private String code;

    /**
     * Instantiates a new Java code object.
     *
     * @param name the name
     * @param code the code
     */
    public MemoryJavaCodeObject(String name, String code) {
        super(URI.create(name + ".java"), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
} 