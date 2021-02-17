package test;

import game.Board;
import game.Info;
import game.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    private Board board;
    private Info infobox;

    @Before
    public void setUp() throws Exception {
        infobox=new Info(10);
        board=new Board("Nina",infobox,10,10,10 );
    }

    /**
     * Ellenőrzi, hogy tényleg annyi bomba lesz-e generálva
     */

    @Test
    public void getBombsTest() {
        int result=board.getBombs();
        Assert.assertEquals(10,result);
    }

    /**
     * Ellenőrzi, hogy működik-e a reset
     */

    @Test
    public void resetTest() {
        board.reset();
        Assert.assertFalse(infobox.isFinish());
    }

}