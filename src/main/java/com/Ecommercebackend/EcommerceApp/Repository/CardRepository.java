package com.Ecommercebackend.EcommerceApp.Repository;

import com.Ecommercebackend.EcommerceApp.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
}
