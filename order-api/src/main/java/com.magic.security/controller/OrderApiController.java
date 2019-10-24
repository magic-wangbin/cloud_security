package com.magic.security.controller;

import com.magic.security.entity.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderApiController {

    RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderInfo createOrder(@RequestBody OrderInfo info, @RequestHeader String username) {
        log.info("user is " + username);
//        PriceInfo price = restTemplate.getForObject("http://localhost:9060/prices/" + info.getProductId(), PriceInfo.class);
//        log.info("price is " + price.getPrice());
        return info;
    }

    /**
     * TODO
     *
     * @param id
     * @param username
     * @return
     */
    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Long id, @RequestHeader String username) {
        log.info("user is " + username);
        log.info("orderId is " + id);
        OrderInfo info = new OrderInfo();
        info.setId(id);
        info.setProductId(id * 5);
        return info;
    }
}
