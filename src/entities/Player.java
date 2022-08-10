package entities;

import gamestates.Playing;
import main.Game;
import utilz.*;
import utilz.Helpmethod.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static utilz.Constant.*;
import static utilz.Constant.PlayerConstants.*;
import static utilz.Helpmethod.*;
import static utilz.Helpmethod.isEntityOnFloor;

@SuppressWarnings("FieldCanBeLocal")
public class Player extends SuperEntity {

    private BufferedImage[][] animation;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false; //idle
    private boolean attacking = false;
    private boolean left, up, right, down, jump;
    private float playerspeed = 1.0f * Game.SCALE;
    private int[][] lvlData;
    private float xDrawOffset = 28 * Game.SCALE; // player to corner off set
    private float yDrawOffset = 28 * Game.SCALE;
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -3f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private int statusBarX = (int) (20 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);
    private int maxHealth = 3; // 3 hearts
    private int currentHealth = maxHealth;
    private int checkDamage = 3; // the damage of snake's bite
    private int heartWidth = (int) (30 * Game.SCALE);
    private int heartHeight = (int) (30 * Game.SCALE);

    //attack box
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipW = 1;

    private  boolean attackCheck = false;
    private Playing playing;
    //player constructor playable here
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing =playing;
        LoadAnimation();
        initHitBox(x,y,(int)(22*Game.SCALE),(int)(30*Game.SCALE));
        initAttackBox();

    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(20*Game.SCALE), (int)(20*Game.SCALE));
    }


    public void update() {
        if(currentHealth<=0){
            playing.setGameOver(true);
            return ;
        }
        updatePostion();
        updateAttackBox();
//        updateHitBox();
        if(attacking){
            checkAttack();
        }
        updatetAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if(attackCheck || aniIndex!=1){
            return ;
        }
        attackCheck = true;
        // check when enemy hit player
        playing.checkEnemyHit(attackBox);
    }


    private void updateAttackBox() {
        if(right){
            attackBox.x = hitBox.x + hitBox.width + (int)(Game.SCALE*10);
        }else if(left){
            attackBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE*10);
        }
        attackBox.y = hitBox.y + (int)(Game.SCALE*10);
    }

    private void updatetAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0; // reset lai gia tri Tick de bat dau chuyen anh
            aniIndex++; // gia tri index de thay doi anh trong buffer animation
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;// khong bi lap lai don attack
                attackCheck=false;
            }
        }
    }
    public void renderPlayer(Graphics g, int lvlOffset) {
        g.drawImage(animation[playerAction][aniIndex],(int)(hitBox.x-xDrawOffset) - lvlOffset + flipX,  (int)(hitBox.y- yDrawOffset), width*flipW, height, null);//dat vi tri cho anh
//        drawHitBox(g, lvlOffset);
//        drawAttackBox(g,lvlOffset);
        drawHealthBar(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x-lvlOffset,(int) attackBox.y,(int) attackBox.width, (int)attackBox.height);
    }

    private void drawHealthBar(Graphics g) {
        BufferedImage img   =  LoadSave.GetSpriteAtlas(LoadSave.HEART_HEALTH);
        for(int i=0; i< currentHealth; i++){
            g.drawImage(img, statusBarX*i, statusBarY, heartWidth, heartHeight, null);
        }
    }


    //cac update cho player

    //chuyen đổi các animation khác nhau khoonng bị vấp khi reset aniTick
    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
        if (inAir){
            if (airSpeed < 0) playerAction = JUMP;
            else playerAction = FALLING;

        }

        if (attacking) {
            playerAction = ATTACK;
        }
        //doi animation
        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePostion() {
        moving = false;
        if (jump)
            jump();
        if (!inAir)
            if ((!left && !right) && (right && left))
                return;

        float xSpeed = 0;
        if (left) {
            xSpeed -= playerspeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerspeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir){
            if (!isEntityOnFloor(hitBox,lvlData)){
                inAir = true;
            }
        }

        if (inAir){
            if (CanMoveHere(hitBox.x,hitBox.y + airSpeed, hitBox.width,hitBox.height,lvlData)){
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox,airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);

            }

        } else{
            updateXPos(xSpeed);
        }
        moving = true;

    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x+xSpeed,hitBox.y, hitBox.width, hitBox.height, lvlData)){
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(hitBox,xSpeed);
        }
    }

    // change health when attack
    public void changeHealth(){
        checkDamage--;
        if(checkDamage<=0){
            currentHealth --;
            checkDamage=3; // snake hit 3 times -> player will lose 1 heart
        }
        else if(currentHealth>= maxHealth){
                currentHealth = maxHealth;
            }
    }
    private void LoadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animation = new BufferedImage[16][8];

        //truyen tung anh
        for (int j = 0; j < animation.length; j++)// length cua row
            //length cua column
            for (int i = 0; i < Objects.requireNonNull(animation[j]).length; i++) {
                animation[j][i] = img.getSubimage(39*i, j*32, 40, 32);

            }
    }

    public void loadlvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!isEntityOnFloor(hitBox, lvlData))
            inAir = true;
    }

    public void StopWhenLostFocusPos() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    public void setJump (boolean jump){
        this.jump = jump;
    }
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public void resetAll() {
        resetDirBooleans();
        inAir= false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth  = maxHealth;

        //reset hitbox
        hitBox.x = x;
        hitBox.y = y;

        if(!isEntityOnFloor(hitBox, lvlData)){
            inAir = true;
        }
    }
}
