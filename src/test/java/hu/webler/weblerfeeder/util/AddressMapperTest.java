package hu.webler.weblerfeeder.util;


import hu.webler.weblerfeeder.address.entity.Address;
import hu.webler.weblerfeeder.address.model.AddressModel;
import hu.webler.weblerfeeder.address.model.AddressModelUpdateCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import hu.webler.weblerfeeder.util.AddressMapper.*;

import static hu.webler.weblerfeeder.util.AddressMapper.mapAddressCreateModelToAddressEntity;
import static hu.webler.weblerfeeder.util.AddressMapper.mapAddressEntityToAddressModel;

import static org.assertj.core.api.BDDAssertions.*;

@DisplayName("Address mapper - unit test")
public class AddressMapperTest {

    @Test
    @DisplayName("Given Address create model when mapping to entity then returns Address entity")
    public void givenCustomerCreateModel_whenMappingToEntity_thenReturnsCustomerEntity() {

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
    @DisplayName("Given Address entity when mapping to Address model then returns customer model")
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