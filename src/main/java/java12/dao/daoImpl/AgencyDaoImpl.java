package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.AgencyDao;
import java12.entities.Address;
import java12.entities.Agency;
import java12.entities.RentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgencyDaoImpl implements AgencyDao, AutoCloseable {
    private final EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();
    @Override
    public String saveAgency(Agency newAgency, Address newAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            newAddress.setAgency(newAgency);
            newAgency.setAddress(newAddress);
            entityManager.persist(newAddress);
            entityManager.persist(newAgency);
            entityManager.getTransaction().commit();
            return newAgency.getName() + " Successfully saved!!!";
        } catch (Exception r){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+r.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Agency> findAgencyById(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Agency findAgency = null;
        try {
            entityManager.getTransaction().begin();
            findAgency = entityManager.createQuery("select a from Agency a where id =:agencyId", Agency.class)
                    .setParameter("agencyId", agencyId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception r){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+r.getMessage());
        }finally {
            entityManager.close();
        }
        return Optional.ofNullable(findAgency);
    }

    @Override
    public List<Agency> findAllAgencies() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Agency> agencies = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            agencies = entityManager.createQuery("select a from Agency a", Agency.class)
                    .getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return agencies;
    }

    @Override
    public String updateAgencyById(Long agencyId, Agency newAgency) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            findAgency.setName(newAgency.getName());
            findAgency.setPhoneNumber(newAgency.getPhoneNumber());
            entityManager.getTransaction().commit();
            return newAgency.getName()+" successfully updated!!!";
        } catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public String deleteAgencyById(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency findAgency = entityManager.find(Agency.class, agencyId);

            for (RentInfo rentInfo : findAgency.getRentInfo()) {
                rentInfo.setOwner(null);
                rentInfo.setHouse(null);
                rentInfo.setCustomer(null);
            }
            entityManager.remove(findAgency);
            entityManager.getTransaction().commit();
            return findAgency.getName() + " Successfully deleted";
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
}
