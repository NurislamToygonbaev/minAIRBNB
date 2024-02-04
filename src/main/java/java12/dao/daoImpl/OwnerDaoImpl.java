package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.OwnerDao;
import java12.entities.Agency;
import java12.entities.House;
import java12.entities.Owner;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.*;

public class OwnerDaoImpl implements OwnerDao, AutoCloseable {
    private final EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public String saveOwner(Owner newOwner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(newOwner);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: " + e.getMessage();
        } finally {
            entityManager.close();
        }
        return newOwner.getFirstName() + " Successfully saved!!!";
    }

    @Override
    public String saveOwner(Owner newOwner, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            newOwner.addHouse(newHouse);
            newHouse.setOwner(newOwner);
            entityManager.persist(newOwner);
            entityManager.persist(newHouse);
            entityManager.getTransaction().commit();
            return newOwner.getFirstName() + " Successfully saved!!!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Owner> findOwnerById(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner findOwner = null;
        try {
            entityManager.getTransaction().begin();
            findOwner = entityManager.createQuery("select o from Owner o where id =:ownerId", Owner.class)
                    .setParameter("ownerId", ownerId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(findOwner);
    }

    @Override
    public List<Owner> findAllOwners() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            owners = entityManager.createQuery("select o from Owner o", Owner.class)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return owners;
    }

    @Override
    public String updateOwnerById(Long ownerId, Owner newOwner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            findOwner.setFirstName(newOwner.getFirstName());
            findOwner.setLastName(newOwner.getLastName());
            findOwner.setEmail(newOwner.getEmail());
            findOwner.setDateOfBirth(newOwner.getDateOfBirth());
            findOwner.setGender(newOwner.getGender());
            entityManager.getTransaction().commit();
            return newOwner.getFirstName() + " Successfully updated!!!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public String deleteOwnerById(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            List<RentInfo> rentInfo = findOwner.getRentInfo();
            if (!rentInfo.isEmpty()){
                for (RentInfo info : rentInfo) {
                    if (info.getCheckOut().isAfter(LocalDate.now())) {
                        return "cannot be deleted . A client lives in the owner's house";
                    }
                    info.setCustomer(null);
                    info.setAgency(null);
                }
            }
            entityManager.remove(findOwner);
            entityManager.getTransaction().commit();
            return findOwner.getFirstName()+" Successfully deleted";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public String assignOwnerToAgency(Long ownerId, Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            findOwner.getAgencies().add(findAgency);
            findAgency.getOwners().add(findOwner);
            entityManager.getTransaction().commit();
            return "Successfully assigned!!!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: " + e.getMessage();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            owners = entityManager.createQuery("select o from Owner o join o.agencies a" +
                            " where a.id =:agencyId", Owner.class)
                    .setParameter("agencyId", agencyId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return owners;
    }

    @Override
    public Map<String, Integer> getOwnerOnlyNameAndAge() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<String, Integer> namesAndAges = new HashMap<>();
        try {
            entityManager.getTransaction().begin();
            List<Owner> allOwners = entityManager.createQuery("select o from Owner o", Owner.class)
                    .getResultList();
            for (Owner allOwner : allOwners) {
                namesAndAges.put(allOwner.getFirstName(), LocalDate.now().getYear() - allOwner.getDateOfBirth().getYear());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return namesAndAges;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
