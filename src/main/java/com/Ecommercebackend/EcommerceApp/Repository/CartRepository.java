package com.Ecommercebackend.EcommerceApp.Repository;

import com.Ecommercebackend.EcommerceApp.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
}
