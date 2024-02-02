package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.CustomerDao;
import java12.entities.Customer;

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
    public String saveCustomerWithRent(Customer newCustomer){
        return null;
    }

    @Override
    public Optional<Customer> findCustomerById(Long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer findCustomer = null;
        try {
            entityManager.getTransaction().begin();
            findCustomer = entityManager.createQuery("select c from Customer c where id =:customerId", Customer.class)
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
            entityManager.createQuery("""
            update Customer set firstName =:firstName, lastName =:lastName, email =:email,
            dateOfBirth =:dateOfBirth, gender =:gender, nationality =:nationality,
            familyStatus =:familyStatus where id =:customerId""")
                            .setParameter("firstName", newCustomer.getFirstName())
                            .setParameter("lastName", newCustomer.getLastName())
                            .setParameter("email", newCustomer.getEmail())
                            .setParameter("dateOfBirth", newCustomer.getDateOfBirth())
                            .setParameter("gender", newCustomer.getGender())
                            .setParameter("nationality", newCustomer.getNationality())
                            .setParameter("familyStatus", newCustomer.getFamilyStatus())
                            .setParameter("customerId", customerId)
                                    .executeUpdate();

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

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
