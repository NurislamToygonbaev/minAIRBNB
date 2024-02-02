package java12.entities;

import jakarta.persistence.*;
import java12.enums.HouseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "houses")
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "base_gen_id", sequenceName = "houses_seq", allocationSize = 1)
public class House extends BaseEntityId{
    @Column(name = "house_type")
    private HouseType houseType;
    private BigDecimal price;
    private double rating;
    @Column(length = 2000)
    private String description;
    private int room;
    private boolean furniture;
    @OneToOne
    private Address address;
    @OneToOne
    private RentInfo rentInfo;
    @ManyToOne
    private Owner owner;
}
