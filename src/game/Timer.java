package game;

import java.awt.event.ActionListener;

public class Timer {
    private javax.swing.Timer timer; //Időmérő
    private int delay=1000; //A késleltetés ideje, egy másodperc
    private GameFrame gf; //Az időmérőhöz tartozó játék kijelző
    private Info infobox; //Az időmérőhöz tartozó infó sáv
    private Menu menu; //Az időmérőhöz tartozó menü

    /**
     * Másodpercenként ellenőrzi a feltételeket, mint hogy a játék-nak vége lett-e már, illetve melyik kijelző aktív jelenleg.
     * A két kijelző küzöl mindig csak az egyik lehet aktív.
     */

    ActionListener taskPerformer = evt -> {

        if(infobox.isGameIsOn()&&!infobox.isFinish()) {
            infobox.plusOneSec();
            gf.repaint();
        }
        if(infobox.isFinish()){
            infobox.setGameIsOn(false);
        }
        if(gf.isVisible()){
            menu.setVisible(false);
        }
        if(menu.isVisible()){
            gf.setVisible(false);
        }

    };

    /**
     * Elindítja a timert, ami másodpercenként élesedik.
     * @param gf A játék kijelzőja, amihez tartozik és változtatásokat eszközöl.
     * @param menu A menü kijelzője, amit szintén meg tud jelenítnei.
     */

    public Timer(GameFrame gf, Menu menu) {
        timer=new javax.swing.Timer(delay,taskPerformer);
        timer.start();
        this.gf=gf;
        this.infobox=(gf.getInfobox());
        this.menu=menu;
    }
}
