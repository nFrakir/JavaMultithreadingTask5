package com.gmail.seliverstova.hanna;

import java.io.File;
import java.text.SimpleDateFormat;

public class FolderWatcher implements Runnable {
    private File mainFolder;

    public FolderWatcher(File mainFolder) {
        super();
        this.mainFolder = mainFolder;
    }

    public FolderWatcher() {
        super();
    }

    public File getMainFolder() {
        return mainFolder;
    }

    @Override
    public void run() {
        if (mainFolder == null) {
            return;
        }
        Thread th = Thread.currentThread();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy   HH:mm:ss");
        File[] prevArray = mainFolder.listFiles();
        File[] currArray = mainFolder.listFiles();
        String changesInfo = "";

        for (; !th.isInterrupted(); ) {
            currArray = mainFolder.listFiles();
            changesInfo = getChangesInfo(prevArray, currArray);
            if (!(changesInfo.isEmpty())) {
                System.out.println(sdf.format(System.currentTimeMillis()));
                System.out.println(changesInfo);
                System.out.println();

                prevArray = currArray;
            }
            try {
                th.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }

    }

    private String getChangesInfo(File[] prevArray, File[] currArray) {
        StringBuilder sb = new StringBuilder();

        sb.append(compareArrays(prevArray, currArray, "was deleted"));
        sb.append(compareArrays(currArray, prevArray, "was added"));

        return sb.toString();
    }

    private String compareArrays(File[] arrayOne, File[] arrayTwo, String direction) {
        StringBuilder sb = new StringBuilder();
        boolean isFinded = false;
        for (File fileOne : arrayOne) {
            isFinded = false;
            for (File fileTwo : arrayTwo) {
                if (fileOne.getName().equals(fileTwo.getName())) {
                    isFinded = true;
                    break;
                }
            }
            if (!isFinded) {
                sb.append(fileOne.getName() + "\t" + direction).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}