package java12.dao;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Optional;

public interface AgencyDao {
    String saveAgency(Agency newAgency, Address newAddress);
    Optional<Agency> findAgencyById(Long agencyId);
    List<Agency> findAllAgencies();
    String updateAgencyById(Long agencyId, Agency newAgency);
    String deleteAgencyById(Long agencyId);
}
