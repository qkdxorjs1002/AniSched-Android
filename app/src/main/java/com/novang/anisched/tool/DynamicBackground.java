package com.novang.anisched.tool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

/**
 * Pick color from bitmap to generate gradient drawable
 *
 * @author Novang (qkdxorjs1002)
 */
public class DynamicBackground {

    private final GradientDrawable background;
    private final GradientDrawable shade;
    private final int topColor;
    private final int bottomColor;
    private final boolean isTopDark;

    public DynamicBackground(GradientDrawable background, GradientDrawable shade, int topColor, int bottomColor, boolean isTopDark) {
        this.background = background;
        this.shade = shade;
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        this.isTopDark = isTopDark;
    }

    public static DynamicBackground generate(Bitmap bitmap) {
        Palette.Swatch dominantSwatch = Palette.from(bitmap).generate().getDominantSwatch();
        Palette.Swatch topDominantSwatch = Palette.from(
                Bitmap.createBitmap(
                        bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight() / 6))
                .generate()
                .getDominantSwatch();

        int topColor = 0, bottomColor = 0;
        boolean isTopDark = false;
        float hue = 0;

        if (dominantSwatch != null) {
            hue = dominantSwatch.getHsl()[0];
            if (topDominantSwatch != null) {
                isTopDark = (topDominantSwatch.getHsl()[2] <= 0.6f);
            }
        } else {
            return null;
        }

        topColor = ColorUtils.HSLToColor(new float[] { hue, 0.3f, 0.5f });
        bottomColor = ColorUtils.HSLToColor(new float[] { (hue % 360f) + 60f, 0.35f, 0.3f });

        GradientDrawable background = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] { topColor, topColor, bottomColor });

        GradientDrawable shade = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] { Color.TRANSPARENT, topColor, topColor });

        return new DynamicBackground(background, shade, topColor, bottomColor, isTopDark);
    }

    public GradientDrawable getBackground() {
        return background;
    }

    public GradientDrawable getShade() {
        return shade;
    }

    public int getTopColor() {
        return topColor;
    }

    public int getBottomColor() {
        return bottomColor;
    }

    public boolean isTopDark() {
        return isTopDark;
    }
}
