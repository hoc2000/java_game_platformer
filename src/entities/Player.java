package entities;

import utilz.LoadSave;
import static utilz.Helpmethod.CanMoveHere;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static utilz.Constant.PlayerConstants.*;

@SuppressWarnings("FieldCanBeLocal")
public class Player extends SuperEntity {
    private BufferedImage[][] animation;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false; //idle
    private boolean attacking = false;
    private boolean left, up, right, down;
    private float playerspeed = 2.0f;
    private int[][] lvlData;


    //player constructor playable here
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        LoadAnimation();
    }


    public void update() {
        updatePostion();
        updateHitBox();
        updatetAnimationTick();
        setAnimation();
    }

    public void renderPlayer(Graphics g) {
        g.drawImage(animation[playerAction][aniIndex], (int) x, (int) y, width, height, null);//dat vi tri cho anh
        drawHitBox(g);
    }


    //cac update cho player
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
//chuyen đổi các animation khác nhau khoonng bị vấp khi reset aniTick
    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
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
        if (!left && !right && !up && !down)
            return;
        float xSpeed = 0, ySpeed = 0;
        if (left && !right) {
            xSpeed -= playerspeed;
        } else if (right && !left) {
            xSpeed += playerspeed;
        }
        if (up && !down) {
            ySpeed -= playerspeed;
        } else if (down && !up) {
            ySpeed += playerspeed;
        }
        if(CanMoveHere(x +xSpeed,y+ySpeed ,width,height,lvlData))
            this.x += xSpeed;
            this.y += ySpeed;
            moving =true;


    }

    private void LoadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animation = new BufferedImage[9][6];
        //truyen tung anh
        for (int j = 0; j < animation.length; j++)// length cua row
            //length cua column
            for (int i = 0; i < Objects.requireNonNull(animation[j]).length; i++) {
                animation[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

            }
    }

    public void loadlvlData(int[][] lvlData) {
        this.lvlData = lvlData;
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
}
