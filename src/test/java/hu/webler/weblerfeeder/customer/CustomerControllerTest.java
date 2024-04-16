package hu.webler.weblerfeeder.customer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerfeeder.base.Identifier;
import hu.webler.weblerfeeder.customer.controller.CustomerController;
import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import hu.webler.weblerfeeder.customer.service.CustomerService;
import hu.webler.weblerfeeder.value.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Customer controller - Integration test")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("Given empty list when listAllCustomers then return empty list")
    public void givenEmptyList_whenListAllCustomers_thenReturnEmptyList() throws Exception {
        //Given
        given(customerService.getAllCustomers()).willReturn(Collections.emptyList());
        List<CustomerModel> expectedModels = new ArrayList<>();

        //When
        MvcResult result = mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

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
    @DisplayName("Given non-empty list when listAllCustomers then return non empty list")
    public void givenNonEmptyList_whenListAllCustomers_thenReturnNonEmptyList() throws Exception {
        //Given
        CustomerModel customerModelOne = new CustomerModel();
        customerModelOne.setId(new Identifier().getId());
        customerModelOne.setRegistrationDate(LocalDateTime.now());
        customerModelOne.setFirstName("Mikulas");
        customerModelOne.setMidName("mikcsek");
        customerModelOne.setLastName("Abraham");
        customerModelOne.setStreetAndNumber("Klapka 44B");
        customerModelOne.setCity("Komarom");
        customerModelOne.setPostalCode("2900");
        customerModelOne.setCell("06702147621");
        customerModelOne.setEmail("mikcsek2@gmail.com");
        customerModelOne.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customerModelOne.setStatus(Status.INACTIVE);

        CustomerModel customerModelTwo = new CustomerModel();
        customerModelTwo.setId(new Identifier().getId());
        customerModelTwo.setRegistrationDate(LocalDateTime.now());
        customerModelTwo.setFirstName("Ferenc");
        customerModelTwo.setMidName("nincs");
        customerModelTwo.setLastName("Kovacs");
        customerModelTwo.setStreetAndNumber("Erdo utca 95");
        customerModelTwo.setCity("Nagymegyer");
        customerModelTwo.setPostalCode("93201");
        customerModelTwo.setCell("0918291615");
        customerModelTwo.setEmail("mikcsekfree@gmail.com");
        customerModelTwo.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customerModelTwo.setStatus(Status.INACTIVE);

        List<CustomerModel> expectedModels = Arrays.asList(
                customerModelOne, customerModelTwo
        );

        given(customerService.getAllCustomers()).willReturn(expectedModels);

        //When
        MvcResult result = mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<CustomerModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

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

    @Test
    @DisplayName("Given valid email when getCustomerByEmail then return customer")
    public void givenValidEmail_whenGetCustomerByEmail_thenReturnCustomerModel() throws Exception{
        //Given
        String email = "mikcsek2@gmail.com"; // valid email

        // Mock the customerService to return a customer model
        CustomerModel customer = new CustomerModel(1L, LocalDateTime.now() ,"Mikulas", "mikcsek",
                "Abraham","Klapka 44B", "Komarom", "2900", "0918291615",
                "mikcsek2@gmail.com", LocalDate.of(1991, 12, 7), Status.INACTIVE);

        when(customerService.getCustomerByEmail(any(String.class))).thenReturn(customer);

        // When
        MvcResult result = mockMvc.perform(get("/api/customers/customer/email/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(email).isNotNull();
        assertThat(email).isNotBlank();
        assertThat(email).isEqualTo(customer.getEmail());

        String responseContent = result.getResponse().getContentAsString();
        CustomerModel actualUser = objectMapper.readValue(responseContent, CustomerModel.class);

        assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(customer);

    }

    @Test
    @DisplayName("Given valid id when getCustomerById then return customer")
    public void givenValidEmail_whenGetCustomerById_thenReturnCustomerModel() throws Exception{
        //Given
        Long id = 1L; // valid id

        // Mock the customerService to return a customer
        Customer customer = new Customer("Mikulas", "mikcsek",
                "Abraham","Klapka 44B", "Komarom", "2900", "0918291615",
                "mikcsek2@gmail.com", LocalDate.of(1991, 12, 7), Status.INACTIVE, List.of());

        when(customerService.getCustomerById(any(Long.class))).thenReturn(customer);

        // When
        MvcResult result = mockMvc.perform(get("/api/customers/customer/id/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(id).isNotNull();
        assertThat(id).isNotNegative();
        assertThat(id).isNotZero();

        String responseContent = result.getResponse().getContentAsString();
        Customer actualCustomer = objectMapper.readValue(responseContent, Customer.class);

        assertThat(actualCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate", "createdAt", "orders")
                .isEqualTo(customer);

    }


}
