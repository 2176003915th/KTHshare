package idusw.leafton.model.repository;

import idusw.leafton.model.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

//Member entity에 접근
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /* id와 password를 매개변수로 받아 해당 매개변수로 member entity에 레코드를 조회하는 메서드로,
    레코드가 없을 경우가 많기 때문에 리턴값을 Optional로 준다. */
    Optional<Member> findByEmailAndPassword(String email, String password);

    //email을 통해 select
    Optional<Member> findByEmail(String email);

    //relation의 데이터에 따라 insert 혹은 update 처리
    @Override
    <S extends Member> S save(S member);

    @Override
    void deleteById(Long memberId);
}