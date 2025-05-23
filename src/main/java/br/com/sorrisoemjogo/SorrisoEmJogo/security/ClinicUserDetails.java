package br.com.sorrisoemjogo.SorrisoEmJogo.security;

import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class ClinicUserDetails implements UserDetails {

    private final Clinic clinic;

    public ClinicUserDetails(Clinic clinic) {
        this.clinic = clinic;
    }

    // Expor a clínica para os templates: #authentication.principal.clinic
    public Clinic getClinic() {
        return clinic;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return clinic.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
    @Override public String getUsername()               { return clinic.getEmail(); }
    @Override public String getPassword()               { return clinic.getPassword(); }
    @Override public boolean isAccountNonExpired()      { return true; }
    @Override public boolean isAccountNonLocked()       { return true; }
    @Override public boolean isCredentialsNonExpired()  { return true; }
    @Override public boolean isEnabled()                { return true; }
}
