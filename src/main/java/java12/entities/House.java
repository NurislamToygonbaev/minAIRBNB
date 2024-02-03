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
@Getter @Setter
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
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private RentInfo rentInfo;
    @ManyToOne
    private Owner owner;

    public House(HouseType houseType, BigDecimal price, double rating,
                 String description, int room, boolean furniture) {
        this.houseType = houseType;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.room = room;
        this.furniture = furniture;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseType=" + houseType +
                ", price=" + price +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", room=" + room +
                ", furniture=" + furniture +
                '}';
    }
}
