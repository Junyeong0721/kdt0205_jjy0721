package model.dto;

import java.util.Date;

public class Board {
    private int no;
    private String title;
    private String writer;
    private String content;
    private Date date;
    private int hitcount;

    public Board() {}

    public Board(int no, String title, String writer, String content, Date date, int hitcount) {
        this.no = no;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.date = date;
        this.hitcount = hitcount;
    }

    // Getter & Setter
    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getHitcount() { return hitcount; }
    public void setHitcount(int hitcount) { this.hitcount = hitcount; }
}
