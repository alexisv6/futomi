package com.valenciaprod.futomi;
//
// Copyright (c) 2017 Record360. All rights reserved.
//

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.okta.appauth.android.OktaAppAuth;

import net.openid.appauth.AuthorizationException;

public class LoginActivity extends AppCompatActivity {
    private OktaAppAuth oktaAppAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oktaAppAuth = OktaAppAuth.getInstance(this);
        oktaAppAuth.init(
                this,
                new OktaAppAuth.OktaAuthListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("LoginActivity", "Successfully Init");
                        Intent completionIntent = new Intent(this, MainActivity.class);
                        Intent cancelIntent = new Intent(this, LoginActivity.class);
                        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        oktaAppAuth.login(
                                this,
                                PendingIntent.getActivity(this, 0, completionIntent, 0),
                                PendingIntent.getActivity(this, 0, cancelIntent, 0)
                        );
                    }

                    @Override
                    public void onTokenFailure(@NonNull AuthorizationException e) {
                        Log.e("LoginActivity", "Error Logging In", e);
                    }
                }
        );
    }
}
