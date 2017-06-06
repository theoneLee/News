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

    public void addCategory(Category category) {
        categoryDao.save(category);
    }

    public void deleteCategoryByName(String categoryName) {
        categoryDao.delete(categoryDao.findByName(categoryName));
    }

    public Category getCategoryById(String cid) {
        return categoryDao.findOne(Long.valueOf(cid));
    }


    public Category getCategoryByCourseName(String newsCategoryName) {
        return categoryDao.findByName(newsCategoryName);
    }

    public List<Category> getAllCategoryName() {
        return categoryDao.findAll();
    }
}
