package razerdp.com.widget.linechat;

import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2018/1/11.
 */
final class InternalChatInfo {
    final String lineTag;

    List<LineChatInfoWrapper> mInfos;
    Paint linePaint;
    Paint hightLightCirclePaint;
    Path linePath;

    public InternalChatInfo(String lineTag) {
        this(lineTag, null);
    }

    public InternalChatInfo(String lineTag, ILineChatInfo info) {
        this.lineTag = lineTag;
        if (mInfos == null) {
            mInfos = new ArrayList<>();
        }
        add(info);
        initPaint(info);
    }

    private void initPaint(ILineChatInfo info) {
        if (info == null) return;
        if (linePaint == null) {
            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(info.getChatLineWidth());
            linePaint.setColor(info.getChatLineColor());
        }
        if (hightLightCirclePaint == null) {
            hightLightCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            hightLightCirclePaint.setStyle(Paint.Style.FILL);
            hightLightCirclePaint.setColor(info.getHightLightColor());
        }
        if (linePath == null) {
            linePath = new Path();
        }
    }

    InternalChatInfo add(ILineChatInfo info) {
        if (info == null) return this;
        if (mInfos == null) {
            mInfos = new ArrayList<>();
        }
        mInfos.add(new LineChatInfoWrapper(info));
        initPaint(info);
        return this;
    }

    List<? extends ILineChatInfo> getRawInfoList() {
        List<ILineChatInfo> result = new ArrayList<>();
        for (LineChatInfoWrapper info : mInfos) {
            result.add(info.mInfo);
        }
        return result;
    }

    LineChatInfoWrapper getInfo(int index) {
        if (index < 0 || index >= mInfos.size()) return null;
        return mInfos.get(index);
    }

    InternalChatInfo calculateYpercent(double minValue, double maxValue) {
        double range = Math.abs(maxValue);
        for (LineChatInfoWrapper info : mInfos) {
            double yPercent = Math.abs(info.mInfo.getValue()) / range;
            info.setyPercent(yPercent);
        }
        return this;
    }

    int getRawInfoListSize() {
        return mInfos.size();
    }
}
