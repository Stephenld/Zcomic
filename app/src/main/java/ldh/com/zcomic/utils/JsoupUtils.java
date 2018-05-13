package ldh.com.zcomic.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ldh.com.zcomic.bean.Chapter;
import ldh.com.zcomic.bean.ComicBean;
import ldh.com.zcomic.bean.ComicItem;

/**
 * Created by allen liu on 2018/5/7.
 */

public class JsoupUtils {
    private static JsoupUtils mJsoupUtil;
    public static JsoupUtils getInstance(){
        if (mJsoupUtil == null) {
            synchronized (JsoupUtils.class) {
                if (mJsoupUtil == null) {
                    mJsoupUtil = new JsoupUtils();
                }
            }
        }
        return mJsoupUtil;
    }

    /**
     * 全部分类漫画数据
     */
    public List<ComicBean> getComicAllData(String s) {
        List<ComicBean> categorylists = new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Element element = doc.select("ul.ret-search-list").first();
        Elements elements = element.getElementsByTag("li");
        for (Element e : elements) {
           ComicBean bean = new ComicBean();
           Element e1 = e.select("div.ret-works-cover").first();
           Element eI = e1.getElementsByTag("a").first();
            bean.setTitle(eI.attr("title"));
            bean.setContentUrl(eI.attr("href"));
            Element eeI = eI.getElementsByTag("img").first();
            bean.setImgUrl(eeI.attr("data-original"));
            Element eeM = e1.getElementsByTag("p").first();
            Element eeN = eeM.select("span.mod-cover-list-text").first();
            bean.setCurrent(eeN.text());
            Element e2 = e.select("p.ret-works-decs").first();
            bean.setDesc(e2.text());
            categorylists.add(bean);
        }
        return categorylists;
    }

    /**
     * 搜索漫画数据
     */
    public List<ComicBean> getComicSearchData(String s) {
        List<ComicBean> searchLists = new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Element element = doc.select("ul.mod_book_list").select("ul.mod_all_works_list").select("ul.mod_of").first();
        Elements elements = element.getElementsByTag("li");
        for (Element e : elements) {
            ComicBean bean = new ComicBean();
            Element e1 = e.getElementsByTag("a").first();
            bean.setContentUrl(e1.attr("href"));
            bean.setTitle(e1.attr("title"));
            Element e2 = e1.getElementsByTag("img").first();
            bean.setImgUrl(e2.attr("data-original"));
            Element e3 = e.select("h3.mod_book_update").select("h3.fw").first();
            bean.setCurrent(e3.text());
            searchLists.add(bean);
        }
        return searchLists;
    }

    /**
     * 热门日漫
     */
    public List<ComicBean> getComicHotData(String s) {
        List<ComicBean> hotlists = new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Element element = doc.select("ul.ret-search-list").first();
        Elements elements = element.getElementsByTag("li");
        for (Element e : elements) {
            ComicBean bean = new ComicBean();
            Element e1 = e.select("div.ret-works-cover").first();
            Element eI = e1.getElementsByTag("a").first();
            bean.setTitle(eI.attr("title"));
            bean.setContentUrl(eI.attr("href"));
            Element eeI = eI.getElementsByTag("img").first();
            bean.setImgUrl(eeI.attr("data-original"));
            Element eeM = e1.getElementsByTag("p").first();
            Element eeN = eeM.select("span.mod-cover-list-text").first();
            bean.setCurrent(eeN.text());
            Element e2 = e.select("div.ret-works-info").first();
            Element e3 = e2.select("p.ret-works-tags").first();
            Element e4 = e3.select("em").get(0);
//            Element e2 = doc.getElementsByAttributeValue("class","ret-works-tags").get(0); //不行，一页所有item都是相同人气值
//            Element e3 = e2.select("em").get(0);
            bean.setPopularity(e4.text());
            hotlists.add(bean);
        }
        return hotlists;
    }
    /**
     * 漫画资讯
     */
    public List<ComicBean> getComicNewsData(String s) {
        List<ComicBean> newslists = new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Element es = doc.select("div#tabs-1").first();
        Elements ess = es.select("div.Q-tpList");
        for (Element ee : ess) {
            ComicBean bean = new ComicBean();
            Element e1= ee.getElementsByTag("a").first();
            bean.setContentUrl(e1.attr("href"));
            Element e2 = ee.getElementsByTag("img").first();
            String e3 = e2.attr("src");
            bean.setImgUrl(e3);
            Element e4 = ee.select("h3.f18").first();
            Element ee4 = e4.getElementsByTag("a").first();
            String e5 = ee4.attr("title");
            bean.setTitle(e5);
            Element e6 = ee.getElementsByTag("p").first();
            bean.setDesc(e6.text());
            Element e7 = ee.select("div.newsinfo").first();
            Element e8 =e7.select("span").get(0);
            bean.setCurrent(e8.text());
            newslists.add(bean);
        }
        return newslists;
    }

