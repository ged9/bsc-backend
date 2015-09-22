package info.exac.bsc.backend;

import org.joda.money.Money;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by exac on 22.09.15.
 */
public class DataHolderTest {

    private DataHolder dataHolder = null;

    @Before
    public void setUp() {
        dataHolder = DataHolder.getInstance();

        dataHolder.add(Money.parse("USD 12.24"));
        dataHolder.add(Money.parse("USD 10"));
        dataHolder.add(Money.parse("RMB 2000"));
        dataHolder.add(Money.parse("RMB -100"));
    }


    @After
    public void tearDown() {
        dataHolder.clear();
    }


    @Test
    public void testGetList() throws Exception {
        assertEquals(dataHolder.getList().size(), 2);
    }


    @Test
    public void testAdding() throws Exception {
        boolean found = false;
        for (Money money : dataHolder.getList()) {
            if ("USD".equals(money.getCurrencyUnit().getCurrencyCode())) {
                assertEquals(money.getAmount().compareTo(new BigDecimal("22.24")), 0);
                found = true;
            }
        }
        assertEquals(found, true);
    }


    @Test
    public void testSubtracting() throws Exception {
        boolean found = false;
        for (Money money : dataHolder.getList()) {
            if ("RMB".equals(money.getCurrencyUnit().getCurrencyCode())) {
                assertEquals(money.getAmount().compareTo(new BigDecimal("1900")), 0);
                found = true;
            }
        }
        assertEquals(found, true);
    }

}