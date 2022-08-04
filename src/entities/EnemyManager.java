package entities;

import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static utilz.Constant.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] snakeArr;
    private ArrayList<Enemy> Enemies = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        Enemies = LoadSave.getSnakes();
    }

    public void update(int [][] lvlData, Player player){
        for(Enemy n: Enemies){
            if(n.isActive()){
                n.update(lvlData, player);
            }
        }
    }
    public void draw(Graphics g, int x){
        drawSnakes(g,x);
    }

    private void drawSnakes(Graphics g, int x) {
        for(Enemy n: Enemies){
           if(n.isActive()){
//               n.drawHitBox(g,x);
               g.drawImage(snakeArr[n.getEnemyState()][n.getAniIndex()],
                       (int) n.getHitBox().x-x+ n.flipX(), (int) n.getHitBox().y-42, SNAKE_WIDTH *n.flipW(), SNAKE_HEIGHT, null);
//               n.drawAttackBox(g,x);
           }
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


    //check enemy hit
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Enemy s: Enemies){
           if(s.isActive()){
               if(attackBox.intersects(s.getHitBox())){
                   s.hurt();
               }
           }
        }
    }

    public void resetAllEnemies() {
        for(Enemy n : Enemies){
            n.resetEnemy();
        }
    }
}
