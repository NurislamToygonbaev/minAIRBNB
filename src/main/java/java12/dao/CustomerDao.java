package java12.dao;

import java12.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    String saveCustomer(Customer newCustomer);
    String saveCustomerWithRent(Customer newCustomer);
    Optional<Customer> findCustomerById(Long customerId);
    List<Customer> findAllCustomers();
    String updateCustomerById(Long customerId, Customer newCustomer);
    String deleteCustomerById(Long customerId);
}
