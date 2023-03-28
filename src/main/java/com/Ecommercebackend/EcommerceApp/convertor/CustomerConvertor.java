package com.Ecommercebackend.EcommerceApp.convertor;

import com.Ecommercebackend.EcommerceApp.Model.Customer;
import com.Ecommercebackend.EcommerceApp.RequestDTO.CustomerRequestDto;


public class CustomerConvertor {

    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .name(customerRequestDto.getName())
                .age(customerRequestDto.getAge())
                .email(customerRequestDto.getEmail())
                .mobNo(customerRequestDto.getMobNo())
                .build();
    }
}
