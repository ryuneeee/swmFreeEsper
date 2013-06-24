package so.tree.example.esper.event;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: Ryun
 * Date: 13. 2. 14.
 * Time: 오전 1:31
 * To change this template use File | Settings | File Templates.
 */


public class Jagei {
    private long no;
    private int dulCount;
    private String title;
    private String commentCount;
    private int viewCount;
    private Date time;


    public int getDulCount() {
        return dulCount;
    }

    public void setDulCount(int dulCount) {


        this.dulCount = dulCount;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }


    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}