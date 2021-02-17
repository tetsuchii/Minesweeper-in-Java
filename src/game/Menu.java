package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JFrame implements ActionListener {
    private ImageIcon background; //A menü háttérképe
    private JMenuBar menuBar; //A menü menübar-ja
    private JMenu game,toplistmenu; //A menü felső gombja, melyek a játék indítására, illetve a toplista megtekintésére szolgálnak
    private JMenuItem easy,medium,hard,custom, watch; //A különböző játéktípusok megadására, illetve a toplista megtekintésére menitem-ek
    private CreateGame createGame; //A menü pályaszerkesztője
    private Toplist tl; //A jáék toplistája

    /**
     * Létrehozza a menü frame-jét, elhelyezi a menubar elemeit és beállítja a játék háttérképét.
     */

    public Menu(){
        super("Minesweeper");
        setSize(new Dimension(600+getInsets().right+getInsets().left,600+getInsets().top+getInsets().bottom+40));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        try{
            background=new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("game/others/background.png")));
        }catch (IOException e){
            e.printStackTrace();
        }

        JLabel backgroundPanel = new JLabel();
        backgroundPanel.setIcon(background);
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel,BoxLayout.Y_AXIS));
        add(backgroundPanel);

        createGame=new CreateGame(this);
        backgroundPanel.add(createGame);

        menuBar=new JMenuBar();
        this.setJMenuBar(menuBar);

        game=new JMenu("Game");
        menuBar.add(game);
        toplistmenu=new JMenu("Toplist");
        menuBar.add(toplistmenu);

        easy=new JMenuItem("Create easy game");
        game.add(easy);
        easy.addActionListener(this);
        medium=new JMenuItem("Create medium game");
        game.add(medium);
        medium.addActionListener(this);
        hard=new JMenuItem("Create hard game");
        game.add(hard);
        hard.addActionListener(this);
        custom=new JMenuItem("Create custom game");
        custom.addActionListener(this);
        game.add(custom);

        watch=new JMenuItem("Watch toplist");
        watch.addActionListener(this);
        toplistmenu.add(watch);
        tl=new Toplist();

        setVisible(true);
    }

    /**
     * Ellenőrzi, melyik menüpont lett megnyomva és elvégzi az adott menüponthoz tartozó feladatokat
     * @param e ActionEvent
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == easy){
            createGame.setEasy();
            createGame.setVisible(true);
        }
        if(e.getSource() == medium){
            createGame.setMedium();
            createGame.setVisible(true);

        }
        if(e.getSource() == hard){
            createGame.setHard();
            createGame.setVisible(true);

        }
        if(e.getSource() == custom){
            createGame.setCustom();
            createGame.setVisible(true);
        }
        if(e.getSource() == watch){
            tl.display();
        }
    }
}
