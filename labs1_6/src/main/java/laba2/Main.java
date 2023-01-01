package laba2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        assert(args.length <1);
        MoneyChanger moneyChanger;
        try {
            moneyChanger = new MoneyChanger(args);
        }
        catch (NumberFormatException exception) {
            System.out.println("No correct orguments");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int coin;
        while(true) {
            coin = scanner.nextInt();
            moneyChanger.WriteInformationAboutChange(coin);
        }
    }
}
