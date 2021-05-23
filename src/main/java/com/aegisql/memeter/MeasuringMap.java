package com.aegisql.memeter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MeasuringMap <K,V> implements Map<K,V>, MemEvaluation {

    private final Map<MeasuringBox<K>,MeasuringBox<V>> map;
    private long totalSize = 0;

    public MeasuringMap() {
        this(HashMap::new);
    }

    public MeasuringMap(Supplier<Map> supplier) {
        this.map = supplier.get();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        var val =  map.get(key);
        if(val==null) {
            return null;
        }
        return val.getPayload();
    }

    @Override
    public V put(K key, V value) {
        var val = map.put(new MeasuringBox<>(key), new MeasuringBox<>(value));
        if(val==null) {
            return null;
        }
        return val.getPayload();
    }

    @Override
    public V remove(Object key) {
        var val =map.remove(key);
        if(val==null) {
            return null;
        }
        return val.getPayload();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if(m != null) {
            m.forEach(this::put);
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet().stream().map(MeasuringBox::getPayload).collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return map.values().stream().map(MeasuringBox::getPayload).collect(Collectors.toSet());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet().stream().map(es-> new Entry<K,V>() {
            @Override
            public K getKey() {
                return es.getKey().getPayload();
            }

            @Override
            public V getValue() {
                return es.getValue().getPayload();
            }

            @Override
            public V setValue(V value) {
                var val = es.setValue(new MeasuringBox<>(value));
                if(val==null) {
                    return null;
                }
                return val.getPayload();
            }

            @Override
            public boolean equals(Object o) {
                return es.equals(o);
            }

            @Override
            public int hashCode() {
                return es.hashCode();
            }
        }).collect(Collectors.toSet());
    }

    @Override
    public long totalSize() {
        return 0;
    }

    @Override
    public void invalidate() {
        totalSize = 0;
    }

    private void invalidate(K key) {

    }

}
