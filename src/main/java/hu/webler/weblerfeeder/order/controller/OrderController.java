package hu.webler.weblerfeeder.order.controller;

import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.webler.weblerfeeder.util.OrderMapper;

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
    public ResponseEntity<OrderModel> addOrderWithCustomer(@RequestBody OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(orderService.addOrder(orderCreateAndUpdateModel));
    }

    @PostMapping("/orders/order/id/{id}/food/{id2}")
    public ResponseEntity<OrderModel> addFoodToOrder(@PathVariable Long id, @PathVariable Long id2) {
        return ResponseEntity.status(200).body((OrderMapper.mapOrderEntityToOrderModel(orderService.addFoodToOrderById(id,id2))));
    }

    @DeleteMapping("/orders/order/foods/id/{id}")
    public ResponseEntity<OrderModel> removeFoodFromOrder(@PathVariable Long id) {
        return ResponseEntity.status(200).body((OrderMapper.mapOrderEntityToOrderModel(orderService.removeFoodFromOrderById(id))));
    }

    @GetMapping("/orders/order/id/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable Long id) {
        return ResponseEntity.status(200).body((OrderMapper.mapOrderEntityToOrderModel(orderService.getOrderById(id))));
    }

    @DeleteMapping("/orders/order/id/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteCustomer(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/orders/order/id/{id}")
    public ResponseEntity<OrderModel> updateOrder(@PathVariable Long id, @RequestBody OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(orderService.updateOrder(id, orderCreateAndUpdateModel));
    }
}
