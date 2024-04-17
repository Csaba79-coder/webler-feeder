package address;

import hu.webler.weblerfeeder.address.entity.Address;
import hu.webler.weblerfeeder.address.model.AddressModel;
import hu.webler.weblerfeeder.address.model.AddressModelUpdateCreate;
import hu.webler.weblerfeeder.address.repository.AddressRepository;
import hu.webler.weblerfeeder.address.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static hu.webler.weblerfeeder.util.AddressMapper.mapAddressCreateModelToAddressEntity;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Address service test - unit test")
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    @DisplayName("Given valid Address create model when addAddress then returns Address model")
    public void givenValidAddressCreateModel_whenAddAddress_thenReturnsAddressModel() {


        //Given
        AddressModelUpdateCreate addressModelUpdateCreate = new AddressModelUpdateCreate();

        addressModelUpdateCreate.setPostalCode("1014");
        addressModelUpdateCreate.setStreetAndNumber("Tarnok 2");
        addressModelUpdateCreate.setCity("Bp");

        //Mock customerRepository.save() to return a mock CustomerModel
        AddressModel expectedAddress = new AddressModel();

        expectedAddress.setPostalCode("1014");
        expectedAddress.setCity("Bp");
        expectedAddress.setStreetAndNumber("Tarnok 2");

        // when(customerRepository.save(any())).thenReturn(mapCustomerCreateModelToCustomerEntity(customerCreateModel));
        when(addressRepository.save(any())).thenReturn(mapAddressCreateModelToAddressEntity(addressModelUpdateCreate));

        //When
        AddressModel createdModel = addressService.addAddress(addressModelUpdateCreate);

        //Then
        then(createdModel.getCity()).isEqualTo(addressModelUpdateCreate.getCity());
        verify(addressRepository, times(1)).save(any());
        then(expectedAddress)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate")
                .isEqualTo(createdModel);
    }

    @Test
    @DisplayName("Given empty Address list when getAllCustomers then returns empty list")
    public void givenEmptyCustomersList_whenGetAllCustomers_thenReturnsEmptyList() {
        //Given
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());

        //When
        List<AddressModel> address = addressService.getAllAddress();

        //Then
        then(address).isEmpty();
    }

    @Test
    @DisplayName("Given customer id when getCustomerById then returns customer")
    public void givenCustomerId_whenGetCustomerById_thenReturnsCustomer() {

        //Given
        Long id = 1L;

        Address address = new Address();
        address.setCity("Bp");
        address.setPostalCode("1014");
        address.setStreetAndNumber("Tarnok 4");

        when(addressRepository.findById(id)).thenReturn(Optional.of(address));

        //When
        Address existingAddress = addressService.getAddressById(id);

        //Then
        then(existingAddress).isEqualTo(address);

    }
    @Test
    @DisplayName("Given a non empty address list when getAllAddress then returns the list of address models")
    public void givenNonEmptyCustomersList_whenGetAllCustomers_thenReturnsTheListsOfCustomerModels() {
        //Given
        List<Address> addressesData = List.of(
                new Address("1014", "Tarnok 2 ", "BP", null),
                new Address("1810", "Disz ter 8 ", "BP", null)
        );
        when(addressRepository.findAll()).thenReturn(addressesData);

        //When
        List<AddressModel> customers = addressService.getAllAddress();

        //Then
        then(customers).hasSize(2)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate", "orders", "address")
                .isEqualTo(addressesData);
    }

    @Test
    @DisplayName("Given Address id when getAddressById then throws no such element exception")
    public void givenCustomerId_whenGetCustomerById_thenThrowsNoSuchElementException() {
        //Given
        Long id = 2L;

        // When / Then
        assertThatThrownBy(() -> addressService.getAddressById(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Address with id %s not found", id);

        // Ensure userRepository.save() is not called
        verify(addressRepository, never()).save(any());
    }
}