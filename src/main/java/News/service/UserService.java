package News.service;

import News.dao.UserDao;
import News.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lee on 2017/5/31 0031.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    /**
     * 判断用户是否合法，合法就返回一个包含全部用户信息的user（注意密码项要置为null）
     * @param user
     * @return
     */
    public User checkUser(User user) {
        String reqName=user.getName();
        String reqPassword=user.getPassword();
        User trueUser=userDao.findByName(reqName);
        if (reqPassword!=null&&reqPassword.equals(trueUser.getPassword())){
            trueUser.setPassword("");
            //System.out.println(trueUser.getPermission());
            return trueUser;
        }
        return null;
    }
}
