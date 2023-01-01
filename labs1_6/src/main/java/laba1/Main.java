package laba1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        assert (args.length != 2);
        Parser parser = null;
        try {
            parser = new Parser(args[0]);
            parser.TryOpenAndCheck(args[1]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}