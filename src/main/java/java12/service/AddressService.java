package java12.service;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AddressService {
    Address findAddressById(Long addressId);
    String updateAddressById(Long addressId, Address newAddress);
    Address findAddressWithAgency(Long addressId);
    Integer countAgenciesInTheCity(String city);
    Map<String, List<Agency>> groupByRegion();
}
