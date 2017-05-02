package com.cruskit.finsight.strategy;

import com.cruskit.finsight.analysis.Metric;
import com.cruskit.finsight.analysis.Stats;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author cruskit
 */
public class StrategyValuerTest {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void testCalculatePositions(){

        LocalDateTime refDate = LocalDateTime.parse("2016-01-01 00:00", dateFormatter);

        List<Metric> prices = Arrays.asList(
                new Metric(refDate.plusDays(0), new BigDecimal("0.01")),
                new Metric(refDate.plusDays(1), new BigDecimal("2")), // 500 shares
                new Metric(refDate.plusDays(2), new BigDecimal("0.01")),
                new Metric(refDate.plusDays(3), new BigDecimal("4")), // $2000
                new Metric(refDate.plusDays(4), new BigDecimal("10")), // 200 shares
                new Metric(refDate.plusDays(5), new BigDecimal("0.01")),
                new Metric(refDate.plusDays(6), new BigDecimal("8"))
        );

        List<Action> actions = Arrays.asList(
                new Action(Action.BUY, refDate.plusDays(1)),
                new Action(Action.SELL, refDate.plusDays(3)),
                new Action(Action.BUY, refDate.plusDays(4))
        );


        List<Position> expectedPositions = Arrays.asList(
                new Position(refDate.plusDays(0), BigDecimal.ZERO, StrategyValuer.START_VALUE, Action.SELL),
                new Position(refDate.plusDays(1), BigDecimal.valueOf(500).setScale(Stats.SCALE), BigDecimal.ZERO, Action.BUY),
                new Position(refDate.plusDays(3), BigDecimal.ZERO, BigDecimal.valueOf(2000).setScale(Stats.SCALE), Action.SELL),
                new Position(refDate.plusDays(4), BigDecimal.valueOf(200).setScale(Stats.SCALE), BigDecimal.ZERO, Action.BUY)
        );

        List<Position> calcPositions = StrategyValuer.calculatePositions(prices, actions);

        assertArrayEquals(expectedPositions.toArray(), calcPositions.toArray());
    }
}
