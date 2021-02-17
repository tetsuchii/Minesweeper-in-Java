package game;

import game.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile  {
    private int x,y; //A blokk szélességi és magassági koordinátája
    private int countedBombs; //A blokkhoz tartozó számérték, azaz hány bomba van a közelében
    private Board board; //A blokkhoz tartozó játékmező

    private boolean isMine; //Megadja, hogy az adott blokk bomba-e
    private boolean opened; //Megadja, hogy az adott blokk fel van-e már nyitva
    private boolean flag; //Megadja, hogy az adott blokk be van-e zászlózva
    private boolean firstClick; //Megadja, hogy az adott blokk volt-e az első tipp

    private static int height=600/Board.getBoardHeight(); //Az adott blokk magassága
    private static int width=600/Board.getBoardWidth(); //Az adott blokk szélessége

    private BufferedImage unknownImage; //Az adott blokk fel nem fedett képe
    private BufferedImage openedImage; //Az adott blokk felfedett képe
    private BufferedImage flagImage; //Az adott blokk zászlós képe

    /**
     * Létrehoz egy új blokkot a táblán és az alap értékeit beállítja, mint hogy még nincs felnyitva,
     * nem tartozik hozzá bomba, nincs megjelölve zászlóként, még nem történt meg az első tipp
     * és még nem tudni hány bomba van a környezetében.
     * @param x A blokk x koordinátája a táblán.
     * @param y A blokk y koordinátája a pályán.
     * @param board A tábla,amelyikhez tartozik.
     */

    public Tile(int x, int y,Board board) {
        height = 600/Board.getBoardHeight();
        width = 600/Board.getBoardWidth();
        this.x=x;
        this.y=y;
        this.board=board;
        this.isMine=false;
        this.opened=false;
        this.flag=false;
        this.firstClick=false;
        this.countedBombs=0;
    }

    /**
     * Kirajzolja a blokkot a megadott értékei szerint, mint fel van-e nyitva vagy meg van-e zászlózva.
     * @param g Grafikus elem.
     */

    public void draw(Graphics g){

        if(!opened){
            if(!flag) g.drawImage(unknownImage,x*width,y*height,null);
            else g.drawImage(flagImage,x*width,y*height,null);
        }else
            g.drawImage(openedImage,x*width,y*height,null);
    }

    /**
     * Az adott mező "bombaságát" állítja.(Igen/Nem)
     * @param bomb Bomba-e az adott blokk.
     */

    public void setMine(boolean bomb){
        this.isMine=bomb;
    }

    /**
     * Visszaadja, hogy az adott blok bomba-e.
     * @return Igen, vagy nem.
     */

    public boolean isMine(){
        return isMine;
    }

    /**
     * Beállítja, hogy a blokk meg van-e zászlózva.
     * @param flag Igaz vagy hamis érték.
     */

    public void setFlag(boolean flag){
        this.flag=flag;
    }

    /**
     * Visszaadja, hogy az adott blokk zászlózva van-e.
     * @return Igaz vagy hamis érték.
     */

    public boolean isFlag(){
        return flag;
    }

    /**
     * Beállítja a felnyitott képét az adott blokknak,azaz azt a számértéket mutató képet, ahány bomba van a környezetében.
     * @param image A beállytandó kép.
     */

    public void setOpenedImage(BufferedImage image){
        this.openedImage=image;
    }

    /**
     *  Beállítja a még fel nem nyitott képet az adott blokkhoz.
     * @param image A fel nem nyitott kép.
     */

    public void setUnknownImage(BufferedImage image){
        this.unknownImage=image;
    }

    /**
     * Beállítja a zászlózot képet az adott blokkhoz.
     * @param image A zászlózott kép.
     */

    public void setFlagImage(BufferedImage image){
        this.flagImage=image;
    }

    /**
     * Beállítja, hogy az adott blog fel van-e nyitva.
     * @param opened Igaz/hamis érték
     */

    public void setOpened(boolean opened){
        this.opened=opened;
    }

    /**
     * Visszaadja, hogy az adott blokk fel van-e már nyitva.
     * @return Igaz/hamis érték
     */

    public boolean isOpened(){
        return opened;
    }

    /**
     * Visszadja, hogy az adott blokk felnyitható-e, azaz nincs-e már felnyitva és nem bomba-e.
     * @return Igaz/Hamis érték
     */

    public boolean canReveal(){
        return !opened&&!isMine&&countedBombs>=0;
    }

    /**
     *  Visszaadja a blokk szélességét.
     * @return Szélesség értéke.
     */

    public static int getTileWidth(){ //STATIC
        return width;
    }

    /**
     * Visszaadja, a blokk magasságát.
     * @return Magasság értéke.
     */

    public static int getTileHeight(){
        return height;
    }

    /**
     * Beállítja az adott blokk körülötti bombák számát.
     * @param number Blokk körülötti bombák száma.
     */

    public void setCountedBombs(int number){
        this.countedBombs=number;
    }

    /**
     * Visszaadja az adott blokk körülött lévő bombák számát.
     * @return A blokk örülötti bombák száma.
     */

    public int getCountedBombs(){
        return countedBombs;
    }

    /**
     * Visszaadja, hogy az adott blokk első tipp volt-e.
     * @return Igaz/Hamis érték
     */

    public boolean isFirstClick(){
        return firstClick;
    }

    /**
     *  Beállítja, hogy az adott blokk első tipp volt-e.
     * @param fc Igaz/hamis érték
     */

    public void setFirstClick(boolean fc){
        this.firstClick=fc;
    }

    /**
     * Visszaállítja a blokk értékeit kezdési értékre, hogy az új játék indítható legyen.
     */

    public void reset(){
        flag=false;
        isMine=false;
        opened=false;
    }
}
