package News.controller;

import News.entity.Category;
import News.entity.News;
import News.entity.User;
import News.service.CategoryService;
import News.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;

import static News.util.PermissionUtil.isPermission;

/**
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    /**
     *
     * @param id
     * @param model
     * @return 返回一个包含news且要包含commentList的
     */
    @GetMapping(value = "/news/detail")
    public String getNewsById(@RequestParam("id")String id, Model model,HttpSession httpSession){
        News news=newsService.getNewsById(id);
        //System.out.println("news:"+news.getTitle());

        model.addAttribute("commentList",news.getCommentList());
        news.setCommentList(null);
        model.addAttribute("news",news);
        return "newsDetail";
    }

    @GetMapping("/search")
    public String getSearchView(){
        return "searchResult";
    }

    @GetMapping("/news/name")
    public String getNewsByName(@RequestParam("name")String name, Model model){
        List<News> newsList=newsService.getNewsByNewsName(name);//模糊查询
        //System.out.println("newsList(Name):"+newsList);
        model.addAttribute("newsList1",newsList);
        return "searchResult";
    }


    /**
     * 时间查询格式为yyyy-MM-dd~yyyy-MM-dd
     * @param time
     * @param model
     * @return
     */
    @GetMapping("/news/time")
    public String getNewsByTime(@RequestParam("time")String time, Model model){
        //根据时间段查询
        List<News> newsList=newsService.getNewsByNewsTime(time);//模糊查询
        //System.out.println("timeList:"+newsList);
        model.addAttribute("newsList1",newsList);
        return "searchResult";
    }

    /**
     *
     * @param nid
     * @param httpSession
     * @param content 表单的name也要是一样参数名
     * @return
     */
    @RequestMapping(value = "/news/{nid}/comment",method = RequestMethod.POST)
    public String comment(@PathVariable(name = "nid")String nid, HttpSession httpSession,String content){
        User user= (User) httpSession.getAttribute("user");
        if (user!=null){
            newsService.comment(nid,user,content);//封装一个comment，然后持久化
            return "redirect:/news/detail?id="+nid;//该重定向到新闻页（使其评论可见）
        }else {
            return "redirect:/login";//重定向到登录页
        }
    }


    //获取某个分类下的所有新闻(选择select时，链接到一个新的url，包含categoryName的)
    @GetMapping(value = "/admin/allNews")
    public String allNewsView(@RequestParam(defaultValue = "hot") String categoryName,@RequestParam(value = "page",defaultValue = "0")int page,Model model,HttpSession httpSession){
        User user= (User) httpSession.getAttribute("user");
        if (user==null){
            return "redirect:/login";
        }
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            model.addAttribute("categoryList",categoryService.getAllCategoryName());
            model.addAttribute("page",newsService.getNewsByCategoryName(categoryName,page,100));//不使用spring data分页，所以每次取足够多
            return "/admin/allNews";
        }
        return "redirect:/login";//重定向到登录页
    }

    /**
     *
     * @param httpSession
     * @param model 包含所有分类name，然后迭代显示在select标签中
     * @return
     */
    @GetMapping(value = "/admin/addNews")
    public String addNewsView(HttpSession httpSession,Model model){
        User user= (User) httpSession.getAttribute("user");
        if (user==null){
            return "redirect:/login";
        }
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            model.addAttribute("categoryList",categoryService.getAllCategoryName());
            return "/admin/addNews";
        }
        return "redirect:/login";//重定向到登录页
    }


    /**
     * todo 富文本
     * 图片直接以base64的方式直接作为html内容直接传给newsContent容器
     * 目前将图片和富文本作为html内容直接传递给数据库保存，在查看时直接将html内容取出来，然后js来将内容赋值给对应的容器来展示
     * 注意
     * @param httpSession
     * @param newsTitle
     * @param newsCategoryName
     *
     * @return
     */
    @PostMapping(value = "/admin/addNews/post")
    public String addNews(HttpSession httpSession,String newsTitle,String newsCategoryName,String newsContent){
        User user= (User) httpSession.getAttribute("user");
        if (user==null){
            return "redirect:/login";
        }
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            //将拿到的数据封装成News，然后关联category并持久化
            News news=new News();
            news.setTitle(newsTitle);
            news.setNewsManagerName(user.getName());
            news.setDate(new Date());
            news.setContent(newsContent);
            Category category=categoryService.getCategoryByCourseName(newsCategoryName);
            news.setCategory(category);
            newsService.addNews(news);
            return "redirect:/admin/allNews";
        }
        return "redirect:/login";//重定向到登录页
    }


    @GetMapping(value = "/admin/updateNews")
    public String updateNewsView(HttpSession httpSession,Model model,@RequestParam(name = "nid")String nid){
        User user= (User) httpSession.getAttribute("user");
        if (user==null){
            return "redirect:/login";
        }
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            model.addAttribute("categoryList",categoryService.getAllCategoryName());
            model.addAttribute("newsTemp",newsService.getNewsById(nid));//todo 将news数据填充到界面
            return "/admin/updateNews";
        }
        return "redirect:/login";//重定向到登录页
    }

    @PostMapping(value = "/admin/updateNews/post")
    public String updateNews(HttpSession httpSession,String newsTitle,String newsCategoryName,String newsContent,@RequestParam(name = "nid")String nid){
        User user= (User) httpSession.getAttribute("user");
        if (user==null){
            return "redirect:/login";
        }
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            //将拿到的数据封装成News，然后关联category并持久化
            News news=newsService.getNewsById(nid);
            news.setTitle(newsTitle);
            news.setNewsManagerName(user.getName());
            news.setDate(new Date());
            news.setContent(newsContent);

            Category category=categoryService.getCategoryByCourseName(newsCategoryName);
            news.setCategory(category);

            newsService.updateNews(news);
            return "redirect:/admin/allNews";
        }
        return "redirect:/login";//重定向到登录页
    }

    @GetMapping(value = "/admin/deleteNews")
    public String deleteNews(HttpSession httpSession,@RequestParam(name = "nid")String nid){
        User user= (User) httpSession.getAttribute("user");
        if (user==null){
            return "redirect:/login";
        }
        String permission=user.getPermission();
        if (isPermission(permission)) {//有权限
            newsService.deleteNews(nid);
            return "redirect:/admin/allNews";
        }
        return "redirect:/login";
    }



}
