package test.java.tests;

import main.java.chess.engine.classic.Alliance;
import main.java.chess.engine.classic.board.*;
import main.java.chess.engine.classic.board.Board.Builder;
import main.java.chess.engine.classic.board.Move.MoveFactory;
import main.java.chess.engine.classic.pieces.*;
import main.java.chess.engine.classic.player.ai.BoardEvaluator;
import main.java.chess.engine.classic.player.ai.StandardBoardEvaluator;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.Assert;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestBoard {

    @Test
    public void initialBoard() {

        final Board board = Board.createStandardBoard();
        Assert.assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        Assert.assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
        Assert.assertFalse(board.currentPlayer().isInCheck());
        Assert.assertFalse(board.currentPlayer().isInCheckMate());
        Assert.assertFalse(board.currentPlayer().isCastled());
        Assert.assertTrue(board.currentPlayer().isKingSideCastleCapable());
        Assert.assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        Assert.assertEquals(board.currentPlayer(), board.whitePlayer());
        Assert.assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        Assert.assertFalse(board.currentPlayer().getOpponent().isInCheck());
        Assert.assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        Assert.assertFalse(board.currentPlayer().getOpponent().isCastled());
        Assert.assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
        Assert.assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
        Assert.assertTrue(board.whitePlayer().toString().equals("White"));
        Assert.assertTrue(board.blackPlayer().toString().equals("Black"));

        final Iterable<Piece> allPieces = board.getAllPieces();
        final Iterable<Move> allMoves = Iterables.concat(board.whitePlayer().getLegalMoves(), board.blackPlayer().getLegalMoves());
        for(final Move move : allMoves) {
            Assert.assertFalse(move.isAttack());
            Assert.assertFalse(move.isCastlingMove());
            Assert.assertEquals(MoveUtils.exchangeScore(move), 1);
        }

        Assert.assertEquals(Iterables.size(allMoves), 40);
        Assert.assertEquals(Iterables.size(allPieces), 32);
        Assert.assertFalse(BoardUtils.isEndGame(board));
        Assert.assertFalse(BoardUtils.isThreatenedBoardImmediate(board));
        Assert.assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);
        Assert.assertEquals(board.getPiece(35), null);
    }

    @Test
    public void testPlainKingMove() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();
        System.out.println(board);

        Assert.assertEquals(board.whitePlayer().getLegalMoves().size(), 6);
        Assert.assertEquals(board.blackPlayer().getLegalMoves().size(), 6);
        Assert.assertFalse(board.currentPlayer().isInCheck());
        Assert.assertFalse(board.currentPlayer().isInCheckMate());
        Assert.assertFalse(board.currentPlayer().getOpponent().isInCheck());
        Assert.assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        Assert.assertEquals(board.currentPlayer(), board.whitePlayer());
        Assert.assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        BoardEvaluator evaluator = StandardBoardEvaluator.get();
        System.out.println(evaluator.evaluate(board, 0));
        Assert.assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);

        final Move move = MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"),
                BoardUtils.INSTANCE.getCoordinateAtPosition("f1"));

        final MoveTransition moveTransition = board.currentPlayer()
                .makeMove(move);

        Assert.assertEquals(moveTransition.getTransitionMove(), move);
        Assert.assertEquals(moveTransition.getFromBoard(), board);
        Assert.assertEquals(moveTransition.getToBoard().currentPlayer(), moveTransition.getToBoard().blackPlayer());

        Assert.assertTrue(moveTransition.getMoveStatus().isDone());
        Assert.assertEquals(moveTransition.getToBoard().whitePlayer().getPlayerKing().getPiecePosition(), 61);
        System.out.println(moveTransition.getToBoard());

    }

    @Test
    public void testBoardConsistency() {
        final Board board = Board.createStandardBoard();
        Assert.assertEquals(board.currentPlayer(), board.whitePlayer());

        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h4")));
        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        final MoveTransition t13 = t12.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t14 = t13.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t13.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        Assert.assertTrue(t14.getToBoard().whitePlayer().getActivePieces().size() == calculatedActivesFor(t14.getToBoard(), Alliance.WHITE));
        Assert.assertTrue(t14.getToBoard().blackPlayer().getActivePieces().size() == calculatedActivesFor(t14.getToBoard(), Alliance.BLACK));

    }

    @Test(expected=RuntimeException.class)
    public void testInvalidBoard() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        builder.build();
    }

    @Test
    public void testAlgebreicNotation() {
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(0), "a8");
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(1), "b8");
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(2), "c8");
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(3), "d8");
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(4), "e8");
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(5), "f8");
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(6), "g8");
        Assert.assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(7), "h8");
    }

    @Test
    public void mem() {
        final Runtime runtime = Runtime.getRuntime();
        long start, end;
        runtime.gc();
        start = runtime.freeMemory();
        Board board = Board.createStandardBoard();
        end =  runtime.freeMemory();
        System.out.println("That took " + (start-end) + " bytes.");

    }
    private static int calculatedActivesFor(final Board board,
                                            final Alliance alliance) {
        int count = 0;
        for (final Piece p : board.getAllPieces()) {
            if (p.getPieceAllegiance().equals(alliance)) {
                count++;
            }
        }
        return count;
    }

}

