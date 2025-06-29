//package com.example.preojt_riskalert_mobile.ui.profile;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//public class ProfileViewModel extends ViewModel {
//    private final MutableLiveData<String> mText;
//
//    public ProfileViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is profile fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
//}

package com.example.preojt_riskalert_mobile.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.preojt_riskalert_mobile.models.response.ProfileResponse;

public class ProfileViewModel extends ViewModel  {
    private final MutableLiveData<ProfileResponse> profileData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<ProfileResponse> getProfileData() {
        return profileData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setProfileData(ProfileResponse response) {
        profileData.setValue(response);
    }

    public void setErrorMessage(String error) {
        errorMessage.setValue(error);
    }
}
