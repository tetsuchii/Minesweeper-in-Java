package test;

import game.Board;
import game.Info;
import game.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {
    private Board board;
    private Tile tile;

    @Before
    public void setUp() throws Exception {
        Info infobox=new Info(10);
        board=new Board("Nina",infobox,10,10,10 );
        tile=new Tile(0,0,board);
    }

    /**
     * Ellenőrzi, hogy a bombák kihelyezése előtt, megfelelő-e a blokkok bombaságának beállítása
     */

    @Test
    public void isMine() {
        boolean result=tile.isMine();
        Assert.assertFalse(result);
    }

    /**
     * Ellenőrzi, hogy a játék kezdetén megfelelő-e a zászlósság értéke a blokkon
     */

    @Test
    public void isFlag() {
        boolean result=tile.isFlag();
        Assert.assertFalse(result);
    }

    /**
     * Ellenőrzi, hogy a játék kezdetén megfelelő-e a felnyitottság értéke a blokkon
     */

    @Test
    public void isOpened() {
        boolean result=tile.isOpened();
        Assert.assertFalse(result);
    }

    /**
     * Ellenőrzi, hogy megfelelő-e a blokk mérete
     */

    @Test
    public void getTileWidth() {
        int result=tile.getTileWidth();
        Assert.assertEquals(600/Board.getBoardWidth(),result);
    }

}