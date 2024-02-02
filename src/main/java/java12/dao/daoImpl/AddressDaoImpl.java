package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.AddressDao;
import java12.entities.Address;
import java12.entities.Agency;

import java.util.*;

public class AddressDaoImpl implements AddressDao, AutoCloseable{
    private final EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();
    @Override
    public Optional<Address> findAddressById(Long addressId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address findAddress = null;
        try {
            entityManager.getTransaction().begin();
            findAddress = entityManager.createQuery("select a from Address a where id =:addressId", Address.class)
                    .setParameter("addressId", addressId)
                    .getSingleResult();
            
            entityManager.getTransaction().commit();
        } catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return Optional.ofNullable(findAddress);
    }
    @Override
    public List<Address> findAllAddresses(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Address> addresses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            addresses = entityManager.createQuery("select a from Address a", Address.class)
                    .getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return addresses;
    }
    @Override
    public String updateAddressById(Long addressId, Address newAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("update Address set city =:city, region =:region, street =:street where id =:id", Address.class)
                            .setParameter("city", newAddress.getCity())
                            .setParameter("region", newAddress.getRegion())
                            .setParameter("street", newAddress.getStreet())
                            .setParameter("id", addressId)
                                    .executeUpdate();
            entityManager.getTransaction().commit();
            return newAddress.getCity() + " Successfully saved!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Address> findAddressWithAgency(Long addressId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address findAddress = null;
        try {
            entityManager.getTransaction().begin();
            findAddress = entityManager.createQuery("select a from Address a join a.agency where a.id =:addressId", Address.class)
                    .setParameter("addressId", addressId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return Optional.ofNullable(findAddress);
    }

    @Override
    public Integer countAgenciesInTheCity(String city) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Integer findCity = 0;
        try {
            entityManager.getTransaction().begin();
            findCity = entityManager.createQuery("select count(Agency.id) from Address a join a.agency where a.city =:city", Integer.class)
                    .setParameter("city", city)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return findCity;
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<String, Object> regionGroupsBy = new HashMap<>();
        Map<String, List<Agency>> listMap = new HashMap<>();
        try {
            entityManager.getTransaction().begin();
            regionGroupsBy = entityManager.createQuery("select a.region, ag from Address a join a.agency ag group by a.region", Map.class)
                    .getHints();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return listMap;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
