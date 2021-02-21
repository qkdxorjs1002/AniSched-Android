package com.novang.anisched.tool;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;

import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

/**
 * Pick color from bitmap to generate gradient drawable
 *
 * @author Novang (qkdxorjs1002)
 */
public class DynamicBackground {

    private GradientDrawable gradient;
    private int topColor;
    private int bottomColor;
    private boolean isDark;

    public DynamicBackground(GradientDrawable gradient, int topColor, int bottomColor, boolean isDark) {
        this.gradient = gradient;
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        this.isDark = isDark;
    }

    public static DynamicBackground generate(Bitmap bitmap) {
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch dominantSwatch = palette.getDominantSwatch();

        int topColor = 0, bottomColor = 0;
        boolean isDark = false;
        float hue = 0;

        if (dominantSwatch != null) {
            hue = dominantSwatch.getHsl()[0];
            isDark = !(dominantSwatch.getHsl()[2] >= 0.5f);
        } else {
            return null;
        }

        topColor = ColorUtils.HSLToColor(new float[] { hue, 0.3f, 0.5f });
        bottomColor = ColorUtils.HSLToColor(new float[] { (hue % 360f) + 60f, 0.35f, 0.3f });

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] { topColor, topColor, bottomColor });

        return new DynamicBackground(gradientDrawable, topColor, bottomColor, isDark);
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

    public boolean isDark() {
        return isDark;
    }
}
