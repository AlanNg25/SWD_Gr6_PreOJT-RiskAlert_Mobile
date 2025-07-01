package com.example.preojt_riskalert_mobile.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class CustomProgressView extends androidx.appcompat.widget.AppCompatTextView {

    private int percentage = 0;

    public CustomProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPercentage(int percent) {
        this.percentage = percent;
        invalidate(); // Vẽ lại view khi thay đổi phần trăm
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 10;

        float centerX = width / 2f;
        float centerY = height / 2f;
        float strokeWidth = 15f;

        // Vẽ nền đỏ (chưa hoàn thành)
        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.RED);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        // Vẽ phần đã hoàn thành màu xanh
        Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(Color.parseColor("#5dc750"));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        RectF rect = new RectF(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );

        // Vẽ vòng đỏ 360 độ
        canvas.drawArc(rect, 0, 360, false, backgroundPaint);

        // Vẽ phần progress màu xanh
        float sweepAngle = (percentage * 360f) / 100f;
        canvas.drawArc(rect, -90, sweepAngle, false, progressPaint);

        // Vẽ text phần trăm ở giữa
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(32f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        canvas.drawText(percentage + "%", centerX, centerY + textHeight / 4, textPaint);
    }
}
