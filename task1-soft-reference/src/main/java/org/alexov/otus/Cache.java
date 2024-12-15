package org.alexov.otus;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class Cache<K, V> {
    private final Map<K, Reference<V>> cache;
    private String directory;

    public Cache() {
        cache = new WeakHashMap<>();
    }

    public V get(K key) {
        if (cache.isEmpty()) {
            addCacheData(key);
        }
        Reference<V> ref = cache.get(key);
        if (ref == null) {
            return null;
        }
        return ref.get();
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getDirectory() {return directory;}

    public void putSoft(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public void putWeak(K key, V value) {
        cache.put(key, new WeakReference<>(value));
    }

    public void clear() {
        cache.clear();
    }

    public boolean remove(K key) {
        return cache.remove(key) != null;
    }

    public abstract void addCacheData(K key);


}
