package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game2d.Handler;
import game2d.ui.ClickListener;
import game2d.ui.UIImageButton;
import game2d.ui.UIManager;
import gfx.Assets;

public class MenuState extends State {
    private UIManager uiManager;

    private int titleTimer = 60, titleWidth = 300, titleHeight = 60,
            btnWidth = 90, btnHeight = 56, backgroundWidthFinal = 600, backgroundHeightFinal = 600, backgroundWidth = 2000, backgroundHeight = 2000;
    private boolean introKey = true;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        // start match button
        uiManager.addObject(new UIImageButton(100 - (btnWidth) / 2, 100 - (btnHeight) / 2, btnWidth, btnHeight, Assets.btn_start, new ClickListener() {
            public void onClick() {
                TrainingState.trainingPlayer = 0;
                handler.getGame().getDisplay().getFrame().validate();
                State.setState(new GameState(handler));
                handler.getMouseManager().setUIManager(State.getState().getUIManager());
            }
        }));
        //training P1 button
        uiManager.addObject(new UIImageButton(100 - (btnWidth) / 2, 300 - (btnHeight) / 2, btnWidth, btnHeight, Assets.btn_trainingP1, new ClickListener() {
            public void onClick() {
                TrainingState.trainingPlayer = 1;
                State.setState(new TrainingState(handler));
                handler.getGame().getDisplay().getFrame().validate();
                handler.getMouseManager().setUIManager(State.getState().getUIManager());
            }
        }));
        //training P2 button
        uiManager.addObject(new UIImageButton(100 - (btnWidth) / 2, 500 - (btnHeight) / 2, btnWidth, btnHeight, Assets.btn_trainingP2, new ClickListener() {
            public void onClick() {
                TrainingState.trainingPlayer = 2;
                State.setState(new TrainingState(handler));
                handler.getGame().getDisplay().getFrame().validate();
                handler.getMouseManager().setUIManager(State.getState().getUIManager());
            }
        }));
        //settings button
        uiManager.addObject(new UIImageButton(handler.getWidth() - 100 - (btnWidth) / 2, 100 - (btnHeight) / 2, btnWidth, btnHeight, Assets.btn_settings, new ClickListener() {
            public void onClick() {
                TrainingState.trainingPlayer = 0;
                State.setState(new SettingsState(handler));
                handler.getGame().getDisplay().getFrame().validate();
                handler.getMouseManager().setUIManager(State.getState().getUIManager());
            }
        }));
        //info button
        uiManager.addObject(new UIImageButton(handler.getWidth() - 100 - (btnWidth) / 2, 500 - (btnHeight) / 2, btnWidth, btnHeight, Assets.btn_info, new ClickListener() {
            public void onClick() {
                TrainingState.trainingPlayer = 0;
                State.setState(new InfoState(handler));
                handler.getGame().getDisplay().getFrame().validate();
                handler.getMouseManager().setUIManager(State.getState().getUIManager());
            }
        }));
    }

    @Override
    public void tick() {
        if (!introKey) uiManager.tick();
        backgroundSizer();
    }

    public void backgroundSizer() {
        if (titleTimer >= 0) titleTimer--;
        if (titleTimer <= 0) {
            if (backgroundWidth > backgroundWidthFinal) backgroundWidth = backgroundWidth - 6;
            if (backgroundHeight > backgroundHeightFinal) backgroundHeight = backgroundHeight - 6;
            if ((backgroundWidth <= backgroundWidthFinal) && (introKey)) introKey = false;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
        g.drawImage(Assets.menuBackground, (handler.getWidth() - backgroundWidth) / 2, (handler.getHeight() - backgroundHeight) / 2, backgroundWidth, backgroundHeight, null);
        if (titleTimer > 0)
            g.drawImage(Assets.halvesText, (handler.getWidth() - titleWidth) / 2, (handler.getHeight() - titleHeight) / 2 - 20, titleWidth, titleHeight, null);
        if (!introKey) uiManager.render(g);
    }

    public void renderG2D(Graphics2D g2d) {
    }

    public UIManager getUIManager() {
        return uiManager;
    }

}
