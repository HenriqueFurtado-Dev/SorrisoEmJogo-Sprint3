package br.com.sorrisoemjogo.SorrisoEmJogo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    // Campo para idade (tipo int)
    private int idade;

    private String email;

    // Nome do campo: problemasOdontologicos
    @Column(name = "odontologic_problems")
    private String problemasOdontologicos;

    // Nome do campo: planoOdontologico
    @Column(name = "plano_odontologico")
    private String planoOdontologico;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
