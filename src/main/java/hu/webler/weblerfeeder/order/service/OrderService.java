package hu.webler.weblerfeeder.order.service;

import hu.webler.weblerfeeder.customer.service.CustomerService;
import hu.webler.weblerfeeder.exception.InvalidInputException;
import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.food.service.FoodService;
import hu.webler.weblerfeeder.order.entity.Order;
import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.order.repository.OrderRepository;
import hu.webler.weblerfeeder.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static hu.webler.weblerfeeder.util.OrderMapper.mapOrderCreateAndUpdateModelToOrderEntity;
import static hu.webler.weblerfeeder.util.OrderMapper.mapOrderEntityToOrderModel;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodService foodService;

    private final CustomerService customerService;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::mapOrderEntityToOrderModel)
                .collect(Collectors.toList());
    }

    public OrderModel addOrder(Long customerId, OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
            if(orderCreateAndUpdateModel.getDescription() != null) {
            OrderCreateAndUpdateModel newOrder = new OrderCreateAndUpdateModel();
            newOrder.setDescription(orderCreateAndUpdateModel.getDescription());
            newOrder.setCustomer(customerService.getCustomerById(customerId));
            return mapOrderEntityToOrderModel(orderRepository
                    .save(mapOrderCreateAndUpdateModelToOrderEntity(newOrder)));
        } else throw new InvalidInputException("Please fill description");
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                            String message = String.format("Order with id %s not found", id);
                            log.info(message);
                            return new NoSuchElementException(message);
                        }
        );
    }

    public Order addFoodToOrderById(Long orderId, Long foodId) {
        Order order = getOrderById(orderId);
        Food food = foodService.getFoodById(foodId);

        List<Food> foods = order.getFoods();
        foods.add(food);
        order.setFoods(foods);

        return orderRepository.save(order);
    }

    public Order removeFoodFromOrderById(Long orderId, Long foodId) {
        Order order = getOrderById(orderId);
        List<Food> foods = order.getFoods();
        foods.remove(foodService.getFoodById(foodId));
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(getOrderById(id));
    }

    public OrderModel updateOrder(Long id, OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        Order existingOrder = getOrderById(id);
        addNewDataToExistingOrder(existingOrder, orderCreateAndUpdateModel);
        return mapOrderEntityToOrderModel(orderRepository.save(existingOrder));
    }

    private void addNewDataToExistingOrder(Order existingOrder, OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        if (orderCreateAndUpdateModel.getDescription()  != null) {
            existingOrder.setDescription(orderCreateAndUpdateModel.getDescription());
        } else throw new InvalidInputException("Please fill description");
    }
}
