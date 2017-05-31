package News.controller;

import News.entity.Category;
import News.entity.News;
import News.service.CategoryService;
import News.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class IndexController {

    @Autowired//spring 推荐使用构造器注入，如下面所示（注意将这个Autowired注释掉）。但我就是觉得字段注入很方便啊。理解机制就行了嘛。
    private CategoryService categoryService;

    @Autowired
    private NewsService newsService;

//    @Autowired
//    public IndexController(CategoryService categoryService){
//        this.categoryService=categoryService;
//    }

    @GetMapping(value = "/")
    public String index(Model model){
        //从service拿到对应数据，然后封装到model
        List<Category> categoryList=categoryService.getMoreCategory();//拿到更多分类，包含分类名即可
        model.addAttribute("categoryList",categoryList);
        List<News> newsList=newsService.getIndexNews();//拿到首页上最新的4条新闻，要包含标题，裁剪过的新闻内容
        model.addAttribute("news",newsList);
        return "index";
    }


}
