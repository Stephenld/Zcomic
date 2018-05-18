package ldh.com.zcomic.entity;

/**
 * Created by allen liu on 2018/5/4.
 */

public class Constants {
    public static int osScreenBrightValue = 150;//屏幕亮度值
    public static boolean osNightModel = false;//夜间模式

    public static final int CONNECT_TIMEOUT = 6;
    public static final int READ_TIMEOUT = 100;
    public static final int WRITE_TIMEOUT = 60;

    public static String PREF_KEY_THEME = "pref_key_theme";
    public static String AUTO_RUN = "autoRun";
    public static String EXTRA_IS_UPDATE_THEME = "ldh.com.zcomic.IS_UPDATE_THEME";
    public static String[] COMIC_CATEGORY_ALL = {
               "全部","爆笑", "热血", "冒险", "恐怖", "科幻", "魔幻",
               "玄幻", "校园", "悬疑", "推理", "萌系", "穿越",
               "后宫", "都市", "恋爱", "武侠", "格斗", "战争",
               "历史", "彩虹", "同人", "竞技", "励志", "百合",
               "治愈", "机甲", "纯爱", "美食", "血腥", "僵尸",
               "恶搞", "虐心", "生活", "动作", "惊险", "唯美",
               "震撼", "复仇", "侦探", "其它", "脑洞", "奇幻",
               "宫斗", "爆笑", "运动", "青春", "穿越", "灵异",
               "古风", "权谋", "节操", "明星", "暗黑", "社会","浪漫"
                                                      };

    public static String CLASSIFYKEY = "classifykey";

    // 漫画分类数据
    public static String COMIC_All_HEAD = "http://ac.qq.com/Comic/all/theme/";
    public static String COMIC_ALL_END ="/state/pink/search/hot/page/";
    // 热门日漫
    public static String COMIC_JAPAN ="http://ac.qq.com/Comic/all/state/pink/nation/4/search/hot/page/";
    // 漫画资讯
    public static String COMIC_NEWS = "http://comic.qq.com/";
    // 轮播图
    public static String COMIC_BANNER = "http://comic.sina.com.cn/";
    // 八卦杂谈
    public static String COMIC_Extra = "http://comic.qq.com/view/";
    // 最近更新
    public static String COMIC_UPDATE = "http://ac.qq.com/Light";
    // 漫画搜索
    public static String COMIC_SEARCH = "http://ac.qq.com/Comic/searchList/search/";
}
