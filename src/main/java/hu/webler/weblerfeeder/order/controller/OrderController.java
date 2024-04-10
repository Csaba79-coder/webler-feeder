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

    @PostMapping("/orders/{customerId}")
    public ResponseEntity<OrderModel> addOrderToCustomer(@PathVariable Long customerId,
                                                         @RequestBody OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(orderService.addOrder(customerId, orderCreateAndUpdateModel));
    }

    @PostMapping("/orders/order/id/{orderId}/food/id/{foodId}")
    public ResponseEntity<OrderModel> addFoodToOrder(@PathVariable Long orderId, @PathVariable Long foodId) {
        return ResponseEntity.status(200).body((OrderMapper
                .mapOrderEntityToOrderModel(orderService.addFoodToOrderById(orderId, foodId))));
    }

    @DeleteMapping("/orders/order/id/{orderId}/food/id/{foodId}")
    public ResponseEntity<OrderModel> removeFoodFromOrder(@PathVariable Long orderId, @PathVariable Long foodId) {
        return ResponseEntity.status(200).body((OrderMapper
                .mapOrderEntityToOrderModel(orderService.removeFoodFromOrderById(orderId, foodId))));
    }

    @GetMapping("/orders/order/id/{orderId}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.status(200).body((OrderMapper
                .mapOrderEntityToOrderModel(orderService.getOrderById(orderId))));
    }

    @PutMapping("/orders/order/id/{orderId}")
    public ResponseEntity<OrderModel> updateOrder(@PathVariable Long orderId,
                                                  @RequestBody OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(orderService.updateOrder(orderId, orderCreateAndUpdateModel));
    }

    @DeleteMapping("/orders/order/id/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(204).build();
    }
}
