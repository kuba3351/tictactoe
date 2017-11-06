package com.urz.tictactoe;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jakub on 17.10.17.
 */

public class AIAlgorhitmsOrderProvider {
    public static List<AIAlgorhitm> getAlgorhitmsList() {
        return Arrays.asList(
                new SimpleWinAlgorhitm(),
                new BlockSimpleWinAlgorhitm());
    }
}
