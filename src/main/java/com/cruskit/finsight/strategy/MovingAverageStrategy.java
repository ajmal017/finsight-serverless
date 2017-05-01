package com.cruskit.finsight.strategy;

import com.cruskit.finsight.analysis.Metric;
import com.cruskit.finsight.analysis.Stats;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Determines uses a moving average strategy to determine buy and sell dates for a stock.
 * <p>
 * Created by paul on 1/5/17.
 */
public class MovingAverageStrategy {

    public List<Action> calculateMovingAverageTransactionDates(int fastAvgPeriod,
                                                               int slowAvgPeriod,
                                                               List<Metric> prices,
                                                               LocalDateTime firstAllowedPurchaseDate) {

        if (fastAvgPeriod >= slowAvgPeriod) {
            throw new IllegalArgumentException("Fast avg must be less than slow avg period, [fast,slow]: "
                    + fastAvgPeriod + ", " + slowAvgPeriod);
        }

        List<Action> actions = new LinkedList<>();
        List<Metric> fastAvg = Stats.movingAverage(prices, fastAvgPeriod);
        List<Metric> slowAvg = Stats.movingAverage(prices, slowAvgPeriod);

        // Make the fast average start at the same date as the slow average
        for (int i = 0; i < (slowAvgPeriod - fastAvgPeriod); i++) {
            fastAvg.remove(0);
        }

        for (int i = 1; i < fastAvg.size(); i++) {

            // Low to high cross-over - buy condition
            if (firstAllowedPurchaseDate.compareTo(fastAvg.get(i).getDateTime()) <= 0
                    && fastAvg.get(i).getValue().compareTo(slowAvg.get(i).getValue()) > 0
                    && fastAvg.get(i - 1).getValue().compareTo(slowAvg.get(i - 1).getValue()) <= 0) {

                actions.add(new Action(Action.BUY, fastAvg.get(i).getDateTime()));
            }
            // High to low cross-over - sell condition
            if (firstAllowedPurchaseDate.compareTo(fastAvg.get(i).getDateTime()) <= 0
                    && fastAvg.get(i).getValue().compareTo(slowAvg.get(i).getValue()) < 0
                    && fastAvg.get(i - 1).getValue().compareTo(slowAvg.get(i - 1).getValue()) >= 0) {

                actions.add(new Action(Action.SELL, fastAvg.get(i).getDateTime()));
            }
        }

        return actions;
    }

}
