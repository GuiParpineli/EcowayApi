package com.ecoway.api.model;

import com.ecoway.api.security.SystemUserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "usuarios")
public class SystemUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nome")
    @NotEmpty(message = "Name may not be empty")
    private String name;

    @Column(name = "sobrenome")
    @NotEmpty(message = "Lastname may not be empty")
    private String lastname;

    @Transient
    private String jwt;

    @Column(unique = true)
    @NotEmpty(message = "Email may not be empty")
    @Size(min = 5)
    private String email;

    @Column(name = "senha")
    @NotEmpty(message = "Password may not be empty")
    @Size(min = 6)
    private String password;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "cpf")
    private String cpf;

    private String cnh;

    @Column(name = "data_nascimento")
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_de_acesso")
    @NotEmpty(message = "Role may not be empty")
    private SystemUserRoles systemUserRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(systemUserRoles.name());
        return Collections.singleton(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
