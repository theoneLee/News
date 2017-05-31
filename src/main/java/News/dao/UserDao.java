package News.dao;

import News.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lee on 2017/5/31 0031.
 */
public interface UserDao extends JpaRepository<User,Long>{

    User findByName(String reqName);
}
