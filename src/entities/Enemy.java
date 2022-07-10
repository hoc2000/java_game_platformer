package entities;
import gamestates.Playing;
import main.Game;

import static utilz.Constant.EnemyConstants.*;
import static utilz.Helpmethod.*;
import static utilz.Constant.Directions.*;
public abstract class Enemy extends SuperEntity{
    private int aniIndex, enemyState;
    private int aniTick, aniSpeed = 25;
    private boolean firstUpdate = true;
    private  boolean inAir = false;
    private float fallSpeed;
    private  float gravity = 0.04f* Game.SCALE;
    private float walkSpeed = 0.06f*Game.SCALE;
    private int walkDir = LEFT;
    private float attackDistance = Game.TILES_SIZE;
    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitBox(x,y,width,height);
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick>=aniSpeed){
            aniTick =0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(enemyState)){
                aniIndex  = 0;
                if(enemyState==ATTACK){
                    enemyState = IDLE;
                }
            }
        }
    }
    public void update(int [][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
    }
    private void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate) {
            if (!isEntityOnFloor(hitBox, lvlData))
                inAir = true;
            firstUpdate = false;
        }

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
            }
        } else {
            switch (enemyState) {
                case IDLE -> newState(RUNNING);
                case RUNNING -> {
                    if (isPlayerInRange(player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player)) {
                            System.out.println("Attack");
                            newState(ATTACK);
                        }

                    }
                    float xSpeed = 0;
                    if (walkDir == LEFT)
                        xSpeed = -walkSpeed;
                    else
                        xSpeed = walkSpeed;
                    if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
                        if (IsFloor(hitBox, xSpeed, lvlData, walkDir)) {
                            hitBox.x += xSpeed;
                            return;
                        }
                    changeWalkDir();
                }
            }

    }

    }
    private boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDistance * 3;
    }
    private void turnTowardsPlayer(Player player) {
        if(player.hitBox.x >hitBox.x){
            walkDir = RIGHT;
        }
        else {
            walkDir = LEFT;
        }

    }
    private boolean isPlayerCloseForAttack(Player player){
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
            return absValue <= attackDistance;

    }
    private void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;

    }
    private void newState(int enemyState){
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }
    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
}
