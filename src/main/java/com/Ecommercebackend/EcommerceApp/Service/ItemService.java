package com.Ecommercebackend.EcommerceApp.Service;

import com.Ecommercebackend.EcommerceApp.Exception.ProductNotFoundException;
import com.Ecommercebackend.EcommerceApp.Model.Item;
import com.Ecommercebackend.EcommerceApp.Model.Product;
import com.Ecommercebackend.EcommerceApp.Repository.ProductRepository;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.ItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ProductRepository productRepository;

    public ItemResponseDto viewItem(int productId) throws ProductNotFoundException {
        Product product;

        try{
            product = productRepository.findById(productId).get();
        }catch (Exception e){
            throw new ProductNotFoundException("Sorry! Invalid product id.");
        }

        Item item = Item.builder()
                .requiredQuantity(0)
                .product(product)
                .build();

        //map item to product
        product.setItem(item);

        //save both item and product(product is parent)
        productRepository.save(product);

        //prepare response dto
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .build();

        return itemResponseDto;
    }
}
