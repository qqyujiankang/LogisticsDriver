package com.cn.android.utils;

import androidx.fragment.app.FragmentActivity;

import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.hjq.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class PayUtils {
    private  OnPayListener payListener;

    public  OnPayListener getPayListener() {
        return payListener;
    }

    public void setPayListener(OnPayListener pay) {
        this.payListener = pay;
    }

    /**
     * activity 上下文\
     *type     1为购买VIP  2为议价  3为单票查看订单详情  4为接单付定金
     *userid   用户id
     *pay_type 支付方式  1 支付宝  2 微信   3 余额支付
     *money 金额
     *orderid 订单id
     *bargainprice 议价时：输入的议价金额
     *is_duty 接单时：是否有物流责任险  1是 2否
     *is_empty 接单时：是否有放空险  1是 2否
     *is_time 接单时：是否有待时险  1是 2否
     * */
    public  PayUtils infoPay(FragmentActivity activity, int type, String userid, int payType,
                               double money, String orderid, double bargainprice, int is_duty, int is_empty, int is_time) {
        new PublicInterfaceePresenetr(new PublicInterfaceView() {
            @Override
            public Map<String, Object> setPublicInterfaceData(int tag) {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("type", type);
                paramsMap.put("userid", userid);
                paramsMap.put("pay_type", payType);
                paramsMap.put("money", money);
                paramsMap.put("orderid", orderid);
                paramsMap.put("bargainprice", bargainprice);
                paramsMap.put("is_duty", is_duty);
                paramsMap.put("is_empty", is_empty);
                paramsMap.put("is_time", is_time);
                return paramsMap;
            }

            @Override
            public void onPublicInterfaceSuccess(String data, int tag) {
                payListener.paySuccess(tag);
            }

            @Override
            public void onPublicInterfaceError(String error, int tag) {
                payListener.payError(tag);
                ToastUtils.show(error);
            }
        }).getPostTokenRequest(activity, ServerUrl.infopay, payType);
        return this;
    }

    public interface OnPayListener {
        void paySuccess(int payType);

        void payError(int payType);
    }
}
