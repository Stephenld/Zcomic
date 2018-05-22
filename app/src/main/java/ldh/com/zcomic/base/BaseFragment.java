package ldh.com.zcomic.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.ButterKnife;
import ldh.com.zcomic.entity.Constants;
import ldh.com.zcomic.utils.ActivityUtils;
import ldh.com.zcomic.utils.ViewUtil;

/**
 * Created by allen liu on 2018/5/2.
 */

public abstract class BaseFragment extends Fragment {
    protected View mView ;
    protected Activity mActivity;
    protected ActivityUtils utils;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity)context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int resRootViewId =  getResRootViewId () ;
        mView = inflater.inflate(resRootViewId , container , false) ;
        ButterKnife.bind(this,mView); // 不能漏写，不然后面会报空指针异常
        utils = new ActivityUtils(this);
        Fresco.initialize(getActivity());
        initData();
        initListener();
        return mView ;
    }
    protected abstract int getResRootViewId() ;
    protected abstract void initData();
    protected abstract void initListener();

    @Override
    public void onResume() {
        super.onResume();
        //屏幕亮度
        Constants.osScreenBrightValue = ViewUtil.getScreenBrightness(getActivity());
        if (Constants.osNightModel){
            ViewUtil.setScreenBrightness(getActivity(), 2);
        }else {
            ViewUtil.setScreenBrightness(getActivity(), Constants.osScreenBrightValue);
        }
    }
}
