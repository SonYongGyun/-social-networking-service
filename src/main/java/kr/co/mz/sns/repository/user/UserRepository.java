package kr.co.mz.sns.repository.user;

import java.util.Optional;
import kr.co.mz.sns.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findBySeq(Long seq);

  Optional<UserEntity> findByName(String name);

  @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.userDetail WHERE u.email = :email")
  Optional<UserEntity> findByEmailWithUserDetail(@Param("email") String email);

  boolean existsByEmail(String email);

  /*
   * SELECT * FROM user
   * WHERE 'email' like '%?%'  어떤 인자를 받고 그 인자에 검색어를 설정
   * ORDER BY seq DESC       내림차
   * LIMIT ?, ?;            앞의 숫자는 offset 이라고 몇개를 건너뛰고 보여주겠다는것. LIMIT 는  최대 몇개까지.
   *                         즉 페이징 할때, 이걸 쓴다.
   *
   *
   *
   *  size = 10;
   *  offset = 0+(page-1)*size;   페이지 로직.
   *
   *
   *
   *
   *
# 쿼리 정의
# query.yml
myQuery: SELECT * FROM my_table WHERE column = :param
*
* query.properties 파일은 application.yml 과 같은 경로에 두면 된다.

@Repository
public interface MyRepository extends JpaRepository<MyEntity, Long> {
    @Query(value = "${myQuery}", nativeQuery = true)
    List<MyEntity> findByColumn(@Param("param") String param);
}

위의 코드에서 @Param 어노테이션을 사용하여 name이라는 파라미터를 정의하고,
쿼리문에서 :name과 같이 사용하여 해당 파라미터를 쿼리에 넣을 수 있습니다.
*
*
*
*
*
   * */

}
