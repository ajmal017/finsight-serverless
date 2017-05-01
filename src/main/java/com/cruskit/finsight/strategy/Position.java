package com.cruskit.finsight.strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a financial position held at a point in time.
 *
 * @author cruskit
 */
public class Position {

    private LocalDateTime date;

    private BigDecimal numUnits = BigDecimal.ZERO;

    private BigDecimal cash = BigDecimal.ZERO;

    private String lastAction = "";

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getNumUnits() {
        return numUnits;
    }

    public void setNumUnits(BigDecimal numUnits) {
        this.numUnits = numUnits;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public Position(LocalDateTime date, BigDecimal numUnits, BigDecimal cash, String lastAction) {
        this.date = date;
        this.numUnits = numUnits;
        this.cash = cash;
        this.lastAction = lastAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (date != null ? !date.equals(position.date) : position.date != null) return false;
        if (!numUnits.equals(position.numUnits)) return false;
        if (!cash.equals(position.cash)) return false;
        return lastAction.equals(position.lastAction);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + numUnits.hashCode();
        result = 31 * result + cash.hashCode();
        result = 31 * result + lastAction.hashCode();
        return result;
    }
}
