package com.cruskit.finsight.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by paul on 26/4/17.
 */
@RunWith(Parameterized.class)
public class StatsTest {


    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Collection<Object[]> testParams = new LinkedList<>();

        LocalDateTime refDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        // Multiple values result
        List<Metric> samples = new LinkedList<>();
        samples.add(new Metric(refDate, BigDecimal.valueOf(1)));
        samples.add(new Metric(refDate.plusDays(1), BigDecimal.valueOf(2)));
        samples.add(new Metric(refDate.plusDays(2), BigDecimal.valueOf(3)));

        List<Metric> averages = new LinkedList<>();
        averages.add(new Metric(refDate.plusDays(1), BigDecimal.valueOf(1.5).setScale(Stats.SCALE)));
        averages.add(new Metric(refDate.plusDays(2), BigDecimal.valueOf(2.5).setScale(Stats.SCALE)));

        testParams.add(new Object[]{samples, averages, 2});


        // Single value result
        samples = new LinkedList<>();
        samples.add(new Metric(refDate, BigDecimal.valueOf(1)));
        samples.add(new Metric(refDate.plusDays(1), BigDecimal.valueOf(2)));
        samples.add(new Metric(refDate.plusDays(2), BigDecimal.valueOf(3)));

        averages = new LinkedList<>();
        averages.add(new Metric(refDate.plusDays(2), BigDecimal.valueOf(2).setScale(Stats.SCALE)));

        testParams.add(new Object[]{samples, averages, 3});

        return testParams;
    }


    private List<Metric> inputs;

    private List<Metric> expectedResults;

    private int period;

    public StatsTest(List<Metric> inputs, List<Metric> expectedResults, int period) {
        this.inputs = inputs;
        this.expectedResults = expectedResults;
        this.period = period;
    }

    @Test
    public void testMovingAverageCalculation() {

        System.out.println("Hello world: " + inputs.size() + ", " + expectedResults.size());

        List<Metric> results = Stats.movingAverage(inputs, period);

        assertArrayEquals(expectedResults.toArray(), results.toArray());
    }
}
