package com.novang.anisched.tool;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;

import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

public class DynamicBackground {

    private GradientDrawable gradient;
    private int topColor;
    private int bottomColor;

    public DynamicBackground(GradientDrawable gradient, int topColor, int bottomColor) {
        this.gradient = gradient;
        this.topColor = topColor;
        this.bottomColor = bottomColor;
    }

    public static DynamicBackground generate(Bitmap bitmap) {
        Palette palette = Palette.from(bitmap).generate();

        Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();
        Palette.Swatch vibrant = palette.getVibrantSwatch();
        Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
        Palette.Swatch lightMuted = palette.getLightMutedSwatch();
        Palette.Swatch muted = palette.getMutedSwatch();
        Palette.Swatch darkMuted = palette.getDarkMutedSwatch();

        int topColor = 0, bottomColor = 0;
        float hue = 0;

        if (lightVibrant != null) {
            hue = lightVibrant.getHsl()[0];
        } else if (vibrant != null) {
            hue = vibrant.getHsl()[0];
        } else if (darkVibrant != null) {
            hue = darkVibrant.getHsl()[0];
        } else if (lightMuted != null) {
            hue = lightMuted.getHsl()[0];
        } else if (muted != null) {
            hue = muted.getHsl()[0];
        } else if (darkMuted != null) {
            hue = darkMuted.getHsl()[0];
        } else {
            return null;
        }

        topColor = ColorUtils.HSLToColor(new float[] { hue, 0.3f, 0.5f });
        bottomColor = ColorUtils.HSLToColor(new float[] { (hue % 360f) + 60f, 0.35f, 0.3f });

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] { topColor, topColor, bottomColor });

        return new DynamicBackground(gradientDrawable, topColor, bottomColor);
    }

    public GradientDrawable getGradient() {
        return gradient;
    }

    public int getTopColor() {
        return topColor;
    }

    public int getBottomColor() {
        return bottomColor;
    }
}
