package com.aegisql.memeter;

import org.openjdk.jol.info.GraphLayout;

import java.util.Objects;

public class MeasuringBox<T> implements MemEvaluation{

    private volatile long totalSize = 0;
    private final T payload;

    public MeasuringBox(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public long totalSize() {
        if(totalSize==0) {
            totalSize = GraphLayout.parseInstance(this).totalSize();
        }
        return totalSize;
    }

    @Override
    public void invalidate() {
        totalSize = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasuringBox<?> that = (MeasuringBox<?>) o;
        return Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeasuringBox{");
        sb.append("totalSize=").append(totalSize());
        sb.append(", payload=").append(payload);
        sb.append('}');
        return sb.toString();
    }
}
