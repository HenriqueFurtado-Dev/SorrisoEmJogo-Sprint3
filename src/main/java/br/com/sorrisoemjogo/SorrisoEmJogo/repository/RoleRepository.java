package br.com.sorrisoemjogo.SorrisoEmJogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
