package main;

import entities.Player;
import level.LevelManager;

import java.awt.*;

//main
@SuppressWarnings("FieldCanBeLocal")
public class Game implements Runnable {
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    //tao player
    private Player player;
    private LevelManager levelManager;

    public final static int TILES_DEFAULT_SIZE =32;
    public final static float SCALE = 2.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE *SCALE); //kich thuoc 1 khoi nho(tiles)
    public final static int GAME_WIDTH = TILES_SIZE *TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE *TILES_IN_HEIGHT;


    public Game() {
        //khoi tao Player
        init_Classes();
        //tao panel
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        //chay loop
        startGameLoop();

    }
    //thanh phan can khoi tao class
    public void init_Classes(){
        levelManager = new LevelManager(this);
        player = new Player(100, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        player.loadlvlData(levelManager.getcurrentLevel().getLevelData());
        }

    private void startGameLoop() {
        Thread gameloopThread = new Thread(this);
        gameloopThread.start();//tao 1 thread moi va dong thoi run
    }
    public void update(){
        player.update();
        levelManager.update();
    }

    public void render(Graphics g){
        levelManager.draw(g);
        player.renderPlayer(g);
    }
    @Override
    public void run() {
//        while(true){
//        update();
//        gamePanel.repaint();}
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime =System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0 ;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1){
                update(); //capnhat
                updates++;
                deltaU --;
            }
            if(deltaF>=1){
                gamePanel.repaint();//xuatDoHoa
                frames++;
                deltaF --;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames+" | UPS: " +updates);
                frames = 0;
                updates = 0;
            }
        }

    }
    public Player getPlayer(){
        return player;
    }

    public void widowFocusLost(){
        player.StopWhenLostFocusPos();
    }
}
