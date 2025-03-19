package br.com.sorrisoemjogo.SorrisoEmJogo.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDateTime;

    // Relacionamento muitos para um com Client
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
