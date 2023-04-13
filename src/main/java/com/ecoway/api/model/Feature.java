package com.ecoway.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "caracteristicas")
public class Feature {
    @Id
    private String id;
    @Column(name ="nome_caracteristica")
    private String name;
    @Column(name = "icone")
    private String icon;
}
