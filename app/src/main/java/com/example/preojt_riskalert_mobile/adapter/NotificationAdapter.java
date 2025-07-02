package com.example.preojt_riskalert_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.preojt_riskalert_mobile.R;
import com.example.preojt_riskalert_mobile.models.response.NotificationResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<NotificationResponse> notificationList;
    private final Context context;

    public NotificationAdapter(Context context, List<NotificationResponse> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationResponse notification = notificationList.get(position);

        // Format ngày nếu cần
        String formattedDate = formatDate(notification.getSentTime());

        holder.tvDate.setText("Date: " + formattedDate);
        holder.tvSubject.setText("Subject: " + notification.getCourse().getCourseCode());
        holder.tvContent.setText("Content: " + notification.getContent());

        holder.tvDownload.setOnClickListener(v -> {
            String url = notification.getAttachment();
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvSubject, tvContent, tvDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDownload = itemView.findViewById(R.id.tvDownload);
        }
    }

    private String formatDate(String isoDateString) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = isoFormat.parse(isoDateString);
            return DateFormat.format("d/M/yyyy", date).toString();
        } catch (ParseException e) {
            return isoDateString;
        }
    }
}

