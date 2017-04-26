package com.cruskit.finsight.analysis;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents an individual metric value at a point in time.
 *
 * Created by paul on 26/4/17.
 */
public class Metric {

    private LocalDateTime dateTime;
    private BigDecimal value;

    public Metric(LocalDateTime dateTime, BigDecimal value) {
        this.dateTime = dateTime;
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metric metric = (Metric) o;

        if (!dateTime.equals(metric.dateTime)) return false;
        return value.equals(metric.value);
    }

    @Override
    public int hashCode() {
        int result = dateTime.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "dateTime=" + dateTime +
                ", value=" + value +
                '}';
    }
}
