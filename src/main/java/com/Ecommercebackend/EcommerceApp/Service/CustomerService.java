package com.Ecommercebackend.EcommerceApp.Service;

import com.Ecommercebackend.EcommerceApp.Model.Cart;
import com.Ecommercebackend.EcommerceApp.Model.Customer;
import com.Ecommercebackend.EcommerceApp.Repository.CustomerRepository;
import com.Ecommercebackend.EcommerceApp.RequestDTO.CustomerRequestDto;
import com.Ecommercebackend.EcommerceApp.convertor.CustomerConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    public String addCustomer(CustomerRequestDto customerRequestDto) {

        Customer customer = CustomerConvertor.customerRequestDtoToCustomer(customerRequestDto);

        //allocate a cart to customer
        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(customer);

        //set cart in customer
        customer.setCart(cart);

        customerRepository.save(customer);

        return "New Customer added";
    }
}
