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

    @PostMapping("/orders")
    public ResponseEntity<OrderModel> addOrder(@RequestBody OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(orderService.addCustomer(orderCreateAndUpdateModel));
    }


}
