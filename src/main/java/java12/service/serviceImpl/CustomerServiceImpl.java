package java12.service.serviceImpl;

import java12.dao.AgencyDao;
import java12.dao.CustomerDao;
import java12.dao.HouseDao;
import java12.dao.daoImpl.AgencyDaoImpl;
import java12.dao.daoImpl.CustomerDaoImpl;
import java12.dao.daoImpl.HouseDaoImpl;
import java12.entities.Agency;
import java12.entities.Customer;
import java12.entities.House;
import java12.entities.RentInfo;
import java12.service.CustomerService;

import java.time.LocalDate;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao = new CustomerDaoImpl();
    private final HouseDao houseDao = new HouseDaoImpl();
    private final AgencyDao agencyDao = new AgencyDaoImpl();
    @Override
    public String saveCustomer(Customer newCustomer) {
        if (checkEmail(newCustomer) == 1){
            return newCustomer.getEmail() +" already exist!!!";
        }
        return customerDao.saveCustomer(newCustomer);
    }

    @Override
    public String saveCustomerWithRent(Customer newCustomer, Long houseId, Long agencyId,
                                       LocalDate checkIn, LocalDate checkout) {
        if (checkIn.isBefore(LocalDate.now())){
            return "Check-in date cannot be in the last time";
        }
        if (checkEmail(newCustomer) == 1){
            return newCustomer.getEmail() +" already exist!!!";
        }
        return customerDao.saveCustomerWithRent(newCustomer, houseId, agencyId,
                checkIn, checkout);
    }

    @Override
    public Customer findCustomerById(Long customerId) {
         Customer findCustomer = null;
         try {
             findCustomer = customerDao.findCustomerById(customerId)
                     .orElseThrow(() ->
                             new RuntimeException("Customer with id: "+customerId+" not found!!!"));
         }catch (RuntimeException e){
             System.out.println(e.getMessage());
         }
         return findCustomer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerDao.findAllCustomers();
    }

    @Override
    public String updateCustomerById(Long customerId, Customer newCustomer) {
        if (checkEmail(newCustomer) == 1){
            return newCustomer.getEmail() +" already exist!!!";
        }
        Customer findCustomer = findCustomerById(customerId);
        if (findCustomer == null){
            return "Failed to update";
        }
        return customerDao.updateCustomerById(customerId, newCustomer);
    }

    @Override
    public String deleteCustomerById(Long customerId) {
        Customer findCustomer = findCustomerById(customerId);
        if (findCustomer == null){
            return "Failed to delete";
        }
        return customerDao.deleteCustomerById(customerId);
    }

    @Override
    public String rentingHouseByCustomer(Long customerId, Long houseId, Long agencyId,
                                         LocalDate checkIn, LocalDate checkout) {
        if (checkIn.isBefore(LocalDate.now())){
            return "Check-in date cannot be in the last time";
        }
        House findHouse = houseDao.findHouseById(houseId)
                .orElse(null);

        Agency findAgency = agencyDao.findAgencyById(agencyId)
                .orElse(null);

        Customer findCustomer = findCustomerById(customerId);
        if (findCustomer == null && findAgency == null && findHouse == null){
            return "Failed to renting";
        }
        return customerDao.rentingHouseByCustomer(customerId, houseId, agencyId, checkIn, checkout);
    }

    private int checkEmail(Customer newCustomer){
        List<Customer> allCustomers = customerDao.findAllCustomers();
        for (Customer allCustomer : allCustomers) {
            if (allCustomer.getEmail().equalsIgnoreCase(newCustomer.getEmail())){
                return 1;
            }
        }
        return 0;
    }
}
