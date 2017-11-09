package com.urz.tictactoe.AI;

import com.urz.tictactoe.AI.algorithms.AlwaysCenterAlgorithm;
import com.urz.tictactoe.AI.algorithms.AlwaysCornerAlgorithm;
import com.urz.tictactoe.AI.algorithms.SimpleBlockAlgorithm;
import com.urz.tictactoe.AI.algorithms.SimpleWinAlgorithm;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jakub on 17.10.17.
 */

public class AIAlgorhitmsOrderProvider {
    public static List<AIAlgorithm> getAlgorhitmsList() {
        return Arrays.asList(
                new SimpleWinAlgorithm(),
                new SimpleBlockAlgorithm(),
                new AlwaysCenterAlgorithm(),
                new AlwaysCornerAlgorithm());
    }
}
