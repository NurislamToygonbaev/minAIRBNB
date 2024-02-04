package java12.service.serviceImpl;

import java12.dao.AgencyDao;
import java12.dao.OwnerDao;
import java12.dao.daoImpl.AgencyDaoImpl;
import java12.dao.daoImpl.OwnerDaoImpl;
import java12.entities.House;
import java12.entities.Owner;
import java12.service.OwnerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OwnerServiceImpl implements OwnerService {
    private final OwnerDao ownerDao = new OwnerDaoImpl();
    private final AgencyDao agencyDao = new AgencyDaoImpl();
    @Override
    public String saveOwner(Owner newOwner) {
        if (checkEmail(newOwner) == 1){
            return newOwner.getEmail() + " already exist!!!";
        }
        if (checkAge(newOwner) == 1){
            return "the age must be over 18 years old";
        }
        return ownerDao.saveOwner(newOwner);
    }

    @Override
    public String saveOwner(Owner newOwner, House newHouse) {
        if (checkEmail(newOwner) == 1){
            return newOwner.getEmail() + " already exist!!!";
        }
        if (checkAge(newOwner) == 1){
            return "the age must be over 18 years old";
        }
        return ownerDao.saveOwner(newOwner, newHouse);
    }

    @Override
    public Owner findOwnerById(Long ownerId) {
        Owner owner = null;
        try {
            owner = ownerDao.findOwnerById(ownerId)
                    .orElseThrow(() ->
                            new RuntimeException("Owner with id: "+ownerId+" not found!!!"));
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return owner;
    }

    @Override
    public List<Owner> findAllOwners() {
        return ownerDao.findAllOwners();
    }

    @Override
    public String updateOwnerById(Long ownerId, Owner newOwner) {
        Owner findOwner = findOwnerById(ownerId);
        if (findOwner == null){
            return "Failed to updated!!!";
        }
        if (checkEmail(newOwner) == 1){
            return newOwner.getEmail() + " already exist!!!";
        }
        if (checkAge(newOwner) == 1){
            return "the age must be over 18 years old";
        }
        return ownerDao.updateOwnerById(ownerId, newOwner);
    }

    @Override
    public String deleteOwnerById(Long ownerId) {
        Owner findOwner = findOwnerById(ownerId);
        if (findOwner == null){
            return "Failed to updated!!!";
        }
        return ownerDao.deleteOwnerById(ownerId);
    }

    @Override
    public String assignOwnerToAgency(Long ownerId, Long agencyId) {
        Owner findOwner = findOwnerById(ownerId);
        if (findOwner == null){
            return "Failed to assign!!!";
        }
        try {
            agencyDao.findAgencyById(agencyId)
                    .orElseThrow(() ->
                            new RuntimeException("Agency with id: "+agencyId+" not found!!!"));
        }catch (RuntimeException e){
            return e.getMessage();
        }
        return ownerDao.assignOwnerToAgency(ownerId, agencyId);
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        try {
            agencyDao.findAgencyById(agencyId)
                    .orElseThrow(() ->
                            new RuntimeException("Agency with id: "+agencyId+" not found!!!"));
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return ownerDao.getOwnersByAgencyId(agencyId);
    }

    @Override
    public Map<String, Integer> getOwnerOnlyNameAndAge() {
        return ownerDao.getOwnerOnlyNameAndAge();
    }
    private int checkEmail(Owner newOwner){
        List<Owner> allOwners = ownerDao.findAllOwners();
        for (Owner allOwner : allOwners) {
            if (allOwner.getEmail().equalsIgnoreCase(newOwner.getEmail())){
                return 1;
            }
        }
        return 0;
    }
    private int checkAge(Owner newOwner){
        int currentYear = LocalDate.now().getYear();
        int year = newOwner.getDateOfBirth().getYear();
         if (18 > (currentYear - year)){
             return 1;
         }
        return 0;
    }
}
