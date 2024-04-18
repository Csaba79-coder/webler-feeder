package hu.webler.weblerfeeder.order.util;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.order.entity.Order;
import hu.webler.weblerfeeder.order.model.OrderCreateAndUpdateModel;
import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.value.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static hu.webler.weblerfeeder.util.OrderMapper.mapOrderCreateAndUpdateModelToOrderEntity;
import static hu.webler.weblerfeeder.util.OrderMapper.mapOrderEntityToOrderModel;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Order mapper - unit test")
public class OrderMapperTest {

    @Test
    @DisplayName("Given Order create model when mapping to entity then returns order entity")
    public void givenOrderCreateModel_whenMappingToEntity_thenReturnsOrderEntity() {

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

        //When
        Order order = mapOrderCreateAndUpdateModelToOrderEntity(orderCreateAndUpdateModel);

        //Then
        then(orderCreateAndUpdateModel)
                .usingRecursiveComparison()
                .isEqualTo(order);
    }

    @Test
    @DisplayName("Given Order entity when mapping to order model then returns order model")
    public void givenOrderEntity_whenMappingToOrderModel_thenReturnsOrderModel() {
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

        OrderModel expectedOrderModel = new OrderModel();

        expectedOrderModel.setDescription("Please, ring to second floor name: Peter");
        expectedOrderModel.setCustomer(customer);

        //When
        OrderModel orderModel1 = mapOrderEntityToOrderModel(order);

        //Then
        then(orderModel1)
                .usingRecursiveComparison()
                .ignoringFields("registrationDate", "createdAt")
                .isEqualTo(expectedOrderModel);
    }
}