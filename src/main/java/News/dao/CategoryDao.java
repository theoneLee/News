package News.dao;

import News.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Repository
public interface CategoryDao extends JpaRepository<Category,Long>{

    //@Query("select c from Category c where c.flag=false")
    List<Category> findByFlagFalse();

}
