package News.controller;

import News.entity.Category;
import News.entity.User;
import News.service.CategoryService;
import News.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static News.util.PermissionUtil.isPermission;


/**
 * 处理/category下的请求（比如请求某个分类下的全部新闻）
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NewsService newsService;



    @GetMapping(value = "/category")
    public String getMoreCategory(@RequestParam(value = "name")String name,@RequestParam(value = "page",defaultValue = "0")int page,Model model){
        List<Category> categoryList=categoryService.getMoreCategory();//拿到更多分类，包含分类名和获取该分类下新闻的链接
        //System.out.println("categoryListSize:"+categoryList.size());
        model.addAttribute("categoryList",categoryList);
        //int page=0;//这里的page是要从前端请求获取，在https://my.oschina.net/waylau/blog/857292中是没有page参数的
        model.addAttribute("page",newsService.getNewsByCategoryName(name,page));//使用spring data分页器接口

        return "category";
    }


    @GetMapping(value = "/admin/addCategory")
    public String addCategoryView(HttpSession httpSession){
        User user= (User) httpSession.getAttribute("user");
        String permission=user.getPermission();
        if (isPermission(permission)){//有权限
            return "/admin/addCategory";//返回逻辑视图（/admin/addCategory.html）
        }
        return "redirect:/login";
    }

    /**
     *
     * @param name 表单提交数据的字段名
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/admin/addCategory/post")
    public String addCategory(String name,HttpSession httpSession){
        User user= (User) httpSession.getAttribute("user");
        String permission=user.getPermission();
        if (isPermission(permission)){//有权限
            Category category=new Category();
            category.setName(name);
            category.setFlag(false);
            categoryService.addCategory(category);
            return "redirect:/admin/allCategory";//重定向到/admin/allCategory"，而不是返回一个视图(即：提交后返回一个查看刚才提交的页面)
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/admin/allCategory")
    public String allCategoryView(HttpSession httpSession, Model model){
        User user= (User) httpSession.getAttribute("user");
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            model.addAttribute("categoryList",categoryService.getMoreCategory());
            return "/admin/allCategory";
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/admin/deleteCategory")
    public String deleteCategory(HttpSession httpSession,@RequestParam(value = "name")String categoryName){
        User user= (User) httpSession.getAttribute("user");
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            categoryService.deleteCategoryByName(categoryName);
            return "redirect:/admin/allCategory";
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/admin/updateCategory")
    public String updateCategoryView(HttpSession httpSession,Model model, @RequestParam(value = "cid") String cid){
        User user= (User) httpSession.getAttribute("user");
        String permission=user.getPermission();
        if (isPermission(permission)){//有权限
            Category category=categoryService.getCategoryById(cid);
            model.addAttribute("category",category);
            return "/admin/updateCategory";//返回逻辑视图（/admin/addCategory.html）
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/admin/updateCategory/post")
    public String updateCategory(String name, HttpSession httpSession, @RequestParam(value = "cid") String cid){

        User user= (User) httpSession.getAttribute("user");
        String permission=user.getPermission();
        if (isPermission(permission)){//有权限
            Category category=categoryService.getCategoryById(cid);
            category.setName(name);
            category.setFlag(false);
            categoryService.addCategory(category);
            return "redirect:/admin/allCategory";//重定向到/admin/allCategory"，而不是返回一个视图(即：提交后返回一个查看刚才提交的页面)
        }
        return "redirect:/login";
    }

}
