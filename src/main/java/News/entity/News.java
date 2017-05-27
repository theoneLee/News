package News.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Entity
public class News {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private Date date;
    private String newsManagerName;
    private String content;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    //todo 待加入comment

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNewsManagerName() {
        return newsManagerName;
    }

    public void setNewsManagerName(String newsManagerName) {
        this.newsManagerName = newsManagerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
