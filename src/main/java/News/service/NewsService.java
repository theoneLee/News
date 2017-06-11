package News.service;

import News.dao.CategoryDao;
import News.dao.NewsDao;
import News.entity.Category;
import News.entity.Comment;
import News.entity.News;
import News.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    @Autowired
    CategoryDao categoryDao;

    public List<News> getIndexNews() {
        //拿到首页上最新的4条新闻，要包含id,标题，裁剪过的新闻内容
        Category category=categoryDao.findByName("hot");
        List<News> list=newsDao.findTop4ByCategory(category,new Sort(Sort.Direction.DESC,"date"));//todo 注意这里是根据Category这个实体来查询，无法直接通过categoryName来查询
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
        List<News> list=newsDao.findById1(Integer.valueOf(id));
        //System.out.println("comment:"+list.get(0).getCommentList());
        News news=list.get(0);
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
        News news=newsDao.findById1(Integer.valueOf(nid)).get(0);
        news.getCommentList().add(comment);
        newsDao.save(news);//这里是相当于saveOrUpdate ，在开启CascadeType.MERGE时（http://docs.spring.io/spring-data/jpa/docs/1.11.3.RELEASE/reference/html/#jpa.entity-persistence）
    }

    /**
     * http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
     * @param name
     * @param page
     * @return
     */
    public Page<News> getNewsByCategoryName(String name,int page) {
        int size=5;
        Category category=categoryDao.findByName(name);
        Page<News> pageNews=newsDao.findByCategory(category,new PageRequest(page,size));
        //System.out.println("TotalElements:"+pageNews.getTotalElements());
        //System.out.println("TotalPages:"+pageNews.getTotalPages());
        return pageNews;
    }

    public Page<News> getNewsByCategoryName(String name,int page,int size) {
        Category category=categoryDao.findByName(name);
        Page<News> pageNews=newsDao.findByCategory(category,new PageRequest(page,size));
        //System.out.println("TotalElements:"+pageNews.getTotalElements());
        //System.out.println("TotalPages:"+pageNews.getTotalPages());
        return pageNews;
    }

    public void addNews(News news) {
        newsDao.save(news);
    }

    public void deleteNews(String nid) {
        newsDao.delete(Integer.valueOf(nid));
    }

    public void updateNews(News news) {

        newsDao.save(news);
    }

    public List<News> getNewsByNewsName(String name) {

        List<News> list=newsDao.findByTitleLike(name+"%");
        //System.out.println("newsListNameService:"+list);
        for (News n:list){
            n.setCommentList(null);
        }
        return list;
    }


    public List<News> getNewsByNewsTime(String time) {
        List<Date> list=string2Date(time);
        List<News> newsList=newsDao.findByDateIsBetween(list.get(0),list.get(1));

        for (News n:newsList){
            n.setCommentList(null);
        }
        return newsList;
    }

    private List<Date> string2Date(String s){
        String s1=s.split("~")[0];
        String s2=s.split("~")[1];
        List<Date> list=new ArrayList<>();
        //System.out.println("date1:"+s1);
        //System.out.println("date2:"+s2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            list.add(sdf.parse(s1));
            list.add(sdf.parse(s2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
}
