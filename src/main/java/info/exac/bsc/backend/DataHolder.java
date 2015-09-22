package info.exac.bsc.backend;


import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataHolder {

    private static final Logger LOG = LoggerFactory.getLogger(DataHolder.class);

    private static DataHolder INSTANCE = null;

    public synchronized static DataHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataHolder();
        }
        return INSTANCE;
    }



    private Map<String, Money> sumByCurrencyMap = new HashMap<String, Money>();


    private DataHolder() {
        // Add RMB abbreviation, but CNY is correct
        CurrencyUnit.registerCurrency("RMB", 0, 2, Arrays.asList("PRC"));

        load();
    }


    private void load() {
        InputStream inputStream = DataHolder.class.getResourceAsStream("source.txt");
        try {
//            List<String> lines = IOUtils.readLines(inputStream, "UTF-8");
            List<String> lines = Files.readAllLines(Paths.get(".", "source.txt"), Charset.forName("UTF-8"));

            for (String line : lines) {
                add(Money.parse(line));
            }
        } catch (IOException e) {
            LOG.error("Money cannot be loadede from file!", e);
        }
    }



    public synchronized void add(Money moneyToAdd) {
        Money stored = sumByCurrencyMap.get(moneyToAdd.getCurrencyUnit().getCurrencyCode());
        if (stored != null) {
            stored = stored.plus(moneyToAdd);
            sumByCurrencyMap.put(stored.getCurrencyUnit().getCurrencyCode(), stored);
        } else {
            sumByCurrencyMap.put(moneyToAdd.getCurrencyUnit().getCurrencyCode(), moneyToAdd);
        }
    }



    public synchronized List<Money> getList() {
        List<Money> result = new ArrayList<Money>();
        for (Money moenyItem : sumByCurrencyMap.values()) {
            result.add(Money.of(moenyItem));
        }
        return result;
    }



    public synchronized void clear() {
        sumByCurrencyMap.clear();
    }
}
