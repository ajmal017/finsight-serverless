package com.cruskit.finsight.analysis;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Statistical operations for analysing Metrics
 * <p>
 * <p>
 * Created by paul on 26/4/17.
 */
public class Stats {

    /**
     * Produce a moving average for the list of @Metrics passed in.
     * <p>
     * Assumes that the Metrics are already sorted in date order.
     * Date periods not taken into account for average (a period of
     * 3 just means three consecutive samples)
     *
     * @param metrics the metrics to produce the moving average for
     * @param period  the number of samples to include in each average value
     * @return a collection of moving average values. First sample will be for
     * the Metric.date of the sample in the period position (ie: if period is
     * three the first moving average value will be for the third date)
     */
    public static List<Metric> movingAverage(List<Metric> metrics, int period) {

        if (period > metrics.size()) {
            throw new IllegalArgumentException("Period larger than number of samples [p,size]: " + period + ", " + metrics.size());
        }

        List<Metric> averages = new LinkedList<Metric>();

        for (int i = 0; i < (metrics.size() - period + 1); i++) {

            BigDecimal total = BigDecimal.ZERO;
            for (int sample = 0; sample < period; sample++) {
                total = total.add(metrics.get(i + sample).getValue());
            }

            // Use the date from the last metric in the period
            Metric average = new Metric(metrics.get(i + period - 1).getDateTime(),
                    total.divide(BigDecimal.valueOf(period), 10, BigDecimal.ROUND_HALF_UP));
            averages.add(average);
        }

        return averages;
    }


}
