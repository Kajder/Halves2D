package game2d.ui;

import game2d.Handler;
import game2d.ui.support.ColorPanel;
import game2d.ui.support.GBC;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class SettingsPanel_1 extends SettingsPanel {
    private Handler handler;
    private SliderPanel s1, s2, s3, s4, s5, s6, s7, s8, s9, s10;
    private JButton regButton, proButton, superButton;
    private JPanel buttonsPanel;
    private Font font = new Font("SansSerif", Font.BOLD, 12);
    private String toolTipS1,toolTipS2,toolTipS3,toolTipS4,toolTipS5,toolTipS6,toolTipS7,toolTipS8,toolTipS9,toolTipS10;
    private String labelS1,labelS2,labelS3,labelS4,labelS5,labelS6,labelS7,labelS8,labelS9,labelS10;
    private Hashtable<Integer,JLabel> labelsMapAngular,labelsMapRestitution,labelsMapDensity, labelsMapContact, labelsMapWalk, labelsMapSprint;
    private ChangeListener listenerS1,listenerS2,listenerS3,listenerS4,listenerS5,listenerS6,listenerS7,listenerS8,listenerS9,listenerS10;
    private DecimalFormat formatter, formatter1digit, formatter2digits;
    private double angularVel, density, resCoef, contactTime, walkSpeed, sprintSpeed, speedScale;
    public SettingsPanel_1(Handler handler){
        super(handler);
        this.handler=handler;
        formatter1digit = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        formatter1digit.setRoundingMode( RoundingMode.DOWN );
        formatter2digits = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        formatter2digits.setRoundingMode( RoundingMode.DOWN );
        formatter = new DecimalFormat("#.###", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        formatter.setRoundingMode( RoundingMode.DOWN );
        speedScale=60/handler.getGame().getRatio();

        //CREATING BUTTONS
        //regular
        regButton=new JButton("Regular");
        regButton.setBackground(Color.WHITE);
        regButton.setForeground(Color.BLACK);
        regButton.setPreferredSize(new Dimension(200,30));
        regButton.setFont(font);
        regButton.setFocusPainted(false);
        regButton.addActionListener(e->{
            handler.getParameters().getCustomParameters().putAll(handler.getParameters().getRegularParameters());
            setSlidersValues();
            repaint();});
        //pro
        proButton=new JButton("Professional");
        proButton.setBackground(Color.WHITE);
        proButton.setForeground(Color.BLACK);
        proButton.setPreferredSize(new Dimension(200,30));
        proButton.setFont(font);
        proButton.setFocusPainted(false);
        proButton.addActionListener(e->{
            handler.getParameters().getCustomParameters().putAll(handler.getParameters().getProParameters());
            setSlidersValues();
            repaint();});
        //super
        superButton=new JButton("Super Human");
        superButton.setBackground(Color.WHITE);
        superButton.setForeground(Color.BLACK);
        superButton.setPreferredSize(new Dimension(200,30));
        superButton.setFont(font);
        superButton.setFocusPainted(false);
        superButton.addActionListener(e->{
            handler.getParameters().getCustomParameters().putAll(handler.getParameters().getSuperParameters());
            setSlidersValues();
            repaint();});
        //panel
        buttonsPanel=new JPanel();
        buttonsPanel.setBackground(Color.DARK_GRAY);
        buttonsPanel.setBorder(BorderFactory.createEtchedBorder());
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.add(regButton, new GBC(0,0,1,1).setAnchor(GridBagConstraints.CENTER).setWeight(1,1));
        buttonsPanel.add(proButton, new GBC(1,0,1,1).setAnchor(GridBagConstraints.CENTER).setWeight(1,1));
        buttonsPanel.add(superButton, new GBC(2,0,1,1).setAnchor(GridBagConstraints.CENTER).setWeight(1,1));

        //CREATING JLABELS TABLES
        //for angular velocity(s3)
        labelsMapAngular = new Hashtable<>();
        labelsMapAngular.put(200,new JLabel("2"));
        labelsMapAngular.put(400,new JLabel("4"));
        labelsMapAngular.put(600,new JLabel("6"));
        labelsMapAngular.put(800,new JLabel("8"));
        labelsMapAngular.get(200).setForeground(Color.WHITE);
        labelsMapAngular.get(400).setForeground(Color.WHITE);
        labelsMapAngular.get(600).setForeground(Color.WHITE);
        labelsMapAngular.get(800).setForeground(Color.WHITE);
        //for air density (s3)
        labelsMapDensity = new Hashtable<>();
        labelsMapDensity.put(500,new JLabel("0.5"));
        labelsMapDensity.put(1500,new JLabel("1.5"));
        labelsMapDensity.put(2500,new JLabel("2.5"));
        labelsMapDensity.put(3500,new JLabel("3.5"));
        labelsMapDensity.get(500).setForeground(Color.WHITE);
        labelsMapDensity.get(1500).setForeground(Color.WHITE);
        labelsMapDensity.get(2500).setForeground(Color.WHITE);
        labelsMapDensity.get(3500).setForeground(Color.WHITE);
        //for coeff of restitution (s4)
        labelsMapRestitution = new Hashtable<>();
        labelsMapRestitution.put(40,new JLabel("0.4"));
        labelsMapRestitution.put(60,new JLabel("0.6"));
        labelsMapRestitution.put(80,new JLabel("0.8"));
        labelsMapRestitution.put(100,new JLabel("1"));
        labelsMapRestitution.get(40).setForeground(Color.WHITE);
        labelsMapRestitution.get(60).setForeground(Color.WHITE);
        labelsMapRestitution.get(80).setForeground(Color.WHITE);
        labelsMapRestitution.get(100).setForeground(Color.WHITE);
        //for contact time (s9)
        labelsMapContact = new Hashtable<>();
        labelsMapContact.put(60,new JLabel("6"));
        labelsMapContact.put(80,new JLabel("8"));
        labelsMapContact.put(100,new JLabel("10"));
        labelsMapContact.put(120,new JLabel("12"));
        labelsMapContact.get(60).setForeground(Color.WHITE);
        labelsMapContact.get(80).setForeground(Color.WHITE);
        labelsMapContact.get(100).setForeground(Color.WHITE);
        labelsMapContact.get(120).setForeground(Color.WHITE);
        //for contact time (s5)
        labelsMapWalk = new Hashtable<>();
        labelsMapWalk.put(300,new JLabel("3"));
        labelsMapWalk.put(400,new JLabel("4"));
        labelsMapWalk.put(500,new JLabel("5"));
        labelsMapWalk.put(600,new JLabel("6"));
        labelsMapWalk.get(300).setForeground(Color.WHITE);
        labelsMapWalk.get(400).setForeground(Color.WHITE);
        labelsMapWalk.get(500).setForeground(Color.WHITE);
        labelsMapWalk.get(600).setForeground(Color.WHITE);
        //for contact time (s6)
        labelsMapSprint = new Hashtable<>();
        labelsMapSprint.put(600,new JLabel("6"));
        labelsMapSprint.put(800,new JLabel("8"));
        labelsMapSprint.put(1000,new JLabel("10"));
        labelsMapSprint.put(1200,new JLabel("12"));
        labelsMapSprint.get(600).setForeground(Color.WHITE);
        labelsMapSprint.get(800).setForeground(Color.WHITE);
        labelsMapSprint.get(1000).setForeground(Color.WHITE);
        labelsMapSprint.get(1200).setForeground(Color.WHITE);


    //CREATING STRINGS TO DISPLAY
        labelS1=String.format("<html>Maximum force generated by leg muscles during the shot: %d N</html>",
                handler.getParameters().getCustomParameters().get("maxForce").intValue());
        toolTipS1="<html>Mind the fact it is not the force that Player's leg passes to the ball.<br>" +
                        "It is the force that accelerates Player's foot!<br>" +
                "So, for a certain value of this parameter, <br>the heavier the Player is, the lower is the velocity of his foot during the shot.<br>" +
                "<br>However, if you increase the mass of the Player vs 80kg, you will get some " +
                        "<br>extra hidden leg force as a bonus... And loose some for less than 80kg." +
                        "<br>Checkout other parameters and find your balance!:)</html>";

        angularVel=((Math.round(((handler.getParameters().getCustomParameters().get("maxAngular").intValue())*1000d))/100000d));
        labelS2=String.format("<html>Maximum angular velocity of the ball:<br>%s rounds/s</html>",
                formatter2digits.format(angularVel));
        toolTipS2="<html>The faster the ball rotates, <br>" +
                "the bigger is the force of Magnus effect<br>" +
                "(the cause of turns of rotating ball).<br>" +
                "Mind the fact it is the initial velocity<br>" +
                "and that the velocity decreases exponentially.</html>";
        density=((Math.round(((handler.getParameters().getCustomParameters().get("airDensity").intValue())*1000d))/1000000d));
        labelS3=String.format("<html>Air density:<br>%s kg/m^3</html>",formatter.format(density));
        toolTipS3="<html>The denser the air is, <br>" +
                "the bigger is the force of air resistance<br>" +
                "(causing more rapid ball's slowdown), <br>" +
                "but also the bigger is the force of Magnus effect<br>" +
                "(the cause of turns of rotating ball).</html>";
        resCoef=((Math.round(((handler.getParameters().getCustomParameters().get("restitutionCoef").intValue())*1000d))/100000d));
        labelS4=String.format("<html>Foot-ball coefficient of restitution: %s</html>",
                formatter2digits.format(resCoef));
        toolTipS4="<html>The higher the coefficient,<br>" +
                "the higher initial ball velocity.<br><br>" +
                "For the value of 1 the foot-ball collision<br>" +
                "is elastic and kinetic energy is fully conserved<br>" +
                "(there is no energy loss during the kick).<br>" +
                "For 0 the collision is plastic<br>" +
                "(it is the most inefficient case).</html>";
        walkSpeed=((Math.round(((handler.getParameters().getCustomParameters().get("walkSpeed").intValue())*speedScale*1000d))/100000d));
        labelS5=String.format("<html>Walk speed<br>(for 80kg of<br>Player's mass):<br>%s m/s</html>",
                formatter2digits.format(walkSpeed));
        toolTipS5="<html>The faster Player moves,<br>" +
                "the higher initial ball velocity is after he shoots.<br><br>" +
                "However, his stamina decreases faster too.<br>" +
                "Once he runs out of stamina, his speed<br>" +
                "and muscle strength gets radically reduced.</html>";
        sprintSpeed=((Math.round(((handler.getParameters().getCustomParameters().get("sprintSpeed").intValue())*speedScale*1000d))/100000d));
        labelS6=String.format("<html>Maximum sprint speed (for 80kg of Player's mass):<br>%s m/s</html>",
                formatter2digits.format(sprintSpeed));
        toolTipS6="<html>The faster Player moves,<br>" +
                "the higher initial ball velocity is after he shoots.<br><br>" +
                "However, his stamina decreases faster too.<br>" +
                "Once he runs out of stamina, his speed<br>" +
                "and muscle strength gets radically reduced.</html>";
        labelS7=String.format("<html>Mass of Blue Player:<br>%d kg</html>",handler.getParameters().getCustomParameters().get("blueMass").intValue());
        toolTipS7="<html>The heavier the Player gets,<br>" +
                "the lower his sprint speed and foot velocity is<br>" +
                "(causing lower ball velocity after shot).<br><br>" +
                "However, with mass increase Player will get<br>" +
                "some extra muscle force from the game (as it is in reality).<br>" +
                "Also, that is to encourage you<br>" +
                "to play with this parameter.:)</html>";
        labelS8=String.format("<html>Mass of Red Player:<br>%d kg</html>",handler.getParameters().getCustomParameters().get("redMass").intValue());
        toolTipS8="<html>The heavier the Player gets,<br>" +
                "the lower his sprint speed and foot velocity is<br>" +
                "(causing lower ball velocity after shot).<br><br>" +
                "However, with mass increase Player will get<br>" +
                "some extra muscle force from the game (as it is in reality).<br>" +
                "Also, that is to encourage you<br>" +
                "to play with this parameter.:)</html>";
        contactTime=((Math.round(((handler.getParameters().getCustomParameters().get("contactTime").intValue())*1000d))/10000d));
        labelS9=String.format("<html>Foot-ball time of contact during the shot: %s ms</html>", formatter1digit.format(contactTime));
        toolTipS9="<html>The longer the time of contact,<br>" +
                "the higher initial ball velocity.</html>";
        labelS10=String.format("<html>Players initial<br>stamina:<br>%d</html>",handler.getParameters().getCustomParameters().get("stamina").intValue());
        toolTipS10="<html>More stamina Player has,<br>" +
                "longer he will run<br>" +
                "before he gets exhausted.<br>" +
                "Once exhausted, his sprint speed and muscle force<br>" +
                "are radically reduced.</html>";

    //CREATING SLIDERS
        s1 = new SliderPanel(handler, labelS1,  toolTipS1, 2000,5000,handler.getParameters().getCustomParameters().get("maxForce").intValue());
        s2 = new SliderPanel(handler, labelS2,  toolTipS2,200,800,handler.getParameters().getCustomParameters().get("maxAngular").intValue());
        s3 = new SliderPanel(handler, labelS3,  toolTipS3,500,3500,handler.getParameters().getCustomParameters().get("airDensity").intValue());
        s4 = new SliderPanel(handler, labelS4,  toolTipS4,40,100,handler.getParameters().getCustomParameters().get("restitutionCoef").intValue());
        s5 = new SliderPanel(handler, labelS5,  toolTipS5,300,600,(int)(handler.getParameters().getCustomParameters().get("walkSpeed").intValue()*speedScale));
        s6 = new SliderPanel(handler, labelS6,  toolTipS6,600,1200,(int)(handler.getParameters().getCustomParameters().get("sprintSpeed").intValue()*speedScale));
        s7 = new SliderPanel(handler, labelS7,  toolTipS7,60,150,handler.getParameters().getCustomParameters().get("blueMass").intValue());
        s8 = new SliderPanel(handler, labelS8,  toolTipS8,60,150,handler.getParameters().getCustomParameters().get("redMass").intValue());
        s9 = new SliderPanel(handler, labelS9, toolTipS9,60,120,handler.getParameters().getCustomParameters().get("contactTime").intValue());
        s10 = new SliderPanel(handler, labelS10,  toolTipS10,5000,14000,handler.getParameters().getCustomParameters().get("stamina").intValue());

        s2.setLabelTable(labelsMapAngular);
        s3.setLabelTable(labelsMapDensity);
        s3.getLabelTitle().setHorizontalAlignment(SwingConstants.LEFT);
        s4.setLabelTable(labelsMapRestitution);
        s5.setLabelTable(labelsMapWalk);
        s6.setLabelTable(labelsMapSprint);
        s9.setLabelTable(labelsMapContact);


        //CREATING ACTIONS
        listenerS1 = (event)->{
            JSlider source = (JSlider) event.getSource();
            s1.setLabelText(String.format("<html>Maximum force<br>generated by leg<br>muscles during the<br>shot: %d N</html>",source.getValue()));
            handler.getParameters().getCustomParameters().put("maxForce",(double)source.getValue());
        };
        listenerS2 = (event)->{
            JSlider source = (JSlider) event.getSource();
            angularVel=((Math.round(((source.getValue())*1000d))/100000d));
            s2.setLabelText(String.format("<html>Maximum angular<br>velocity of the ball:<br>%s rounds/s</html>",formatter2digits.format(angularVel)));
            handler.getParameters().getCustomParameters().put("maxAngular",(double)source.getValue());
        };
        listenerS3 = (event)->{
            JSlider source = (JSlider) event.getSource();
            density=((Math.round((source.getValue())*1000d))/1000000d);
            s3.setLabelText(String.format("<html>Air density:<br>%s kg/m^3</html>",formatter.format(density)));
            handler.getParameters().getCustomParameters().put("airDensity",(double)source.getValue());
        };
        listenerS4 = (event)->{
            JSlider source = (JSlider) event.getSource();
            resCoef=((Math.round(((source.getValue())*1000d))/100000d));
            s4.setLabelText(String.format("<html>Foot-ball coefficient<br>of restitution: %s</html>",formatter2digits.format(resCoef)));
            handler.getParameters().getCustomParameters().put("restitutionCoef",(double)source.getValue());
        };
        listenerS5 = (event)->{
            JSlider source = (JSlider) event.getSource();
            walkSpeed=((Math.round(((source.getValue())*1000d))/100000d));
            s5.setLabelText(String.format("<html>Walk speed<br>(for 80kg of<br>Player's mass):<br>%s m/s</html>",formatter2digits.format(walkSpeed)));
            handler.getParameters().getCustomParameters().put("walkSpeed",(double)source.getValue()/speedScale);
        };
        listenerS6 = (event)->{
            JSlider source = (JSlider) event.getSource();
            sprintSpeed=((Math.round(((source.getValue())*1000d))/100000d));
            s6.setLabelText(String.format("<html>Maximum sprint<br>speed (for 80kg of<br>Player's mass):<br>%s m/s</html>",formatter2digits.format(sprintSpeed)));
            handler.getParameters().getCustomParameters().put("sprintSpeed",(double)source.getValue()/speedScale);
        };
        listenerS7 = (event)->{
            JSlider source = (JSlider) event.getSource();
            s7.setLabelText(String.format("<html>Mass of Blue Player:<br>%d kg</html>",source.getValue()));
            handler.getParameters().getCustomParameters().put("blueMass",(double)source.getValue());
        };
        listenerS8 = (event)->{
            JSlider source = (JSlider) event.getSource();
            s8.setLabelText(String.format("<html>Mass of Red Player:<br>%d kg</html>",source.getValue()));
            handler.getParameters().getCustomParameters().put("redMass",(double)source.getValue());
        };
        listenerS9 = (event)->{
            JSlider source = (JSlider) event.getSource();
            contactTime=((Math.round(((source.getValue())*1000d))/10000d));
            s9.setLabelText(String.format("<html>Foot-ball time of<br>contact during the<br>shot: %s ms</html>",formatter1digit.format(contactTime)));
            handler.getParameters().getCustomParameters().put("contactTime",(double)source.getValue());
        };
        listenerS10 = (event)->{
            JSlider source = (JSlider) event.getSource();
            s10.setLabelText(String.format("<html>Players initial<br>stamina:<br>%d</html>",handler.getParameters().getCustomParameters().get("stamina").intValue()));
            handler.getParameters().getCustomParameters().put("stamina",(double)source.getValue());
        };

        //ADDING ACTIONS
        s1.getSlider().addChangeListener(listenerS1);
        s2.getSlider().addChangeListener(listenerS2);
        s3.getSlider().addChangeListener(listenerS3);
        s4.getSlider().addChangeListener(listenerS4);
        s5.getSlider().addChangeListener(listenerS5);
        s6.getSlider().addChangeListener(listenerS6);
        s7.getSlider().addChangeListener(listenerS7);
        s8.getSlider().addChangeListener(listenerS8);
        s9.getSlider().addChangeListener(listenerS9);
        s10.getSlider().addChangeListener(listenerS10);

        //POPULATING BOTTOM BAR
        bottomBar.add(buttonsPanel, new GBC(0,0,2,1).setFill(1).setInsets(0,10,0,10));
        bottomBar.add(s1, new GBC(0,1,1,1).setFill(1).setInsets(10,10,5,5));
        bottomBar.add(s2, new GBC(1,1,1,1).setFill(1).setInsets(10,5,5,10));
        bottomBar.add(s3, new GBC(0,2,1,1).setFill(1).setInsets(5,10,5,5));
        bottomBar.add(s4, new GBC(1,2,1,1).setFill(1).setInsets(5,5,5,10));
        bottomBar.add(s9, new GBC(0,3,1,1).setFill(1).setInsets(5,10,5,5));
        bottomBar.add(s10, new GBC(1,3,1,1).setFill(1).setInsets(5,5,5,10));
        bottomBar.add(s5, new GBC(0,4,1,1).setFill(1).setInsets(5,10,5,5));
        bottomBar.add(s6, new GBC(1,4,1,1).setFill(1).setInsets(5,5,5,10));
        bottomBar.add(s7, new GBC(0,5,1,1).setFill(1).setInsets(5,10,10,5));
        bottomBar.add(s8, new GBC(1,5,1,1).setFill(1).setInsets(5,5,10,10));

        for (int i=0;i<=1;i++){
            for (int j=0;j<5;j++){
                bottomBar.add(new ColorPanel(Color.DARK_GRAY), new GBC(i,j,1,1)
                        .setFill(1)
                        .setWeight(1,1));
            }
        }
        labelTitle.setText("PHYSICS & PLAYERS");
        addTopBottomPanels();
    }

    public void setSlidersValues(){
        s1.setValue(handler.getParameters().getCustomParameters().get("maxForce").intValue());
        s2.setValue(handler.getParameters().getCustomParameters().get("maxAngular").intValue());
        s3.setValue(handler.getParameters().getCustomParameters().get("airDensity").intValue());
        s4.setValue(handler.getParameters().getCustomParameters().get("restitutionCoef").intValue());
        s5.setValue((int)(handler.getParameters().getCustomParameters().get("walkSpeed").intValue()*speedScale));
        s6.setValue((int)(handler.getParameters().getCustomParameters().get("sprintSpeed").intValue()*speedScale));
        s7.setValue(handler.getParameters().getCustomParameters().get("blueMass").intValue());
        s8.setValue(handler.getParameters().getCustomParameters().get("redMass").intValue());
        s9.setValue(handler.getParameters().getCustomParameters().get("contactTime").intValue());
        s10.setValue(handler.getParameters().getCustomParameters().get("stamina").intValue());
    }
}