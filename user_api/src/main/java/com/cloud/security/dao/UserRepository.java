/**
 *
 */
package com.cloud.security.dao;

import com.cloud.security.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author jojo
 *
 */
public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {

    /**
     * 用户名唯一查询.
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 根据用户名查询集合.
     * @param username
     * @return
     */
    List<User> findAllByUserName(String username);
}
