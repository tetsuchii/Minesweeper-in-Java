package game;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private double ratio; //A játékos pontszáma
    private int min, sec,rows,columns,bombs; //A játék idejének perc és másodperc értéke, a játékmező szélessége és hosszúsága, illetve a bombák száma
    private String name; // A játékos neve

    /**
     * Létrehoz egy újabb toplista bejegyzést és kalkulál egy pont értéket, melyben a következő értékeket menti el:
     * @param rows Milyen széles a pálya
     * @param columns Milyen hosszú a pálya
     * @param bombs Hány bomba van a pályán
     * @param min A játék végéig eltelt idő perc értéke
     * @param sec A játék végéig eltelt idő másodperc értéke
     * @param name A játékos neve
     */

    public Score(int rows, int columns, int bombs, int min,int sec, String name){
        ratio = ((double) bombs) / (rows*columns);
        this.min=min;
        this.sec=sec;
        this.rows=rows;
        this.columns=columns;
        this.bombs=bombs;
        this.name=name;
    }

    /**
     * Összehasonlítja egy másik bejegyzés pontértékét a jelenlegivel.
     * @param o A másik bejegyzés
     * @return Megadja, hogy az adott pont kisebb vagy nagyobb a másikhoz képest.
     */

    @Override
    public int compareTo(Score o) {
        if(ratio < o.getRatio())
            return -1;
        else if(ratio > o.getRatio())
            return 1;
        else if(ratio == o.getRatio()){
            if ((min*60+sec) > o.getTime())
                return 1;
            if(min*60+sec < o.getTime())
                return -1;
        }
        return 0;
    }

    /**
     * Átkonvertálja String-é a Score adatait.
     * @return Az adott Score string formában.
     */

    public String toString(){
        if(min >0) {
            return String.format("%-12s%d x %d with %d mines in %d minutes and %d seconds", name, rows, columns, bombs, min, sec);
        }else {
            return String.format("%-12s%d x %d with %d mines in %d seconds", name, rows, columns, bombs, sec);
        }
    }

    /**
     * Visszadja az adott Score bejegyzés pont értékét.
     * @return A pont értéke
     */

    public double getRatio() {
        return ratio;
    }

    /**
     * Visszaadja az adott bejegyzés játékidejét
     * @return A játékidő másodpercben
     */

    public int getTime() {
        return min*60+sec;
    }
}
