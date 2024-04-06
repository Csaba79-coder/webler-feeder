package hu.webler.weblerfeeder.order.controller;

import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderModel>> listAllOrders() {
        return ResponseEntity.status(200).body(orderService.getAllOrders()) ;
    }

    @GetMapping("/orders/order/address/{address}")
    public  ResponseEntity<OrderModel> getOrderModelByEmail(@PathVariable String address) {
        return ResponseEntity.status(200).body(orderService.getOrderByAddress(address));
    }

    @GetMapping("/orders/order/address2/{address}")
    public  ResponseEntity<List<OrderModel>> getOrderModelByAddress(@PathVariable String address) {
        return ResponseEntity.status(200).body(orderService.getAllOrderByAddress(address));
    }
}
