package laba1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

public class Parser {
    BracketsAnalyzer analyzeBrackets = null;

    Parser(String filePath) throws IOException {
        analyzeBrackets = new BracketsAnalyzer(filePath);
    }

    private void ReadingAndCheck(String filePath) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        char symbol = (char) bufferedReader.read();
        int symbolIndex = 0;
        while (bufferedReader.ready()) {
            if (!analyzeBrackets.IsCorrectSymbol(symbol)) {
                System.out.println("No correct Bracket found: " + symbol);
                System.out.println("Symbol index: " + symbolIndex);
                break;
            }
            symbol = (char) bufferedReader.read();
            symbolIndex++;
        }
        bufferedReader.close();
        analyzeBrackets.writeIfRemainingBrackets();
    }

    public void TryOpenAndCheck(String filePath) {
        try {
            ReadingAndCheck(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    ;
}
