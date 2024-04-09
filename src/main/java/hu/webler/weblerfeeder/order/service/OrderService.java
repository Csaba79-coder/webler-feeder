package hu.webler.weblerfeeder.order.service;

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
        if(isAllFieldsContainData(orderCreateAndUpdateModel) && isAllFieldsContainDataCustomer(orderCreateAndUpdateModel)) {
            return OrderMapper.mapOrderEntityToOrderModel(orderRepository
                    .save(OrderMapper.mapOrderCreateAndUpdateModelToOrderEntity(orderCreateAndUpdateModel)));
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

    public Order removeFoodFromOrderById(Long id) {
        Order order = getOrderById(id);
        order.getFoods().removeAll(order.getFoods());
        return orderRepository.save(order);
    }

    public void deleteCustomer(Long id) {
        orderRepository.delete(getOrderById(id));
    }

    public OrderModel updateOrder(Long id, OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        Order existingOrder = getOrderById(id);
        addNewDataToExistingCustomer(existingOrder, orderCreateAndUpdateModel);
        return OrderMapper.mapOrderEntityToOrderModel(orderRepository.save(existingOrder));
    }

    private void addNewDataToExistingCustomer(Order existingOrder, OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        if (orderCreateAndUpdateModel.getDescription()  != null && (!orderCreateAndUpdateModel.getDescription().equals("")) )
        {
            existingOrder.setDescription(orderCreateAndUpdateModel.getDescription());
        }

        if(orderCreateAndUpdateModel.getCustomer() != null )  {

            if (orderCreateAndUpdateModel.getCustomer().getFirstName() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getFirstName().equals(""))
            {
                existingOrder.getCustomer().setFirstName(orderCreateAndUpdateModel.getCustomer().getFirstName());
            }

            if (orderCreateAndUpdateModel.getCustomer().getMidName() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getMidName().equals(""))
            {
                existingOrder.getCustomer().setMidName(orderCreateAndUpdateModel.getCustomer().getMidName());
            }

            if (orderCreateAndUpdateModel.getCustomer().getLastName() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getLastName().equals(""))
            {
                existingOrder.getCustomer().setLastName(orderCreateAndUpdateModel.getCustomer().getLastName());
            }

            if( orderCreateAndUpdateModel.getCustomer().getCell() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getCell().equals("")) {
                existingOrder.getCustomer().setCell(orderCreateAndUpdateModel.getCustomer().getCell());
            }

            if( orderCreateAndUpdateModel.getCustomer().getStreetAndNumber() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getStreetAndNumber().equals("")) {
                existingOrder.getCustomer().setStreetAndNumber(orderCreateAndUpdateModel.getCustomer().getStreetAndNumber());
            }

           if ( orderCreateAndUpdateModel.getCustomer().getCity() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getCity().equals("")) {
               existingOrder.getCustomer().setCity(orderCreateAndUpdateModel.getCustomer().getCity());
           }

            if ( orderCreateAndUpdateModel.getCustomer().getPostalCode() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getPostalCode().equals("")) {
                existingOrder.getCustomer().setPostalCode(orderCreateAndUpdateModel.getCustomer().getPostalCode());
            }

            if(orderCreateAndUpdateModel.getCustomer().getDateOfBirth() != null &&
                    !orderCreateAndUpdateModel.getCustomer().getDateOfBirth().equals("")) {
                existingOrder.getCustomer().setDateOfBirth(orderCreateAndUpdateModel.getCustomer().getDateOfBirth());
            }
        }
    }

    private boolean isAllFieldsContainData(OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        if (orderCreateAndUpdateModel.getDescription() != null && (!orderCreateAndUpdateModel.getDescription().equals("")) ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }

    private boolean isAllFieldsContainDataCustomer(OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        if (
                        orderCreateAndUpdateModel.getCustomer().getFirstName() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getFirstName().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getMidName() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getMidName().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getLastName() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getLastName().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getCell() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getCell().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getStreetAndNumber() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getStreetAndNumber().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getCity() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getCity().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getPostalCode() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getPostalCode().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getEmail() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getEmail().equals("") &&
                        orderCreateAndUpdateModel.getCustomer().getDateOfBirth() != null &&
                        !orderCreateAndUpdateModel.getCustomer().getDateOfBirth().equals("")

        ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }
}
