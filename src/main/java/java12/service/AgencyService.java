package java12.service;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Optional;

public interface AgencyService {
    String saveAgency(Agency newAgency, Address newAddress);
    Agency findAgencyById(Long agencyId);
    List<Agency> FindAllAgencies();
    String updateAgencyById(Long agencyId, Agency newAgency);
    String deleteAgencyById(Long agencyId);
}
