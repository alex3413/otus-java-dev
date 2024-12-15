package org.alexov.otus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

public class Emulator {
    private final Cache<String, byte[]> cache;

    private Emulator(Cache<String, byte[]> cache) {
        this.cache = cache;
    }
    public Cache<String, byte[]> getCache(){
        return cache;
    }
    public void setCacheDirectory(String directory){
        cache.setDirectory(directory);
    }

    public static Emulator createWeakEmulator() {
        return new Emulator(new Cache<>() {
            @Override
            public void addCacheData(String filename) {
                Optional.of(new File(getDirectory()))
                        .map(File::listFiles)
                        .map(Arrays::stream)
                        .flatMap(fileStream -> fileStream
                                .filter(f -> filename.equals(f.getName()))
                                .findAny())
                        .ifPresent(file ->
                                {
                                    try {
                                        putWeak(file.getName(), Files.readAllBytes(file.toPath()));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );

            }
        });
    }
    public static Emulator createSoftEmulator() {
        return new Emulator(new Cache<>() {
            @Override
            public void addCacheData(String filename) {
                Optional.ofNullable(getDirectory())
                        .map(File::new)
                        .map(File::listFiles)
                        .map(Arrays::stream)
                        .flatMap(fileStream -> fileStream
                                .filter(f -> filename.equals(f.getName()))
                                .findAny())
                        .ifPresent(file ->
                                {
                                    try {
                                        putWeak(file.getName(), Files.readAllBytes(file.toPath()));
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );

            }
        });
    }
    public byte[] extractCacheData(String filename){
        return cache.get(filename);
    }

    public void putSoftCache(String fileName, byte[] data) {
        cache.putSoft(fileName, data);
    }

    public void putWeakCache(String fileName, byte[] data) {
        cache.putWeak(fileName, data);
    }


}
