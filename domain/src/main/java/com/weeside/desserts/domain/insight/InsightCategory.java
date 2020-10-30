package com.weeside.desserts.domain.insight;

public enum InsightCategory {
    GOOD_START("저번주 통계가 없을때 이번주 통계가 5점 이상인 경우"),
    BAD_START("저번주 통계가 없을때 이번주 통계가 5점 미만인 경우"),
    LESS_THAN_LAST_WEEK("저번주 통계가 있고 저번달 통계가 없을때 저번주가 이번주 점수보다 높은 경우"),
    BETTER_THAN_LAST_WEEK("저번주 통계가 있고 저번달 통계가 없을때 저번주보다 이번주 점수가 높은 경우"),
    LESS_THAN_LAST_WEEK_AND_BAD_LAST_MONTH("저번달 통계가 있는 경우 저번달이 이번달보다 높고 저번주가 이번주 점수보다 높은 경우"),
    BETTER_THAN_LAST_WEEK_AND_BAD_LAST_MONTH("저번달 통계가 있는 경우 저번달이 이번달보다 높고 저번주가 이번주 점수보다 높은 경우"),
    LESS_THAN_LAST_WEEK_AND_GOOD_LAST_MONTH("저번달 통계가 있는 경우 이번달이 저번달보다 높고 저번주가 이번주 점수보다 높은 경우"),
    BETTER_THAN_LAST_WEEK_AND_GOOD_LAST_MONTH("저번달 통계가 있는 경우 이번달이 저번달보다 높고 저번주가 이번주 점수보다 높은 경우");

    private String description;

    InsightCategory(String description) {
        this.description = description;
    }
}
