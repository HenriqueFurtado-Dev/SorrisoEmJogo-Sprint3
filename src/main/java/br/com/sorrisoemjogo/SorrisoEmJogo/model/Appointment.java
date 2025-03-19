package br.com.sorrisoemjogo.SorrisoEmJogo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDateTime;

    // Novo campo para armazenar o problema do paciente
    private String problemaPaciente;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
