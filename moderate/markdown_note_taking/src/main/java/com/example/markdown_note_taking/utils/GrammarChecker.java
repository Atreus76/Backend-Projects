package com.example.markdown_note_taking.utils;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

public class GrammarChecker {
    private static final JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
    public static String checkGrammar(String text){
        try {
            List<RuleMatch> matches = langTool.check(text);
            if (matches.isEmpty()){
                return ("No grammar issues found");
            }
            StringBuilder result = new StringBuilder("Grammar issues:\n");
            for (RuleMatch match : matches) {
                result.append(String.format("Issue at position %d: %s\nSuggested fix: %s\n",
                        match.getFromPos(), match.getMessage(), match.getSuggestedReplacements()));
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
