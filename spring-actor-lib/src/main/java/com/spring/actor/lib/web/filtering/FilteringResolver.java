package com.spring.actor.lib.web.filtering;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilteringResolver implements HandlerMethodArgumentResolver {

    private static final Map<String, String> WHITE_LIST_OPERATIONS = new HashMap<String, String>() {{
        put("eq", "=");
        put("neq", "!=");
        put("lt", "<");
        put("lte", "<=");
        put("gt", ">");
        put("gte", ">=");
        put("like", "like");
    }};

    private static final String FILTERS = "filters";
    private static final String FILTER_AND_DELIMITER = ";";
    private static final String FILTER_OR_DELIMITER = "\\|";
    private static final String PROPERTY_DELIMITER = ",";
    private static final int PROPERTY_NUMBER = 3;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterAnnotation(Filtering.class) != null;
    }

    @Override
    public Object resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) throws Exception {
        String filters = webRequest.getParameter(FILTERS);
        return decrypt(filters);
    }

    private List<Criterion> decrypt(final String filters) {
        List<Criterion> criteria = new ArrayList<>();
        if (null != filters && !filters.isEmpty()) {
            Arrays.stream(filters.split(FILTER_AND_DELIMITER))
                .forEach((andFilterParts) -> {
                    String[] orFilterParts = andFilterParts.split(FILTER_OR_DELIMITER);
                    for (int i = 0; i < orFilterParts.length; i++) {
                        addCriteria(orFilterParts[i], i == orFilterParts.length - 1 ? "AND" : "OR", criteria);
                    }
                });
        }

        return criteria;
    }

    private void addCriteria(final String rawFilter, final String operator, List<Criterion> criteria) {
        Criterion criterion = parseCriterion(rawFilter.split(PROPERTY_DELIMITER), operator);
        if (criterion != null) {
            criteria.add(criterion);
        }
    }

    private Criterion parseCriterion(final String[] filter, final String nextOperator) {
        if (filter.length != PROPERTY_NUMBER) {
            return null;
        }
        String fieldName = filter[0].trim();
        String operator = filter[1].trim();
        String value = filter[2].trim();
        if (fieldName.isEmpty()) {
            return null;
        }
        if (value.isEmpty()) {
            value = null;
        }
        String resolvedOperator = WHITE_LIST_OPERATIONS.get(operator);
        if (null == resolvedOperator) {
            return null;
        }

        return new LinkedCriterion(fieldName, resolvedOperator, value, nextOperator);
    }
}
