package com.seata.demo.controller;

import com.seata.demo.mapper.goodsdb.GoodsMapper;
import com.seata.demo.mapper.orderdb.OrderMapper;
import com.seata.demo.pojo.Goods;

import javax.annotation.Resource;
import com.seata.demo.pojo.Order;
import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/home")
public class HomeController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsMapper goodsMapper;

    //添加一个订单,访问两个数据库
    @GlobalTransactional(timeoutMills = 300000,rollbackFor = Exception.class)
    @GetMapping("/addorderseata")
    public String addOrderSeata(@RequestParam(value="isfail",required = true,defaultValue = "0") int isFail) {

        String goodsId = "3";
        String goodsNum = "1";

        Order order = new Order();
        //得到sn
        String orderSn = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        order.setOrderSn(orderSn);
        order.setOrderStatus(0);
        order.setPrice(new BigDecimal(100.00));
        order.setUserId(8);

        int orderId = orderMapper.insertOneOrder(order);
        System.out.println("orderId:"+order.getOrderId());

        int count = -1;
        int res = goodsMapper.updateGoodsStock(Long.parseLong(goodsId),count);
        System.out.println("res:"+res);

        if (isFail == 1) {
            int divide = 0;
            int resul = 100 / divide;
        }

        if (res>0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }


    //添加一个订单,访问两个url(分别访问不同的数据库)
    @GlobalTransactional(timeoutMills = 300000,rollbackFor = Exception.class)
    @GetMapping("/addorderseatarest")
    public String addOrderSeataRest(@RequestParam(value="isfail",required = true,defaultValue = "0") int isFail) {
        String goodsId = "3";
        String goodsNum = "1";

        RestTemplate restTemplate = new RestTemplate();
        String xid = RootContext.getXID();
        System.out.println("xid before send:"+xid);
        if (StringUtils.isEmpty(xid)) {
            System.out.println("xid is null,return");
            return FAIL;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(RootContext.KEY_XID, xid);

        System.out.println("xid not null");
        String urlAddOrder = "http://127.0.0.1:8080/order/orderadd/"+goodsId+"/"+goodsNum+"/";

        String resultAdd = restTemplate.postForObject(urlAddOrder,new HttpEntity<String>(headers),String.class);
        if (!SUCCESS.equals(resultAdd)) {
            throw new RuntimeException();
        }

        String goodsUPNum = "-1";
        String urlUpStock = "http://127.0.0.1:8080/goods/goodsstock/"+goodsId+"/"+goodsUPNum+"/";
        String resultUp = restTemplate.postForObject(urlUpStock,new HttpEntity<String>(headers),String.class);
        if (!SUCCESS.equals(resultUp)) {
            throw new RuntimeException();
        }
        if (isFail == 1) {
            int divide = 0;
            int resul = 100 / divide;
        }
        return SUCCESS;
    }
}

