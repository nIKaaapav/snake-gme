package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Lee {
    private final static int[][] deltas = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    private static final int OBSTACLE = -1;
    private static final int FREE = 0;

    private final int sizeX;
    private final int sizeY;
    private final int[][] board;

    public Lee() {
        this.sizeX = 14;
        this.sizeY = 14;
        this.board = new int[sizeY][sizeX];
    }

    public void printBoard() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeX; y++) {
                Point p = new PointImpl(x, y);
                System.out.print(formatted(p));
            }
            System.out.println();
        }
        System.out.println();
    }

    public String formatted(Point point) {
        int val = at(point);
        if (val == OBSTACLE) return " XX ";
        return String.format("%3d ", val);
    }

    private void putObstacles(List<Point> obstacles) {
        obstacles.forEach(p -> mark(p, OBSTACLE));
    }


    void mark(int x, int y, int value) {
        board[y][x] = value;
    }

    void mark(Point pt, int value) {
        mark(pt.getX(), pt.getY(), value);
    }

    int at(int x, int y) {
        return board[y][x];
    }

    int at(Point pt) {
        return at(pt.getX(), pt.getY());
    }

    boolean isFreeAt(int x, int y) {
        return at(x, y) == FREE;
    }

    boolean isFreeAt(Point pt) {
        return isFreeAt(pt.getX(), pt.getY());
    }

    boolean isOnBoard(Point pt) {
        return pt.getX() >= 0 && pt.getX() < sizeX && pt.getY() >= 0 && pt.getY() < sizeY;
    }

    Stream<PointImpl> neighbours(Point pt) {
        return Arrays.stream(deltas)
                .map(d -> new PointImpl(pt.getX() + d[0], pt.getY() + d[1]))
                .filter(this::isOnBoard);
    }

    Stream<PointImpl> neighboursUnvisited(Point pt) {
        return neighbours(pt)
                .filter(this::isFreeAt);
    }

    boolean flood(List<Point> from, Point to, int value) {
        if (from.contains(to)) return true;

        List<Point> nextWave = from.stream().flatMap(this::neighboursUnvisited)
                .peek(p -> mark(p, value))
                .collect(toList());
//        printBoard();
        if (nextWave.isEmpty()) return false;
        return flood(nextWave, to, value + 1);
    }

    Point anyNeighborByValue(Point point, int value) {
        return neighbours(point)
                .filter(p -> at(p) == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("shouldn't be there"));
    }

    private Optional<List<Point>> backStep(Point p, List<Point> path) {
        path.add(p);
        if (at(p) == 1) return Optional.of(path);
        Point prev = anyNeighborByValue(p, at(p) - 1);
        return backStep(prev, path);
    }

    private Optional<List<Point>> backTrace(Point to) {
        return backStep(to, new ArrayList<>());
    }

    private void initBoard() {
        IntStream.range(0, sizeY).forEach(y ->
                IntStream.range(0, sizeX).forEach(x -> board[y][x] = 0));
    }

    public Optional<List<Point>> path(Point from, Point to, List<Point> obstacles) {
        initBoard();
        putObstacles(obstacles);
        boolean found = flood(Arrays.asList(from), to, 1);
        System.out.println("found" + found);
        if (!found) return Optional.empty();
        return backTrace(to);
    }
}
