package ldh.com.zcomic.bean;

import java.util.List;

/**
 * Created by allen liu on 2018/5/7.
 */

public class ComicItem {
    private String imgUrl;
    private String title;
    private String status;
    private String score;
    private String author;
    private String summary;
    private boolean isCollected;
    private String updates;
    private List<ChapterBean> mChapterList;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public List<ChapterBean> getChapterList() {
        return mChapterList;
    }

    public void setChapterList(List<ChapterBean> mEpisodeList) {
        this.mChapterList = mEpisodeList;
    }
}
