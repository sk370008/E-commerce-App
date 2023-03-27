package com.Ecommercebackend.EcommerceApp.Service;

import com.Ecommercebackend.EcommerceApp.Model.Seller;
import com.Ecommercebackend.EcommerceApp.Repository.SellerRepository;
import com.Ecommercebackend.EcommerceApp.RequestDTO.SellerRequestDto;
import com.Ecommercebackend.EcommerceApp.convertor.SellerConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SellerService{

    @Autowired
    SellerRepository sellerRepository;

    public String addSeller(SellerRequestDto sellerRequestDto){

        Seller seller = SellerConvertor.SellerRequestDtoToSeller(sellerRequestDto);

        sellerRepository.save(seller);
        return "Seller Added";
    }



}