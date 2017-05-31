package News.dao;

import News.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lee on 2017/5/31 0031.
 */
@Repository
public interface NewsDao extends JpaRepository<News,Long> {

    List<News> findTop4ByCategory(String categoryName, Sort sort);//又要排序，又要限制结果数量，又要有查找条件

    /**
     * 包含news且要包含commentList的
     * @param id
     * @return
     */
    @Query("select n from News n inner join fetch n.commentList where id=?1")//查询主对象时一并查询关联对象，注意在entity上的@Fetch注解也是有相关联的。
    News findById(String id);

    /**
     * 分页
     * @param category
     * @param pageable
     * @return
     */
    Page<News> findByCategory(String category, Pageable pageable);
}
