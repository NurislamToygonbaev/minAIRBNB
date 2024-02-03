package java12;


import java12.entities.Address;
import java12.entities.Agency;
import java12.service.*;
import java12.service.serviceImpl.*;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerStr = new Scanner(System.in);

        AddressService addressService = new AddressServiceImpl();
        AgencyService agencyService = new AgencyServiceImpl();
        CustomerService customerService = new CustomerServiceImpl();
        HouseService houseService = new HouseServiceImpl();
        OwnerService ownerService = new OwnerServiceImpl();
        RentInfoService rentInfoService = new RentInfoServiceImpl();


        Loop:
        while (true){
            menu();
            switch (scanner.nextInt()){
                case 1 -> {
                    System.out.println(agencyService.saveAgency(new Agency("peaksoft", "+996500400300"),
                            new Address("Bishkek", "Chuy", "kievckaya")));
                }
                case 2 ->{
                    System.out.print("Write agency id: ");
                    System.out.println(agencyService.findAgencyById(scanner.nextLong()));
                }
                case 3 -> System.out.println(agencyService.findAllAgencies());
                case 4 -> {
                    System.out.print("Write agency id: ");
                    System.out.println(agencyService.updateAgencyById(scanner.nextLong(),
                            new Agency("batken", "+996700700700")));
                }
                case 5 -> {
                    System.out.print("Write agency id: ");
                    System.out.println(agencyService.deleteAgencyById(scanner.nextLong()));
                }
                case 6 -> {
                    System.out.print("Write address id: ");
                    System.out.println(addressService.findAddressById(scanner.nextLong()));
                }
                case 7 -> System.out.println(addressService.findAllAddresses());
                case 8 -> {
                    System.out.print("Write address id: ");
                    System.out.println(addressService.updateAddressById(scanner.nextLong(), new Address("osh", "kg", "oshhh")));
                }
                case 9 -> System.out.println(addressService.findAddressWithAgency());
                case 10 -> {
                    System.out.print("Write city: ");
                    System.out.println(addressService.countAgenciesInTheCity("osh"));
                }
                case 11 -> System.out.println(addressService.groupByRegion());
            }
        }
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
