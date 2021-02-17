package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageLoader {

    /**
     * Betölti a kiválasztott képet.
     * @param path A kép elérési útvonala és neve
     * @return ha sikerült betölteni a képet, akkor a képet adja vissza, különben NULL
     */

    public static BufferedImage loadImg(String path){
        try{
            return ImageIO.read(ImageLoader.class.getClassLoader().getResourceAsStream(path));
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Átmérezi a kért méretre a képet.
     * @param source  A kép elérési útvonala és neve
     * @param width A kép kért szélessége
     * @param height A kép kért magassága
     * @return Visszadja a megváltoztatott képet
     */

    public static BufferedImage scale(BufferedImage source, int width, int height){
        BufferedImage scaled=new BufferedImage(width,height, source.getType());
        Graphics g=scaled.getGraphics();
        g.drawImage(source, 0,0,width,height,null);
        g.dispose();
        return scaled;
    }

}
