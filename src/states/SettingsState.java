package states;
import game2d.Handler;
import game2d.ui.ClickListener;
import game2d.ui.SettingsPanel;
import game2d.ui.UIImageButton;
import game2d.ui.UIManager;
import game2d.ui.support.GBC;
import gfx.Assets;
import javax.swing.*;
import java.awt.*;

public class SettingsState extends State {
    private UIManager uiManager;
    private JFrame frame;
    private JPanel mainPanel;
    private Canvas canvas;
    private SettingsPanel settingsPanel;
    private Graphics g;
    private Graphics2D g2d;
    private Dimension dimension;
    private int width, height;

    public SettingsState(Handler handler){
        super(handler);
        width = handler.getGame().getWidth();
        height = handler.getGame().getHeight();
        uiManager=new UIManager(handler);
        frame=handler.getGame().getDisplay().getFrame();
        //mainPanel = handler.getGame().getDisplay().getPanel();
        //frame.remove(mainPanel);
        canvas=handler.getGame().getDisplay().getCanvas();
        //frame.remove(canvas); //causing error, as game.render has a null pointer
        canvas.setVisible(false);

        if (settingsPanel==null){
        settingsPanel = new SettingsPanel(handler, uiManager);
        settingsPanel.setLayout(new GridLayout(2,1));
        settingsPanel.addMouseListener(handler.getMouseManager());
        settingsPanel.addMouseMotionListener(handler.getMouseManager());
        //settingsPanel.addKeyListener(handler.getGame().getKeyManager());
        settingsPanel.setPreferredSize(new Dimension(width, height));
        dimension = settingsPanel.getSize();
        frame.setLayout(new GridBagLayout());
        frame.add(settingsPanel);
        frame.validate();
        }
        else{
            frame.validate();
            settingsPanel.setVisible(true);
        }


        uiManager.addObject(new UIImageButton((int)(dimension.width-30), 5, 20, 20, Assets.btn_arrow, new ClickListener(){
            public void onClick(){
                State.setState(handler.getGame().menuState);
                handler.getMouseManager().setUIManager(State.getState().getUIManager());
                //frame.add(mainPanel);
                frame.remove(settingsPanel);
                settingsPanel.setFocusable(false);
                frame.setLayout(new GridBagLayout());
                frame.repaint();
                handler.getGame().getDisplay().getFrame().validate();
                frame.setFocusable(true);
                frame.requestFocus();
                //settingsPanel.setVisible(false);
                canvas.setPreferredSize(new Dimension(width, height));
                canvas.setVisible(true);
            }
        }));
    }
    public void tick(){
        dimension = settingsPanel.getSize();
        uiManager.getObject(0).setX((dimension.width-30));
        uiManager.tick();
    };
    public void render(Graphics g) {
        uiManager.render(g);
        settingsPanel.repaint();
      }
    public void renderG2D(Graphics2D g2d) {}
    public UIManager getUIManager(){return uiManager;}
}
