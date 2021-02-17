package game;

import java.awt.image.BufferedImage;
import java.awt.*;

public class Info {
    private int flags; //A játékmezőn elhelyezhető zászlók száma
    private int bombs; //A játékmezőn elhelyezett bombák száma

    private BufferedImage smiley= ImageLoader.scale(ImageLoader.loadImg("game/others/smiley.png"), 40,40); //Boldog smiley képe
    private BufferedImage sadsmiley= ImageLoader.scale(ImageLoader.loadImg("game/others/smileysad.png"), 40,40); //Szomorú smiley képe
    private boolean sad; //Szomorú-e a smiley, azaz vesztett-e a játékos

    private int sec; //A játék idejének másodperc értéke
    private int min; //A játék idejének perc értéke

    private boolean gameIsOn; // A játék folyamatban van-e
    private boolean finish; //A játék befejeződött-e

    /**
     * Kirajzolja az info sávot, azaz a pályán elhelyezhető zászlók számának kijelzőjét, a játék újraindító gombját és az időmérő kijelzőjét.
     * @param g Grafikus elem
     */

    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(0,600,600,40);
        if(!sad){
            g.drawImage(smiley,280,600,null);
        }else g.drawImage(sadsmiley,280,600,null);

        g.setColor(Color.BLACK);
        g.fillRect(480,605,80,30);
        g.setColor(Color.RED);
        g.setFont(new Font("Tahoma",Font.BOLD,22));

        if(sec>=60){
            min+=sec/60;
            sec=sec-60;
        }
        if(sec<10&&min>9)g.drawString(Integer.toString(min)+":0" + Integer.toString(sec),490,628);
        if(sec<10&&min<10)g.drawString("0"+Integer.toString(min)+":0" + Integer.toString(sec),490,628);
        if(sec>9&&min<10)g.drawString("0"+Integer.toString(min)+":" + Integer.toString(sec),490,628);
        if(sec>9&&min>9)g.drawString(Integer.toString(min)+":" + Integer.toString(sec),490,628);

        g.setColor(Color.BLACK);
        g.fillRect(40,605,80,30);
        g.setColor(Color.RED);
        g.setFont(new Font("Tahoma",Font.BOLD,22));
        g.drawString(Integer.toString(flags),65,628);

    }

    /**
     * Létrehozza az infó sávot és beállítja az alap értékeket.
     * @param bombs A pályán elhelyezett bombák, azaz a kihelyezhető zászlók száma
     */

    public Info(int bombs) {
        sad=false;
        sec=0;
        finish=false;
        gameIsOn=false;
        flags=bombs;
        this.bombs=bombs;

    }

    /**
     * Beállítja a smiley (újraindító gomb) értékét, azaz hogy a boldog vagy a szomorú smileyt mutassa.
     * (A játék folyik, vagy meghalt a játékos.)
     * @param sad Igaz/Hamis érték
     */

    public void setSad(boolean sad) {
        this.sad = sad;
    }

    /**
     * Beállítja, hogy a játéknak vége van-e.
     * @param finish Igaz/hamis érték
     */

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    /**
     * Lekérdezi, hogy vége van-e a játéknak.
     * @return Igaz/hamis érték
     */

    public boolean isFinish() {
        return finish;
    }

    /**
     * Hozzáad még egy másodpercet a időmérőhöz.
     */

    public void plusOneSec(){
        sec++;
    }

    /**
     * Beállítja, hogy aktuálisan hány zászló használható még.
     * @param count A felhasználható zászlók száma
     */

    public void setFlags(int count){
            flags+=count;
    }

    /**
     * Visszaadja, hogy aktuálisan hány zászló használható még.
     * @return A felhasználható zászlók száma
     */

    public int getFlags(){
        return flags;
    }

    /**
     * Visszaállítja az alap értékeket a játék újraindításához.
     */

    public void reset(){
        finish=false;
        sad=false;
        sec=0;
        min=0;
        gameIsOn=false;
        flags=bombs;
    }

    /**
     * Visszaadja, hogy jelenleg zajlik-e a játék.
     * @return Igaz/hamis érték
     */

    public boolean isGameIsOn() {
        return gameIsOn;
    }

    /**
     * Beállítja, hogy a játék jelenleg zajlik-e.
     * @param gameIsOn Igaz/hamis érték
     */

    public void setGameIsOn(boolean gameIsOn) {
        this.gameIsOn = gameIsOn;
    }

    /**
     * Visszaadja, hogy mennyi a percek értéke.
     * @return Percek értéke
     */

    public int getMin() {
        return min;
    }

    /**
     * Visszaajd,a hogy mennyi a másodpercek értéke
     * @return Másodpercek érétke
     */

    public int getSec() {
        return sec;
    }
}
