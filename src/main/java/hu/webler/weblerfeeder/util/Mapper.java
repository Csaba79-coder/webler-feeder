package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.model.CustomerCreateModel;
import hu.webler.weblerfeeder.model.CustomerModel;

public class Mapper {

    public static CustomerModel mapCustomerEntityToCustomerModel(Customer customer) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(customer.getId());
        customerModel.setFirstName(customer.getFirstName());
        customerModel.setMidName(customer.getMidName());
        customerModel.setLastName(customer.getLastName());
        customerModel.setCell(customer.getCell());
        customerModel.setEmail(customer.getEmail());
        customerModel.setRegistrationDate(customer.getRegistrationDate());
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
        customer.setRegistrationDate(customerCreateModel.getRegistrationDate());
        customer.setStatus(customerCreateModel.getStatus());
        return customer;
    }
}
