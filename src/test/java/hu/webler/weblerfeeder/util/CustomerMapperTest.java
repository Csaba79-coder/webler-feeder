package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.address.entity.Address;
import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import hu.webler.weblerfeeder.value.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static hu.webler.weblerfeeder.util.CustomerMapper.mapCustomerCreateModelToCustomerEntity;
import static hu.webler.weblerfeeder.util.CustomerMapper.mapCustomerEntityToCustomerModel;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Customer mapper - unit test")
public class CustomerMapperTest {

    @Test
    @DisplayName("Given customer create model when mapping to entity then returns customer entity")
    public void givenCustomerCreateModel_whenMappingToEntity_thenReturnsCustomerEntity() {

        //Given
        CustomerCreateModel customerCreateModel = new CustomerCreateModel();
        customerCreateModel.setFirstName("Mikulas");
        customerCreateModel.setMidName("");
        customerCreateModel.setLastName("Abraham");
        customerCreateModel.setCell("0918291615");
        customerCreateModel.setEmail("mikcsek2@gmail.com");
        customerCreateModel.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customerCreateModel.setAddress(Address.builder()
                .streetAndNumber("Klapka 44B")
                .city("Komarom")
                .postalCode("2900")
                .build());

        //When
        Customer customer = mapCustomerCreateModelToCustomerEntity(customerCreateModel);

        //Then
        then(customerCreateModel)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("Given customer entity when mapping to customer model then returns customer model")
    public void givenCustomerEntity_whenMappingToCustomerModel_thenReturnsCustomerModel() {
        //Given
        Customer customer = new Customer();
        customer.setFirstName("Mikulas");
        customer.setMidName("");
        customer.setLastName("Abraham");
        customer.setCell("0918291615");
        customer.setEmail("mikcsek2@gmail.com");
        customer.setDateOfBirth(LocalDate.parse("1991-12-07"));
        customer.setStatus(Status.INACTIVE);
        customer.setAddress(Address.builder()
                .streetAndNumber("Klapka 44B")
                .city("Komarom")
                .postalCode("2900")
                .build());

        CustomerModel expectedCustomerModel = new CustomerModel();
        expectedCustomerModel.setFirstName("Mikulas");
        expectedCustomerModel.setMidName("");
        expectedCustomerModel.setLastName("Abraham");
        expectedCustomerModel.setCell("0918291615");
        expectedCustomerModel.setEmail("mikcsek2@gmail.com");
        expectedCustomerModel.setDateOfBirth(LocalDate.parse("1991-12-07"));
        expectedCustomerModel.setStatus(Status.INACTIVE);
        expectedCustomerModel.setAddress(Address.builder()
                .streetAndNumber("Klapka 44B")
                .city("Komarom")
                .postalCode("2900")
                .build());

        //When
        CustomerModel customerModel = mapCustomerEntityToCustomerModel(customer);

        //Then
        then(customerModel)
                .usingRecursiveComparison()
                .ignoringFields("registrationDate")
                .isEqualTo(expectedCustomerModel);
    }
}
