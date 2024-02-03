package java12.entities;

import jakarta.persistence.*;
import java12.enums.FamilyStatus;
import java12.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen_id", sequenceName = "customer_seq", allocationSize = 1)
public class Customer extends BaseEntityId{
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private Gender gender;
    private String nationality;
    @Column(name = "family_status", nullable = false)
    private FamilyStatus familyStatus;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RentInfo> rentInfo;

    public Customer(String firstName, String lastName, String email, LocalDate dateOfBirth,
                    Gender gender, String nationality, FamilyStatus familyStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.familyStatus = familyStatus;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", nationality='" + nationality + '\'' +
                ", familyStatus=" + familyStatus +
                '}';
    }
}
