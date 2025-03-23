package com.ocean.scnext.api.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Order {

    private Long id;
    private Long customerId;
    private String orderDate;
    private String items;
}