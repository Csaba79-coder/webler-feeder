package hu.webler.weblerfeeder.service;

import hu.webler.weblerfeeder.entity.Customer;
import hu.webler.weblerfeeder.model.CustomerCreateModel;
import hu.webler.weblerfeeder.model.CustomerModel;
import hu.webler.weblerfeeder.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.repository.CustomerRepository;
import hu.webler.weblerfeeder.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(Mapper::mapCustomerEntityToCustomerModel)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerModel getCustomerByEmail(String email) {
        return Mapper.mapCustomerEntityToCustomerModel(
                customerRepository.findByEmail(email)
                        .orElseThrow(() -> {
                            String message = String.format("User with this email %s not found", email);
                            log.info(message);
                            return new NoSuchElementException(message);
                        })
        );
    }

    @Override
    public CustomerModel getCustomerById(Long id) {
        return Mapper.mapCustomerEntityToCustomerModel(customerRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Customer with id %s not found", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                })
        );
    }

    @Override
    public CustomerModel addCustomer(CustomerCreateModel customerCreateModel) {
        return Mapper.mapCustomerEntityToCustomerModel(customerRepository
                .save(Mapper.mapCustomerCreateModelToCustomerEntity(customerCreateModel)));
    }

    @Override
    public CustomerModel updateCustomer(CustomerUpdateModel customerUpdateModel) {
        Optional<Customer> customerOptionalByEmail = customerRepository.findByEmail(customerUpdateModel.getEmail());
        if (customerOptionalByEmail.isPresent()) {
            Customer existingCustomer = customerOptionalByEmail.get();
            if (customerUpdateModel.getFirstName() != null && !customerUpdateModel.getFirstName().equals(existingCustomer.getFirstName())) {
                existingCustomer.setFirstName(customerUpdateModel.getFirstName());
            }
            if (customerUpdateModel.getMidName() != null && !customerUpdateModel.getMidName().equals(existingCustomer.getMidName())) {
                existingCustomer.setMidName(customerUpdateModel.getMidName());
            }
            if (customerUpdateModel.getLastName() != null && !customerUpdateModel.getLastName().equals(existingCustomer.getLastName())) {
                existingCustomer.setLastName(customerUpdateModel.getLastName());
            }
            if (customerUpdateModel.getCell() != null && !customerUpdateModel.getCell().equals(existingCustomer.getCell())) {
                existingCustomer.setCell(customerUpdateModel.getCell());
            }
            if (customerUpdateModel.getStatus() != null && !customerUpdateModel.getStatus().equals(existingCustomer.getStatus())) {
                existingCustomer.setStatus(customerUpdateModel.getStatus());
            }
            return Mapper.mapCustomerEntityToCustomerModel(customerRepository.save(existingCustomer));
        }
        String message = String.format("User with email %s not found", customerUpdateModel.getEmail());
        log.info(message);
        throw new NoSuchElementException(message);
    }

    @Override
    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            String message = String.format("Customer with id %d not found", id);
            log.info(message);
            throw new NoSuchElementException(message);
        }
    }
}