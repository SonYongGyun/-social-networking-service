package kr.co.mz.sns.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User", schema = "sns")
@Data
@NoArgsConstructor
public class UserEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private Long seq;
  @Basic
  @Column(name = "email")
  private String email;
  @Basic
  @Column(name = "name")
  private String name;
  @Basic
  @Column(name = "password")
  private String password;
  @Basic
  @Column(name = "last_login_at")
  private Timestamp lastLoginAt;
  @Basic
  @Column(name = "created_at")
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at")
  private Timestamp modifiedAt;
  @Basic
  @Column
  private String role;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "seq", referencedColumnName = "User_seq")
  private UserDetailEntity userDetail;

  @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
  private List<PostEntity> posts;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var that = (UserEntity) o;
    return Objects.equals(seq, that.seq) && Objects.equals(email, that.email) && Objects.equals(name, that.name)
        && Objects.equals(password, that.password) && Objects.equals(lastLoginAt, that.lastLoginAt)
        && Objects.equals(createdAt, that.createdAt) && Objects.equals(modifiedAt, that.modifiedAt)
        && Objects.equals(role, that.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, email, name, password, lastLoginAt, role, createdAt, modifiedAt);
  }
}
