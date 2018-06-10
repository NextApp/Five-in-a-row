package com.tuyasmart.five.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by letian on 2017/9/16.
 */

public class AnalyzeAIScore {

    private static HashMap<String, Boolean> allChessState = new HashMap<>();
    private static HashMap<String, Boolean> allChessStateWithoutUnless = new HashMap<>();
    private static HashMap<String, Integer> allChessStateAndScore = new HashMap<>();

    /**
     *
     */
    private static void printState(String str, int chess, int deep) {
        if (deep == 1) {
//        println str
            allChessState.put(str, true);
            return;
        } else {
            if (chess == -1) {
                str += "B";
            } else if (chess == 1) {
                str += "W";
            } else if (chess == 0) {
                str += "A";
            }
        }
        printState(str, 1, deep - 1);
        printState(str, 0, deep - 1);
        printState(str, -1, deep - 1);
    }

    private void allChessWithoutUnless() {
        for (Map.Entry<String, Boolean> entry : allChessState.entrySet()) {
            String key = entry.getKey();
            boolean find = false;
            String chess = "";
            for (int i = 0; i < key.length(); i++) {
                char c = key.charAt(i);
                if (c == 'B') {
                    allChessStateWithoutUnless.put(chess + "B", true);
                    find = true;
                    break;
                } else {
                    chess += c;
                }
            }
            if (!find) {
                allChessStateWithoutUnless.put(chess, true);
            }
        }
    }

    private void calculatScore() {
        for (Map.Entry<String, Boolean> entry : allChessStateWithoutUnless.entrySet()) {
            for (Map.Entry<String, Boolean> entry1 : allChessStateWithoutUnless.entrySet()) {
                String key = new StringBuilder(entry.getKey()).reverse().toString() + "D" + entry1.getKey();
                allChessStateAndScore.put(key, calculatSingleScore(key));
            }
        }
    }

    private Integer calculatSingleScore(String key) {
        return 100;
    }
}
