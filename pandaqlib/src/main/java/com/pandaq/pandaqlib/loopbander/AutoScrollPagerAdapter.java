package com.pandaq.pandaqlib.loopbander;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 */
public class AutoScrollPagerAdapter extends PagerAdapter {

    private PagerAdapter wrappedAdapter;

    private boolean isSaveed = false;

    public AutoScrollPagerAdapter(PagerAdapter wrappedAdapter) {
        this.wrappedAdapter = wrappedAdapter;
    }

    @Override
    public int getCount() {
        if (wrappedAdapter == null) {
            return 0;
        } else if (wrappedAdapter.getCount() > 1) {
            return wrappedAdapter.getCount() + 2;
        } else {
            return wrappedAdapter.getCount();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

      //  Log.e("AutoScrollPagerAdapter","instantiateItem position=" + position);


        if (!isSaveed){
            if (position == 0) {
                return wrappedAdapter.instantiateItem(container, wrappedAdapter.getCount() - 1);
            } else if (position == wrappedAdapter.getCount() + 1) {
                return wrappedAdapter.instantiateItem(container, 0);
            } else {
                return wrappedAdapter.instantiateItem(container, position - 1);
            }
        }
        else {
            isSaveed = false;
            return wrappedAdapter.instantiateItem(container, 0);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // Log.e("position", "" + position);
        wrappedAdapter.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return wrappedAdapter.isViewFromObject(view, object);
    }


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
     //   Log.e("AutoScrollPagerAdapter","restoreState");
        isSaveed = true;
        super.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

//
//    private static final String INSTANCE_STATUS = "instance_status";
//    private static final String STATUS_INDEX = "status_index";
//
//    @Override
//    public Parcelable onSaveInstanceState() {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
//        bundle.putInt(STATUS_INDEX,getCurrentItem());
//        return bundle;
//    }
//
//    @Override
//    public void onRestoreInstanceState(Parcelable state) {
//        if (state instanceof Bundle) {
//            Bundle bundle = (Bundle) state;
//            setCurrentItem(bundle.getInt(STATUS_INDEX));
//            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
//            return;
//        }
//        super.onRestoreInstanceState(state);
//    }
}
