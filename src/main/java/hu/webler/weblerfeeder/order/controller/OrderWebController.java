package hu.webler.weblerfeeder.order.controller;

import hu.webler.weblerfeeder.customer.service.CustomerService;
import hu.webler.weblerfeeder.food.service.FoodService;
import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class OrderWebController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final FoodService foodService;

    @GetMapping(value = "/orders")
    public String renderAllOrdersOnWeb(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping(value = "/create-order")
    public String addEmptyOrderToCustomerByCustomerIdOnWeb(@RequestParam Long customerId,
                                                           Model model) {
        model.addAttribute("customers", customerService.getCustomerById(customerId));
        return "create-order";
    }

    @GetMapping(value = "/create-order/order")
    public String renderOrderByOrderIdOnWeb(@RequestParam Long customerId,
                                            @RequestParam Long orderId,
                                            Model model) {
        model.addAttribute("customers", customerService.getCustomerById(customerId));
        model.addAttribute("orders", orderService.getOrderById(orderId));
        model.addAttribute("foods", foodService.getAllFoods());
        return "order";
    }
    @GetMapping(value = "/create-order/order/add-food")
    public void addFoodToOrderOnWeb(@RequestParam Long foodId, @RequestParam Long orderId) {
        orderService.addFoodToOrderById(orderId, foodId);
    }

    @GetMapping(value = "/update-order")
    public String updateOrderOnWeb(@RequestParam Long orderId,
                                   Model model) {
        model.addAttribute("orders", orderService.getOrderById(orderId));
        return "update-order";
    }

    @PostMapping(value = "/orders/order/create")
    public String addOrderOnWeb(@RequestParam String description,
                                Long customerId) {
        OrderCreateAndUpdateModel newOrder = new OrderCreateAndUpdateModel();
        newOrder.setDescription(description);
        orderService.addOrder(customerId, newOrder);
        return "redirect:/orders";
    }

    @PostMapping(value = "/orders/order/update")
    public String updateOrderOnWeb(@RequestParam String description,
                                   Long orderId) {
        OrderCreateAndUpdateModel orderCreateAndUpdateModel = new OrderCreateAndUpdateModel();
        orderCreateAndUpdateModel.setDescription(description);
        orderService.updateOrder(orderId, orderCreateAndUpdateModel);
        return "redirect:/orders";
    }

    @PostMapping(value = "/orders/order")
    public String deleteOrderOnWeb(@RequestParam Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
