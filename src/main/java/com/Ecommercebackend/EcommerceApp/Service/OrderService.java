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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    JavaMailSender emailSender;

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


        int totalCostOfCurrent = orderRequestDto.getRequiredQuantity() * product.getPrice();
        int deliveryChargeOfCurrent = 0;
        if(totalCostOfCurrent < 500){
            deliveryChargeOfCurrent = 50;
            totalCostOfCurrent += deliveryChargeOfCurrent;
        }

        Ordered order = new Ordered();
        order.setTotalCost(totalCostOfCurrent);
        order.setDeliveryCharge(deliveryChargeOfCurrent);

        //prepare the card String
        Card card = customer.getCards().get(0);
        String cardUsed = "";
        int cardNo = card.getCardNo().length();
        for(int i = 0;i<cardNo-4;i++){
            cardUsed += 'X';
        }
        cardUsed += card.getCardNo().substring(cardNo-4);
        order.setCardUsedForPayment(cardUsed);

        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        item.setProduct(product);
        item.setOrder(order);
        order.getItems().add(item);
        order.setCustomer(customer);

        //update the quantity of product left in stock
        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        if(leftQuantity <= 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
        product.setQuantity(leftQuantity);

        customer.getOrders().add(order);
        Customer savedCustomer = customerRepository.save(customer);
        Ordered savedOrder = savedCustomer.getOrders().get(savedCustomer.getOrders().size()-1);


        //Prepare response dto
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .productName(product.getProductName())
                .orderDate(savedOrder.getOrderedDate())
                .quantityOrdered(orderRequestDto.getRequiredQuantity())
                .cardUsedForPayment(cardUsed)
                .itemPrice(product.getPrice())
                .totalCost(order.getTotalCost())
                .deliveryCharge(savedOrder.getDeliveryCharge())
                .build();

        // send an email
        String text = "Congrats your order with total value "+order.getTotalCost()+" has been placed";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("johnwicksuar@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order Placed Notification(direct buy)");
        message.setText(text);
        emailSender.send(message);

        return orderResponseDto;
    }
}
