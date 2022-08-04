package entities;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

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
    private Rectangle2D.Float attackBox;
    protected int maxHealth=5;
    protected int currentHealth;
    private boolean active = true;
    private boolean attackCheck = false;
    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitBox(x,y,(int)(32* Game.SCALE),(int)(19*Game.SCALE));
        initAttackBox();
        currentHealth  = maxHealth;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float((int)x,(int)y, (int)(10* Game.SCALE), (int)(10*Game.SCALE));
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
                else if(enemyState==DEAD){
                    active = false;
                }
            }
        }
    }
    public void update(int [][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if(walkDir==RIGHT){
            attackBox.x = hitBox.x + hitBox.width + (int)(Game.SCALE);
        }else if(walkDir==LEFT){
            attackBox.x = hitBox.x+ (int)(Game.SCALE);
        }
        attackBox.y = hitBox.y + (int)(Game.SCALE*10);
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
                case ATTACK -> {
                    if(aniIndex==0){
                        attackCheck = false;
                    }
                    if(!attackCheck && aniIndex ==6){
                        checkEnemyHit(player);
                    }
                    break;
                }
            }

    }


    }
    public void resetEnemy(){
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }

    private void checkEnemyHit( Player player) {
            player.changeHealth();
            attackCheck= true;
    }

    private boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDistance * 2;
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
            return absValue <= attackDistance-20;

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
    public int flipX(){
        if(walkDir==RIGHT){
            return width;
        }else return 0;
    }
    public int flipW(){
        if(walkDir==RIGHT)
            return -1;
        else return 1;
    }
    public void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x -xLvlOffset, (int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
    }
    public void hurt(){
        currentHealth = 0;
        newState(DEAD);
    }
    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
    public boolean isActive(){
        return  active;
    }
}
