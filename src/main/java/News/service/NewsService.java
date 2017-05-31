package News.service;

import News.dao.NewsDao;
import News.entity.Comment;
import News.entity.News;
import News.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    public List<News> getIndexNews() {
        //拿到首页上最新的4条新闻，要包含id,标题，裁剪过的新闻内容
        List<News> list=newsDao.findTop4ByCategory("hot",new Sort(Sort.Direction.DESC,"date"));
        for (News news:list){
            news.setContent(news.getContent().substring(0,12));
        }
        return list;
    }


    /**
     * 包含news且要包含commentList的
     * @param id
     * @return
     */
    public News getNewsById(String id) {
        News news=newsDao.findById(id);
        return news;
    }

    /**
     * 封装一个comment，然后持久化
     * @param nid
     * @param user
     * @param content
     */
    @Transactional
    public void comment(String nid, User user, String content) {
        Comment comment=new Comment();
        comment.setUsername(user.getName());
        comment.setContent(content);
        comment.setDate(new Date());
        News news=newsDao.findById(nid);
        news.getCommentList().add(comment);
        newsDao.save(news);//这里是相当于saveOrUpdate ，在开启CascadeType.MERGE时（http://docs.spring.io/spring-data/jpa/docs/1.11.3.RELEASE/reference/html/#jpa.entity-persistence）
    }

    public Page<News> getNewsByCategoryName(String name,int page) {
        int size=5;
        return newsDao.findByCategory(name,new PageRequest(page,size));
    }
}
