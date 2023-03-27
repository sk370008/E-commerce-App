package com.Ecommercebackend.EcommerceApp.convertor;

import com.Ecommercebackend.EcommerceApp.Model.Seller;
import com.Ecommercebackend.EcommerceApp.RequestDTO.SellerRequestDto;

public class SellerConvertor {

    public static Seller SellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){

        return Seller.builder()
                .name(sellerRequestDto.getName())
                .email(sellerRequestDto.getEmail())
                .mobNo(sellerRequestDto.getMobNo())
                .panNo(sellerRequestDto.getPanNo())
                .build();
    }
}
