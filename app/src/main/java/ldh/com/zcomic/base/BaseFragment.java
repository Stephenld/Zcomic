package ldh.com.zcomic.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by allen liu on 2018/5/2.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext ;
    protected View mView ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resRootViewId =  getResRootViewId () ;
        mView = inflater.inflate(resRootViewId , container , false) ;

        mContext = this.getContext() ;
        init();
        return mView ;
    }

    protected abstract int getResRootViewId() ;
    protected abstract void init() ;

}
