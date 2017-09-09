package co.bugg.quickplay.util;

import co.bugg.quickplay.QuickPlay;

import java.awt.*;

/**
 * Class extending Java's base Color class
 * but with more fields relating to QuickPlay
 */
public class QuickPlayColor extends Color {

    /**
     * Name in the lang file.
     * Also the name in the HashMap
     * Namespace: quickplay.color.name
     */
    String unlocalizedName;
    boolean isChroma;

    public QuickPlayColor(int r, int g, int b, String unlocalizedName, boolean isChroma) {
        super(r, g, b);

        this.unlocalizedName = unlocalizedName;
        this.isChroma = isChroma;
    }

    public QuickPlayColor(int r, int g, int b, int a, String unlocalizedName, boolean isChroma) {
        super(r, g, b, a);

        this.unlocalizedName = unlocalizedName;
        this.isChroma = isChroma;
    }

    public QuickPlayColor(float r, float g, float b, String unlocalizedName, boolean isChroma) {
        super(r, g, b);

        this.unlocalizedName = unlocalizedName;
        this.isChroma = isChroma;
    }

    public QuickPlayColor(float r, float g, float b, float a, String unlocalizedName, boolean isChroma) {
        super(r, g, b, a);

        this.unlocalizedName = unlocalizedName;
        this.isChroma = isChroma;
    }

    public QuickPlayColor(int rgb, String unlocalizedName, boolean isChroma) {
        super(rgb);

        this.unlocalizedName = unlocalizedName;
        this.isChroma = isChroma;
    }

    public void setColor(int rgb) {
        QuickPlay.configManager.getConfig().colors.put(getUnlocalizedName(), new QuickPlayColor(rgb, getUnlocalizedName(), getIsChroma()));
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setIsChroma(boolean isChroma) {
        this.isChroma = isChroma;
    }

    public boolean getIsChroma() {
        return isChroma;
    }

    public static int nextChromaFrame(QuickPlayColor color) {
        float[] hsb = RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[0] += 0.01;
        return HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }
}
