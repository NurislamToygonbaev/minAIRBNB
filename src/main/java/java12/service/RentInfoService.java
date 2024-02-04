package java12.service;

import java12.entities.House;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;

public interface RentInfoService {
    List<RentInfo> rentInfoBetweenDates(LocalDate fromDate, LocalDate toDate);
    String housesByAgencyIdAndDate(Long agencyId);
}
