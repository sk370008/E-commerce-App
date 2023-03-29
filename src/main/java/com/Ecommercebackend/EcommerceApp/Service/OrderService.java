package com.Ecommercebackend.EcommerceApp.Service;

import com.Ecommercebackend.EcommerceApp.Enum.ProductStatus;
import com.Ecommercebackend.EcommerceApp.Exception.CustomerNotFoundException;
import com.Ecommercebackend.EcommerceApp.Exception.InsufficientQuantityException;
import com.Ecommercebackend.EcommerceApp.Exception.ProductNotFoundException;
import com.Ecommercebackend.EcommerceApp.Model.*;
import com.Ecommercebackend.EcommerceApp.Repository.CustomerRepository;
import com.Ecommercebackend.EcommerceApp.Repository.ProductRepository;
import com.Ecommercebackend.EcommerceApp.RequestDTO.OrderRequestDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.ItemResponseDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ItemService itemService;

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientQuantityException {
        Customer customer;
        try{
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }catch (Exception e){
            throw new CustomerNotFoundException("Invalid customer id");
        }

        Product product;

        try{
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }catch (Exception e){
            throw new ProductNotFoundException("Invalid product id");
        }

        if(product.getQuantity() < orderRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Required quantity not available");
        }

        //Create item
        Item item = Item.builder()
                .product(product)
                .requiredQuantity(orderRequestDto.getRequiredQuantity())
                .build();



        //Prepare order

        int totalCost = orderRequestDto.getRequiredQuantity() * product.getPrice();
        int deliveryCharge = 0;
        if(totalCost<500){
            deliveryCharge = 50;
            totalCost += deliveryCharge;
        }
        Ordered order = Ordered.builder()
                .totalCost(totalCost)
                .deliveryCharge(deliveryCharge)
                .customer(customer)
                .build();

        //prepare the card String
        Card card = customer.getCards().get(0);
        String cardUsed = "";
        int cardNo = card.getCardNo().length();
        for(int i = 0;i<cardNo-4;i++){
            cardUsed += 'X';
        }
        cardUsed += card.getCardNo().substring(cardNo-4);
        order.setCardUsedForPayment(cardUsed);

        //update customer current order list
        customer.getOrders().add(order);

        //update the quantity of product left in stock
        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        if(leftQuantity <= 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
        product.setQuantity(leftQuantity);

        //update item
        item.setOrder(order);

        //save product-item and customer order
        customerRepository.save(customer);
        productRepository.save(product);

        //Prepare response dto
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .productName(product.getProductName())
                .orderDate(order.getOrderedDate())
                .quantityOrdered(orderRequestDto.getRequiredQuantity())
                .cardUsedForPayment(cardUsed)
                .itemPrice(product.getPrice())
                .totalCost(order.getTotalCost())
                .deliveryCharge(deliveryCharge)
                .build();

        return orderResponseDto;
    }
}
