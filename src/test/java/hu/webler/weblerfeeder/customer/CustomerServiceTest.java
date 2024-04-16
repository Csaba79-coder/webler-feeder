package hu.webler.weblerfeeder.customer;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import hu.webler.weblerfeeder.customer.repository.CustomerRepository;
import hu.webler.weblerfeeder.customer.service.CustomerService;
import hu.webler.weblerfeeder.exception.EntityAlreadyExistsException;
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

import static hu.webler.weblerfeeder.util.CustomerMapper.mapCustomerCreateModelToCustomerEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer service test - unit test")
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Given valid customer create model when addCustomer then returns customer model")
    public void givenValidCustomerCreateModel_whenAddCustomer_thenReturnsCustomerModel() {

        //Given
        CustomerCreateModel customerCreateModel = new CustomerCreateModel();
        customerCreateModel.setFirstName("Mikulas");
        customerCreateModel.setMidName("mikcsek");
        customerCreateModel.setLastName("Abraham");
        customerCreateModel.setStreetAndNumber("Klapka 44B");
        customerCreateModel.setCity("Komarom");
        customerCreateModel.setPostalCode("2900");
        customerCreateModel.setCell("0918291615");
        customerCreateModel.setEmail("m2@gmail.com");
        customerCreateModel.setDateOfBirth(LocalDate.parse("1991-12-07"));

        //Mock customerRepository.save() to return a mock CustomerModel
        CustomerModel expectedCustomer = new CustomerModel();
        expectedCustomer.setFirstName("Mikulas");
        expectedCustomer.setMidName("mikcsek");
        expectedCustomer.setLastName("Abraham");
        expectedCustomer.setStreetAndNumber("Klapka 44B");
        expectedCustomer.setCity("Komarom");
        expectedCustomer.setPostalCode("2900");
        expectedCustomer.setCell("0918291615");
        expectedCustomer.setEmail("m2@gmail.com");
        expectedCustomer.setDateOfBirth(LocalDate.parse("1991-12-07"));
        expectedCustomer.setStatus(Status.valueOf("INACTIVE"));
        when(customerRepository.save(any())).thenReturn(mapCustomerCreateModelToCustomerEntity(customerCreateModel));

        //When
        CustomerModel createdCustomer = customerService.addCustomer(customerCreateModel);

        //Then
        then(createdCustomer.getEmail()).isEqualTo(customerCreateModel.getEmail());
        verify(customerRepository, times(1)).save(any());
        then(expectedCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate")
                .isEqualTo(createdCustomer);

    }

    @Test
    @DisplayName("Given valid customer create model when addCustomer then throws user already exists exception ")
    public void givenValidCustomerCreateModel_whenAddCustomer_thenThrowsUserAlreadyExistsException() {

        //Given
        String email = "a@gmail.com";
        CustomerCreateModel customerCreateModel = new CustomerCreateModel("Mikulas", "mikcsek",
                "Abraham", "Klapka 44B",
                "Komarom", "2900", "123", "a@gmail.com", LocalDate.parse("1991-12-07"));

        when(customerRepository.save(any())).thenReturn(mapCustomerCreateModelToCustomerEntity(customerCreateModel))
                .thenThrow(new EntityAlreadyExistsException(String.format("User with this email %s not found", email)));

        //When
        customerService.addCustomer(customerCreateModel);

        //Then
        assertThatThrownBy(() -> customerService.addCustomer(customerCreateModel))
                .isInstanceOf(EntityAlreadyExistsException.class)
                .hasMessage("User with this email %s not found", email);
    }

    @Test
    @DisplayName("Given empty customers list when getAllCustomers then returns empty list")
    public void givenEmptyCustomersList_whenGetAllCustomers_thenReturnsEmptyList() {
        //Given
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        //When
        List<CustomerModel> customers = customerService.getAllCustomers();

        //Then
        then(customers).isEmpty();
    }

    @Test
    @DisplayName("Given a non empty customers list when getAllCustomers then returns the list of customer models")
    public void givenNonEmptyCustomersList_whenGetAllCustomers_thenReturnsTheListsOfCustomerModels() {
        //Given
        List<Customer> customerData = List.of(
                new Customer("Mikulas", "mikcsek", "Abraham", "Klapka 44B",
                        "Komarom", "2900", "123", "a@gmail.com", LocalDate.parse("1991-12-07"),
                        Status.INACTIVE, null),
                new Customer("asd", "asd", "asd", "Klapka 44B",
                        "Komarom", "2900", "456", "b@gmail.com", LocalDate.parse("1991-12-07"),
                        Status.INACTIVE, null)
        );
        when(customerRepository.findAll()).thenReturn(customerData);

        //When
        List<CustomerModel> customers = customerService.getAllCustomers();

        //Then
        then(customers).hasSize(2)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate", "orders")
                .isEqualTo(customerData);
    }

    @Test
    @DisplayName("Given a valid customer email when getCustomerByEmail then returns customer with that email")
    public void givenValidCustomerEmail_whenGetCustomerByEmail_thenReturnsCustomerWithThatEmail() {
        //Given
        String email = "ab@gmail.com";
        Customer customer = new Customer("Mikulas", "mikcsek", "Abraham", "Klapka 44B",
                "Komarom", "2900", "123", "ab@gmail.com", LocalDate.parse("1991-12-07"),
                Status.INACTIVE, null);

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        //When
        CustomerModel existingCustomer = customerService.getCustomerByEmail(email);

        //Then
        then(existingCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    @DisplayName("Given customer email when getCustomerByEmail then throws no such element exception")
    public void givenCustomerEmail_whenGetCustomerByEmail_thenThrowsNoSuchElementException() {
        //Given
        String email = "aa@gmail.com";

        // When / Then
        assertThatThrownBy(() -> customerService.getCustomerByEmail(email))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User with this email %s not found", email);

        // Ensure userRepository.save() is not called
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Given customer id when getCustomerById then returns customer")
    public void givenCustomerId_whenGetCustomerById_thenReturnsCustomer() {
        //Given
        Long id = 1L;
        Customer customer = new Customer("Mikulas", "mikcsek", "Abraham", "Klapka 44B",
                "Komarom", "2900", "123", "ab@gmail.com", LocalDate.parse("1991-12-07"),
                Status.INACTIVE, null);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        //When
        Customer existingCustomer = customerService.getCustomerById(id);

        //Then
        then(existingCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("Given customer id when getCustomerById then throws no such element exception")
    public void givenCustomerId_whenGetCustomerById_thenThrowsNoSuchElementException() {
        //Given
        Long id = 2L;

        // When / Then
        assertThatThrownBy(() -> customerService.getCustomerById(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Customer with id %s not found", id);

        // Ensure userRepository.save() is not called
        verify(customerRepository, never()).save(any());
    }
}
