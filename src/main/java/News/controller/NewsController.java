package News.controller;

import News.entity.News;
import News.entity.User;
import News.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class NewsController {

    @Autowired
    NewsService newsService;

    /**
     *
     * @param id
     * @param model
     * @return 返回一个包含news且要包含commentList的
     */
    @GetMapping(value = "/news/detail")
    public String getNewsById(@RequestParam("id")String id, Model model,HttpSession httpSession){
        News news=newsService.getNewsById(id);
        System.out.println("news:"+news.getTitle());

        model.addAttribute("commentList",news.getCommentList());
        news.setCommentList(null);
        model.addAttribute("news",news);
//        if (httpSession.getAttribute("user")==null){//正常情况下session里面包含的是一个user
//            httpSession.setAttribute("user","null");
//        }
        User user=new User();
        user.setName("test");
        user.setId(44);
        httpSession.setAttribute("user",user);
        //System.out.println("sessionId:"+httpSession.getId());
        return "newsDetail";
    }

    @RequestMapping(value = "/news/{nid}/comment",method = RequestMethod.POST)
    public String comment(@PathVariable(name = "nid")String nid, HttpSession httpSession, @RequestBody String content){
        User user= (User) httpSession.getAttribute("user");
        if (user!=null){
//            Comment comment=new Comment();
//            comment.setUserName(user.getUserName());
//            comment.setNews()
            //todo
            newsService.comment(nid,user,content);//封装一个comment，然后持久化
            return "redirect:/news/detail?id="+nid;//该重定向到新闻页（使其评论可见）
        }else {
            return "redirect:/login";//重定向到登录页
        }
    }


}
