package com.sampleapp.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.sampleapp.R;

import de.telekom.smartcredentials.authentication.factory.SmartCredentialsAuthenticationFactory;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.authentication.AuthenticationError;
import de.telekom.smartcredentials.core.authentication.AuthenticationServiceInitListener;
import de.telekom.smartcredentials.core.authentication.configuration.AuthenticationConfiguration;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class AuthenticationModule extends ReactContextBaseJavaModule implements AuthenticationServiceInitListener {
    AuthenticationApi authenticationApi;
    ReactApplicationContext context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public AuthenticationModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
        PkceProvider pkceProvider = new PkceProvider();
        authenticationApi = SmartCredentialsAuthenticationFactory.getAuthenticationApi();
        authenticationApi.initialize(new AuthenticationConfiguration.ConfigurationBuilder(
                context,
                "GOOGLE",
                R.raw.google_openid_config,
                context.getColor(R.color.catalyst_redbox_background))
                .setPkceConfiguration(pkceProvider.generatePkceConfiguration())
                .build(), this);

    }

    @NonNull
    @Override
    public String getName() {
        return "AuthenticationModule";
    }

    @ReactMethod
    public boolean login() {
        Intent completionIntent = new Intent(context, CompletionActivity.class);
        completionIntent.putExtras(new Bundle());
        Intent cancelIntent = new Intent(context, CancelActivity.class);
        cancelIntent.putExtras(new Bundle());
        SmartCredentialsApiResponse<Boolean> login = authenticationApi.login(context, completionIntent, cancelIntent);
        return login.isSuccessful();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailed(AuthenticationError errorDescription) {

    }
}