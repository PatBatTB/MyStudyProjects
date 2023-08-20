package com.example.kvserver;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
    }
}
