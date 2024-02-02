package java12.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@Getter @Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "base_gen_id", sequenceName = "address_seq", allocationSize = 1)
public class Address extends BaseEntityId {
    @Column(nullable = false, unique = true)
    private String city;
    @Column(nullable = false, unique = true)
    private String region;
    @Column(nullable = false)
    private String street;
    @OneToOne
    private Agency agency;
    @OneToOne
    private House house;
}
