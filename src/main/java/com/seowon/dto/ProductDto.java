package com.seowon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductDto {
    private final Long productId;
    private final Integer quantity;
}

