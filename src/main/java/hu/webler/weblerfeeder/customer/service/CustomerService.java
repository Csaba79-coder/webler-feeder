package hu.webler.weblerfeeder.customer.service;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import hu.webler.weblerfeeder.customer.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.customer.repository.CustomerRepository;
import hu.webler.weblerfeeder.exception.UserAlreadyExistsException;
import hu.webler.weblerfeeder.exception.InvalidInputException;
import hu.webler.weblerfeeder.util.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static hu.webler.weblerfeeder.util.CustomerMapper.mapCustomerCreateModelToCustomerEntity;
import static hu.webler.weblerfeeder.util.CustomerMapper.mapCustomerEntityToCustomerModel;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::mapCustomerEntityToCustomerModel)
                .collect(Collectors.toList());
    }

    private boolean isCustomerAlreadyExistsWithThisEmail(String email) {
        List<CustomerModel> customers = getAllCustomers();
        for (CustomerModel customer : customers) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public CustomerModel getCustomerByEmail(String email) {
        return mapCustomerEntityToCustomerModel(
                customerRepository.findByEmail(email)
                        .orElseThrow(() -> {
                            String message = String.format("User with this email %s not found", email);
                            log.info(message);
                            return new NoSuchElementException(message);
                        })
        );
    }

    private Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Customer with id %s not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                }
        );
    }

    public CustomerModel addCustomer(CustomerCreateModel customerCreateModel) {
        Optional<Customer> existingCustomerWithThisEmail = findCustomerByEmail(customerCreateModel.getEmail());
        if (isRequiredFieldsExistsAndContainData(customerCreateModel) && existingCustomerWithThisEmail.isEmpty()) {
            return mapCustomerEntityToCustomerModel(customerRepository
                    .save(mapCustomerCreateModelToCustomerEntity(customerCreateModel)));
        } else {
            String email = customerCreateModel.getEmail();
            String message = String.format("Please use another email, customer with this email: %s already exists", email);
            throw new UserAlreadyExistsException(message);
        }
    }

    public CustomerModel updateCustomer(Long id, CustomerUpdateModel customerUpdateModel) {
        Customer existingCustomer = getCustomerById(id);
        if (isRequiredFieldsExistsAndContainData(customerUpdateModel)) {
            addNewDataToExistingCustomer(existingCustomer, customerUpdateModel);
        }
        return CustomerMapper.mapCustomerEntityToCustomerModel(customerRepository.save(existingCustomer));
    }

    private void addNewDataToExistingCustomer(Customer existingCustomer, CustomerUpdateModel customerUpdateModel) {
        existingCustomer.setFirstName(customerUpdateModel.getFirstName());
        existingCustomer.setMidName(customerUpdateModel.getMidName());
        existingCustomer.setLastName(customerUpdateModel.getLastName());
        existingCustomer.setStreetAndNumber(customerUpdateModel.getStreetAndNumber());
        existingCustomer.setCity(customerUpdateModel.getCity());
        existingCustomer.setPostalCode(customerUpdateModel.getPostalCode());
        existingCustomer.setCell(customerUpdateModel.getCell());
        if (existingCustomer.getEmail().equals(customerUpdateModel.getEmail()) ||
                !isCustomerAlreadyExistsWithThisEmail(customerUpdateModel.getEmail())) {
            existingCustomer.setEmail(customerUpdateModel.getEmail());
        } else {
            String email = customerUpdateModel.getEmail();
            String message = String.format("Please use another email, customer with this email: %s already exists", email);
            throw new UserAlreadyExistsException(message);
        }
        existingCustomer.setDateOfBirth(customerUpdateModel.getDateOfBirth());
        existingCustomer.setStatus(customerUpdateModel.getStatus());
    }

    private boolean isRequiredFieldsExistsAndContainData(CustomerUpdateModel customerUpdateModel) {
        if (
                customerUpdateModel.getFirstName() != null && !customerUpdateModel.getFirstName().equals("") &&
                customerUpdateModel.getMidName() != null &&
                customerUpdateModel.getLastName() != null && !customerUpdateModel.getLastName().equals("") &&
                customerUpdateModel.getCell() != null && !customerUpdateModel.getCell().equals("") &&
                customerUpdateModel.getStreetAndNumber() != null && !customerUpdateModel.getStreetAndNumber().equals("") &&
                customerUpdateModel.getCity() != null && !customerUpdateModel.getCity().equals("") &&
                customerUpdateModel.getPostalCode() != null && !customerUpdateModel.getPostalCode().equals("") &&
                customerUpdateModel.getEmail() != null && !customerUpdateModel.getEmail().equals("") &&
                customerUpdateModel.getDateOfBirth() != null && !customerUpdateModel.getDateOfBirth().toString().equals("") &&
                customerUpdateModel.getStatus() != null
        ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }

    private boolean isRequiredFieldsExistsAndContainData(CustomerCreateModel customerCreateModel) {
        if (
                customerCreateModel.getFirstName() != null && !customerCreateModel.getFirstName().equals("") &&
                customerCreateModel.getMidName() != null &&
                customerCreateModel.getLastName() != null && !customerCreateModel.getLastName().equals("") &&
                customerCreateModel.getCell() != null && !customerCreateModel.getCell().equals("") &&
                customerCreateModel.getStreetAndNumber() != null && !customerCreateModel.getStreetAndNumber().equals("") &&
                customerCreateModel.getCity() != null && !customerCreateModel.getCity().equals("") &&
                customerCreateModel.getPostalCode() != null && !customerCreateModel.getPostalCode().equals("") &&
                customerCreateModel.getEmail() != null && !customerCreateModel.getEmail().equals("") &&
                customerCreateModel.getDateOfBirth() != null && !customerCreateModel.getDateOfBirth().toString().equals("")
        ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }

    public void deleteCustomer(Long id) {
        customerRepository.delete(getCustomerById(id));
    }
}
