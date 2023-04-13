package com.ecoway.api.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cidades")
public class City {
    @Id
    private String id;
    @Column(name = "cidade")
    private String cityName;
    @Column(name = "estado")
    private String state;
    @Column(name = "pais")
    private String country;

}
