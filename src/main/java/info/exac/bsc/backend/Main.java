package info.exac.bsc.backend;


import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        new Timer().schedule(new PrintTask(), 0, 1000);
    }

}
