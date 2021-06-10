package com.spring.actor.lib.database_operations.operations;

import java.util.List;
import java.util.Map;

public class Page {
    private List<Map<String, Object>> content;
    private Long total;
    private Integer pageNumbers;

    public Page(List<Map<String, Object>> content, Long total, Integer pageNumbers) {
        this.content = content;
        this.total = total;
        this.pageNumbers = pageNumbers;
    }

    public List<Map<String, Object>> getContent() {
        return content;
    }

    public void setContent(List<Map<String, Object>> content) {
        this.content = content;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(Integer pageNumbers) {
        this.pageNumbers = pageNumbers;
    }
}
