package com.yxtec.t8;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by jerrie on 17-5-23.
 */
public class MemoryJavaClassObject extends SimpleJavaFileObject {

    private OutputStream out;

    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given URI.
     *
     * @param name the name
     * @param out  the out
     */
    protected MemoryJavaClassObject(String name, OutputStream out) {
        super(URI.create(name + ".class"), Kind.CLASS);
        this.out = out;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return out;
    }
}
