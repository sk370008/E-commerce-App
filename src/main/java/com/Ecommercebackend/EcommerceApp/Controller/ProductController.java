package com.Ecommercebackend.EcommerceApp.Controller;

import com.Ecommercebackend.EcommerceApp.Enum.ProductCategory;
import com.Ecommercebackend.EcommerceApp.Exception.SellerNotFoundException;
import com.Ecommercebackend.EcommerceApp.RequestDTO.ProductRequestDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.ProductResponseDto;
import com.Ecommercebackend.EcommerceApp.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto) throws SellerNotFoundException {

        ProductResponseDto productResponseDto;

        try{
            productResponseDto = productService.addProduct(productRequestDto);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }

    @GetMapping("/get/category/{productCategory}")
    public List<ProductResponseDto> getAllProductsByCategory(@PathVariable("productCategory") ProductCategory productCategory){
        return productService.getProductsByCategory(productCategory);
    }

}
