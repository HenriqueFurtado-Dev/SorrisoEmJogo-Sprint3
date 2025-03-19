package br.com.sorrisoemjogo.SorrisoEmJogo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Relacionamento muitos para um com Clinic
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

}
