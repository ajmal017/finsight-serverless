package com.cruskit.finsight.strategy;

import java.time.LocalDateTime;

/**
 * Created by paul on 1/5/17.
 */
public class Action {

    public final static String BUY = "buy";
    public final static String SELL = "sell";

    private String action;

    private LocalDateTime date;

    public Action(String action, LocalDateTime date) {
        this.action = action;
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action1 = (Action) o;

        if (!action.equals(action1.action)) return false;
        return date.equals(action1.date);
    }

    @Override
    public int hashCode() {
        int result = action.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Action{" +
                "action='" + action + '\'' +
                ", date=" + date +
                '}';
    }


}
