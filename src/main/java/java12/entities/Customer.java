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
@ToString
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
}
