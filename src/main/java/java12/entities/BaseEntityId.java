package java12.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public class BaseEntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_gen_id")
    private Long id;
}
