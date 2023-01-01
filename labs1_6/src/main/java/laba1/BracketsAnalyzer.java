package laba1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;

public class BracketsAnalyzer {
    private Brackets brackets = null;
    private ArrayDeque<Bracket> bracketStack = new ArrayDeque<Bracket>();

    BracketsAnalyzer(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            brackets = mapper.readValue(new File(filePath), Brackets.class);

        } catch (IOException e) {
            throw new IOException("JsonFile: " + e.getMessage());
            //System.out.println("JsonFile: " + e.getMessage());
        }
    }

    private boolean IsBracketStackEmpty() {
        return bracketStack.size() != 0;
    }

    public void writeIfRemainingBrackets() {
        if (IsBracketStackEmpty()) {
            System.out.println("All brackets is correct");
        } else if (bracketStack.size() != 0) {
            StringBuilder brackets = new StringBuilder("");
            for (Bracket bracket : bracketStack) {
                brackets.append(bracket.getRight()).append(" ");
            }
            System.out.println("Not found brackets: " + brackets);
        }
        bracketStack.clear();
    }

    private boolean IsItRightBracketIfTruePop(Character symbol) {
        if (IsBracketStackEmpty()) {
            if (bracketStack.peek().getRight().equals(symbol)) {
                bracketStack.pop();
                return true;
            }
        }
        return false;
    }

    public boolean IsCorrectSymbol(Character symbol) {
        if (IsItRightBracketIfTruePop(symbol)) {
            return true;
        }
        return IsUsableSymbolIfLeftBracketPushIt(symbol);
    }

    private boolean IsUsableSymbolIfLeftBracketPushIt(Character symbol) {
        for (Bracket bracket : brackets.getBracket()) {
            if (symbol.equals(bracket.getLeft())) {
                bracketStack.push(bracket);
                return true;
            } else if (symbol.equals(bracket.getRight())) {
                bracketStack.clear();
                return false;
            }
        }
        return true;
    }
}
