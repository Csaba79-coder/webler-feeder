package hu.webler.weblerfeeder.address.util;

import hu.webler.weblerfeeder.address.entity.Address;
import hu.webler.weblerfeeder.address.model.AddressModel;
import hu.webler.weblerfeeder.address.model.AddressModelUpdateCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hu.webler.weblerfeeder.util.AddressMapper.mapAddressCreateModelToAddressEntity;
import static hu.webler.weblerfeeder.util.AddressMapper.mapAddressEntityToAddressModel;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Address mapper - unit test")
public class AddressMapperTest {

    @Test
    @DisplayName("Given address create model when mapping to entity then returns address entity")
    public void givenAddressCreateModel_whenMappingToEntity_thenReturnsAddressEntity() {

        //Given
        AddressModelUpdateCreate addressModelUpdateCreate = new AddressModelUpdateCreate();

        addressModelUpdateCreate.setCity("Budapes");
        addressModelUpdateCreate.setStreetAndNumber("Tarnok 4");
        addressModelUpdateCreate.setPostalCode("1014");

        //When
        Address address = mapAddressCreateModelToAddressEntity(addressModelUpdateCreate);

        //Then
        then(addressModelUpdateCreate)
                .usingRecursiveComparison()
                .isEqualTo(address);
    }

    @Test
    @DisplayName("Given Address entity when mapping to address model then returns address model")
    public void givenAddressEntity_whenMappingToAddressModel_thenReturnsAddressModel() {
        //Given
        Address address = new Address();

        address.setCity("Komarom");
        address.setStreetAndNumber("Klapka 44");
        address.setPostalCode("2900");

        AddressModel expectedAddressModel = new AddressModel();

        expectedAddressModel.setCity("Komarom");
        expectedAddressModel.setStreetAndNumber("Klapka 44");
        expectedAddressModel.setPostalCode("2900");

        //When
        AddressModel addressModel = mapAddressEntityToAddressModel(address);

        //Then
        then(addressModel)
                .usingRecursiveComparison()
                .ignoringFields("registrationDate")
                .isEqualTo(expectedAddressModel);
    }
}