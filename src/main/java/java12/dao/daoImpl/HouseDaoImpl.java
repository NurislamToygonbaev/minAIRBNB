package java12.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.HouseDao;
import java12.entities.House;
import java12.entities.Owner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HouseDaoImpl implements HouseDao, AutoCloseable {
    private final EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();
    @Override
    public String saveHouse(Long ownerId, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            findOwner.getHouses().add(newHouse);
            newHouse.setOwner(findOwner);
            entityManager.persist(newHouse);
            entityManager.getTransaction().commit();
            return newHouse.getHouseType() + " Successfully saved!!!";
        }catch (Exception r){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+r.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<House> findHouseById(Long houseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        House findHouse = null;
        try {
            entityManager.getTransaction().begin();
            findHouse = entityManager.createQuery("select h from House h where id =:houseId", House.class)
                    .setParameter("houseId", houseId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return Optional.ofNullable(findHouse);
    }

    @Override
    public List<House> findAllHouse() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h", House.class)
                    .getResultList();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public String updateHouseById(Long houseId, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("""
            update House set houseType =:houseType, price =:price, rating =:rating,
            description =:description, room =:room, furniture =:furniture where id =:houseId""")
                            .setParameter("houseType", newHouse.getHouseType())
                            .setParameter("price", newHouse.getPrice())
                            .setParameter("rating", newHouse.getRating())
                            .setParameter("description", newHouse.getDescription())
                            .setParameter("room", newHouse.getRoom())
                            .setParameter("furniture", newHouse.isFurniture())
                            .setParameter("houseId", houseId)
                                    .executeUpdate();

            entityManager.getTransaction().commit();
            return newHouse.getHouseType() + " Successfully updated!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public String deleteHouseById(Long houseId) {
        return null;
    }

    @Override
    public List<House> getHousesInRegion(String region) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h join h.address a where a.region =:region", House.class)
                            .setParameter("region", region).getResultList();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public List<House> allHousesByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h join h.address a join a.agency ag where ag.id =:agencyId", House.class)
                    .setParameter("agencyId", agencyId).getResultList();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public List<House> allHousesByOwnerId(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h join h.owner o where o.id =:ownerId", House.class)
                    .setParameter("ownerId", ownerId).getResultList();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println("Failed: "+e.getMessage());
        }finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public List<House> housesBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
