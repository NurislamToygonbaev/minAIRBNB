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
            findAddress = entityManager.createQuery("select a from Address a where id =:addressId",
                            Address.class)
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
            Address findAddress = entityManager.find(Address.class, addressId);
            findAddress.setCity(newAddress.getCity());
            findAddress.setRegion(newAddress.getRegion());
            findAddress.setStreet(newAddress.getStreet());
            entityManager.getTransaction().commit();
            return newAddress.getCity() + " Successfully updated!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Map<Address, Agency> findAddressWithAgency() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<Address, Agency> addresses = new HashMap<>();
        try {
            entityManager.getTransaction().begin();

            List<Address> resultList = entityManager.createQuery(
                    "select distinct a from Address a join a.agency", Address.class)
                    .getResultList();
            for (Address address : resultList) {
                addresses.put(address, address.getAgency());
            }

            entityManager.getTransaction().commit();
        } catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return addresses;
    }

    @Override
    public Long countAgenciesInTheCity(String city) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long findCity = 0L;
        try {
            entityManager.getTransaction().begin();
            findCity = entityManager.createQuery("select count(a.id) from Agency a" +
                            " where a.address.city =:city", Long.class)
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
        Map<String, List<Agency>> map = new HashMap<>();
        try {
            entityManager.getTransaction().begin();
            List<Address> addresses = entityManager.createQuery("select a from Address a", Address.class)
                    .getResultList();
            List<Agency> agencies = new ArrayList<>();
            for (Address address : addresses) {
                agencies.add(address.getAgency());
                map.put(address.getRegion(), agencies);
            }
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return map;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
