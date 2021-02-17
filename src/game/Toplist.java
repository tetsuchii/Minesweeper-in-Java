package game;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Toplist implements Serializable {
    private ArrayList<Score> scores; // A toplistára került játékosok játékadatainak a tömbje

    /**
     * Beolvassa a már elmentett toplistán szereplő tételeket. Ha még nincsenek, egy új tömböt hoz létre számukra.
     */

    public Toplist() {
        ObjectInputStream inputStream;
        try {
            FileInputStream fis = new FileInputStream("scores.dat");
            inputStream = new ObjectInputStream(fis);
            scores = (ArrayList<Score>) inputStream.readObject();
            inputStream.close();
        }catch (IOException  ioe){
            scores = new ArrayList<Score>(10);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Egy új tételt ad hozzá a toplistához, ehhez a következő paramétereket gyűjti össze és menti el a fájlba.
     *
     * @param rows A pálya hosszúsága blokkokban mérve.
     * @param columns A pálya szélessége blokkokban mérve.
     * @param bombs A pályán elhelyezett bombák száma.
     * @param min A játék befejezéséig eltelt idő perc egysége.
     * @param sec A játék befejezéséig eltelt idő másodperc egysége.
     * @param name A játékos neve.
     */

    public void add(int rows, int columns, int bombs, int min, int sec, String name) {
        Score score = new Score(rows, columns, bombs, min, sec, name);
        if (scores.size() == 0){
            scores.add(score);
        }
        else {
            int index = 0;
            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i).compareTo(score) > 0) {
                    if (i == scores.size() - 1)
                        index++;
                    continue;
                } else
                    break;
            }
            if (index < 10)
                scores.add(index, score);
        }
        try {
            FileOutputStream fos=new FileOutputStream("scores.dat");
            ObjectOutputStream output = new ObjectOutputStream((fos));
            output.writeObject(scores);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Megjeleníti a toplistát megfelelő sorrendben. Ha nincs mit megjeleníteni, akkor erről tájékoztatja a felhasználót.
     */

    public void display() {
        if (scores.size() > 0) {
            String output = "";
            for (int i = 0; i < scores.size(); i++)
                output += (i + 1) + ") " + scores.get(i).toString() + "\n";
            JOptionPane.showMessageDialog(null, output);
        } else {
            JOptionPane.showMessageDialog(null, "No score to display");
        }
    }
}
