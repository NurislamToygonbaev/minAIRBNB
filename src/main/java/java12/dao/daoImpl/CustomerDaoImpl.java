package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.CustomerDao;
import java12.entities.Agency;
import java12.entities.Customer;
import java12.entities.House;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao, AutoCloseable {
    private final EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();
    @Override
    public String saveCustomer(Customer newCustomer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(newCustomer);
            entityManager.getTransaction().commit();
            return newCustomer.getFirstName() + " Successfully saved!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }
    @Override
    public String saveCustomerWithRent(Customer newCustomer,Long houseId, Long agencyId,
                                       LocalDate checkIn, LocalDate checkout){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            House findHouse = entityManager.find(House.class, houseId);
            if (!checkHouseAble(entityManager, houseId, checkIn, checkout)){
                return "There are no houses for the selected dates";
            }
            RentInfo rentInfo = new RentInfo();
            rentInfo.setCustomer(newCustomer);
            rentInfo.setHouse(findHouse);
            rentInfo.setAgency(findAgency);
            rentInfo.setCheckIn(checkIn);
            rentInfo.setCheckOut(checkout);
            findHouse.setRentInfo(rentInfo);
            findAgency.getRentInfo().add(rentInfo);
            newCustomer.getRentInfo().add(rentInfo);
            entityManager.persist(rentInfo);
            entityManager.persist(newCustomer);
            entityManager.getTransaction().commit();
            return newCustomer.getFirstName() + " Successfully saved!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Customer> findCustomerById(Long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer findCustomer = null;
        try {
            entityManager.getTransaction().begin();
            findCustomer = entityManager.createQuery("select c from Customer c" +
                            " where id =:customerId", Customer.class)
                    .setParameter("customerId", customerId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return Optional.ofNullable(findCustomer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Customer> customers = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            customers = entityManager.createQuery("select c from Customer c", Customer.class)
                    .getResultList();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }
        return customers;
    }

    @Override
    public String updateCustomerById(Long customerId, Customer newCustomer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer findCustomer = entityManager.find(Customer.class, customerId);
            findCustomer.setFirstName(newCustomer.getFirstName());
            findCustomer.setLastName(newCustomer.getLastName());
            findCustomer.setEmail(newCustomer.getEmail());
            findCustomer.setDateOfBirth(newCustomer.getDateOfBirth());
            findCustomer.setGender(newCustomer.getGender());
            findCustomer.setNationality(newCustomer.getNationality());
            findCustomer.setFamilyStatus(newCustomer.getFamilyStatus());
            entityManager.getTransaction().commit();
            return newCustomer.getFirstName() + " Successfully updated!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public String deleteCustomerById(Long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer findCustomer = entityManager.find(Customer.class, customerId);
            List<RentInfo> rentInfo = findCustomer.getRentInfo();
            if (!rentInfo.isEmpty()){
                for (RentInfo info : rentInfo) {
                    if (info.getCheckOut().isAfter(LocalDate.now())){
                        return "Customer rent for the future "+info.getCheckOut();
                    }
                    info.setCustomer(null);
                    info.setOwner(null);
                    info.setAgency(null);
                }
            }
            entityManager.remove(findCustomer);
            entityManager.getTransaction().commit();
            return findCustomer.getFirstName() +" Successfully deleted!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }
    }

    public String rentingHouseByCustomer(Long customerId, Long houseId, Long agencyId,
                                         LocalDate checkIn, LocalDate checkout){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer findCustomer = entityManager.find(Customer.class, customerId);
            House findHouse = entityManager.find(House.class, houseId);
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            boolean houseAble = checkHouseAble(entityManager, houseId, checkIn, checkout);
            if (!houseAble){
                return "There are no houses for the selected dates";
            }
            RentInfo rentInfo = new RentInfo();
            rentInfo.setCustomer(findCustomer);
            rentInfo.setHouse(findHouse);
            rentInfo.setAgency(findAgency);
            rentInfo.setCheckIn(checkIn);
            rentInfo.setCheckOut(checkout);
            findCustomer.getRentInfo().add(rentInfo);
            findHouse.setRentInfo(rentInfo);
            findAgency.getRentInfo().add(rentInfo);
            entityManager.persist(rentInfo);
            entityManager.getTransaction().commit();
            return "House successfully rented by customer!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }

    private boolean checkHouseAble(EntityManager entityManager, Long houseId,
                                   LocalDate checkIn, LocalDate checkout) {
        String jpql = "select count(distinct r.house.id) from RentInfo r " +
                "where r.house.id = :houseId " +
                "and (:checkIn between r.checkIn and r.checkIn " +
                "or :checkout between r.checkIn and r.checkOut)";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("houseId", houseId)
                .setParameter("checkIn", checkIn)
                .setParameter("checkout", checkout)
                .getSingleResult();

        return count == 0;
    }
}
