package com.Ecommercebackend.EcommerceApp.Controller;

import com.Ecommercebackend.EcommerceApp.RequestDTO.CustomerRequestDto;
import com.Ecommercebackend.EcommerceApp.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public String addCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        return customerService.addCustomer(customerRequestDto);

    }

    //get customer by id

    //view all customers

    //delete customer by id

    //get a customer by email

    //update customer
}
