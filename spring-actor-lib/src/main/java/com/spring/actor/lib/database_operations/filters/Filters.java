package com.spring.actor.lib.database_operations.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filters implements IQueryFilter {
    private final List<String> filterRules;
    private final List<IQueryFilter> filters;

    private static final String AND_CONDITION = "and";
    private static final String OR_CONDITION = "or";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String WHITE_SPACE = " ";

    public Filters(final String filtersRule, final List<IQueryFilter> filters) {
        int rulesSize = filters != null && !filters.isEmpty() ? filters.size() - 1 : 1;

        this.filterRules = Stream.generate(() -> filtersRule).limit(rulesSize).collect(Collectors.toList());
        this.filters = filters;
    }

    public Filters(final List<String> filterRules, final List<IQueryFilter> filters) {
        this.filterRules = filterRules;
        this.filters = filters;
    }

    private String getFilterRule(int index) {
        if (index == 0) {
            return "";
        }

        String rule = AND_CONDITION;
        if (filterRules.size() >= index) {
            rule = filterRules.get(index - 1);
            rule = rule != null ? rule : AND_CONDITION;
        }

        return rule.toLowerCase();
    }

    @Override
    public String build() {
        StringBuilder value = new StringBuilder();
        int index = 0;
        for (IQueryFilter filter : this.filters) {
            String filterRule = getFilterRule(index);
            switch (filterRule) {
                case AND_CONDITION:
                case OR_CONDITION:
                    if (index > 0) {
                        value
                                .append(WHITE_SPACE)
                                .append(filterRule)
                                .append(WHITE_SPACE);
                    }
                    break;
                default:
                    break;
            }
            if (filter instanceof Filters) {
                value.append(LEFT_BRACKET);
                value.append(filter.build());
                value.append(RIGHT_BRACKET);
            } else {
                value.append(filter.build());
            }
            index++;
        }
        return value.toString();
    }

    @Override
    public Object[] getArguments() {
        List<Object> arguments = new ArrayList<>();
        this.filters.forEach(
                (filter) -> {
                    arguments.addAll(Arrays.asList(filter.getArguments()));
                }
        );
        return arguments.toArray();
    }
}
