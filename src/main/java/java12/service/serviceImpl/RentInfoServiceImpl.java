package java12.service.serviceImpl;

import java12.dao.AgencyDao;
import java12.dao.RentInfoDao;
import java12.dao.daoImpl.AgencyDaoImpl;
import java12.dao.daoImpl.RentInfoDaoImpl;
import java12.entities.House;
import java12.entities.RentInfo;
import java12.service.RentInfoService;

import java.time.LocalDate;
import java.util.List;

public class RentInfoServiceImpl implements RentInfoService {
    private final RentInfoDao rentInfoDao = new RentInfoDaoImpl();
    private final AgencyDao agencyDao = new AgencyDaoImpl();
    @Override
    public List<RentInfo> rentInfoBetweenDates(LocalDate fromDate, LocalDate toDate) {
        List<RentInfo> rentInfos = rentInfoDao.rentInfoBetweenDates(fromDate, toDate);
        try {
            if (rentInfos.isEmpty()){
                throw new RuntimeException("is empty");
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return rentInfos;
    }

    @Override
    public String housesByAgencyIdAndDate(Long agencyId) {
        try {
            agencyDao.findAgencyById(agencyId)
                    .orElseThrow(() ->
                            new RuntimeException("Agency with id: "+agencyId+" not found!!!"));
        }catch (RuntimeException e){
            return e.getMessage();
        }
        Long count = rentInfoDao.housesByAgencyIdAndDate(agencyId);
        if (count <= 0){
            return "No houses for rent";
        }
        return "Houses for rent: "+count;
    }
}
