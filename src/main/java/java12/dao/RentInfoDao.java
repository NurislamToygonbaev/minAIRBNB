package java12.dao;

import java12.entities.House;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;

public interface RentInfoDao {
    List<RentInfo> rentInfoBetweenDates(LocalDate fromDate, LocalDate toDate);
    List<House> housesByAgencyIdAndDate(Long agencyId, LocalDate currentDate);
}
