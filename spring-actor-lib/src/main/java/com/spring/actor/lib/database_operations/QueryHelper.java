package com.spring.actor.lib.database_operations;

import com.spring.actor.lib.database_operations.filters.CaseInsensitiveFilter;
import com.spring.actor.lib.database_operations.filters.Filters;
import com.spring.actor.lib.database_operations.filters.IQueryFilter;
import com.spring.actor.lib.web.filtering.Criterion;
import com.spring.actor.lib.web.filtering.LinkedCriterion;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryHelper {

    private static final String DEFAULT_EXPRESSION = "data->>'id'";
    private static final String COLLATE_OPERATOR = "COLLATE \"C\"";
    private static final String LOWER_OPERATOR = "lower";
    private static final String WHITE_SPACE = " ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String QUESTION = "?";

    public static IQueryFilter getFilterFromCriteria(final Query query, final List<Criterion> criteria) {
        IQueryFilter filters = null;
        if (null != query) {
            Map<String, WhiteParameter> whiteList = query.getWhitelist();
            List<IQueryFilter> filterList = new ArrayList<>();
            List<String> filterRules = new ArrayList<>();
            if (criteria != null) {
                criteria.forEach(
                        criterion -> {
                            if (null != whiteList) {
                                if (whiteList.containsKey(criterion.getFieldName())) {
                                    WhiteParameter parameter = whiteList.get(criterion.getFieldName());
                                    String expression = parameter.getExpression();
                                    String result = null != expression && !expression.isEmpty() ? expression : DEFAULT_EXPRESSION;
                                    if (null == parameter.getCastToClass() || parameter.getCastToClass().equals(String.class)) {
                                        result = LOWER_OPERATOR + LEFT_BRACKET + result + RIGHT_BRACKET + WHITE_SPACE + COLLATE_OPERATOR;
                                    }
                                    filterList.add(
                                            new CaseInsensitiveFilter(
                                                    result + WHITE_SPACE + criterion.getOperator() + WHITE_SPACE + QUESTION,
                                                    cast(criterion.getValue(), parameter.getCastToClass())
                                            )
                                    );
                                    if (criterion instanceof LinkedCriterion) {
                                        filterRules.add(((LinkedCriterion) criterion).getNextOperator());
                                    } else {
                                        filterRules.add("AND");
                                    }
                                }
                            }
                        }
                );
            }
            if (!filterList.isEmpty()) {
                filters = new Filters(filterRules, filterList);
            }
        }

        return filters;
    }

    public static String getSortStringFromPageable(final Pageable paging) {
        return null;
    }

    private static Object cast(final String value, final Class<?> clazz) {
        if (null == clazz) {
            return value;
        }
        if (clazz.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        }
        if (clazz.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        if (clazz.equals(Double.class)) {
            return Double.parseDouble(value);
        }
        if (clazz.equals(Float.class)) {
            return Float.parseFloat(value);
        }
        if (clazz.equals(Long.class)) {
            return Long.parseLong(value);
        }
        if (clazz.equals(BigDecimal.class)) {
            return new BigDecimal(value);
        }

        return value;
    }
}
