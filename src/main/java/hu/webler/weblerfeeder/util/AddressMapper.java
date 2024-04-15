package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.address.entity.Address;
import hu.webler.weblerfeeder.address.model.AddressModel;
import hu.webler.weblerfeeder.address.model.AddressModelUpdateCreate;

public class AddressMapper {

    public static AddressModel mapAddressEntityToAddressModel(Address address) {
        return AddressModel
                .builder()
                .id(address.getId())
                .registrationDate(address.getCreatedAt())
                .streetAndNumber(address.getStreetAndNumber())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .build();
    }

    public static Address mapAddressCreateModelToAddressEntity(AddressModelUpdateCreate addressModelUpdateCreate) {
        return Address
                .builder()
                .streetAndNumber(addressModelUpdateCreate.getStreetAndNumber())
                .city(addressModelUpdateCreate.getCity())
                .postalCode(addressModelUpdateCreate.getPostalCode())
                .build();
    }
}
