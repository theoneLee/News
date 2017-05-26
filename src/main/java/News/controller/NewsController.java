package News.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Lee on 2017/5/26 0026.
 */
@Controller
public class NewsController {

    /**
     *
     * @param id
     * @param model
     * @return 返回一个包含news且要包含commentList的
     */
    @RequestMapping(value = "/news/detail")
    public String getNewsById(@RequestParam("id")String id, Model model){
        News news=newsService.getNewsById(id);
        model.addAttribute("news",news);
        return "newsDetail";
    }

    @RequestMapping(value = "/news/{nid}/comment",method = RequestMethod.POST)
    public String comment(@PathVariable(name = "nid")String nid, HttpSession httpSession, @RequestBody String content){
        User user=httpSession.getAttribute("user");
        if (user!=null){
//            Comment comment=new Comment();
//            comment.setUserName(user.getUserName());
//            comment.setNews()
            newsService.comment(nid,user,content);//封装一个comment，然后持久化
            return "redirect:/news/detail?id="+nid;//该重定向到新闻页（使其评论可见）
        }else {
            return "redirect:/login";//重定向到登录页
        }
    }


}
