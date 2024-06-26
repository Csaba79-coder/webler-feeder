package hu.webler.weblerfeeder.order.service;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.service.CustomerService;
import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.order.entity.Order;
import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.order.repository.OrderRepository;
import hu.webler.weblerfeeder.value.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hu.webler.weblerfeeder.util.OrderMapper.mapOrderCreateAndUpdateModelToOrderEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Order service test - unit test")
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Given valid orders create model when addOrders then returns orders model")
    public void givenValidOrderCreateModel_whenAddOrder_thenReturnsOrderModel() {

        //Given
        OrderCreateAndUpdateModel orderCreateAndUpdateModel = new OrderCreateAndUpdateModel();

        Customer customer = new Customer();
        customer.setFirstName("Mikulas");
        customer.setMidName("mikcsek");
        customer.setLastName("Abraham");
        customer.setCell("123");
        customer.setEmail("mikcsek2@gmail.com");
        customer.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customer.setStatus(Status.INACTIVE);

        orderCreateAndUpdateModel.setDescription("Please, ring to second floor name: Peter");
        orderCreateAndUpdateModel.setCustomer(customer);

        //Mock customerRepository.save() to return a mock CustomerModel
        OrderModel expectedOrder = new  OrderModel();

        expectedOrder.setDescription("Please, ring to second floor name: Peter");
        expectedOrder.setCustomer(customer);

        // when(customerRepository.save(any())).thenReturn(mapCustomerCreateModelToCustomerEntity(customerCreateModel));
        when(orderRepository.save(any())).thenReturn(mapOrderCreateAndUpdateModelToOrderEntity(orderCreateAndUpdateModel));
        when(customerService.getCustomerById(any())).thenReturn(customer);

        //When
        OrderModel createdModel = orderService.addOrder(1L, orderCreateAndUpdateModel);

        //Then
        then(createdModel.getDescription()).isEqualTo(orderCreateAndUpdateModel.getDescription());
        verify(orderRepository, times(1)).save(any());
        then(expectedOrder)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate", "createdAt")
                .isEqualTo(createdModel);
    }

    @Test
    @DisplayName("Given empty order list when getAllOrder then returns empty list")
    public void givenEmptyOrdersList_whenGetAllOrders_thenReturnsEmptyList() {
        //Given
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        //When
        List<OrderModel> orders = orderService.getAllOrders();

        //Then
        then(orders).isEmpty();
    }

    @Test
    @DisplayName("Given order id when getOrderById then returns order")
    public void givenOrderId_whenGetOrderById_thenReturnsOrder() {

        //Given
        Long id = 1L;

        Order order = new Order();

        Customer customer = new Customer();
        customer.setFirstName("Mikulas");
        customer.setMidName("mikcsek");
        customer.setLastName("Abraham");
        customer.setCell("123");
        customer.setEmail("mikcsek2@gmail.com");
        customer.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customer.setStatus(Status.INACTIVE);

        order.setDescription("Please, ring to second floor name: Peter");
        order.setCustomer(customer);

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        //When
        Order existingOrder = orderService.getOrderById(id);

        //Then
        then(existingOrder).isEqualTo(order);
    }

    @Test
    @DisplayName("Given a non empty orders list when getAllOrders then returns the list of Orders models")
    public void givenNonEmptyOrdersList_whenGetAllOrders_thenReturnsTheListsOfOrderModels() {

        //Given
        Order order = new Order();

        Customer customer = new Customer();
        customer.setFirstName("Mikulas");
        customer.setMidName("mikcsek");
        customer.setLastName("Abraham");
        customer.setCell("123");
        customer.setEmail("mikcsek2@gmail.com");
        customer.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customer.setStatus(Status.INACTIVE);

        order.setDescription("Please, ring to second floor name: Peter");
        order.setCustomer(customer);

        order.setFoods(List.of(new Food("Dinnye", "Gyumi", 100d, "/images/defaultFood.png")));
        List<Order> ordersData = List.of(order);

        when(orderRepository.findAll()).thenReturn(ordersData);

        //When
        List<OrderModel>  orders = orderService.getAllOrders();

        //Then
        then(orders).hasSize(1)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate", "orders", "address")
                .isEqualTo(ordersData);
    }

    @Test
    @DisplayName("Given Order id when getOrderById then throws no such element exception")
    public void givenOrderId_whenGetOrderById_thenThrowsNoSuchElementException() {
        //Given
        Long id = 2L;

        // When / Then
        assertThatThrownBy(() -> orderService.getOrderById(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Order with id %s not found", id);

        // Ensure userRepository.save() is not called
        verify(orderRepository, never()).save(any());
    }
}