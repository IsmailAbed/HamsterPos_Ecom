package com.simple.ecommerce.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveFromCartdto {

    private Long productId;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
