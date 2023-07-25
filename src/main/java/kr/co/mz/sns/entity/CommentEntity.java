package kr.co.mz.sns.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comment", schema = "sns", catalog = "")
@Data
@NoArgsConstructor
public class CommentEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private Long seq;
  @Basic
  @Column(name = "content")
  private String content;
  @Basic
  @Column(name = "created_at")
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at")
  private Timestamp modifiedAt;

  @ManyToOne
  @JoinColumn(name = "user_seq")
  private UserEntity userEntity;

  @ManyToOne
  @JoinColumn(name = "post_seq")
  private PostEntity postEntity;

  public void setPosts(PostEntity postEntity){
    this.postEntity = postEntity;
    postEntity.getComments().add(this);
  }
  public void setUsers(UserEntity userEntity){
    this.userEntity = userEntity;
    userEntity.getComments().add(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CommentEntity that)) return false;
    return Objects.equals(getSeq(), that.getSeq()) && Objects.equals(getContent(), that.getContent()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getModifiedAt(), that.getModifiedAt()) && Objects.equals(getUserEntity(), that.getUserEntity()) && Objects.equals(getPostEntity(), that.getPostEntity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSeq(), getContent(), getCreatedAt(), getModifiedAt(), getUserEntity(), getPostEntity());
  }
}
