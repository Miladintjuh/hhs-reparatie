package io.gameoftrades.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Utility panel voor het tonen van een label en component. 
 */
public class LabelPanel extends JPanel {

    public LabelPanel(String str, JComponent comp) {
        super(new BorderLayout());
        JLabel label = new JLabel(str);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        label.setPreferredSize(new Dimension(120, 20));
        this.add(label, BorderLayout.WEST);
        this.add(comp, BorderLayout.CENTER);
    }

}
