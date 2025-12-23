package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDTO toCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setName(customer.getName());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        return customerDTO;
    }

    public static Customer toCustomer(CustomerDTO customerDTO, Customer customer) {
        customer.setEmail(customerDTO.getEmail());
        customer.setName(customerDTO.getName());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        return customer;
    }
}
