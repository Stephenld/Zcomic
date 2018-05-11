package ldh.com.zcomic.utils;

import java.util.ArrayList;
import java.util.List;

import ldh.com.channelmanager.ProjectChannelBean;

/**
 * Created by Administrator on 2016/12/26.
 */

public class CategoryDataUtils {
    public static List<ProjectChannelBean> getChannelCategoryBeans(){
        List<ProjectChannelBean> beans=new ArrayList<>();
        beans.add(new ProjectChannelBean("头条","T1348647909107"));
        beans.add(new ProjectChannelBean("要闻","T1467284926140"));
        beans.add(new ProjectChannelBean("科技","T1348649580692"));
        beans.add(new ProjectChannelBean("财经","T1348648756099"));
        beans.add(new ProjectChannelBean("社会","T1348648037603"));
        beans.add(new ProjectChannelBean("军事","T1348648141035"));
        beans.add(new ProjectChannelBean("娱乐","T1348648517839"));
        beans.add(new ProjectChannelBean("体育","T1348649079062"));
        beans.add(new ProjectChannelBean("数码","T1348649776727"));

        return beans;
    }
}
