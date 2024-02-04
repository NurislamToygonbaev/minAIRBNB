package java12.dao;

import java12.entities.House;
import java12.entities.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OwnerDao {
    String saveOwner(Owner newOwner);
    String saveOwner(Owner newOwner, House newHouse);
    Optional<Owner> findOwnerById(Long ownerId);
    List<Owner> findAllOwners();
    String updateOwnerById(Long ownerId, Owner newOwner);
    String deleteOwnerById(Long ownerId);
    String assignOwnerToAgency(Long ownerId, Long agencyId);
    List<Owner> getOwnersByAgencyId(Long agencyId);
    Map<String, Integer> getOwnerOnlyNameAndAge();
}
