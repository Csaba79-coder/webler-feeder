package hu.webler.weblerfeeder.order.service;

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

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::mapOrderEntityToOrderModel)
                .collect(Collectors.toList());
    }

    public OrderModel getOrderByAddress(String address) {
        return OrderMapper.mapOrderEntityToOrderModel(
                orderRepository
                        .findByAddress(address)
                        .orElseThrow(() -> {
            String message = String.format("User with this address %s not found", address);
            log.info(message);
            return new NoSuchElementException(message);
        }))
                ;
    }

    public List<OrderModel> getAllOrderByAddress(String address) {
        return orderRepository.findAllByAddress(address)
                .stream()
                .map(OrderMapper::mapOrderEntityToOrderModel)
                .collect(Collectors.toList());
    }
}
