package main.java.chess.pgn;

import main.java.chess.engine.classic.board.Board;
import main.java.chess.engine.classic.board.Move;
import main.java.chess.engine.classic.player.Player;

public interface PGNPersistence {

    void persistGame(Game game);

    Move getNextBestMove(Board board, Player player, String gameText);

}
