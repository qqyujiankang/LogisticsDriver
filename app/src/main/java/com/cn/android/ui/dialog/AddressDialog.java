package com.cn.android.ui.dialog;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.android.R;
import com.cn.android.bean.CityAreaBean;
import com.cn.android.common.MyDialogFragment;
import com.cn.android.common.MyRecyclerViewAdapter;
import com.google.android.material.tabs.TabLayout;
import com.hjq.dialog.base.BaseDialog;
import com.hjq.base.BaseRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/02/12
 * desc   : 省市区选择对话框
 * doc    : https://baijiahao.baidu.com/s?id=1615894776741007967
 */
public final class AddressDialog {

    public static final class Builder
            extends MyDialogFragment.Builder<Builder>
            implements BaseRecyclerViewAdapter.OnItemClickListener,
            View.OnClickListener, TabLayout.OnTabSelectedListener, Runnable {

        private final TextView mTitleView;
        private final ImageView mCloseView;
        private final TabLayout mTabLayout;
        private final ImageView mHintView;

        private final RecyclerView mCityView;
        private final RecyclerView mAreaView;

        private final AddressDialogAdapter mCityAdapter;
        private final AddressDialogAdapter mAreaAdapter;

        private OnListener mListener;

        private String mCity = null;
        private String mArea = null;

        private List<CityAreaBean> list;
        private List<String> cityList=new ArrayList<>();
        private List<String> areaList=new ArrayList<>();

        public Builder(FragmentActivity activity, List<CityAreaBean> list) {
            super(activity);
            this.list = list;
            setContentView(R.layout.dialog_address);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getSystemService(WindowManager.class).getDefaultDisplay().getMetrics(displayMetrics);
            setHeight(displayMetrics.heightPixels / 2);

            mTitleView = findViewById(R.id.tv_address_title);
            mCloseView = findViewById(R.id.iv_address_closer);
            mTabLayout = findViewById(R.id.tb_address_tab);
            mHintView = findViewById(R.id.iv_address_hint);

            mCityView = findViewById(R.id.rv_address_city);
            mAreaView = findViewById(R.id.rv_address_area);

            mCityAdapter = new AddressDialogAdapter(getContext());
            mAreaAdapter = new AddressDialogAdapter(getContext());

            mCloseView.setOnClickListener(this);

            mCityAdapter.setOnItemClickListener(this);
            mAreaAdapter.setOnItemClickListener(this);

            mCityView.setAdapter(mCityAdapter);
            mAreaView.setAdapter(mAreaAdapter);

            mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.address_hint)), true);
            mTabLayout.addOnTabSelectedListener(this);
            for (int i = 0; i < list.size(); i++) {
                cityList.add(list.get(i).getName());
            }
            // 显示一级列表
            mCityAdapter.setData(this.cityList);
        }

        public Builder setTitle(@StringRes int id) {
            return setTitle(getString(id));
        }

        public Builder setTitle(CharSequence text) {
            mTitleView.setText(text);
            return this;
        }


        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * {@link BaseRecyclerViewAdapter.OnItemClickListener}
         */

        @SuppressWarnings("all")
        @Override
        public synchronized void onItemClick(RecyclerView recyclerView, View itemView, int position) {
            if (recyclerView == mCityView) {
                // 记录当前选择的城市
                for (int i = 0; i < list.get(position).getSecondList().size(); i++) {
                    areaList.add(list.get(position).getSecondList().get(i).getName());
                }
                mCity = mCityAdapter.getItem(position);
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mCity);
                mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.address_hint)), true);
                mAreaAdapter.setData(areaList);
                mCityView.setVisibility(View.GONE);
                mAreaView.setVisibility(View.VISIBLE);

            } else if (recyclerView == mAreaView) {

                // 记录当前选择的区域
                mArea = mAreaAdapter.getItem(position);

                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mArea);

                mAreaView.setVisibility(View.INVISIBLE);
                mHintView.setVisibility(View.VISIBLE);

                if (mListener != null) {
                    mListener.onSelected(getDialog(), mCity, mArea);
                }

                // 延迟关闭
                postDelayed(this, 300);
            }
        }

        /**
         * {@link Runnable}
         */

        @Override
        public void run() {
            if (getDialogFragment() != null &&
                    getDialogFragment().isAdded() &&
                    getDialog() != null &&
                    getDialog().isShowing()) {
                dismiss();
            }
        }

        /**
         * {@link View.OnClickListener}
         */

        @Override
        public void onClick(View v) {
            if (v == mCloseView) {
                dismiss();
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
        }

        /**
         * {@link TabLayout.OnTabSelectedListener}
         */

        /**
         * Tab条目被选中
         */
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            tab.setText(getString(R.string.address_hint));
            switch (tab.getPosition()) {
                case 0:
                    mCity = mArea = null;
                    if (mTabLayout.getTabAt(2) != null) {
                        mTabLayout.removeTabAt(2);
                    }
                    mCityView.setVisibility(View.VISIBLE);
                    mAreaView.setVisibility(View.GONE);
                    break;
                case 1:
                    mArea = null;
                    mCityView.setVisibility(View.GONE);
                    mAreaView.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        /**
         * Tab条目被取消选中
         */
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        /**
         * Tab条目被重复点击
         */
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    private static final class AddressDialogAdapter extends MyRecyclerViewAdapter<String> {

        private AddressDialogAdapter(Context context) {
            super(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            TextView textView = new TextView(parent.getContext());
            textView.setGravity(Gravity.CENTER);
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textView.setBackground(getDrawable(typedValue.resourceId));
                } else {
                    textView.setBackgroundDrawable(getDrawable(typedValue.resourceId));
                }
            }
            textView.setTextColor(0xFF222222);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            return new ViewHolder(textView);
        }

        final class ViewHolder extends MyRecyclerViewAdapter.ViewHolder {

            private final TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) getItemView();
            }

            @Override
            public void onBindView(int position) {
                mTextView.setText(getItem(position));
            }
        }
    }

    public interface OnListener {

        /**
         * 选择完成后回调
         *
         * @param city     市
         * @param area     区
         */
        void onSelected(BaseDialog dialog, String city, String area);

        /**
         * 点击取消时回调
         */
        void onCancel(BaseDialog dialog);
    }
}