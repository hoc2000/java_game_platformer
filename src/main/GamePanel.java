package main;

import inputs.MouseInputs;
import inputs.keyBoardinput;

import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        MouseInputs mouseInput = new MouseInputs(this);
        setPanelSize();
        addKeyListener(new keyBoardinput(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size : " + GAME_WIDTH + " --" + GAME_HEIGHT);

    }

    public void updateGame() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);// tao hinh anh moi cho vao Panel
        game.render(g);
    }

    public Game getGame() {
        return game;
    }


}
