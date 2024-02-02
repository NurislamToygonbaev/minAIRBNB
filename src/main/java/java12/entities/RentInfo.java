package java12.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDate;

@Entity
@Table(name = "rent_info")
@Getter
@Setter
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "base_gen_id", sequenceName = "rentInfos_seq", allocationSize = 1)
public class RentInfo extends BaseEntityId{
    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;
    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;
    @ManyToOne
    private Agency agency;
    @OneToOne
    private House house;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Owner owner;
}
