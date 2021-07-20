package com.codenjoy.dojo.snake.client;

import java.awt.*;
import java.util.Objects;

public class PointS extends Point {
    public final int x;
    public final int y;

    private PointS(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static PointS of(int x, int y) {
        return new PointS(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
