package com.github.patbattb.hw7;

import com.github.patbattb.hw7.httpserver.HttpTaskServer;
import com.github.patbattb.hw7.manager.taskmanager.HttpTaskManager;

import java.io.IOException;

@SuppressWarnings("HideUtilityClassConstructor")
public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpTaskManager manager = new HttpTaskManager("Http://localhost:8078");
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.start();


    }
}
