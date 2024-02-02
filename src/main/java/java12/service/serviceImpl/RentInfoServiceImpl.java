package java12.service.serviceImpl;

import java12.dao.AgencyDao;
import java12.dao.RentInfoDao;
import java12.dao.daoImpl.AgencyDaoImpl;
import java12.dao.daoImpl.RentInfoDaoImpl;
import java12.entities.House;
import java12.service.RentInfoService;

import java.time.LocalDate;
import java.util.List;

public class RentInfoServiceImpl implements RentInfoService {
    private final RentInfoDao rentInfoDao = new RentInfoDaoImpl();
    private final AgencyDao agencyDao = new AgencyDaoImpl();
    @Override
    public List<House> rentInfoBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return rentInfoDao.rentInfoBetweenDates(fromDate, toDate);
    }

    @Override
    public Integer housesByAgencyIdAndDate(Long agencyId) {
        try {
            agencyDao.findAgencyById(agencyId)
                    .orElseThrow(() ->
                            new RuntimeException("Agency with id: "+agencyId+" not found!!!"));
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return rentInfoDao.housesByAgencyIdAndDate(agencyId);
    }
}
