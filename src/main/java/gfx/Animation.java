package gfx;

import java.awt.image.BufferedImage;

public class Animation {


    private int period, index;
    private long lastTime, timer;
    private BufferedImage[] frames;

    public Animation(int period, BufferedImage[] frames) {
        this.period = period;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void setPeriod(double val) {
        period = (int) val;
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (timer >= period) {
            index++;
            timer = 0;
            if (index >= frames.length) index = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    public int getIndex() {
        return index;
    }

    public int getPeriod() {
        return period;
    }

    public void setIndex(int val) {
        index = val;
    }
}
