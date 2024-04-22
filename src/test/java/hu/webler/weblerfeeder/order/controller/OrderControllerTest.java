package hu.webler.weblerfeeder.order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerfeeder.base.Identifier;
import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.order.entity.Order;
import hu.webler.weblerfeeder.order.model.OrderModel;
import hu.webler.weblerfeeder.order.service.OrderService;
import hu.webler.weblerfeeder.value.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Order controller - Integration test")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("Given empty list when listAllOrders then return empty list")
    public void givenEmptyList_whenListAllOrders_thenReturnEmptyList() throws Exception {
        //Given
        given(orderService.getAllOrders()).willReturn(Collections.emptyList());
        List<OrderModel> expectedModels = new ArrayList<>();

        //When
        MvcResult result = mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andReturn();

        List<OrderModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        //Then
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isEmpty();
        assertThat(actualModels).isEqualTo(expectedModels);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(0);
    }

    @Test
    @DisplayName("Given non-empty list when listAllOrders then return non empty list")
    public void givenNonEmptyList_whenListAllOrders_thenReturnNonEmptyList() throws Exception {

        //Given
        OrderModel  orderModelOne = new OrderModel();
        Order order = new Order();

        Customer customer = new Customer();
        customer.setFirstName("Mikulas");
        customer.setMidName("mikcsek");
        customer.setLastName("Abraham");
        customer.setCell("123");
        customer.setEmail("mikcsek2@gmail.com");
        customer.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customer.setStatus(Status.INACTIVE);

        orderModelOne.setDescription("Please, ring to second floor name: Peter");
        orderModelOne.setCustomer(customer);

        //Given
        orderModelOne.setId(new Identifier().getId());
        orderModelOne.setCreatedAt(LocalDateTime.now());

        OrderModel  orderModelTwo = new OrderModel();
        orderModelTwo.setDescription("Please, ring to second floor name: Peter");
        orderModelTwo.setCustomer(customer);
        orderModelTwo.setId(new Identifier().getId());
        orderModelTwo.setCreatedAt(LocalDateTime.now());

        List<OrderModel> expectedModels = Arrays.asList(
                orderModelOne, orderModelTwo
        );

        given(orderService.getAllOrders()).willReturn(expectedModels);

        //When
        MvcResult result = mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<OrderModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isNotEmpty(); // Assert that the list is not empty
        assertThat(actualModels)
                .usingRecursiveComparison()
                .isEqualTo(expectedModels); // Assert that the lists are equal
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(expectedModels.size()); // Assert that the size of actual list matches the size of expected list
    }
}
