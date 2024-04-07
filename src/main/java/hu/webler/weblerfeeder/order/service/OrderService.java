package hu.webler.weblerfeeder.order.service;

import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.order.repository.OrderRepository;
import hu.webler.weblerfeeder.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::mapOrderEntityToOrderModel)
                .collect(Collectors.toList());
    }

    public OrderModel addCustomer(OrderCreateAndUpdateModel orderCreateAndUpdateModel) {
        System.out.println(orderCreateAndUpdateModel);
        return OrderMapper.mapOrderEntityToOrderModel(orderRepository.save(OrderMapper.mapOrderCreateAndUpdateModelToOrderEntity(orderCreateAndUpdateModel)));
    }
}
