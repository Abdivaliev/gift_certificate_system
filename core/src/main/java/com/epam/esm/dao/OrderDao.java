package com.epam.esm.dao;

import com.epam.esm.dto.Pageable;
import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderDao extends CRDDao<Order> {
    List<Order> findAllByUserId(long userId, Pageable pageable);
}
