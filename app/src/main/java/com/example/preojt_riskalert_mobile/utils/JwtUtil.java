package com.example.preojt_riskalert_mobile.utils;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class JwtUtil {
    public static String getSubFromToken(String jwtToken) {
        try {
            String[] parts = jwtToken.split("\\.");
            if (parts.length < 2) return null;

            String payload = parts[1];
            String decodedPayload = new String(Base64.decode(payload, Base64.URL_SAFE));
            JSONObject jsonObject = new JSONObject(decodedPayload);

            return jsonObject.getString("sub");  // "sub" là UserID
        } catch (JSONException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}

