package java12.service;

import java12.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    String saveCustomer(Customer newCustomer);
    Customer findCustomerById(Long customerId);
    List<Customer> findAllCustomers();
    String updateCustomerById(Long customerId, Customer newCustomer);
    String deleteCustomerById(Long customerId);
}
