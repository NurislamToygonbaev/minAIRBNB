package java12.service.serviceImpl;

import java12.dao.AddressDao;
import java12.dao.daoImpl.AddressDaoImpl;
import java12.entities.Address;
import java12.entities.Agency;
import java12.service.AddressService;

import java.util.List;
import java.util.Map;

public class AddressServiceImpl implements AddressService {
    private final AddressDao addressDao = new AddressDaoImpl();

    @Override
    public Address findAddressById(Long addressId) {
        Address address = null;
        try {
            address = addressDao.findAddressById(addressId)
                    .orElseThrow(() ->
                            new RuntimeException("Address with id: " + addressId + " not found!!!"));
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return address;
    }
    @Override
    public List<Address> findAllAddresses(){
        return addressDao.findAllAddresses();
    }

    @Override
    public String updateAddressById(Long addressId, Address newAddress) {
        List<Address> allAddresses = findAllAddresses();
        for (Address allAddress : allAddresses) {
            if (allAddress.getStreet().equals(newAddress.getStreet())) {
                return newAddress.getStreet() + " already exist!!!";
            }
        }
        Address findAddress = findAddressById(addressId);
        if (findAddress == null){
            return "Failed to update!!!";
        }
        return addressDao.updateAddressById(addressId, newAddress);
    }

    @Override
    public Map<Address, Agency> findAddressWithAgency() {
        return addressDao.findAddressWithAgency();
    }

    @Override
    public Integer countAgenciesInTheCity(String city) {
        List<Address> allAddresses = findAllAddresses();
        for (Address allAddress : allAddresses) {
            if (!allAddress.getCity().equalsIgnoreCase(city)){
                System.out.println("Incorrect choice!!!");
            }
        }
        return addressDao.countAgenciesInTheCity(city);
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        return addressDao.groupByRegion();
    }
}
