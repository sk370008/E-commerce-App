package com.Ecommercebackend.EcommerceApp.Repository;

import com.Ecommercebackend.EcommerceApp.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
