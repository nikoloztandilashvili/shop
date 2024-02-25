package com.mziuri;

import lombok.Getter;

@Getter
public class GetProductResponse {
    private String name;
    private Integer remainingAmount;

    public GetProductResponse(String name, Integer remainingAmount) {
        this.name = name;
        this.remainingAmount = remainingAmount;
    }
}
