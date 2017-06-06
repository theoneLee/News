package News.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2017/5/27 0027.
 */
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private boolean flag;//flag为false不是固定分类

    //一方的fetch默认为eager，多方的默认为lazy
    @OneToMany(mappedBy = "category",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.LAZY)
    private List<News> newsList=new ArrayList<News>();//在hibernate上用HashSet确实难受。遍历元素只能用foreach。这个好多了

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

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
