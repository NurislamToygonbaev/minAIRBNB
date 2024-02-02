package java12.dao;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AddressDao {
    Optional<Address> findAddressById(Long addressId);
    List<Address> findAllAddresses();
    String updateAddressById(Long addressId, Address newAddress);
    Map<Address, Agency>  findAddressWithAgency();
    Integer countAgenciesInTheCity(String city);
    Map<String, List<Agency>> groupByRegion();
}
