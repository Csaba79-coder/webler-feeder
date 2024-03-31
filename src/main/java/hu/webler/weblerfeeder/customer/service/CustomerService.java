package hu.webler.weblerfeeder.customer.service;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import hu.webler.weblerfeeder.customer.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.customer.repository.CustomerRepository;
import hu.webler.weblerfeeder.exception.DuplicateKeyException;
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

    public Optional<Customer> isCustomerExistsWithThisEmail(String email) {
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
        Optional<Customer> customerOptionalByEmail = isCustomerExistsWithThisEmail(customerCreateModel.getEmail());
        if (customerOptionalByEmail.isEmpty()) {
            return mapCustomerEntityToCustomerModel(customerRepository
                    .save(mapCustomerCreateModelToCustomerEntity(customerCreateModel)));
        } else {
            throw new DuplicateKeyException("User with this email already exists");
        }
    }

    public String updateCustomer(Long id, CustomerUpdateModel customerUpdateModel) {
        Optional<Customer> customerOptionalById = Optional.ofNullable(getCustomerById(id));
        if (customerOptionalById.isPresent()) {
            Customer existingCustomer = customerOptionalById.get();
            if (customerUpdateModel.getFirstName() != null && !customerUpdateModel.getFirstName().equals(existingCustomer.getFirstName())) {
                existingCustomer.setFirstName(customerUpdateModel.getFirstName());
            }
            if (customerUpdateModel.getMidName() != null && !customerUpdateModel.getMidName().equals(existingCustomer.getMidName())) {
                existingCustomer.setMidName(customerUpdateModel.getMidName());
            }
            if (customerUpdateModel.getLastName() != null && !customerUpdateModel.getLastName().equals(existingCustomer.getMidName())) {
                existingCustomer.setMidName(customerUpdateModel.getMidName());
            }
            if (customerUpdateModel.getCell() != null && !customerUpdateModel.getCell().equals(existingCustomer.getCell())) {
                existingCustomer.setCell(customerUpdateModel.getCell());
            }
            if (customerUpdateModel.getEmail() != null && !customerUpdateModel.getEmail().equals(existingCustomer.getEmail())) {
                existingCustomer.setEmail(customerUpdateModel.getEmail());
            } else
                throw new DuplicateKeyException("Please use another email, customer with this email already exists");
            if (customerUpdateModel.getDateOfBirth() != null && !customerUpdateModel.getDateOfBirth().equals(existingCustomer.getDateOfBirth())) {
                existingCustomer.setDateOfBirth(customerUpdateModel.getDateOfBirth());
            }
            if (customerUpdateModel.getStatus() != null && !customerUpdateModel.getStatus().equals(existingCustomer.getStatus())) {
                existingCustomer.setStatus(customerUpdateModel.getStatus());
            }
            CustomerMapper.mapCustomerEntityToCustomerModel(customerRepository.save(existingCustomer));
        }
        return String.format("User with ID %s modified successfully to: %s", id, getCustomerEntityAsString(id));
    }

    private String getCustomerEntityAsString(Long id) {
        Customer customer = getCustomerById(id);
        return String.format("""
                        First Name: %s\s
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
