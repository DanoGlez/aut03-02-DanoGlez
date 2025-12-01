package com._dawb.AUT03_02_Diccionario.services;

import com._dawb.AUT03_02_Diccionario.interfaces.IUserService;
import com._dawb.AUT03_02_Diccionario.models.User;
import com._dawb.AUT03_02_Diccionario.repositories.IRoleRepository;
import com._dawb.AUT03_02_Diccionario.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService, IUserService {
    @Autowired
    private IUserRepository UR;
    @Autowired
    private IRoleRepository RR;

    // Añadir un nuevo Usuario
    public void addUser(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.addRole(RR.findByName("ROLE_BASIC").orElseThrow());
        UR.save(user);
    }

    // Obtener todos los usuarios
    public List<User> getAll(){
        return UR.findAll();
    }

    // Actualizar Roles
    public void updateUserRoles(User user) {
        // Guardar cambios en la base de datos
        UR.save(user);

        // Refrescar la autenticación en el contexto de seguridad
        UserDetails updatedUserDetails = loadUserByUsername(user.getNickName());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                updatedUserDetails.getPassword(),
                updatedUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    // Buscar usuario por nickName
    public Optional<User> findBynickName(String nickName){
        return UR.findBynickName(nickName);
    }

    // Buscar usuario por Email
    public Optional<User> findByEmail(String email){
        return UR.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = UR.findBynickName(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontra con el nickname: " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(
                user.getNickName(),           // username de usuario recuperado
                user.getPassword(),           // Password de usuario recuperado
                true,                 // enabled
                true,                 // accountNonExpired
                true,                 // credentialsNonExpired
                true,                 // accountNonLocked
                authorities           //lista de authorities
        );
    }
}
