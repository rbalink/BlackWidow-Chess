//package com.chess.tests;
//
//import static junit.framework.Assert.assertEquals;
//
//import Rook;
//import com.chess.engine.classic.player.ai.RookStructureAnalyzer;
//import org.junit.Test;
//
//import Alliance;
//import Board;
//import Board.Builder;
//import King;
//
//public class TestRookStructure {
//
//    @Test
//    public void test1() {
//        final Board board = Board.createStandardBoard();
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
//    }
//
//    @Test
//    public void test2() {
//        final Builder builder = new Builder();
//        // Black Layout
//        builder.setPiece(new Rook(Alliance.BLACK, 0));
//        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
//        // White Layout
//        builder.setPiece(new Rook(Alliance.WHITE, 63));
//        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
//        builder.setMoveMaker(Alliance.WHITE);
//        // Set the current player
//        final Board board = builder.build();
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
//    }
//
//
//}