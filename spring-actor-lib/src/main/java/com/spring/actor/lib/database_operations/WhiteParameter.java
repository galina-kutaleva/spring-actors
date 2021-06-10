package com.spring.actor.lib.database_operations;

public class WhiteParameter {
    private String expression;
    private String castToClass;
    private String filterTag;
    private String sortingTag;

    public WhiteParameter() {
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Class<?> getCastToClass() {
        Class<?> clazz;
        switch (this.castToClass) {
            case "int":
                clazz = Integer.class;
                break;
            case "double":
                clazz = Double.class;
                break;
            default:
                clazz = String.class;
        }
        return clazz;
    }

    public void setCastToClass(String castToClass) {
        this.castToClass = castToClass;
    }

    public String getFilterTag() {
        return filterTag;
    }

    public void setFilterTag(String filterTag) {
        this.filterTag = filterTag;
    }

    public String getSortingTag() {
        return sortingTag;
    }

    public void setSortingTag(String sortingTag) {
        this.sortingTag = sortingTag;
    }
}
