package com.Ecommercebackend.EcommerceApp.Service;

import com.Ecommercebackend.EcommerceApp.Enum.ProductStatus;
import com.Ecommercebackend.EcommerceApp.Exception.CustomerNotFoundException;
import com.Ecommercebackend.EcommerceApp.Exception.InsufficientQuantityException;
import com.Ecommercebackend.EcommerceApp.Exception.ProductNotFoundException;
import com.Ecommercebackend.EcommerceApp.Model.*;
import com.Ecommercebackend.EcommerceApp.Repository.CustomerRepository;
import com.Ecommercebackend.EcommerceApp.Repository.ProductRepository;
import com.Ecommercebackend.EcommerceApp.RequestDTO.OrderRequestDto;
import com.Ecommercebackend.EcommerceApp.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    JavaMailSender emailSender;

    public String addToCart(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientQuantityException {

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

        Cart cart = customer.getCart();

        int newCost = cart.getCartTotal() + orderRequestDto.getRequiredQuantity() * product.getPrice();
        cart.setCartTotal(newCost);

        //Add item to the current cart
        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        item.setCart(cart);
        item.setProduct(product);
        cart.getItems().add(item);

        customerRepository.save(customer);

        return "Item added to cart";
    }

    public List<OrderResponseDto> checkout(int customerId) throws CustomerNotFoundException {
        Customer customer;
        try{
            customer = customerRepository.findById(customerId).get();
        }catch (Exception e){
            throw new CustomerNotFoundException("Invalid Customer Id!!");
        }

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        int totalCost = customer.getCart().getCartTotal();
        Cart cart = customer.getCart();
        for (Item item : cart.getItems()){
            Ordered order = new Ordered();
            order.setTotalCost(item.getRequiredQuantity() * item.getProduct().getPrice());
            order.setDeliveryCharge(0);
            order.setCustomer(customer);
            order.getItems().add(item);

            Card card = customer.getCards().get(0);
            String cardNo = "";
            for (int i = 0;i<card.getCardNo().length()-4;i++){
                cardNo += 'X';
            }
            cardNo += card.getCardNo().substring(card.getCardNo().length()-4);
            order.setCardUsedForPayment(cardNo);

            int leftQuantity = item.getProduct().getQuantity() - item.getRequiredQuantity();
            if(leftQuantity<=0){
                item.getProduct().setProductStatus(ProductStatus.OUT_OF_STOCK);
            }
            item.getProduct().setQuantity(leftQuantity);

            customer.getOrders().add(order);

            //prepare response dto
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .productName(item.getProduct().getProductName())
                    .orderDate(order.getOrderedDate())
                    .quantityOrdered(item.getRequiredQuantity())
                    .cardUsedForPayment(cardNo)
                    .itemPrice(item.getProduct().getPrice())
                    .totalCost(order.getTotalCost())
                    .deliveryCharge(0)
                    .build();

            orderResponseDtos.add(orderResponseDto);
        }

        cart.setItems(new ArrayList<>());
        cart.setCartTotal(0);
        customerRepository.save(customer);

        String text = "Congrats your order with total value "+totalCost+" has been placed";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("johnwicksuar@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order Placed");
        message.setText(text);
        emailSender.send(message);

        return orderResponseDtos;

    }

}
