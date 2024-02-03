package java12.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen_id", sequenceName = "address_seq", allocationSize = 1)
public class Address extends BaseEntityId {
    @Column(nullable = false, unique = true)
    private String city;
    @Column(nullable = false, unique = true)
    private String region;
    @Column(nullable = false)
    private String street;
    @OneToOne(cascade = {CascadeType.PERSIST})
    private Agency agency;
    @OneToOne
    private House house;

    public Address(String city, String region, String street) {
        this.city = city;
        this.region = region;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
