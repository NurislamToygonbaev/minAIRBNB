package java12.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "agencies")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen_id", sequenceName = "agencies_seq", allocationSize = 1)
public class Agency extends BaseEntityId{
    @Column(unique = true, nullable = false)
    private String name;
    @Column(name = "phone_number", length = 13)
    private String phoneNumber;
    @OneToOne(mappedBy = "agency", cascade = {REMOVE}, orphanRemoval = true)
    private Address address;
    @OneToMany(cascade = {REMOVE}, orphanRemoval = true)
    private List<RentInfo> rentInfo;
    @ManyToMany
    private List<Owner> owners;

    public Agency(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
