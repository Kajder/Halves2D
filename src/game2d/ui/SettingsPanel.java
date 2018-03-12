package game2d.ui;
import com.sun.deploy.ui.ImageLoader;
import game2d.Handler;
import game2d.ui.support.GBC;
import gfx.Assets;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class SettingsPanel extends JPanel {
    private Handler handler;
    private UIManager uiManager;
    private JPanel upperPanel, bottomPanel, arrowPanel, visualPanel, upperPanel0, upperPanel1, upperPanel2, upperPanel3, fpsPanel, pitchPanel;
    private JTextField textField;
    private JLabel labelFPS, labelPitchType;
    private JRadioButton fps30, fps50, fps60, fps100, pitchGrass, pitchUrban;
    private ButtonGroup groupFPS, groupPitch;
    private ChangeListener listenerSlider;
    private ActionListener listenerRadio30,listenerRadio50,listenerRadio60,listenerRadio100, listenerRadioPitchGrass, listenerRadioPitchUrban;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private ImageIcon iconGrass, iconUrban;
    private Font font1 = new Font("SansSerif", Font.BOLD, 20);
    private BufferStrategy bs;
    private Path2D path = new Path2D.Float();
    private Graphics g;
    private Graphics2D g2d;
    public SettingsPanel(Handler handler, UIManager uiManager){
        this.handler = handler;
        this.uiManager=uiManager;

        //TEXT FIELD
        textField = new JTextField() {
            @Override public void setBorder(Border border) {
            }
        };
        textField.setBackground(Color.DARK_GRAY);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(font1);
        textField.setForeground(Color.WHITE);
        textField.setText("Pitch width: "+(Math.round((handler.getGame().getPitchWidth()-240)/9.74))+" meters");

        visualPanel = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                paintVisualPanel(g);
            }
        };

        //LISTENERS
        //change listener + putting slider value into text label
        listenerSlider = event->{
            JSlider source = (JSlider) event.getSource();
            textField.setText("Pitch width: "+source.getValue()+" meters");
            handler.getGame().setPitchWidth((int)(Math.round(source.getValue()*9.74+240)));
            visualPanel.repaint();
        };
        listenerRadio30=event->{handler.getGame().setFPS(30);};
        listenerRadio50=event->{handler.getGame().setFPS(50);};
        listenerRadio60=event->{handler.getGame().setFPS(60);};
        listenerRadio100=event->{handler.getGame().setFPS(100);};
        listenerRadioPitchGrass=event->{Assets.setGrass();};
        listenerRadioPitchUrban=event->{Assets.setConcrete();};


        //LABELS
        //FPS
        labelFPS=new JLabel("FPS: ");
        labelFPS.setBackground(Color.DARK_GRAY);
        labelFPS.setSize(dim.width/20,dim.height/20);
        labelFPS.setHorizontalAlignment(JTextField.CENTER);
        labelFPS.setForeground(Color.WHITE);
        labelFPS.setFont(font1);
        //Pitch type
        labelPitchType=new JLabel("Pitch type: ");
        labelPitchType.setBackground(Color.DARK_GRAY);
        labelPitchType.setSize(dim.width/20,dim.height/20);
        labelPitchType.setHorizontalAlignment(JTextField.CENTER);
        labelPitchType.setForeground(Color.WHITE);
        labelPitchType.setFont(font1);

        //SLIDER
        JSlider slider = new JSlider(35,75,(int)(Math.round((handler.getGame().getPitchWidth()-240)/9.74)));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setBackground(Color.DARK_GRAY);
        slider.setForeground(Color.WHITE);
        slider.addChangeListener(listenerSlider);

        //RADIO BUTTONS
        //FPS - 30, 50, 60, 100
        fps30 = setRadioButton(fps30, "30",handler.getFPS()==30, Color.DARK_GRAY, Color.WHITE, font1, listenerRadio30);
        fps50 = setRadioButton(fps50, "50",handler.getFPS()==50, Color.DARK_GRAY, Color.WHITE, font1, listenerRadio50);
        fps60 = setRadioButton(fps60, "60",handler.getFPS()==60, Color.DARK_GRAY, Color.WHITE, font1, listenerRadio60);
        fps100 = setRadioButton(fps100, "100",handler.getFPS()==100, Color.DARK_GRAY, Color.WHITE, font1, listenerRadio100);

        //for radio buttons behaviour only
        groupFPS = new ButtonGroup();
        groupFPS.add(fps30);
        groupFPS.add(fps50);
        groupFPS.add(fps60);
        groupFPS.add(fps100);
        //add to panel
        fpsPanel=new JPanel();
        fpsPanel.setLayout(new GridLayout(1,4));
        fpsPanel.setBackground(Color.DARK_GRAY);
        fpsPanel.setForeground(Color.WHITE);
        fpsPanel.add(fps30);
        fpsPanel.add(fps50);
        fpsPanel.add(fps60);
        fpsPanel.add(fps100);

        //ICONS
        iconGrass=new ImageIcon(Assets.imgGrass);
        iconUrban=new ImageIcon(Assets.imgUrban);

        pitchGrass = setRadioButtonIcon(pitchGrass, iconGrass,"Grass", Color.DARK_GRAY, Color.WHITE, font1, listenerRadioPitchGrass);
        pitchGrass.setSelected(true);
        pitchUrban = setRadioButtonIcon(pitchUrban, iconUrban,"Urban",Color.DARK_GRAY, Color.WHITE, font1, listenerRadioPitchUrban);
        groupPitch = new ButtonGroup();
        groupPitch.add(pitchGrass);
        groupPitch.add(pitchUrban);
        pitchPanel=new JPanel();
        pitchPanel.setLayout(new GridLayout(1,2));
        pitchPanel.setBackground(Color.DARK_GRAY);
        pitchPanel.add(pitchGrass);
        pitchPanel.add(pitchUrban);
        arrowPanel = new JPanel(){
            public void paintComponent(Graphics g) {
           super.paintComponent(g);
            uiManager.render(g);
            //g.setColor(Color.BLUE);
            //g.fillRect(getWidth()-40, 20, 30,30);
        };};
        arrowPanel.setBackground(Color.DARK_GRAY);
        //arrowPanel.addMouseListener(handler.getGame().getMouseManager());
        //arrowPanel.addMouseMotionListener(handler.getGame().getMouseManager());

        //PANELS

        //upper - options
        upperPanel = new JPanel(new GridLayout(4,1));
        upperPanel0 = new JPanel(new GridLayout(1,1));
        upperPanel1 = new JPanel(new GridLayout(1,2));
        upperPanel2 = new JPanel(new GridLayout(1,2));
        upperPanel3 = new JPanel(new GridLayout(1,2));
        upperPanel.setBackground(Color.DARK_GRAY);
        upperPanel0.setBackground(Color.DARK_GRAY);
        upperPanel1.setBackground(Color.DARK_GRAY);
        upperPanel2.setBackground(Color.DARK_GRAY);
        upperPanel3.setBackground(Color.DARK_GRAY);

        upperPanel0.add(arrowPanel);
        upperPanel1.add(labelFPS);
        upperPanel1.add(fpsPanel);
        upperPanel2.add(labelPitchType);
        upperPanel2.add(pitchPanel);
        upperPanel3.add(textField);
        upperPanel3.add(slider);

        upperPanel.add(upperPanel0);
        upperPanel.add(upperPanel1);
        upperPanel.add(upperPanel2);
        upperPanel.add(upperPanel3);

        //bottom
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.DARK_GRAY);
        //visualPanel.setPreferredSize(new Dimension(Math.round((int)(bottomPanel.getWidth()/2)),Math.round((int)(bottomPanel.getHeight()*0.9)))); //panel created before action listeners
        visualPanel.setPreferredSize(new Dimension(469,290));
        visualPanel.setBackground(Color.BLACK);
        bottomPanel.add(visualPanel);

        //Borders
        Border etched = BorderFactory.createEtchedBorder();
        //Border titled = BorderFactory.createTitledBorder(etched, "FPS");
        upperPanel1.setBorder(etched);
        upperPanel2.setBorder(etched);
        upperPanel3.setBorder(etched);
        visualPanel.setBorder(etched);

        // upper/bottom
        add(upperPanel);
        add(bottomPanel);
    }

    public JRadioButton setRadioButton(JRadioButton button, String label, Boolean state, Color background, Color foreground, Font font, ActionListener listener){
        button = new JRadioButton(label, state);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(font);
        button.addActionListener(listener);
        return button;
    };
    public JRadioButton setRadioButtonIcon(JRadioButton button, Icon icon, String label, Color background, Color foreground, Font font, ActionListener listener){
        button = new JRadioButton(label, icon);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(font);
        button.addActionListener(listener);
        return button;
    };

    private void paintVisualPanel(Graphics g){
        g2d = (Graphics2D) g;
        g.drawImage(Assets.playground,8,5, 453,280, null);
        g.drawImage(Assets.centerLine, (int)((visualPanel.getWidth())+(5-handler.getPitchWidth())*0.33)/2,(visualPanel.getHeight())/2-2,(int)((-5+handler.getPitchWidth())*0.33),4,null);
        g.drawImage(Assets.centerPoint, visualPanel.getWidth()/2-2,visualPanel.getHeight()/2-2,4,4, null);
        //g.drawImage(Assets.verWall_L, (int)(((visualPanel.getWidth()-16)+(240-handler.getPitchWidth())*0.618)/2),5,12,visualPanel.getHeight()-10, null);
        g.drawImage(Assets.verWall_L,(int)(272-handler.getPitchWidth()*0.22),5,12,visualPanel.getHeight()-10, null);
        g.drawImage(Assets.verWall_R,(int)(185+handler.getPitchWidth()*0.22),5,12,visualPanel.getHeight()-10, null);

        g2d.setClip(calculatePath(path));
        g.drawImage(Assets.horWall, 8,8, visualPanel.getWidth()-16,12, null);
        g.drawImage(Assets.horWall, 8,visualPanel.getHeight()-20, visualPanel.getWidth()-16,12, null);
        g2d.setClip(null);
        path.reset();


    };

    private Path2D calculatePath(Path2D path){
        path.moveTo((int)(272-handler.getPitchWidth()*0.22), 8);
        path.lineTo((int)(272-handler.getPitchWidth()*0.22), visualPanel.getHeight()-8);
        path.lineTo((int)(185+handler.getPitchWidth()*0.22)+12, visualPanel.getHeight()-8);
        path.lineTo((int)(185+handler.getPitchWidth()*0.22)+12, 8);
        path.closePath();
        return path;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        arrowPanel.repaint();
        };



}
