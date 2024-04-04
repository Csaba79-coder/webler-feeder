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

    private Optional<Customer> isCustomerExistsWithThisEmail(String email) {
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
        Optional<Customer> existingCustomerWithThisEmail = isCustomerExistsWithThisEmail(customerCreateModel.getEmail());
        if (isAllFieldsContainData(customerCreateModel) && existingCustomerWithThisEmail.isEmpty()) {
            return mapCustomerEntityToCustomerModel(customerRepository
                    .save(mapCustomerCreateModelToCustomerEntity(customerCreateModel)));
        } else {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
    }

    public String updateCustomer(Long id, CustomerUpdateModel customerUpdateModel) {
        Customer existingCustomer = getCustomerById(id);
        if (isAllFieldsContainData(customerUpdateModel)) {
            addNewDataToExistingCustomer(existingCustomer, customerUpdateModel);
            CustomerMapper.mapCustomerEntityToCustomerModel(customerRepository.save(existingCustomer));
        }
        return String.format("User with ID %s modified successfully to: %s", id, getCustomerEntityAsString(id));
    }

    private void addNewDataToExistingCustomer(Customer existingCustomer, CustomerUpdateModel customerUpdateModel) {
        existingCustomer.setFirstName(customerUpdateModel.getFirstName());
        existingCustomer.setMidName(customerUpdateModel.getMidName());
        existingCustomer.setCell(customerUpdateModel.getCell());
        if (!customerUpdateModel.getEmail().equals(existingCustomer.getEmail())) {
            existingCustomer.setEmail(customerUpdateModel.getEmail());
        } else
            throw new UserAlreadyExistsException("Please use another email, customer with this email already exists");
        existingCustomer.setDateOfBirth(customerUpdateModel.getDateOfBirth());
        existingCustomer.setStatus(customerUpdateModel.getStatus());
    }

    private boolean isAllFieldsContainData(CustomerUpdateModel customerUpdateModel) {
        if (
                customerUpdateModel.getFirstName() != null &&
                customerUpdateModel.getMidName() != null &&
                customerUpdateModel.getLastName() != null &&
                customerUpdateModel.getCell() != null &&
                customerUpdateModel.getEmail() != null &&
                customerUpdateModel.getDateOfBirth() != null &&
                customerUpdateModel.getStatus() != null
        ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }

    private boolean isAllFieldsContainData(CustomerCreateModel customerCreateModel) {
        if (
                customerCreateModel.getFirstName() != null &&
                customerCreateModel.getMidName() != null &&
                customerCreateModel.getLastName() != null &&
                customerCreateModel.getCell() != null &&
                customerCreateModel.getEmail() != null &&
                customerCreateModel.getDateOfBirth() != null
        ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }

    private String getCustomerEntityAsString(Long id) {
        Customer customer = getCustomerById(id);
        return String.format("""
                        \nFirst Name: %s\s
                        Mid Name: %s\s
                        Last Name: %s\s
                        Cell: %s\s
                        Email: %s\s
                        Date of birth: %s\s
                        Status: %s""",
                customer.getFirstName(), customer.getMidName(), customer.getLastName(), customer.getCell(),
                customer.getEmail(), customer.getDateOfBirth(), customer.getStatus());
    }

    public void deleteCustomer(Long id) {
        customerRepository.delete(getCustomerById(id));
    }
}
