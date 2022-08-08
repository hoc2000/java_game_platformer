package utilz;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import static utilz.Constant.Directions.*;
public class Helpmethod {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    return !IsSolid(x, y + height, lvlData);
        }
        return false;


    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return false;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return false;
        }
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        int value = lvlData[(int) yIndex][(int) xIndex];
        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return  tileXPos + xOffset -1;

        } else {
            return currentTile * Game.TILES_SIZE;

        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            //Falling
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData){
        //Check the pixel below bottomleft and bottomright.
        if (!IsSolid(hitBox.x, hitBox.y  + hitBox.height,lvlData ))
            if (!IsSolid(hitBox.x + hitBox.width, hitBox.y  + hitBox.height + 1,lvlData ))
                return false;
        return true;
    }
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData, int type) {
        if(type==LEFT)
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        return IsSolid(hitbox.x + xSpeed+ hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
    }

}
