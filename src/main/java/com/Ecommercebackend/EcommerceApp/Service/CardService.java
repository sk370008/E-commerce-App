package com.Ecommercebackend.EcommerceApp.Service;

import com.Ecommercebackend.EcommerceApp.Exception.CustomerNotFoundException;
import com.Ecommercebackend.EcommerceApp.Model.Card;
import com.Ecommercebackend.EcommerceApp.Model.Customer;
import com.Ecommercebackend.EcommerceApp.Repository.CustomerRepository;
import com.Ecommercebackend.EcommerceApp.RequestDTO.CardRequestDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.CardDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.CardResponseDto;
import com.Ecommercebackend.EcommerceApp.convertor.CardConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CustomerRepository customerRepository;

    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException {

        Customer customer;
        try{
            customer = customerRepository.findById(cardRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new CustomerNotFoundException("Invalid customer id");
        }

        //Make a card object
        Card card = CardConvertor.cardRequestDtoToCard(cardRequestDto);

        card.setCustomer(customer);

        //Add the card to list

        customer.getCards().add(card);

        customerRepository.save(customer);   //this will save both customer and card

        //prepare response dto
        CardResponseDto cardResponseDto = new CardResponseDto();
        cardResponseDto.setName(customer.getName());

        //Convert every card to cardDto
        List<CardDto> cardDtoList = new ArrayList<>();

        for (Card card1 : customer.getCards()){
            CardDto cardDto = new CardDto();
            cardDto.setCardNo(card1.getCardNo());
            cardDto.setCardType(card1.getCardType());

            cardDtoList.add(cardDto);
        }
        cardResponseDto.setCardDtos(cardDtoList);

        return cardResponseDto;
    }


}
