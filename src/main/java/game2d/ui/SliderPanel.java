package game2d.ui;

import game2d.Handler;
import game2d.ui.support.ColorLabel;
import game2d.ui.support.ColorPanel;
import game2d.ui.support.GBC;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Hashtable;

public class SliderPanel extends JPanel {

    private JSlider slider;
    private Handler handler;
    private String labelText;
    private JLabel labelTitle;
    private JLabel toolTip;
    private Font font = new Font("SansSerif", Font.BOLD, 12);
    private Font fontToolTip = new Font("SansSerif", Font.BOLD, 15);
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private int from, to, init;

    public SliderPanel(Handler handler, String labelText, String toolTipText, int from, int to, int init) {
        this.handler = handler;
        this.labelText = labelText;
        this.from = from;
        this.to = to;
        this.init = init;

        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        Border etched = BorderFactory.createEtchedBorder();
        setBorder(etched);

        //SLIDER
        slider = new JSlider(from, to, init);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing((to - from) / 3);
        slider.setMinorTickSpacing((to - from) / 6);
        slider.setBackground(Color.DARK_GRAY);
        slider.setForeground(Color.WHITE);

        //LABEL
        labelTitle = new JLabel();
        labelTitle.setText(labelText);
        labelTitle.setBackground(Color.DARK_GRAY);
        labelTitle.setPreferredSize(new Dimension(dim.width / 30, dim.height / 30));
        labelTitle.setHorizontalAlignment(JLabel.LEFT);
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(font);
        //TOOL TIP
        toolTip = new ColorLabel("?", 20, 20, Color.DARK_GRAY);
        toolTip.setToolTipText(toolTipText);
        toolTip.setHorizontalAlignment(SwingConstants.CENTER);
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        toolTip.setBorder(BorderFactory.createEtchedBorder());
        toolTip.setForeground(Color.BLACK);
        toolTip.setFont(fontToolTip);

        add(labelTitle, new GBC(0, 0, 3, 1).setFill(1).setInsets(5, 5, 5, 5));
        add(slider, new GBC(3, 0, 3, 1).setFill(1).setInsets(5, 5, 5, 5));
        add(toolTip, new GBC(6, 0, 1, 1).setAnchor(GridBagConstraints.NORTHEAST).setInsets(2, 0, 0, 0));

        for (int i = 0; i < 7; i++) {
            add(new ColorPanel(Color.DARK_GRAY), new GBC(i, 0, 1, 1)
                    .setFill(1)
                    .setWeight(1, 1));
        }

    }

    public JSlider getSlider() {
        return slider;
    }

    public JLabel getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTable(Hashtable<Integer, JLabel> hashtable) {
        slider.setLabelTable(hashtable);
    }

    public void setLabelText(String text) {
        labelTitle.setText(text);
    }

    public void setValue(int value) {
        slider.setValue(value);
    }
}
