package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Board extends JPanel {
    private Tile[][] tiles; //A játékmezőn elhelyezkedő blokkok tömbje
    public Info infobox; //A játékmezőhöz tartozó infó sáv
    private int bombs; //A játékmezőn elhelyezendő bombák száma
    private Toplist toplist; //A játékhoz tartozó toplista

    private static int height = 20; //A játékmező blokkjainak száma vízszintesen
    private static int width = 20; //A játékmező blokkjainak száma függőlegesen

    private Random random; //Random érték generáló

    private boolean finish; //Vége van-e a játéknak
    private boolean dead; //Vereséggel zárult-e a játék
    private boolean firstClick; //Aktív-e az első tipp lehetősége
    private String name; //A játékos neve


    private BufferedImage bomb; // Bomba képe
    private BufferedImage flag; //Zászló képe
    private BufferedImage unknown; //A fel nem nyitott blokk képe
    private BufferedImage zero; // 0 értékű blokk képe
    private BufferedImage one; // 1 értékű blokk képe
    private BufferedImage two; // 2 értékű blokk képe
    private BufferedImage three; // 3 értékű blokk képe
    private BufferedImage four; // 4 értékű blokk képe
    private BufferedImage five; // 5 értékű blokk képe
    private BufferedImage six; // 6 értékű blokk képe
    private BufferedImage seven; // 7 értékű blokk képe
    private BufferedImage eight; // 8 értékű blokk képe

    /**
     * Létrehozza a játékmezőt, beállítja az alapértékeket és a megfelelő méretre állítja a képeket.
     * @param name A játékos neve.
     * @param i A hozzá tartozó infó sáv.
     * @param bombs A játékmezőn elhelyezendő bombák száma.
     * @param w A játékmező szélessége.
     * @param h A játékmező hosszúsága.
     */

    public Board(String name, Info i, int bombs, int w, int h) {
        width = w;
        height = h;
        tiles = new Tile[width][height];
        random = new Random();
        infobox = i;
        this.name=name;
        toplist = new Toplist();
        this.bombs = bombs;
        bomb = ImageLoader.scale(ImageLoader.loadImg("game/others/bomb.png"), Tile.getTileWidth(), Tile.getTileHeight());
        flag = ImageLoader.scale(ImageLoader.loadImg("game/others/flag.png"), Tile.getTileWidth(), Tile.getTileHeight());
        unknown = ImageLoader.scale(ImageLoader.loadImg("game/others/begin.png"), Tile.getTileWidth(), Tile.getTileHeight());
        zero = ImageLoader.scale(ImageLoader.loadImg("game/others/0.png"), Tile.getTileWidth(), Tile.getTileHeight());
        one = ImageLoader.scale(ImageLoader.loadImg("game/others/1.png"), Tile.getTileWidth(), Tile.getTileHeight());
        two = ImageLoader.scale(ImageLoader.loadImg("game/others/2.png"), Tile.getTileWidth(), Tile.getTileHeight());
        three = ImageLoader.scale(ImageLoader.loadImg("game/others/3.png"), Tile.getTileWidth(), Tile.getTileHeight());
        four = ImageLoader.scale(ImageLoader.loadImg("game/others/4.png"), Tile.getTileWidth(), Tile.getTileHeight());
        five = ImageLoader.scale(ImageLoader.loadImg("game/others/5.png"), Tile.getTileWidth(), Tile.getTileHeight());
        six = ImageLoader.scale(ImageLoader.loadImg("game/others/6.png"), Tile.getTileWidth(), Tile.getTileHeight());
        seven = ImageLoader.scale(ImageLoader.loadImg("game/others/7.png"), Tile.getTileWidth(), Tile.getTileHeight());
        eight = ImageLoader.scale(ImageLoader.loadImg("game/others/8.png"), Tile.getTileWidth(), Tile.getTileHeight());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new Tile(x, y, this);
                tiles[x][y].setUnknownImage(unknown);
                tiles[x][y].setFlagImage(flag);
            }
        }
        firstClick = true;
        setMaximumSize(new Dimension(600, 600));
        displayMines();

    }

    /**
     * Kirajzolja a játékmezőt a megfelelő értékek szerint.
     * Ha a játékos meghalt, leállítja a játékot, nem változtat az aktuális kirajzoláson.
     * Ha a játék befejeződött, értesíti erről a játékost és hozzáadja az eredményt a toplistához
     * @param g Grafikus elem
     */

    public void draw(Graphics g) {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y].draw(g);
            }
        }

        if (dead) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (!tiles[x][y].isFlag()) tiles[x][y].setOpened(true);
                    tiles[x][y].draw(g);
                    infobox.setSad(true);
                    infobox.setFinish(true);
                }
            }
        }

        if (finish) {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, 600, 600);
            g.setColor(Color.white);
            g.drawString("You won!", 280, 300);
            infobox.setFinish(true);
            addToToplist();
        }
        infobox.draw(g);
    }

    /**
     * Hozzáadja az eredményt a toplistához
     */

    public void addToToplist() {
        toplist.add(height, width, bombs, infobox.getMin(), infobox.getSec(), name);
    }

    /**
     * Visszadja a játékmező magasságát
     * @return A játékmező magassága
     */

    public static int getBoardHeight() { //STATIC
        return height;
    }

    /**
     * Visszadja a játékmező szélességét
     * @return A játékmező szélessége
     */

    public static int getBoardWidth() { //STATIC
        return width;
    }

    /**
     * Visszadja a játékmezőn elhelyezett bombák számát
     * @return A bombák száma
     */

    public int getBombs() {
        return bombs;
    }

    /**
     * Felfedi az adott koordinátájú blokkot, ha az nulla értékű,
     * addig fedi fel a többi körülötte lévő mezőket,
     * amíg azok nullánál nagyobb értéket nem adnak
     * @param x A blokk szélességi koorinátája
     * @param y A blokk magassági koordinátája
     */

    private void reveal(int x, int y) {
        tiles[x][y].setOpened(true);
        if (tiles[x][y].getCountedBombs() == 0) {
            if (x - 1 >= 0 && y - 1 >= 0 && tiles[x - 1][y - 1].canReveal()) reveal(x - 1, y - 1);
            if (x - 1 >= 0 && tiles[x - 1][y].canReveal()) reveal(x - 1, y);
            if (x - 1 >= 0 && y + 1 < height && tiles[x - 1][y + 1].canReveal()) reveal(x - 1, y + 1);

            if (y - 1 >= 0 && tiles[x][y - 1].canReveal()) reveal(x, y - 1);
            if (y + 1 < height && tiles[x][y + 1].canReveal()) reveal(x, y + 1);

            if (x + 1 < width && y - 1 >= 0 && tiles[x + 1][y - 1].canReveal()) reveal(x + 1, y - 1);
            if (x + 1 < width && tiles[x + 1][y].canReveal()) reveal(x + 1, y);
            if (x + 1 < width && y + 1 < height && tiles[x + 1][y + 1].canReveal()) reveal(x + 1, y + 1);
        }
    }

    /**
     * Elhelyez különböző véletlenszerű helyekre a pályán annyi bombát, amennyit a játékos kért.
     */

    public void generateMines() {
        for (int i = 0; i < bombs; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            while (tiles[x][y].isMine() || nearToFirstClick(x, y)) {
                x = random.nextInt(width);
                y = random.nextInt(height);
            }
            tiles[x][y].setMine(true);
        }
    }

    /**
     * Ellenőrzi, hogy az adott koordinátájú blokk az első tipp közelében van-e
     * @param x A blokk szélességi koordinátája
     * @param y A blokk magassági koordnátája
     * @return Igaz/hamis érték
     */

    private boolean nearToFirstClick(int x, int y) {
        if (x - 1 >= 0 && y - 1 >= 0 && tiles[x - 1][y - 1].isFirstClick()) return true;
        if (x - 1 >= 0 && tiles[x - 1][y].isFirstClick()) return true;
        if (x - 1 >= 0 && y + 1 < height && tiles[x - 1][y + 1].isFirstClick()) return true;

        if (y - 1 >= 0 && tiles[x][y - 1].isFirstClick()) return true;
        if (y + 1 < height && tiles[x][y + 1].isFirstClick()) return true;

        if (x + 1 < width && y - 1 >= 0 && tiles[x + 1][y - 1].isFirstClick()) return true;
        if (x + 1 < width && tiles[x + 1][y].isFirstClick()) return true;
        return x + 1 < width && y + 1 < height && tiles[x + 1][y + 1].isFirstClick();
    }

    /**
     * Beállítja a játékmező összes blokkjára a megfelelő felnyitási képet.
     * Ha bomba, akkor a bomba képét, különben a megfelelő számértékű képet.
     */

    public void displayMines() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j].isMine()) {
                    tiles[i][j].setOpenedImage(bomb);
                } else {
                    int count = tiles[i][j].getCountedBombs();
                    switch (count) {
                        case 1:
                            tiles[i][j].setOpenedImage(one);
                            break;
                        case 2:
                            tiles[i][j].setOpenedImage(two);
                            break;
                        case 3:
                            tiles[i][j].setOpenedImage(three);
                            break;
                        case 4:
                            tiles[i][j].setOpenedImage(four);
                            break;
                        case 5:
                            tiles[i][j].setOpenedImage(five);
                            break;
                        case 6:
                            tiles[i][j].setOpenedImage(six);
                            break;
                        case 7:
                            tiles[i][j].setOpenedImage(seven);
                            break;
                        case 8:
                            tiles[i][j].setOpenedImage(eight);
                            break;
                        default:
                            tiles[i][j].setOpenedImage(zero);
                            break;
                    }
                }
            }
        }
        repaint();
    }

    /**
     * Egyesével beállítja, hogy az egyes blokkokhoz milyen számérték tartozik, tehát hány bomba van a környezetükben.
     */

    public void countMines() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int count = 0;
                if (x - 1 >= 0 && y - 1 >= 0 && tiles[x - 1][y - 1].isMine()) count++;
                if (x - 1 >= 0 && tiles[x - 1][y].isMine()) count++;
                if (x - 1 >= 0 && y + 1 < height && tiles[x - 1][y + 1].isMine()) count++;

                if (y - 1 >= 0 && tiles[x][y - 1].isMine()) count++;
                if (y + 1 < height && tiles[x][y + 1].isMine()) count++;

                if (x + 1 < width && y - 1 >= 0 && tiles[x + 1][y - 1].isMine()) count++;
                if (x + 1 < width && tiles[x + 1][y].isMine()) count++;
                if (x + 1 < width && y + 1 < height && tiles[x + 1][y + 1].isMine()) count++;

                tiles[x][y].setCountedBombs(count);
            }
        }
    }

    /**
     * Az első tipp megtörtént, így elhelyezheti a bombákat, kioszthatja a blokkok számértékeit és újrarajzolhatja a pályát.
     * A játék folyamatban van, így ezt az igaz/hamis értéket is átállítja, illetve innentől kezdve, nem történhet több első tipp.
     */

    public void firstClick() {
        generateMines();
        countMines();
        displayMines();
        firstClick = false;
        infobox.setGameIsOn(true);
    }

    /**
     * A játékos a bal egérgombbal klikkelt egy blokkra, tehát fel akarja fedni azt.
     * Kiszámítja, hogy a kapott koordináták melyik blokkra mutatnak, majd ellenőrzi,
     * hogy az adott blokk bomba-e vagy meg van-e már zászlózva. Ha nem, akkor felnyitja.
     * Ha bomba, akkor a játékos meghalt és a játék befejeződött.
     * Ellenőrzi, hogy a játék befejeződött-e.
     * Ha a játékos nem a játékmezőre katttintott, ellenőrzi, hogy a smiley gombra kattintott-e.
     * Ha igen, akkor újrakezdi a játékot.
     * @param x A kijelzőn való kattintás szélességi koordinátája
     * @param y A kijelzőn való kattintás magassági koordinátája
     */

    public void clickedL(int x, int y) {
        int tileX = x / Tile.getTileWidth();
        int tileY = y / Tile.getTileHeight() -1;
        if (!finish && !dead) {

            if (tileY < height) {
                if (firstClick) {
                    tiles[tileX][tileY].setFirstClick(true);
                    firstClick();
                }

                if (!tiles[tileX][tileY].isFlag()) {

                    tiles[tileX][tileY].setOpened(true);

                    if (tiles[tileX][tileY].isMine()) {
                        dead = true;
                    } else {
                        if (tiles[tileX][tileY].getCountedBombs() == 0)
                            reveal(tileX, tileY);
                    }
                    checkFinish();
                }
            }

        }
        if (x >= 284 && x <= 318 && y >= 644 && y <= 677) {
            reset();
        }

    }

    /**
     * A játékos a jobb egérgombbal kattintott, tehát egy blokkot zászlózni vagy zászlótlanítani akar.
     * Kiszámítja, hogy a kapott koordináták melyik blokkra mutatnak,
     * majd ellenőrzi, hogy már zászlózva van-e. Ha igen, akkor leveszi róla a zászlót, ha pedig még nincs, akkor bezászlózza.
     * Végül ellenőrzi, hogy a játék befejeződött-e.
     * @param x A kijelzőn való kattintás szélességi koordinátája
     * @param y A kijelzőn való kattintás magassági koordinátája
     */

    public void clickedR(int x, int y) {
        if (!finish && !dead) {
            int tileX = x / Tile.getTileWidth();
            int tileY = y / Tile.getTileHeight() -1;
            if (tileY < height) {
                if (!tiles[tileX][tileY].isOpened()) {
                    if(!tiles[tileX][tileY].isFlag() && infobox.getFlags()!=0){
                        tiles[tileX][tileY].setFlag(!tiles[tileX][tileY].isFlag());
                        infobox.setFlags(-1);
                    }else{
                        if(tiles[tileX][tileY].isFlag() && infobox.getFlags()!=bombs){
                        tiles[tileX][tileY].setFlag(!tiles[tileX][tileY].isFlag());
                        infobox.setFlags(1);
                        }
                    }
                }
                checkFinish();
            }
        }
    }

    /**
     * Ellenőrzi, hogy a játék befejeződött-e, azaz minden bomba be van-e zászlózva
     * és minden nem számos mező fel lett-e nyitva.
     */

    private void checkFinish() {
        finish = true;
        outer:
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!(tiles[x][y].isOpened() || (tiles[x][y].isMine() && tiles[x][y].isFlag()))) {
                    finish = false;
                    break outer;
                }
            }
        }
    }

    /**
     * A játék újraindításához visszaállítja az alap beállításokat.
     */

    public void reset() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y].reset();
            }
        }
        dead = false;
        finish = false;
        firstClick = true;
        infobox.reset();
        displayMines();
    }

}
