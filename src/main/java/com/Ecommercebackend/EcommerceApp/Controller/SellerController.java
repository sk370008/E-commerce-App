package com.Ecommercebackend.EcommerceApp.Controller;

import com.Ecommercebackend.EcommerceApp.RequestDTO.SellerRequestDto;
import com.Ecommercebackend.EcommerceApp.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public String addSeller(@RequestBody SellerRequestDto sellerRequestDto){
        return sellerService.addSeller(sellerRequestDto);
    }



    //Get all sellers ,, DTos ,, return type List<SellerResponseDto>

    //Get a seller by PAN card  ,, using custom query  findByPanCard

    //find sellers of a particular age  ,, using custom query


}
