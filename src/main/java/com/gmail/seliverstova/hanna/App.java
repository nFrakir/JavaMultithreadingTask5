package com.gmail.seliverstova.hanna;

import java.io.File;

public class App {
    public static void main(String[] args) {
        File folder = new File(".");
        Thread thr = new Thread(new FolderWatcher(folder));
        thr.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thr.interrupt();
    }
}
