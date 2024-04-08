package hu.webler.weblerfeeder.order.service;

import hu.webler.weblerfeeder.exception.InvalidInputException;
import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.repository.FoodRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodService foodService;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::mapOrderEntityToOrderModel)
                .collect(Collectors.toList());
    }

    public OrderModel addOrder(OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        if(isAllFieldsContainData(orderCreateAndUpdateModel)) {
            return OrderMapper.mapOrderEntityToOrderModel(orderRepository.save(OrderMapper.mapOrderCreateAndUpdateModelToOrderEntity(orderCreateAndUpdateModel)));
        }
        return null;
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

    public Order addFoodToOrderById(Long id, Long id2) {
        Order order = getOrderById(id);
        Food food = foodService.getFoodById(id2);

        List<Food> foods = order.getFoods();
        foods.add(food);
        order.setFoods(foods);

        return orderRepository.save(order);
    }
    ;

    public Order removeFoodFromOrderById(Long id) {
        Order order = getOrderById(id);

        order.getFoods().removeAll(order.getFoods());

        return orderRepository.save(order);
    }
    ;

    public void deleteCustomer(Long id) {
        orderRepository.delete(getOrderById(id));
    }

    public Order updateOrder(Long id, OrderCreateAndUpdateModel orderCreateAndUpdateModel) {

        return null;
    }

    private boolean isAllFieldsContainData(OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        if (orderCreateAndUpdateModel.getDescription() != null && (!orderCreateAndUpdateModel.getDescription().equals("")) ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }
}
