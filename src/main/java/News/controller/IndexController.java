package News.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index(Model model){
        //从service拿到对应数据，然后封装到model
        List<Category> categoryList=categoryService.getMoreCategory();//拿到更多分类，
        model.addAttribute("categoryList",categoryList);
        List<News> newsList=categoryService.getIndexNews();//拿到首页上最新的4条新闻，要包含标题，裁剪过的新闻内容，新闻链接
        for (int i = 0; i < newsList.size(); i++) {
            model.addAttribute("news"+(i+1),newsList.get(i));
        }
        return "index";
    }
}
