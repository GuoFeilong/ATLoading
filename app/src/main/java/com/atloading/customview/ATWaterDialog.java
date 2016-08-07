package com.atloading.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atloading.R;

/**
 * Created by jsion on 16/8/7.
 */
public class ATWaterDialog extends Dialog {
    private Context mContext;
    private int mLoadingViewLayout;
    private String mLoadingDesc;
    private boolean mCancelable;
    private boolean mOutsideCancelable;
    private TextView mTextView;

    private ATWaterDialog(Context context) {
        this(context, 0);
    }

    private ATWaterDialog(Builder builder) {
        this(builder.mContext, 0);
        mContext = builder.mContext;
        mLoadingViewLayout = builder.mLoadingViewLayout;
        mLoadingDesc = builder.mLoadingDesc;
        mCancelable = builder.mCancelable;
        mOutsideCancelable = builder.mOutsideCancelable;
    }

    private ATWaterDialog(Context context, int themeResId) {
        super(context, R.style.Translucent_NoTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLoadingViewLayout);
        mTextView = (TextView) findViewById(R.id.tv_gif_oading_desc);
        if (null != mTextView) {
            mTextView.setText(mLoadingDesc);
        }
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(mOutsideCancelable);
    }

    public static class Builder {
        private Context mContext;
        private int mLoadingViewLayout;
        private String mLoadingDesc;
        private boolean mCancelable;
        private boolean mOutsideCancelable;

        public Builder(Context context, int loadingViewLayout) {
            mContext = context;
            mLoadingViewLayout = loadingViewLayout;
        }

        public Builder loadingDesc(String loadingDesc) {
            this.mLoadingDesc = loadingDesc;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder outsideCancelable(boolean outsideCancelable) {
            this.mOutsideCancelable = outsideCancelable;
            return this;
        }

        public ATWaterDialog build() {
            return new ATWaterDialog(this);
        }
    }

}

