package java12.dao;

import java12.entities.House;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HouseDao {
    String saveHouse(Long ownerId, House newHouse);
    Optional<House> findHouseById(Long houseId);
    List<House> findAllHouse();
    String updateHouseById(Long houseId, House newHouse);
    String deleteHouseById(Long houseId);
    List<House> getHousesInRegion(String region);
    List<House> allHousesByAgencyId(Long agencyId);
    List<House> allHousesByOwnerId(Long ownerId);
    List<House> housesBetweenDates(LocalDate fromDate, LocalDate toDate);
}
