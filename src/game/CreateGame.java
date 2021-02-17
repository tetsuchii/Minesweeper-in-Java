package game;

import game.Menu;
import game.Timer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGame extends JPanel implements ActionListener {
    private JTextField name,error; //A név és az error szövegmezője
    private JComboBox size,bombs; //A méret és a bombák számának legördülő menüje
    private JButton btn; // Start gomb
    private int boardSize,bombsOnBoard; //A páyla mérete, illetve a bombák száma
    private GameFrame gf; //A játék kijelzője
    private Menu menu; //A pályaszerkesztőt tartalmazó kijelző
    private JLabel l0,l1,l2; //Feliratok a név,pálya méret és bomba szám részére

    private boolean custom; //Megadja, hogy egyedi-e a pálya

    /**
     * Létrehozza a játékszerkesztő felületet.
     * @param menu Megadja melyik menühöz tartozik
     */

    public CreateGame(Menu menu){
        custom=false;
        this.menu=menu;
        l0=new JLabel("Name:");
        add(l0);
        name=new JTextField(10);
        add(name);

        l1=new JLabel("Size:");
        l1.setVisible(false);
        add(l1);
        Object[] sizeOp =new Object[]{ 5,10,12,15,20 };
        size=new JComboBox(sizeOp);
        size.setVisible(false);
        add(size);
        l2=new JLabel("Bombs:");
        l2.setVisible(false);
        add(l2);
        Object[] bombsOp =new Object[100];
        for(int i=0;i<100;i++) bombsOp[i]=i+1;
        bombs=new JComboBox(bombsOp);
        bombs.setVisible(false);
        add(bombs);

        btn=new JButton();
        btn.setText("Start!");
        btn.addActionListener(this);
        add(btn);

        error=new JTextField(50);
        error.setEditable(false);
        error.setVisible(true);
        add(error);

        setVisible(false);
    }

    /**
     * Elindítja a játék kijelzőjét és az időszámlálót, de előtte ellenőrzi a megadott értékeket.
     * Ha nem megfelelőek, hibaüzenetet ír ki.
     * @param e Event érték
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        Boolean canGameRun=true;
        if(custom) {
            boardSize = (int) size.getSelectedItem();
            bombsOnBoard = (int) bombs.getSelectedItem();
            if(boardSize*boardSize/2-9<bombsOnBoard){
                canGameRun=false;
                error.setText("The number of bombs can't be higher than the half of the size of board");
            }
        }
        if(canGameRun) {
            gf = new GameFrame(name.getText(), bombsOnBoard, boardSize);
            game.Timer timer = new Timer(gf, menu);
            menu.setVisible(false);
        }

    }

    /**
     * Beállítja a könnyű játékmód értékeit.
     */

    public void setEasy(){
        boardSize=10;
        bombsOnBoard=10;
    }

    /**
     * Beállítja a közepes játékmód értékeit.
     */

    public void setMedium(){
        boardSize=15;
        bombsOnBoard=40;
    }

    /**
     * Beállítja a nehéz játékmód értékeit.
     */

    public void setHard(){
        boardSize=20;
        bombsOnBoard=80;
    }

    /**
     * Beállítja a szabadon beállítható játékmódhoz kapcsolódó szerkesztők láthatóságát.
     */

    public void setCustom(){
        l1.setVisible(true);
        size.setVisible(true);
        l2.setVisible(true);
        bombs.setVisible(true);
        custom=true;
    }
}
