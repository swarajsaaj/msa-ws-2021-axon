package com.example.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.OrderDetails;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, String> {

}
