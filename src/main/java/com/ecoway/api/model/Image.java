package com.ecoway.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "imagens")
public class Image {
    @Id
    private String id;
    @Column(name = "titulo")
    private String title;
    private String url;
}
