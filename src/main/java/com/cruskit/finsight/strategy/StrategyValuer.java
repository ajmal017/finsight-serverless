package com.cruskit.finsight.strategy;

import com.cruskit.finsight.analysis.Metric;
import com.cruskit.finsight.analysis.Stats;
import javafx.geometry.Pos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cruskit
 */
public class StrategyValuer {

    public final static BigDecimal START_VALUE = new BigDecimal("1000").setScale(10);

    /**
     * @param prices  the list of unit prices
     * @param actions the list of actions on the portfolio, assumed sorted in ascending date order
     * @return
     */
    public static List<Position> calculatePositions(List<Metric> prices, List<Action> actions) {

        List<Position> positions = new LinkedList<>();

        Position currentPosition = new Position(prices.get(0).getDateTime(),
                BigDecimal.ZERO, START_VALUE, Action.SELL);
        positions.add(currentPosition);

        int dateIndex = 0;
        for (Action action : actions) {

            // Find the date
            boolean foundDate = false;
            while (!foundDate) {
                if (prices.get(dateIndex).getDateTime().equals(action.getDate())) {
                    foundDate = true;
                } else {
                    dateIndex++;
                }

                if (dateIndex >= prices.size()) {
                    throw new IllegalArgumentException("Action specifies date without matching price: " + action);
                }
            }

            if (Action.BUY.equals(action.getAction())) {
                BigDecimal newUnits = currentPosition.getCash().divide(
                        prices.get(dateIndex).getValue(), Stats.SCALE, BigDecimal.ROUND_HALF_UP);

                currentPosition = new Position(prices.get(dateIndex).getDateTime(),
                        currentPosition.getNumUnits().add(newUnits),
                        BigDecimal.ZERO, action.getAction());

            } else if (Action.SELL.equals(action.getAction())) {
                // Sell all available units at current price
                BigDecimal newValue = currentPosition.getNumUnits().multiply(prices.get(dateIndex).getValue());

                currentPosition = new Position(prices.get(dateIndex).getDateTime(),
                        BigDecimal.ZERO,
                        currentPosition.getCash().add(newValue),
                        action.getAction());

            } else {
                throw new IllegalArgumentException("Unknown action: " + action.getAction());
            }
            positions.add(currentPosition);

        }

        return positions;
    }


}
