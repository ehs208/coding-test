package com.seowon.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderDto {

    private final String customerName;
    private final String customerEmail;
    private final List<ProductDto> products;
}

