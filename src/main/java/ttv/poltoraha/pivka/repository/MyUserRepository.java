package ttv.poltoraha.pivka.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.MyUser;

import java.util.List;

@Repository
public interface MyUserRepository extends CrudRepository<MyUser, String> {
    List<MyUser> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE app_user u SET u.password = :password WHERE u.username = :username")
    void updatePassword(@Param("username") String username, @Param("password") String password);
}
