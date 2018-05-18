package ldh.com.zcomic.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by allen liu on 2018/5/7.
 */

public class ComicBean extends BmobObject {
    private String comicId;
    private String contentUrl;
    private String imgUrl;
    private String author;
    private String title;
    private String desc;
    private String current;
    private String popularity;
    public String getComicId() {return comicId;}

    public void setComicId(String comicId) {this.comicId = comicId;}

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
    public String getPopularity() {
        return popularity;
    }
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
}
