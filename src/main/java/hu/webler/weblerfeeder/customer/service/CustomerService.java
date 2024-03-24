package hu.webler.weblerfeeder.customer.service;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import hu.webler.weblerfeeder.customer.model.CustomerUpdateModel;
import hu.webler.weblerfeeder.customer.repository.CustomerRepository;
import hu.webler.weblerfeeder.util.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        return mapCustomerEntityToCustomerModel(customerRepository
                .save(mapCustomerCreateModelToCustomerEntity(customerCreateModel)));
    }

    public CustomerModel updateCustomer(Long id, CustomerUpdateModel customerUpdateModel) {
        //TODO megkeresem ID alapján, ha létezik, akkor az entitás adatait az update modellel módosítom

        /*Optional<Customer> customerOptionalByEmail = customerRepository.findByEmail(customerUpdateModel.getEmail());
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
        throw new NoSuchElementException(message);*/
        return  null;
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }
}
