package states;
import game2d.Handler;
import game2d.ui.*;
import game2d.ui.UIManager;
import game2d.ui.support.GBC;
import gfx.Assets;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoState extends State {
    private UIManager uiManager;
    public ActionListener goMainMenu, goRightPanel, goLeftPanel;
    private JFrame frame;
    private JPanel mainPanel;
    private SettingsPanel panelsArray[];
    private Canvas canvas;
    private NavigationButton goLeftButton, goRightButton, goUpButton;
    private Dimension dimension;
    private int width, height, currentPanel;


    public InfoState(Handler handler){
        super(handler);
        panelsArray=new SettingsPanel[4];
        width = handler.getGame().getWidth();
        height = handler.getGame().getHeight();
        frame=handler.getGame().getDisplay().getFrame();
        canvas=handler.getGame().getDisplay().getCanvas();
        canvas.setVisible(false);
        initActions();
        initNavigationButtons();
        addPanel(currentPanel);
    }

    public void addPanel(int panelID){
        switch (panelID) {
            case 0:{
                panelsArray[panelID] = new InfoPanel_0(handler);
                break;}
            case 1:{
                panelsArray[panelID] = new InfoPanel_1(handler);
                break;}
            case 2:{
                panelsArray[panelID] = new InfoPanel_2(handler);
                break;}
            case 3:{
                panelsArray[panelID] = new InfoPanel_3(handler);
                break;}
        }
        panelsArray[panelID].addMouseListener(handler.getMouseManager());
        panelsArray[panelID].addMouseMotionListener(handler.getMouseManager());
        panelsArray[panelID].setPreferredSize(new Dimension(width, height));
        dimension = panelsArray[panelID].getSize();
        frame.setLayout(new GridBagLayout());
        frame.add(panelsArray[panelID]);

        panelsArray[panelID].addNavigationButtons(goLeftButton, goUpButton, goRightButton);
        frame.validate();
    }

    public void initNavigationButtons() {
        goLeftButton = new NavigationButton(Assets.btn_arrowLEFT[0], Assets.btn_arrowLEFT[1], 49, 30);
        goLeftButton.addActionListener(goLeftPanel);
        goRightButton = new NavigationButton(Assets.btn_arrowRIGHT[0], Assets.btn_arrowRIGHT[1], 49, 30);
        goRightButton.addActionListener(goRightPanel);
        goUpButton = new NavigationButton(Assets.btn_arrowUP[0], Assets.btn_arrowUP[1], 49, 30);
        goUpButton.addActionListener(goMainMenu);
    }

    public void initActions(){
        goMainMenu = event-> {
            goUpButton.changeBackground();
            State.setState(handler.getGame().menuState);
            handler.getMouseManager().setUIManager(State.getState().getUIManager());
            frame.remove(panelsArray[currentPanel]);
            panelsArray[currentPanel].setFocusable(false);
            frame.setLayout(new GridBagLayout());
            frame.repaint();
            handler.getGame().getDisplay().getFrame().validate();
            frame.setFocusable(true);
            frame.requestFocus();
            canvas.setPreferredSize(new Dimension(width, height));
            canvas.setVisible(true);
            frame.validate();
        };
        goRightPanel = event->{
            goRightButton.changeBackground();
            frame.remove(panelsArray[currentPanel]);
            currentPanel++;
            if (currentPanel==panelsArray.length)currentPanel=0;
            addPanel(currentPanel);
        };
        goLeftPanel = event->{
            goLeftButton.changeBackground();
            frame.remove(panelsArray[currentPanel]);
            currentPanel--;
            if (currentPanel==-1)currentPanel=panelsArray.length-1;
            addPanel(currentPanel);
        };

    }
    public void tick(){}
    public void render(Graphics g){}
    public void renderG2D(Graphics2D g2d){}
    public UIManager getUIManager(){return uiManager;}

}