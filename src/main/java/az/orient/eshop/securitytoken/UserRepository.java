package az.orient.eshop.securitytoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsernameAndPasswordAndActive(String username,String password,Integer active);

}
