package guice.example5.pojo;

import java.util.Date;

/**
 * Created by eladw on 9/4/2016.
 */
public class Report {

    String name;
    Date date;
    long id;

    public Report(String name, Date date, long id) {
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
