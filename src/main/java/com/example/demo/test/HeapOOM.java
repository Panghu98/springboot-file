package com.example.demo.test;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Options
 * -Xms20m
 * -Xmx20m
 * -XX:+HeapDumpOnOutOfMemoryError
 * @author panghu
 */
public class HeapOOM {
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new HeapOOM.OOMObject());
        }
    }

    static class OOMObject {
    }

}
