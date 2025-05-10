package br.com.sorrisoemjogo.SorrisoEmJogo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClinicRepository;

@Service
public class ClinicUserDetailsService implements UserDetailsService {
    @Autowired private ClinicRepository clinicRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clinicRepo.findByEmail(email)
                .map(ClinicUserDetails::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Clínica não encontrada: " + email));
    }
}