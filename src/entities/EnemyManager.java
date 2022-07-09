package entities;

import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static utilz.Constant.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] snakeArr;
    private ArrayList<Snake> snakes = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        snakes = LoadSave.getSnakes();
        System.out.println(snakes.size());
    }

    public void update(){
        for(Snake n: snakes){
            n.update();
        }
    }
    public void draw(Graphics g, int x){
        drawSnakes(g,x);
    }

    private void drawSnakes(Graphics g, int x) {
        for(Snake n: snakes){
            g.drawImage(snakeArr[n.getEnemyState()][n.getAniIndex()],
                    (int) n.getHitBox().x-x, (int) n.getHitBox().y-25, SNAKE_WIDTH, SNAKE_HEIGHT, null);
        }
    }

    private void loadEnemyImgs() {
        snakeArr = new BufferedImage[5][10];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_SPRITES);
        for(int j=0; j<snakeArr.length;j++){
            for(int i = 0; i< Objects.requireNonNull(snakeArr)[j].length; i++){
                snakeArr[j][i] = temp.getSubimage(i*SNAKE_WIDTH_DEFAULT, j*SNAKE_HEIGHT_DEFAULT, SNAKE_WIDTH_DEFAULT, SNAKE_HEIGHT_DEFAULT);
            }
        }
    }
}
