package News.dao;

import News.entity.Category;
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


    List<News> findTop4ByCategory(Category category, Sort sort);//又要排序，又要限制结果数量，又要有查找条件

    /**
     * 包含news且要包含commentList的
     * @param id
     * @return
     */
    //@Query("select n from News n inner join fetch n.commentList where n.id=?1")//查询主对象时一并查询关联对象，注意在entity上的@Fetch注解也是有相关联的。注意这里的id是要指明哪一个对象
    @Query("select n from News n where n.id=?1")
    List<News> findById1(Integer id);

    /**
     * 分页
     * 因为是根据Category来查News，所以要根据cateogryName来找到对应的Category对象，然后作为参数传进来
     * @param category
     * @param pageable
     * @return
     */
    Page<News> findByCategory(Category category, Pageable pageable);

}
