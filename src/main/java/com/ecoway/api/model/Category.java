package com.ecoway.api.model;

import com.ecoway.api.model.enums.CategoryName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categorias")
public class Category {
    @Id
    private String id;

    @Column(name="nome_categoria")
    private String name;

    @Column(name="descricao", length = 65535 )
    private String description;

    @Column(name="preco_base", columnDefinition = "decimal")
    private Double basePrice;

    @Column(name="autonomia_media")
    private Integer autonomyAverage;

    @Column(name = "tempo_recarga_medio")
    private Integer rechargeTimeAverage;

    @ManyToMany
    @JoinTable(
            name = "categorias_caracteristicas",
            joinColumns = @JoinColumn(name = "id_categoria"),
            inverseJoinColumns = @JoinColumn(name = "id_caracteristica")
    )
    private List<Feature> feature;

    @OneToMany
    @JoinTable(name = "modelos_categorias",
            joinColumns = @JoinColumn(name = "id_categoria"),
            inverseJoinColumns = @JoinColumn(name = "id_modelo")
    )
    private List<VehicleModel> vehicles;
}
