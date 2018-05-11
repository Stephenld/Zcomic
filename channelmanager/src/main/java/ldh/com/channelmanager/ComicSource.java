package ldh.com.channelmanager;

/**
 * Created by allen liu on 2018/5/11.
 */

public class ComicSource {
    private String title;
    private int themeId;
    // 标签类型，显示是我的频道还是更多频道
    private int tabType;
    // 设置该标签是否可编辑，如果出现在我的频道中，且值为1，则可在右上角显示删除按钮
    private int editStatus;

    public ComicSource() {
    }
    public ComicSource(String title, int themeId, int tabType) {
        this.title = title;
        this.themeId = themeId;
        this.tabType = tabType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }

    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }
}
