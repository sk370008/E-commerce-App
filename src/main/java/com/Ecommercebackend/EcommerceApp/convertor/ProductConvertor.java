package com.Ecommercebackend.EcommerceApp.convertor;

import com.Ecommercebackend.EcommerceApp.Enum.ProductStatus;
import com.Ecommercebackend.EcommerceApp.Model.Product;
import com.Ecommercebackend.EcommerceApp.RequestDTO.ProductRequestDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.ProductResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConvertor {

    public static Product productRequestDtoToProduct(ProductRequestDto productRequestDto){
        return Product.builder()
                .productName(productRequestDto.getProductName())
                .price(productRequestDto.getPrice())
                .quantity(productRequestDto.getQuantity())
                .productCategory(productRequestDto.getProductCategory())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponseDto productToProductResponseDto(Product product){

        return ProductResponseDto.builder()
                .name(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .build();
    }
}
