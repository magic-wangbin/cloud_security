package com.magic.security.controller;

import com.magic.security.entity.OrderInfo;
import com.magic.security.service.resource.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderApiController {

    RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderInfo createOrder(@RequestBody OrderInfo info, @AuthenticationPrincipal User user) {
        log.info("user is " + user.getId() + "username=" + user.getUsername());
//        PriceInfo price = restTemplate.getForObject("http://localhost:9060/prices/" + info.getProductId(), PriceInfo.class);
//        log.info("price is " + price.getPrice());
        return info;
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    /**
     * TODO
     *
     * @param id
     * @param username
     * @return
     */
    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Long id) {
        return null;
    }
}
