package hu.webler.weblerfeeder.order.controller;

import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public  ResponseEntity<OrderModel> getOrderByAddress(@PathVariable String address) {
        return ResponseEntity.status(200).body(orderService.getOrderByAddress(address));
    }

    @GetMapping("/orders/orders/address/{address}")
    public  ResponseEntity<List<OrderModel>> getOrdersByAddress(@PathVariable String address) {
        return ResponseEntity.status(200).body(orderService.getAllOrderByAddress(address));
    }

    @PostMapping("/customers")
    public ResponseEntity<OrderModel> addOrder(@RequestBody OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(orderService.addCustomer(orderCreateAndUpdateModel));
    }
}
