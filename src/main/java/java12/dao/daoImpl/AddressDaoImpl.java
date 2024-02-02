package java12.dao.daoImpl;

import jakarta.persistence.EntityManagerFactory;
import java12.config.DataBaseConnection;
import java12.dao.AddressDao;
import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddressDaoImpl implements AddressDao, AutoCloseable{
    private final EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();
    @Override
    public Optional<Address> findAddressById(Long addressId) {
        return Optional.empty();
    }

    @Override
    public String updateAddressById(Long addressId, Address newAddress) {
        return null;
    }

    @Override
    public Optional<Address> findAddressWithAgency(Long addressId) {
        return Optional.empty();
    }

    @Override
    public Integer countAgenciesInTheCity(String city) {
        return null;
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        return null;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
