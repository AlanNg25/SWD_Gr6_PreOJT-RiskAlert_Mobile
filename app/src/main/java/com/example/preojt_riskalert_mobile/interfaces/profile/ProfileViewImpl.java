package com.example.preojt_riskalert_mobile.interfaces.profile;

import com.example.preojt_riskalert_mobile.models.response.ProfileResponse;

public interface ProfileViewImpl {
    void onProfileSuccess(ProfileResponse profileResponse);

    void onProfileError(String errorMessage);

}
