package News.controller;

import News.entity.User;
import News.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String loginView(){
        return "login";
    }

    /**
     * 拿到表单中的数据
     * @param name 表单字段
     * @param password 表单字段
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/user/login" ,method = RequestMethod.POST)
    public String login(String name,String password, HttpSession httpSession){
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        User user1=userService.checkUser(user);//判断用户是否合法，合法就返回一个包含全部用户信息的user（注意密码项要置为null）
        if (user1!=null){
            httpSession.setAttribute("user",user1);
            return "redirect:/";//重定向到首页
        }
        return "redirect:/login";//重定向到登录页 todo 提示登录失败
    }

}
