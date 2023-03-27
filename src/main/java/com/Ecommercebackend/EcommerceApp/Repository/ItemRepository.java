package com.Ecommercebackend.EcommerceApp.Repository;

import com.Ecommercebackend.EcommerceApp.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
}
