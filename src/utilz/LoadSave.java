package utilz;

import com.sun.security.jgss.GSSUtil;
import entities.Snake;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import static utilz.Constant.EnemyConstants.*;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    //public static final String LEVEL_ONE_STRUCTURE = "level_one_data.png";
    public static final String LEVEL_ONE_STRUCTURE = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background.jpg";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String PLAYING_BG_IMG = "playing.jpg";
//    public static final String BIG_CLOUDS ="big_clouds.png";
    public static final String ENEMY_SPRITES ="snake.png";
    public static ArrayList<Snake> getSnakes(){
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_STRUCTURE);
        ArrayList<Snake> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == SNAKE) {
                    //lay trong khaong ở bound của red(khoảng 45)
                    list.add(new Snake(i*Game.TILES_SIZE, j*Game.TILES_SIZE, SNAKE_WIDTH, SNAKE_HEIGHT));}

            }

        }
        return list;

    }
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] GetLevelData() {

        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_STRUCTURE);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        //pixel default height will be 32\
        //đọc màu từ ảnh level one data gán giá trị dạng bit cho từng "pixel"
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48) {
                    //lay trong khaong ở bound của red(khoảng 45)
                    value = 0;
                }
                System.out.printf("%5d", value);

                lvlData[j][i] = value;
            }
            System.out.println("");
        }
        return lvlData;
    }
}
