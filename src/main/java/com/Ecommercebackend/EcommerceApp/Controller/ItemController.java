package com.Ecommercebackend.EcommerceApp.Controller;

import com.Ecommercebackend.EcommerceApp.Exception.ProductNotFoundException;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.ItemResponseDto;
import com.Ecommercebackend.EcommerceApp.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/view/{productId}")
    public ResponseEntity viewItem(@PathVariable("productId") int productId) throws ProductNotFoundException {
        ItemResponseDto itemResponseDto;

        try{
            itemResponseDto = itemService.viewItem(productId);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(itemResponseDto,HttpStatus.ACCEPTED);
    }


}
