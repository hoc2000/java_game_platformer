package level;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;


public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level Level1;


    public LevelManager(Game game) {
        this.game = game;
//      levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutSideSprites();
        Level1 = new Level(LoadSave.GetLevelData());
    }
    //chia anh thanh cac khoi co kich thuoc 32x32
    //hang ngang sẽ có 12 tiles
    //hàng dọc sẽ có 4 tiles
    private void importOutSideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }

    }

    public void draw(Graphics g) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = Level1.getSpriteindex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
    }

    public void update() {

    }

    public Level getcurrentLevel(){
        return Level1;
    }
}
