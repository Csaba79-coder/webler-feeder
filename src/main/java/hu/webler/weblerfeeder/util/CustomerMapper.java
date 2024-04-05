package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.customer.model.CustomerCreateModel;
import hu.webler.weblerfeeder.customer.model.CustomerModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerMapper {

    public static CustomerModel mapCustomerEntityToCustomerModel(Customer customer) {
        return CustomerModel
                .builder()
                .id(customer.getId())
                .registrationDate(customer.getCreatedAt())
                .firstName(customer.getFirstName())
                .midName(customer.getMidName())
                .lastName(customer.getLastName())
                .cell(customer.getCell())
                .email(customer.getEmail())
                .dateOfBirth(customer.getDateOfBirth())
                .status(customer.getStatus())
                .build();
    }

    public static Customer mapCustomerCreateModelToCustomerEntity(CustomerCreateModel customerCreateModel) {
        return Customer
                .builder()
                .firstName(customerCreateModel.getFirstName())
                .midName(customerCreateModel.getMidName())
                .lastName(customerCreateModel.getLastName())
                .cell(customerCreateModel.getCell())
                .email(customerCreateModel.getEmail())
                .dateOfBirth(customerCreateModel.getDateOfBirth())
                .build();
    }
}
