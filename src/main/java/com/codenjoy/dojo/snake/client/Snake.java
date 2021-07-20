package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import scala.collection.parallel.ParIterableLike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Snake {
    private Point apple;
    private Point snake_head;
    private Board board;
    private List<Point> possibleWays;
    private List<Point> obstacles = new ArrayList<>();

    public Snake(Board board) {
        this.board = board;
        apple = board.getApples().get(0);
        snake_head = board.getHead();

        obstacles.addAll(board.getSnake());
        obstacles.add(board.getStones().get(0));

        possibleWays = new ArrayList<>();
    }

    boolean isObstacles(Point p) {
        for (Point point : obstacles) {
            if (point.getX() == p.getX() && point.getY() == p.getY()) {
                return true;
            }
        }
        return false;
    }

    Direction convert1(Point p, List<Direction> directions) {
        try{
            Direction random = Direction.random();
            if (!isObstacles(p) && !directions.contains(random)) return random;
            directions.add(random);
            return convert1(p, directions);
        } catch (Exception e) {
            return Direction.LEFT;
        }
    }

    Direction setDirection(Point nextPoint, Point head){
        if      (nextPoint.getX() < head.getX()) return Direction.LEFT;
        else if (nextPoint.getX() > head.getX()) return Direction.RIGHT;
        else if (nextPoint.getY() < head.getY()) return Direction.DOWN;
        else                           return Direction.UP;
    }

    Direction nextDirection(Point nextPoint, Point head) {
        if (isObstacles(nextPoint)) return convert1(nextPoint, new ArrayList<>());
        return setDirection(nextPoint, head);
//       return convert(nextPoint, head, apple);
    }


    public Direction solve() {

        Lee lee = new Lee();
        Optional<List<Point>> pathO = lee.path(snake_head, apple, obstacles);

        if (pathO.isPresent()){
            possibleWays = pathO.get();
        }

        System.out.println("FOUND PATH:" + possibleWays);
        System.out.println("obstacles PATH:" + obstacles);
        Point nextPoint = possibleWays.remove(possibleWays.size() - 1);
        System.out.println("nextPoint: "+ nextPoint.toString());
        return nextDirection(nextPoint, snake_head);
    }

}
