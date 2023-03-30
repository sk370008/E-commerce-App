package com.Ecommercebackend.EcommerceApp.Controller;

import com.Ecommercebackend.EcommerceApp.Exception.CustomerNotFoundException;
import com.Ecommercebackend.EcommerceApp.Exception.InsufficientQuantityException;
import com.Ecommercebackend.EcommerceApp.Exception.ProductNotFoundException;
import com.Ecommercebackend.EcommerceApp.RequestDTO.OrderRequestDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.OrderResponseDto;
import com.Ecommercebackend.EcommerceApp.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestBody OrderRequestDto orderRequestDto) throws InsufficientQuantityException, CustomerNotFoundException, ProductNotFoundException {

        String response = "";
        try{
            response = cartService.addToCart(orderRequestDto);
        }catch (Exception e){
            return e.getMessage();
        }

        return response;
    }

    @PostMapping("/checkout/{customerId}")
    public ResponseEntity checkoutCart(@PathVariable("customerId") int customerId){

        List<OrderResponseDto> orderResponseDtos;
        try{
            orderResponseDtos = cartService.checkout(customerId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orderResponseDtos,HttpStatus.ACCEPTED);
    }

}
