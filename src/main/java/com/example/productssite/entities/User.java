package com.example.productssite.entities;

import com.example.productssite.roles.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    @Size(min = 2, max = 20, message = "Your name must be between 2 and 20 characters")
    @NotBlank(message = "You have to indicate your name")
    private String firstName;
    @NotBlank(message = "You have to indicate you last name")
    @Size(min = 2, max = 20, message = "Your last name must be between 2 and 20 characters")
    @Column
    private String lastName;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "The password must contain at least 1 number, 1 small letter, and 1 capital letter")
    @Pattern(regexp = "^.{7,}$",
            message = "The password must be more than 6 characters")
    @Column
    private String password;

    @Column
    @NotBlank(message = "You can not create account without email")
    @Email(message = "Invalid email address")
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number entered")
    @Column
    private String phoneNumber;
    @Column
    @Size(min = 3, max = 30, message = "Your place doesn't exist")
    private String city;
    @Column
    private boolean isEnabled = true;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private List<Role> role = new ArrayList<>();
    @OneToMany(mappedBy = "userr")
    private List<UserProduct> userProducts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isEnabled;
    }
}
