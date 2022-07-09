package entities;

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
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;


    //player constructor playable here
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        LoadAnimation();
        initHitBox(x,y,(int)(20*Game.SCALE),(int)(27*Game.SCALE));


    }


    public void update() {
        updatePostion();
        //updateHitBox();
        updatetAnimationTick();
        setAnimation();
    }
    private void updatetAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0; // reset lai gia tri Tick de bat dau chuyen anh
            aniIndex++; // gia tri index de thay doi anh trong buffer animation
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;// khong bi lap lai don attack
            }
        }
    }
    public void renderPlayer(Graphics g, int lvlOffset) {
        g.drawImage(animation[playerAction][aniIndex],(int)(hitBox.x-xDrawOffset) - lvlOffset,  (int)(hitBox.y- yDrawOffset), width, height, null);//dat vi tri cho anh
        //drawHitBox(g);
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
            playerAction = ATTACK_1;
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
        //if (!left && !right && !inAir)
        //return;
        if (!inAir)
            if ((!left && !right) && (right && left))
                return;

        float xSpeed = 0;
        if (left) {
            xSpeed -= playerspeed;
        }
        if (right) {
            xSpeed += playerspeed;
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

    private void LoadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animation = new BufferedImage[9][6];
//        animation = new BufferedImage[4][8];

        //truyen tung anh
        for (int j = 0; j < animation.length; j++)// length cua row
            //length cua column
            for (int i = 0; i < Objects.requireNonNull(animation[j]).length; i++) {
                animation[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

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
}
