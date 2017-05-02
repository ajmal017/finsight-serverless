package com.cruskit.finsight.strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author cruskit
 */
public class StrategyOutcome {

    private LocalDateTime firstAllowedBuyDate;

    private List<Action> actions = new LinkedList<>();

    private BigDecimal finalValue = BigDecimal.ZERO;

    private List<Position> positions = new LinkedList<>();

    private Map<String,String> settings = new HashMap<>();
}
