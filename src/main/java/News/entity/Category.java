package News.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private boolean flag;

    //一方的fetch默认为eager，多方的默认为lazy
    @OneToMany(mappedBy = "category",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.LAZY)
    private Set<News> newsList=new HashSet<News>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Set<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(Set<News> newsList) {
        this.newsList = newsList;
    }
}