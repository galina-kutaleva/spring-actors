package com.spring.actor.lib.web.pagination;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaginationResolver implements HandlerMethodArgumentResolver {

    private static final int MIN_PAGE = 1;
    private static final String PAGE = "page";
    private static final String PAGE_SIZE = "page_size";
    private static final String SORT = "sort";
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int MAX_PAGE_SIZE = 10000;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterAnnotation(Pagination.class) != null;
    }

    @Override
    public Object resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) throws Exception {
        try {
            String pageStr = webRequest.getParameter(PAGE);
            int page = isBlank(pageStr) ? MIN_PAGE : Integer.valueOf(pageStr);
            String pageSizeStr = webRequest.getParameter(PAGE_SIZE);
            int pageSize = isBlank(pageSizeStr) ? DEFAULT_PAGE_SIZE : Integer.valueOf(pageSizeStr);
            if (pageSize > MAX_PAGE_SIZE) {
                pageSize = MAX_PAGE_SIZE;
            }
            String sort = webRequest.getParameter(SORT);

            return decrypt(page, pageSize, sort);
        } catch (Exception e) {
            throw new ResolvePaginationException(e.getMessage(), e);
        }
    }

    private Pageable decrypt(final int page, final int size, final String sort) {
        if (isBlank(sort)) {
            //Pageable page index starts at 0 while Dto index starts at MIN_PAGE
            return PageRequest.of(page - MIN_PAGE, size, null);
        }

        List<Sort.Order> orders = new ArrayList<>();
        Arrays.stream(sort.split(";")).map((propOrder) -> propOrder.split(",")).forEachOrdered((order) -> {
            if (order.length > 2 || order.length < 1) {
                return;
            }
            String property = order[0].trim();
            if (isBlank(property)) {
                return;
            }
            if (order.length == 1 || isBlank(order[1])) {
                orders.add(Sort.Order.by(property));
            } else {
                Sort.Direction direction = Sort.Direction.fromString(order[1].trim());
                orders.add(new Sort.Order(direction, property));
            }
        });

        //Pageable page index starts at 0 while Dto index starts at MIN_PAGE
        return PageRequest.of(page - MIN_PAGE, size, orders.isEmpty() ? null : Sort.by(orders));
    }

    private boolean isBlank(final String str) {
        return str == null || StringUtils.isEmpty(str.trim());
    }
}
