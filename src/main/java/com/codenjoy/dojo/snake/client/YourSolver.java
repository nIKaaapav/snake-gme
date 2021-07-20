package com.codenjoy.dojo.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Direction;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    private static String adress = "http://167.99.241.128/codenjoy-contest/board/player/4axeud2c1gedo320mbc7?code=5298992005158318235";

    @Override
    public String get(Board board) {
        if (board.isGameOver()) return Direction.UP.toString();
        Snake s = new Snake(board);
        System.out.println(board.toString());
        Direction solved = s.solve();
        return solved.toString();
    }

    public static void main(String[] args) {
        Board board = new Board();
        WebSocketRunner.runClient(
                adress,
                new YourSolver(), board );
    }

}
