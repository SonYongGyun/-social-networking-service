//package kr.co.mz.sns.entity.friend;
//
//import jakarta.persistence.*;
//import kr.co.mz.sns.user.entity.UserEntity;
//import lombok.*;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Getter
//@Setter
//@Entity
//@Builder
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "friend")
//@EntityListeners(AuditingEntityListener.class)
//public class FriendEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected Long seq;
//
//    @ManyToMany(mappedBy = "friendSet")
//    private Set<UserEntity> userEntitySet = new HashSet<>();
//
//    public FriendEntity addUser(UserEntity userEntity) {
//        userEntitySet.add(userEntity);
//        return this;
//    }
//
//}
