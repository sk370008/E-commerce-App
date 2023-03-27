package com.Ecommercebackend.EcommerceApp.Repository;

import com.Ecommercebackend.EcommerceApp.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
}
