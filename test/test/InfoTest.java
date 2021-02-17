package test;

import game.Info;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class InfoTest {
    private Info infobox;

    @Before
    public void setUp() {
        infobox=new Info(10);
    }

    /**
     * Ellenőrzi, hogy a játékkezdésnél jól lesz-e beállítva a finish
     */

    @Test
    public void isFinish() {
        boolean result=infobox.isFinish();
        Assert.assertFalse(result);
    }

    /**
     * Ellenőrzi, hogy a konstruktorban meghatározott bombaszámmal egyenlő-e a kihelyezhető zászlók száma kezdéskor
     */

    @Test
    public void getFlags() {
        int result=infobox.getFlags();
        Assert.assertEquals(10,result);
    }

    /**
     * Ellenőrzi, hogy a játék az első tipp előtt már folyamatban van-e
     */

    @Test
    public void isGameIsOn() {
        boolean result=infobox.isGameIsOn();
        Assert.assertFalse(result);
    }

    /**
     * Ellenőrzi, hogy megfelelően nő-e a másodpercszámláló
     */

    @Test
    public void getSec() {
        int count=0;
        for(int i=0;i<7;i++){
            infobox.plusOneSec();
            count++;
        }
        Assert.assertEquals(count,infobox.getSec());
    }
}