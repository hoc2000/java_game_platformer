package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import level.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;


public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private GameOverOverlay gameWinOverlay;

    private boolean paused = false;

    private int xLvlOffset; // add distance to draw the view when player move
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH); // the line which player is beyond
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

    private BufferedImage backgroundImg;
    private boolean gameOver;
    private boolean gameWin;

    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
    }

    public void resetAll(){
        // reset player, enemies
        gameOver  = false;
        gameWin = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAllEnemies();

    }
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
    public void setGameWin(boolean gameWin){
        this.gameWin = gameWin;
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (64 * Game.SCALE), this);
        player.loadlvlData(levelManager.getcurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this, "Game over");
        gameWinOverlay = new GameOverOverlay(this, "Congratulations you won the game!!!");

    }

    @Override
    public void update() {
        if (!paused && !gameOver &&!gameWin) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getcurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;

    }


    // draw
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT,null);

        levelManager.draw(g, xLvlOffset);
        player.renderPlayer(g,xLvlOffset);
        enemyManager.draw(g,xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }else if(gameOver){
            gameOverOverlay.draw(g);
        }else if(gameWin) gameWinOverlay.draw(g);
    }


    // set player attack
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver|| gameWin) return;
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttack(true);
    }


    // set player mode
    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else if(gameWin) gameWinOverlay.keyPressed(e);
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_K:
                    player.setAttack(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver && !gameWin){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }

    }

    // kéo thả chuột
    public void mouseDragged(MouseEvent e) {
        if (paused && !gameOver && !gameWin)
            pauseOverlay.mouseDragged(e);
    }

    // giữ chuột
    @Override
    public void mousePressed(MouseEvent e) {
        if (paused&& !gameOver && !gameWin)
            pauseOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused&& !gameOver && !gameWin)
            pauseOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused&& !gameOver && !gameWin)
            pauseOverlay.mouseMoved(e);

    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.StopWhenLostFocusPos();
    }

    public Player getPlayer() {
        return player;
    }

}

