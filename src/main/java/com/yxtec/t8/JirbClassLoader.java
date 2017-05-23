package com.yxtec.t8;

/**
 * Created by jerrie on 17-5-23.
 */
public class JirbClassLoader extends ClassLoader {

    private JirbClassLoader() {
    }

    public JirbClassLoader(ClassLoader parent) {
        super(parent);
    }

}
