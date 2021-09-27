package com.sampleapp.auth;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sampleapp.R;

import de.telekom.smartcredentials.authentication.AuthStateManager;


public class CompletionActivity extends AppCompatActivity {

    TextView token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion);
        //startActivity(new Intent(this, MainActivity.class));
        //finish();
        token = (TextView) findViewById(R.id.token_value_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AuthStateManager authStateManager = AuthStateManager.getInstance(this, "GOOGLE");
        Token accessToken = new Token(TokenType.ACCESS_TOKEN, authStateManager.getAccessToken(), authStateManager.getAccessTokenExpirationTime());
        token.setText("ACCESS TOKEN = " + accessToken.getToken() + "\n" + "EXPIRED TIME = " + accessToken.getValidity()
                + "\n" + "TOKEN TYPE = " + accessToken.getTokenType().name());

    }
}