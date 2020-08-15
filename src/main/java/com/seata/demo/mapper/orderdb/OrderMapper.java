package com.seata.demo.mapper.orderdb;

import com.seata.demo.pojo.Goods;
import com.seata.demo.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderMapper {
    Order selectOneOrder(Long orderId);
    int insertOneOrder(Order order);
}
