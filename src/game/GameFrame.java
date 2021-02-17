package game;

import game.Board;
import game.Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame implements MouseListener {
    private static int height=600; // A játék frame magassága
    private static int width=600; // A játék frame szélessége
    private Screen screen; // A játék frame kijelzője
    private Board board; //A játék frame játékmezője
    private int insetLeft; //A baloldali betét a frame-en
    private int insetTop; //A felső betét a frame-en
    private Info infobox; //A játék frame infó sávja

    /**
     * Létrehozza a játék kijelzőjét és annak részeit, mint a játékmezőt és az infó sávot.
     * @param name A játékos neve.
     * @param bombs A játékmezőn elhelyezett bombák száma.
     * @param size A játékmező mérete.
     */

    public GameFrame(String name,int bombs,int size){
        super("Minesweeper");

        infobox=new Info(bombs);
        board=new Board(name,infobox,bombs, size,size);

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(this);

        screen=new Screen();
        add(screen);

        pack();
        insetLeft=getInsets().left;
        insetTop=getInsets().top;
        setSize(width+getInsets().right+getInsets().left,height+getInsets().top+getInsets().bottom+40);
        setVisible(true);
    }

    /**
     * Visszadja a játékmező magasságát.
     * @return A játékmező magassága.
     */

    public static int getScreenHeight(){
        return height;
    }

    /**
     * Visszaadja a játékmező szélességét.
     * @return A játékmező szélessége.
     */

    public static int getScreenWidth(){
        return width;
    }

    /**
     * A játékmező képernyője, amin a kirajzolások megjelennek.
     */

    public class Screen extends JPanel{

        public void paintComponent(Graphics g){
            board.draw(g);
        }
    }

    /**
     * Visszaadja az infó sávot.
     * @return Infó sáv
     */

    public Info getInfobox() {
        return infobox;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Ellenőrzi, hogy melyik egérgomb lett megnyomva és az alapján hívja meg a Board értelmező függvényeit.
     * @param e Event érték
     */

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1)board.clickedL(e.getX() - insetLeft, e.getY() - insetTop+40);
        if(e.getButton() == 3)board.clickedR(e.getX() - insetLeft, e.getY() - insetTop+40);
        screen.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
