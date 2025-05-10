package br.com.sorrisoemjogo.SorrisoEmJogo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ex.: "ROLE_USER", "ROLE_ADMIN"
    @Column(unique = true, nullable = false)
    private String name;
}