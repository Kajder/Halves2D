package game2d;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import display.Display;
import display.MainPanel;
import gfx.Assets;
import input.KeyManager;
import input.MouseManager;
import model.Parameters;
import states.*;

public class Game implements Runnable {

    private Display display;
    private MainPanel mainPanel;
    private int width, height, pitchWidth, modifiedWidth, modifiedHeight, mouseToframeX, mouseToframeY;
    private double ratio = 10; // pitch length is 536 pixels. I want it to be 53meters. So the ratio is 10.
    public String title;
    private Thread thread;
    private boolean running = false;
    private BufferStrategy bs;
    private Graphics g;
    private Graphics2D g2d;
    protected int fps = 60;
    private float compensationFPS = 60 / fps;
    private Handler handler;
    private Parameters parameters;
    private BufferedImage bufferedImage;
    //States
    public State menuState;
    //INPUT
    private KeyManager keyManager;
    private MouseManager mouseManager;

    public Game(String title, int width, int height) {

        this.width = width;
        this.height = height;
        this.title = title;
        this.pitchWidth = 670;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        handler = new Handler(this);
        parameters = new Parameters(handler);
    }

    public void initDisplay() {
        display = new Display(handler, title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        //display.getPanel().addMouseListener(mouseManager);
        //display.getPanel().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        Assets.init();
        //mainPanel=display.getPanel();
        modifiedWidth = display.getModifiedWidth();
        modifiedHeight = display.getModifiedWidth();
    }

    public void initStates() {
        menuState = new MenuState(handler);
        State.setState(menuState);
    }

    private void tick() {
        //decorateFrame();
        keyManager.tick();
        if (State.getState() != null) {
            State.getState().tick();
        }
        //System.out.println("focus set on:   "+display.getFrame().getFocusOwner());
    }

    private void decorateFrame() {
        mouseToframeX = MouseInfo.getPointerInfo().getLocation().x - display.getFrame().getX();
        mouseToframeY = MouseInfo.getPointerInfo().getLocation().y - display.getFrame().getY();

        if ((mouseToframeX > -10) && (mouseToframeX < display.getFrame().getWidth() + 10)) {
            if ((mouseToframeY > -10) && (mouseToframeY < 25)) {
                if (display.getFrame().isUndecorated()) {
                    display.getFrame().dispose();
                    display.getFrame().setUndecorated(false);
                    display.getFrame().setVisible(true);
                    display.getFrame().validate();
                }
            }
        }
        if ((mouseToframeX < -10) || (mouseToframeX > display.getFrame().getWidth() + 10) || (mouseToframeY < -10) || (mouseToframeY > 25)) {
            if (!display.getFrame().isUndecorated()) {
                display.getFrame().dispose();
                display.getFrame().setUndecorated(true);
                display.getFrame().setVisible(true);
                display.getFrame().validate();
            }
        }
    }

    public void render() {
		/*
		bs = display.getFrame().getBufferStrategy();
		if (bs == null) {
			display.getFrame().createBufferStrategy(3);
			return;
		}
		*/
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        //bufferedImage=mainPanel.getImage();
        //g=bufferedImage.getGraphics();
        g = bs.getDrawGraphics();
        g2d = (Graphics2D) g;
        if (State.getState() != null) {
            State.getState().render(g);
            State.getState().renderG2D(g2d);
            //mainPanel.repaint();
        }

        bs.show();
        g.dispose();
        g2d.dispose();
    }

    public void run() {
        initDisplay();
        initStates();
        //time flow regulation
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            timePerTick = 1000000000 / fps;
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            //delta += now - lastTime;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                //display.getPanel().repaint();
                render();
                ticks++;
                delta = 0;
            }
            if (timer >= 1000000000) {
                System.out.println("Ticks and Frames = " + ticks);
                ticks = 0;
                timer = 0;
            }

        }
        stop();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {

        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setFPS(int val) {
        fps = val;
    }

    public void setPitchWidth(int val) {
        pitchWidth = val;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public int getWidth() {
        return width;
    }

    public int getPitchWidth() {
        return pitchWidth;
    }

    public int getHeight() {
        return height;
    }

    public Display getDisplay() {
        return display;
    }

    public int getFPS() {
        return fps;
    }

    public double getRatio() {
        return ratio;
    }

    public float getCompensation() {
        return compensationFPS;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public int getModifiedHeight() {
        return modifiedHeight;
    }

    public int getModifiedWidth() {
        return modifiedWidth;
    }
}
