package java12.service;

import java12.entities.Customer;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    String saveCustomer(Customer newCustomer);
    String saveCustomerWithRent(Customer newCustomer, Long houseId, Long agencyId,
                                LocalDate checkIn, LocalDate checkout);
    Customer findCustomerById(Long customerId);
    List<Customer> findAllCustomers();
    String updateCustomerById(Long customerId, Customer newCustomer);
    String deleteCustomerById(Long customerId);
    String rentingHouseByCustomer(Long customerId, Long houseId, Long agencyId,
                                  LocalDate checkIn, LocalDate checkout);
}
