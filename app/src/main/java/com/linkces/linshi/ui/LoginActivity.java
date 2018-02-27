package com.linkces.linshi.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.linkces.linshi.R;
import com.linkces.linshi.base.RxBaseActivity;
import com.linkces.linshi.entity.ResultModel;
import com.linkces.linshi.network.RetrofitHelper;
import com.linkces.linshi.network.exception.ApiException;
import com.linkces.linshi.network.observer.HttpRxObservable;
import com.linkces.linshi.network.observer.HttpRxObserver;
import com.linkces.linshi.utils.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends RxBaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.submit)
    Button submit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @OnClick(R.id.submit)
    public void login(){
        String accountStr = account.getText().toString();
        String passwordStr = password.getText().toString();

        HttpRxObserver<ResultModel<String>> myObserver = new HttpRxObserver<ResultModel<String>>(TAG) {
            @Override
            protected void onStart(Disposable d) {
                ToastUtil.ShortToast("start");
            }

            @Override
            protected void onError(ApiException e) {
                ToastUtil.ShortToast("error");
            }

            @Override
            protected void onSuccess(ResultModel<String> response) {
                ToastUtil.ShortToast("start"+response.getContent());
            }
        };

        HttpRxObservable.getObservable(
                RetrofitHelper.getLoginService()
                        .getToken(accountStr, passwordStr),this, ActivityEvent.PAUSE)
                        .subscribe(myObserver);

    }
}
