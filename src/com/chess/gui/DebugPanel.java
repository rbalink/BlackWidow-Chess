package com.chess.gui;

import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.ai.AlphaBetaWithMoveOrdering;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;

class DebugPanel extends JPanel implements Observer {

    private static final Dimension CHAT_PANEL_DIMENSION = new Dimension(600, 150);

    public DebugPanel() {
        super(new BorderLayout());
        setPreferredSize(CHAT_PANEL_DIMENSION);
        validate();
        setVisible(true);
    }

    public void redo() {
        validate();
    }

    @Override
    public void update(final Observable obs,
                       final Object obj) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                add(new JTextArea("paul's an idiot"));
                invalidate();
                validate();
                repaint();
            }
        });
    }

}