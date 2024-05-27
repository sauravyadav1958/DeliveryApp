package com.example.deliveryapp.Security.Entity;


import com.example.deliveryapp.Security.Enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = "email_address"
    )
)
public class SignUp implements UserDetails {

  @Id
  @SequenceGenerator(name = "signUp_sequence",
      sequenceName = "signUp_sequence",
      allocationSize = 50)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "signUp_sequence")
  private Long userId;
  private String firstName;
  private String lastName;
  @Column(
      name = "email_address"
  )
  private String emailId;
  private String password;
  private UserRole userRole;
  private boolean enabled = false;
  private boolean locked = false;


  @OneToMany(mappedBy = "signUp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ConfirmationToken> confirmationTokenList;

  public SignUp(String emailId, String firstName, String lastName, String password,
      UserRole admin) {
    this.emailId = emailId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.userRole = admin;
  }

  public SignUp(String emailId, String firstName, String lastName, String password, UserRole admin,
      boolean enabled) {
    this.emailId = emailId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.userRole = admin;
    this.enabled = enabled;
  }

  @JsonManagedReference
  public void setConfirmationTokenList(
      List<ConfirmationToken> confirmationTokenList) {
    this.confirmationTokenList = confirmationTokenList;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());

    return Collections.singletonList(grantedAuthority);
  }

  @Override
  public String getUsername() {
    return emailId;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {

    return enabled;
  }

  @Override
  public String toString() {
    return "SignUp{" +
        "userId=" + userId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailId='" + emailId + '\'' +
        ", password='" + password + '\'' +
        ", userRole=" + userRole +
        ", enabled=" + enabled +
        ", locked=" + locked +
        '}';
  }
}
