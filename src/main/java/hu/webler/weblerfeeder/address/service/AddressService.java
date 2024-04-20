package hu.webler.weblerfeeder.address.service;

import hu.webler.weblerfeeder.address.entity.Address;
import hu.webler.weblerfeeder.address.model.AddressModel;
import hu.webler.weblerfeeder.address.model.AddressModelUpdateCreate;
import hu.webler.weblerfeeder.address.repository.AddressRepository;
import hu.webler.weblerfeeder.exception.InvalidInputException;
import hu.webler.weblerfeeder.exception.EntityAlreadyExistsException;
import hu.webler.weblerfeeder.util.AddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static hu.webler.weblerfeeder.util.AddressMapper.mapAddressEntityToAddressModel;
import static hu.webler.weblerfeeder.util.AddressMapper.mapAddressCreateModelToAddressEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public List<AddressModel> getAllAddress() {
        return addressRepository.findAll()
                .stream()
                .map(AddressMapper::mapAddressEntityToAddressModel)
                .collect(Collectors.toList());
    }

    public AddressModel addAddress(AddressModelUpdateCreate addressModelUpdateCreate) {

        if (isRequiredFieldsExistsAndContainData(addressModelUpdateCreate) ) {
            return  mapAddressEntityToAddressModel(addressRepository.save(mapAddressCreateModelToAddressEntity(addressModelUpdateCreate)));
        } else {
            throw new EntityAlreadyExistsException("Please provide all the fields for Address!");
        }
    }

    private boolean isRequiredFieldsExistsAndContainData(AddressModelUpdateCreate addressModelUpdateCreate) {
        if (
                        addressModelUpdateCreate.getStreetAndNumber() != null && !addressModelUpdateCreate.getStreetAndNumber().equals("") &&
                        addressModelUpdateCreate.getCity() != null && !addressModelUpdateCreate.getCity().equals("") &&
                        addressModelUpdateCreate.getPostalCode() != null && !addressModelUpdateCreate.getPostalCode().equals("")
        ) {
            return true;
        } else throw new InvalidInputException("Please provide all the fields for Address!");
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> {
                            String message = String.format("Address with id %s not found", id);
                            log.info(message);
                            return new NoSuchElementException(message);
                        }
                );
    }

    public void deleteAddress(Long id) {
        addressRepository.delete(getAddressById(id));
    }

    public AddressModel updateAddress(Long id, AddressModelUpdateCreate addressModelUpdateCreate) {
        Address existingAddress = getAddressById(id);
        if (isRequiredFieldsExistsAndContainData(addressModelUpdateCreate)) {
            addNewDataToExistingAddress(existingAddress, addressModelUpdateCreate);
        }
        return mapAddressEntityToAddressModel(addressRepository.save(existingAddress));
    }

    private void addNewDataToExistingAddress(Address existingAddress, AddressModelUpdateCreate addressModelUpdateCreate) {
        existingAddress.setStreetAndNumber(addressModelUpdateCreate.getStreetAndNumber());
        existingAddress.setCity(addressModelUpdateCreate.getCity());
        existingAddress.setPostalCode(addressModelUpdateCreate.getPostalCode());
    }
}