    /**
     * 八卦杂谈
     */
    public List<ComicBean> getComicExtraData(String s) {
        List<ComicBean> newslists = new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Elements ess = doc.select("div.Q-tpList");
        for (Element ee : ess) {
            ComicBean bean = new ComicBean();
            Element e1= ee.getElementsByTag("a").first();
            bean.setContentUrl(e1.attr("href"));
            Element e2 = ee.getElementsByTag("img").first();
            String e3 = e2.attr("src");
            bean.setImgUrl(e3);
            Element e4 = ee.select("h3.f18").first();
            Element ee4 = e4.getElementsByTag("a").first();
            String e5 = ee4.attr("title");
            bean.setTitle(e5);
            Element e6 = ee.getElementsByTag("p").first();
            bean.setDesc(e6.text());
            Element e7 = ee.select("div.newsinfo").first();
            Element e8 =e7.select("span").get(0);
            bean.setCurrent(e8.text());
            newslists.add(bean);
        }
        return newslists;
    }
    /**
     * 轮播图
     */
    public List<ComicBean> getComicBannerData(String s) {
        List<ComicBean> newsBannerLists = new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Element es = doc.select("div.lunbo_wrap").first();
        Elements ess = es.getElementsByTag("li");
        for (Element ee : ess) {
            ComicBean bean = new ComicBean();
            Element e1= ee.getElementsByTag("a").first();
            bean.setContentUrl(e1.attr("href"));
            Element e2 = e1.getElementsByTag("img").first();
            String e3 = e2.attr("src");
            bean.setImgUrl(e3);
            Element e4 = ee.select("div.img_direct").first();
            Element e5 = e4.getElementsByTag("a").first();
            bean.setTitle(e5.text());
            newsBannerLists.add(bean);
        }
        return newsBannerLists;
    }

//    /**
//     * 漫画资讯b ： style display=none 隐藏的数据，不能获取？？
//     */
//    public List<ComicBean> getComicNewsDataB(String s) {
//        List<ComicBean> newslists = new ArrayList<>();
//        Document doc = Jsoup.parse(s);
//        Element es = doc.select("div#tabs-2").first();
//        Elements ess = es.select("div.Q-tpList");
//        for (Element ee : ess) {
//            ComicBean bean = new ComicBean();
//            Element e1= ee.getElementsByTag("a").first();
//            bean.setContentUrl(e1.attr("href"));
//            Element e2 = ee.getElementsByTag("img").first();
//            String e3 = e2.attr("src");
//            bean.setImgUrl(e3);
//            Element e4 = ee.select("h3.f18").first();
//            Element ee4 = e4.getElementsByTag("a").first();
//            String e5 = ee4.attr("title");
//            bean.setTitle(e5);
//            Element e6 = ee.getElementsByTag("p").first();
//            bean.setDesc(e6.text());
//            Element e7 = ee.select("div.newsinfo").first();
//            Element e8 =e7.select("span").get(0);
//            bean.setCurrent(e8.text());
//            newslists.add(bean);
//        }
//        return newslists;
//    }
//    /**
//     * 漫画资讯c
//     */
//    public List<ComicBean> getComicNewsDataC(String s) {
//        List<ComicBean> newslists = new ArrayList<>();
//        Document doc = Jsoup.parse(s);
//        Element es =doc.getElementById("tabs-3");
//        es.attr("style","display:block");
////        Element es = doc.select("div#tabs-3").first();
//        Elements ess = es.select("div.Q-tpList");
//        for (Element ee : ess) {
//            ComicBean bean = new ComicBean();
//            Element e1= ee.getElementsByTag("a").first();
//            bean.setContentUrl(e1.attr("href"));
//            Element e2 = ee.getElementsByTag("img").first();
//            String e3 = e2.attr("src");
//            bean.setImgUrl(e3);
//            Element e4 = ee.select("h3.f18").first();
//            Element ee4 = e4.getElementsByTag("a").first();
//            String e5 = ee4.attr("title");
//            bean.setTitle(e5);
//            Element e6 = ee.getElementsByTag("p").first();
//            bean.setDesc(e6.text());
//            Element e7 = ee.select("div.newsinfo").first();
//            Element e8 =e7.select("span").get(0);
//            bean.setCurrent(e8.text());
//            newslists.add(bean);
//        }
//        return newslists;
//    }
//    /**
//     * 漫画资讯d
//     */
//    public List<ComicBean> getComicNewsDataD(String s) {
//        List<ComicBean> newslists = new ArrayList<>();
//        Document doc = Jsoup.parse(s);
//
//        Element es = doc.select("div#tabs-4").first();
//        Elements ess = es.select("div.Q-tpList");
//        for (Element ee : ess) {
//            ComicBean bean = new ComicBean();
//            Element e1= ee.getElementsByTag("a").first();
//            bean.setContentUrl(e1.attr("href"));
//            Element e2 = ee.getElementsByTag("img").first();
//            String e3 = e2.attr("src");
//            bean.setImgUrl(e3);
//            Element e4 = ee.select("h3.f18").first();
//            Element ee4 = e4.getElementsByTag("a").first();
//            String e5 = ee4.attr("title");
//            bean.setTitle(e5);
//            Element e6 = ee.getElementsByTag("p").first();
//            bean.setDesc(e6.text());
//            Element e7 = ee.select("div.newsinfo").first();
//            Element e8 =e7.select("span").get(0);
//            bean.setCurrent(e8.text());
//            newslists.add(bean);
//        }
//        return newslists;
//    }
//

//    /**
//     * 最新更新轻小说
//     */
//    public List<ComicBean> getComicUpdateData(String s) {
//        List<ComicBean> updatelists = new ArrayList<>();
//        Document doc = Jsoup.parse(s);
//        List<Element> detail = doc.getElementsByAttributeValue("class","update-item");
//        for (int i=0;i<detail.size();i++) {
//            ComicBean bean = new ComicBean();
//            bean.setContentUrl(detail.get(i).select("a").attr("href"));
//            bean.setImgUrl(detail.get(i).getElementsByTag("a").select("img").attr("data-original"));
//            bean.setTitle(detail.get(i).getElementsByTag("h3").select("a").attr("title"));
//            bean.setDesc(detail.get(i).select("123").get(0).text());
//            bean.setCurrent(detail.get(i).select("p").select("a").select("span.update-info-txt").get(0).text());
//            updatelists.add(bean);
//        }
//        return updatelists;
//    }

