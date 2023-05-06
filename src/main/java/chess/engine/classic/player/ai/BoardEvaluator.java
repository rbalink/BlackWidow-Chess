package main.java.chess.engine.classic.player.ai;

import main.java.chess.engine.classic.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
