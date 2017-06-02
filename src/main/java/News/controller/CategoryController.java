package News.controller;

import News.entity.Category;
import News.service.CategoryService;
import News.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 处理/category下的请求（比如请求某个分类下的全部新闻）
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    NewsService newsService;

    @GetMapping(value = "/category")
    public String getMoreCategory(@RequestParam(value = "name")String name,Model model){
        List<Category> categoryList=categoryService.getMoreCategory();//拿到更多分类，包含分类名和获取该分类下新闻的链接
        System.out.println("categoryListSize:"+categoryList.size());
        model.addAttribute("categoryList",categoryList);
        int page=0;//todo 这里的page是否要从前端请求获取，在https://my.oschina.net/waylau/blog/857292中是没有page参数的
        model.addAttribute("page",newsService.getNewsByCategoryName(name,page));//使用spring data分页器接口

        return "category";
    }

}
