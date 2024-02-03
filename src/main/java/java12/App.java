package java12;


import java12.entities.Address;
import java12.entities.Agency;
import java12.entities.Customer;
import java12.entities.House;
import java12.enums.FamilyStatus;
import java12.enums.Gender;
import java12.enums.HouseType;
import java12.service.*;
import java12.service.serviceImpl.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerStr = new Scanner(System.in);

//        AddressService addressService = new AddressServiceImpl();
//        System.out.print("Write address id: ");
//        System.out.println(addressService.findAddressById(scanner.nextLong()));
//        System.out.println(addressService.findAllAddresses());
//        System.out.print("Write address id: ");
//        System.out.println(addressService.updateAddressById(scanner.nextLong(),
//                new Address("osh", "kg", "oshhh")));
//        System.out.println(addressService.findAddressWithAgency());
//        System.out.print("Write city: ");
//        System.out.println(addressService.countAgenciesInTheCity(scannerStr.nextLine()));
//        System.out.println(addressService.groupByRegion());


//        AgencyService agencyService = new AgencyServiceImpl();
//        System.out.println(agencyService.saveAgency(new Agency("peaksoft", "+996500400300"),
//                new Address("Bishkek", "Chuy", "kievckaya")));
//        System.out.print("Write agency id: ");
//        System.out.println(agencyService.findAgencyById(scanner.nextLong()));
//              System.out.println(agencyService.findAllAgencies());
//        System.out.print("Write agency id: ");
//        System.out.println(agencyService.updateAgencyById(scanner.nextLong(),
//                new Agency("batken", "+996700700700")));
//        System.out.print("Write agency id: ");
//        System.out.println(agencyService.deleteAgencyById(scanner.nextLong()));


        CustomerService customerService = new CustomerServiceImpl();
//        System.out.println(customerService.saveCustomer(new Customer(
//                "Nurkamil", "Kamchiev", "n@gmail.com", LocalDate.of(2001, 5,19),
//                Gender.MALE, "Kyzgyz", FamilyStatus.SINGLE
//        )));
//        System.out.println(customerService.saveCustomerWithRent(new Customer(
//                "Nur", "Toi", "nwert@gmail.com", LocalDate.of(1998, 9,23),
//                Gender.MALE, "Kyzgyz", FamilyStatus.SINGLE
//        ), 1L, 1L, LocalDate.of(2024, 5,25),
//                LocalDate.of(2024, 6, 2)));
//        System.out.println(customerService.findCustomerById(1L));
//        System.out.println(customerService.findAllCustomers());
//        System.out.println(customerService.updateCustomerById(1L, new Customer(
//                "aaaa", "zzz", "aa@gmail.com", LocalDate.of(2002,2,5),
//                Gender.MALE, "KG", FamilyStatus.SINGLE
//        )));
//        System.out.println(customerService.rentingHouseByCustomer(1L, 2L, 1L,
//                LocalDate.of(2024, 8, 25),
//                LocalDate.of(2024, 9, 2)));
//        System.out.println(customerService.deleteCustomerById(1L));



        HouseService houseService = new HouseServiceImpl();
//        System.out.println(houseService.saveHouse(1L, new House(
//                HouseType.VIP, BigDecimal.valueOf(194232), 4, "qwert", 3, true
//        )));
        System.out.println(houseService.findHouseById(1L));


//        OwnerService ownerService = new OwnerServiceImpl();
//        RentInfoService rentInfoService = new RentInfoServiceImpl();

    }

    private static void menu(){
        System.out.println("""
                         Agency
                1. save with Address
                2. find
                3. find all
                4. update
                5. delete
                
                         Address
                6. find
                7. find all
                8. update
                9. addresses with agency
                10. how many agency in city
                11. group by region
                
                         Customer
                12. save
                13. save with rent
                14. find
                15. find all
                16. update
                17. delete
                18. rent
                
                          House
                19. save
                20. find
                21. find all
                22. update
                23. delete
                24. houses in region
                25. all houses by agency
                26. all houses by owner
                27. between dates
                
                          Owner
                28. save
                29. save with house
                30. find
                31. find all
                32. update
                33. delete
                34. assign owner to agency
                35. owners by agency
                36. owners only name and age
                
                          RentInfo
                37. renting between dates
                38. how many house in agency
                """);
        System.out.print("----->>>");
    }
}
