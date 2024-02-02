package java12.service;

import java12.entities.House;
import java12.entities.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    String saveOwner(Owner newOwner);
    String saveOwner(Owner newOwner, House newHouse);
    Owner findOwnerById(Long ownerId);
    List<Owner> findAllOwners();
    String updateOwnerById(Long ownerId, Owner newOwner);
    String deleteOwnerById(Long ownerId);
    String assignOwnerToAgency(Long ownerId, Long agencyId);
    List<Owner> getOwnersByAgencyId(Long agencyId);
    List<Owner> getOwnerOnlyNameAndAge();
}
