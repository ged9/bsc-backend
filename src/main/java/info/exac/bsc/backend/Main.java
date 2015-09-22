package info.exac.bsc.backend;


import org.joda.money.Money;

import java.util.Scanner;
import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            DataHolder.getInstance().load(args[0]);
        }
        Timer timer = new Timer();
        timer.schedule(new PrintTask(), 0, 10 * 1000);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(">");

            String line = scanner.nextLine();

            if ("quit".equalsIgnoreCase(line.trim())) {
                timer.cancel();
                break;
            }

            try {
                Money money = Money.parse(line.trim());
                DataHolder.getInstance().add(money);
            } catch (Exception e) {
                System.err.println("Castka zadana nespravne!");
            }
        }

    }

}
