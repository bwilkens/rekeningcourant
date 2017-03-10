package nl.quintor.rc.security;

import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.repository.GebruikerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("loading credentials for user " + username);
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (username.equalsIgnoreCase("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        final Gebruiker gebruiker = gebruikerRepository.getGebruikerByInlognaam(username);
        LOG.debug("ingelogde gebruiker = " + gebruiker.getLoginnaam() + " : " + gebruiker.getPassword());
        return new User(gebruiker.getLoginnaam(), gebruiker.getPassword(), authorities);
    }
}