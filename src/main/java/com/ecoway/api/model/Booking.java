package com.ecoway.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reservas")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "data_retirada")
    private LocalDate checkInDay;

    @Column(name = "hora_retirada")
    private LocalTime checkInTime;

    @Column(name = "data_entrega")
    private LocalDate checkOutDay;

    @Column(name = "hora_entrega")
    private LocalTime checkOutTime;

    @Column(name = "status_reserva")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private SystemUser user;

    @ManyToOne
    @JoinColumn(name = "id_cidade")
    private City city;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Category category;

}
