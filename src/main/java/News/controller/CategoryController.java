package News.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 处理/category下的请求（比如请求某个分类下的全部新闻）
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class CategoryController {

    @RequestMapping(value = "/category")
    public String getMoreCategory(@RequestParam(value = "name")String name,Model model){
        List<Category> categoryList=categoryService.getMoreCategory();//拿到更多分类，包含分类名和获取该分类下新闻的链接
        model.addAttribute("categoryList",categoryList);

        model.addAttribute("page",newsService.getNewsByCategoryName(name));//使用spring data分页器接口

        return "category";
    }

}