    /**
     * 漫画详情数据
     */
    public ComicItem getComicDetailData(String s) {
        ComicItem comic = new ComicItem();
        Document doc = Jsoup.parse(s);
        Element element = doc.select("div.works-intro").first();  //可不用再.select(div.xxx)
        Element element1 = element.select("div.works-cover").first();
        Element element11 = element1.getElementsByTag("a").first();
        comic.setTitle(element11.attr("title"));
        Element element111 = element11.getElementsByTag("img").first();
        comic.setImgUrl(element111.attr("data-original"));
        Element element12 = element1.select("label.works-intro-status").first();
        comic.setStatus(element12.text());
        Element element2 = element.select("strong.ui-text-orange").first();
        if (element2 != null) {
            comic.setScore(element2.text());
        } else {
            comic.setScore("暂未评分");
        }
        Element element3 = element.select("span.first").first();
        comic.setAuthor(element3.text());
        Element element4 = element.select("p.works-intro-short").select("p.ui-text-gray9").first();
        comic.setSummary(element4.text());
        Element element5 = doc.select("ul.works-chapter-log").select("ul.ui-left").first(); // 含有class，可用.
        Element element6 = element5.select("span.ui-pl10").select("span.ui-text-gray6").first();
        comic.setUpdates(element6.text());
        Element e0 = doc.select("ol.chapter-page-all").select("ol.works-chapter-list").first(); //document.select()
        Elements es = e0.select("span.works-chapter-item"); //取Element用first（）,Elements不用first（）
        List<Chapter> list = new ArrayList<>();
        for (Element e : es) {
            Chapter ep = new Chapter();
            Element ee = e.getElementsByTag("a").first();
            ep.setUrl(ee.attr("href"));
            ep.setTitle(ee.text());
            list.add(ep);
        }
        comic.setmChapterList(list);
        return comic;
    }
}














