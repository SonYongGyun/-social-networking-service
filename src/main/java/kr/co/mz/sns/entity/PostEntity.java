package kr.co.mz.sns.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "Post", schema = "sns")
@Data
@NoArgsConstructor
public class PostEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private Long seq;
  @Basic
  @Column(name = "content", nullable = false)
  private String content;
  @Basic
  @Column(name = "likes", columnDefinition = "int DEFAULT 0")
  private Integer likes;
  @Basic
  @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private Timestamp modifiedAt;

  @ManyToOne
  @JoinColumn(name="user_seq", nullable = false)
  private UserEntity userEntity;

  @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentEntity> comments = new ArrayList<>();


  public void setUsers(UserEntity userEntity){
      this.userEntity = userEntity;
      userEntity.getPosts().add(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var that = (PostEntity) o;
    return Objects.equals(seq, that.seq) && Objects.equals(userEntity, that.userEntity) && Objects.equals(content, that.content)
        && Objects.equals(likes, that.likes) && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(modifiedAt, that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, content, likes, createdAt, modifiedAt, userEntity);
  }
}
