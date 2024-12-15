package org.alexov.otus;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {
    public static void main(String[] args) {
        Emulator emulator = Emulator.createSoftEmulator();
        // получаем данные файла из кэша
        emulator.setCacheDirectory("task1-soft-reference/src/main/resources/files");
        byte[] bytes = emulator.extractCacheData("e.txt");

        if (nonNull(bytes)) {
            System.out.println(new String(bytes));
        } else {
            System.out.println("File not found");
        }
    }
}

