package com.Ecommercebackend.EcommerceApp.Service;

import com.Ecommercebackend.EcommerceApp.Enum.ProductCategory;
import com.Ecommercebackend.EcommerceApp.Exception.SellerNotFoundException;
import com.Ecommercebackend.EcommerceApp.Model.Product;
import com.Ecommercebackend.EcommerceApp.Model.Seller;
import com.Ecommercebackend.EcommerceApp.Repository.ProductRepository;
import com.Ecommercebackend.EcommerceApp.Repository.SellerRepository;
import com.Ecommercebackend.EcommerceApp.RequestDTO.ProductRequestDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.ProductResponseDto;
import com.Ecommercebackend.EcommerceApp.convertor.ProductConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException {

        Seller seller;
        try{
            seller = sellerRepository.findById(productRequestDto.getSellerId()).get();
        }
        catch (Exception e){
            throw new SellerNotFoundException("Invalid seller Id");
        }
        Product product = ProductConvertor.productRequestDtoToProduct(productRequestDto);

        product.setSeller(seller);

        seller.getProducts().add(product);

        //saved the seller and product -- as seller is parent and product is child ,it will cascade the update in product table
        sellerRepository.save(seller);

        ProductResponseDto productResponseDto = ProductConvertor.productToProductResponseDto(product);

        return productResponseDto;


    }

    public List<ProductResponseDto> getProductsByCategory(ProductCategory productCategory) {

        List<Product> products = productRepository.findAllByProductCategory(productCategory);
        //prepare a list of response dtos
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (Product product :products){
            ProductResponseDto productResponseDto = ProductConvertor.productToProductResponseDto(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }
}
