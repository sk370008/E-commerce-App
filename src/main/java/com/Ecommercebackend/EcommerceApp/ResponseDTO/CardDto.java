package com.Ecommercebackend.EcommerceApp.ResponseDTO;

import com.Ecommercebackend.EcommerceApp.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private String cardNo;

    private CardType cardType;
}
