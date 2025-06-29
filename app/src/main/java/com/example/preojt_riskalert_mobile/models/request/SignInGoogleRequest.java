package com.example.preojt_riskalert_mobile.models.request;

public class SignInGoogleRequest {
    private String idToken;

    public SignInGoogleRequest(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
