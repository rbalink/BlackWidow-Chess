package main.java.chess.engine.classic.player.ai;

import main.java.chess.engine.classic.board.Board;
import main.java.chess.engine.classic.board.Move;

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    Move execute(Board board);

}
