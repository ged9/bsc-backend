package info.exac.bsc.backend;

import org.joda.money.Money;

import java.util.TimerTask;

/**
 * Created by exac on 22.09.15.
 */
public class PrintTask extends TimerTask {

    @Override
    public void run() {
        DataHolder dataHolder = DataHolder.getInstance();

        for (Money money : dataHolder.getList()) {
            System.out.println(money.toString());
        }
        System.out.println();

    }

}
