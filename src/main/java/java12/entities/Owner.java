package java12.entities;

import jakarta.persistence.*;
import java12.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "base_gen_id", sequenceName = "owner_seq", allocationSize = 1)
public class Owner extends BaseEntityId{
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private Gender gender;
    @OneToMany(mappedBy = "owner")
    private List<RentInfo> rentInfo;
    @OneToMany(mappedBy = "owner")
    private List<House> houses;
    @ManyToMany(mappedBy = "owners")
    private List<Agency> agencies;
}
