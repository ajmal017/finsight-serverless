package com.cruskit.finsight.strategy;

import com.cruskit.finsight.analysis.Metric;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author paul
 */
public class MovingAverageStrategyTest {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Test
    public void testMovingAverageCalculationWithNoActionResults() {

        LocalDateTime refDate = LocalDateTime.parse("2016-01-01 00:00", dateFormatter);

        List<Metric> metric = Arrays.asList(
                new Metric(refDate.plusDays(0), BigDecimal.valueOf(1)),
                new Metric(refDate.plusDays(1), BigDecimal.valueOf(2)),
                new Metric(refDate.plusDays(2), BigDecimal.valueOf(3)),
                new Metric(refDate.plusDays(3), BigDecimal.valueOf(4)),
                new Metric(refDate.plusDays(4), BigDecimal.valueOf(4)),
                new Metric(refDate.plusDays(5), BigDecimal.valueOf(6)),
                new Metric(refDate.plusDays(6), BigDecimal.valueOf(7))
        );

        MovingAverageStrategy mas = new MovingAverageStrategy();
        List<Action> actions = mas.calculateMovingAverageTransactionDates(2,3,metric,refDate);

        assertArrayEquals(new Action[0], actions.toArray());
    }

    @Test
    public void testMovingAverageCalculationResults() {

        LocalDateTime refDate = LocalDateTime.parse("2016-01-01 00:00", dateFormatter);

        List<Metric> metric = Arrays.asList(
                new Metric(refDate.plusDays(0), BigDecimal.valueOf(3)),
                new Metric(refDate.plusDays(1), BigDecimal.valueOf(2)),
                new Metric(refDate.plusDays(2), BigDecimal.valueOf(1)),
                new Metric(refDate.plusDays(3), BigDecimal.valueOf(4)), // buy
                new Metric(refDate.plusDays(4), BigDecimal.valueOf(1)),
                new Metric(refDate.plusDays(5), BigDecimal.valueOf(4)), // sell
                new Metric(refDate.plusDays(6), BigDecimal.valueOf(1)), // buy
                new Metric(refDate.plusDays(7), BigDecimal.valueOf(4)), // sell
                new Metric(refDate.plusDays(8), BigDecimal.valueOf(1))  // buy
        );

        MovingAverageStrategy mas = new MovingAverageStrategy();
        List<Action> actions = mas.calculateMovingAverageTransactionDates(2,3,metric,refDate);

        Action[] expectedActions = new Action[5];
        expectedActions[0] = new Action(Action.BUY, refDate.plusDays(3));
        expectedActions[1] = new Action(Action.SELL, refDate.plusDays(5));
        expectedActions[2] = new Action(Action.BUY, refDate.plusDays(6));
        expectedActions[3] = new Action(Action.SELL, refDate.plusDays(7));
        expectedActions[4] = new Action(Action.BUY, refDate.plusDays(8));

        assertArrayEquals(expectedActions, actions.toArray());
    }

}

