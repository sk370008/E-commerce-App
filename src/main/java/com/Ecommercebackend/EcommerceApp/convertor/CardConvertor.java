package com.Ecommercebackend.EcommerceApp.convertor;

import com.Ecommercebackend.EcommerceApp.Model.Card;
import com.Ecommercebackend.EcommerceApp.RequestDTO.CardRequestDto;

public class CardConvertor {

    public static Card cardRequestDtoToCard(CardRequestDto cardRequestDto){
        return Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cvv(cardRequestDto.getCvv())
                .cardType(cardRequestDto.getCardType())
                .build();
    }
}
