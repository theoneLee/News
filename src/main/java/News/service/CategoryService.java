package News.service;

import News.dao.CategoryDao;
import News.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getMoreCategory() {
        //拿到flag为false的分类，然后提取出name
        List<Category> categoryList=categoryDao.findByFlagFalse();
        return categoryList;
    }
}
