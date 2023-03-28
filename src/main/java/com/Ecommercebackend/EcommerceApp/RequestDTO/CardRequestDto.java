package com.Ecommercebackend.EcommerceApp.RequestDTO;

import com.Ecommercebackend.EcommerceApp.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequestDto {

    private int customerId;

    private String cardNo;

    private int cvv;

    private CardType cardType;
}
