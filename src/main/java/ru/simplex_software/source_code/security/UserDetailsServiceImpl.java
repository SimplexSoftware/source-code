package ru.simplex_software.source_code.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.simplex_software.source_code.dao.SpeakerDAO;
import ru.simplex_software.source_code.model.Speaker;

import java.util.ArrayList;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SpeakerDAO speakerDAO;

    @Override @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Speaker user = speakerDAO.get(username);
        boolean enabled = true;
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }
        ArrayList<GrantedAuthority> grantedAuthorities = getGrantedAuthorities();
//        User userDetails = new User(username, user.getPassword(), enabled, true, true, true, grantedAuthorities);
//        return userDetails;
        return null;
    }

    private static ArrayList<GrantedAuthority> getGrantedAuthorities() {
        final ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        return grantedAuthorities;
    }
}
