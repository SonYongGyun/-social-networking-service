package kr.co.mz.sns.repository.user;

import kr.co.mz.sns.dto.user.friend.FriendDetailDto;
import kr.co.mz.sns.entity.friend.FriendRelationshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationshipEntity, Long> {

    @Query("""
            select new kr.co.mz.sns.dto.user.friend.FriendDetailDto (
                f.seq,
                f.userEntity.seq,
                f.status,
                f.createdAt,
                f.friendEntity.seq,
                f.friendEntity.name
            )
            FROM FriendRelationshipEntity f
            WHERE f.userEntity.seq = :userSeq
            """)
    Page<FriendDetailDto> findAllByUserIdAsPage(@Param("userSeq") Long userSeq, Pageable pageable);

    Optional<FriendRelationshipEntity> findByUserEntity_SeqAndFriendEntity_Seq(Long userSeq, Long friendSeq);

    @Query("""
            select new kr.co.mz.sns.dto.user.friend.FriendDetailDto (
                f.seq,
                f.userEntity.seq,
                f.status,
                f.createdAt,
                f.friendEntity.seq,
                f.friendEntity.name
            )
            from FriendRelationshipEntity f
            where f.userEntity.seq = :userSeq and f.friendEntity.name = :friendName and f.status = :status
            """)
    List<FriendDetailDto> findByUserIdAndFriendNameAndStatus(
            @Param("userSeq") Long userSeq,
            @Param("friendName") String friendName,
            @Param("status") String status
    );
}

