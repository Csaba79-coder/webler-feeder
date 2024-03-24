package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;

public class CustomerMapper {

    public static CustomerModel mapCustomerEntityToCustomerModel(Customer customer) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(customer.getId());
        customerModel.setRegistrationDate(customer.getCreatedAt());
        customerModel.setFirstName(customer.getFirstName());
        customerModel.setMidName(customer.getMidName());
        customerModel.setLastName(customer.getLastName());
        customerModel.setCell(customer.getCell());
        customerModel.setEmail(customer.getEmail());
        customerModel.setDateOfBirth(customer.getDateOfBirth());
        customerModel.setStatus(customer.getStatus());
        return customerModel;
    }

    public static Customer mapCustomerCreateModelToCustomerEntity(CustomerCreateModel customerCreateModel) {
        Customer customer = new Customer();
        customer.setFirstName(customerCreateModel.getFirstName());
        customer.setMidName(customerCreateModel.getMidName());
        customer.setLastName(customerCreateModel.getLastName());
        customer.setCell(customerCreateModel.getCell());
        customer.setEmail(customerCreateModel.getEmail());
        customer.setDateOfBirth(customerCreateModel.getDateOfBirth());
        return customer;
    }

    private CustomerMapper() {
    }
}
