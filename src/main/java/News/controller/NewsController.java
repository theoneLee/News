package News.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


}
