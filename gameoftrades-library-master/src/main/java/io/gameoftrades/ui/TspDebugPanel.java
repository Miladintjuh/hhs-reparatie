package io.gameoftrades.ui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;

/**
 * Debugger GUI voor het StedenTour algoritme. 
 */
public class TspDebugPanel extends AbstractDebugPanel {

    private DefaultComboBoxModel<StedenTourAlgoritme> model = new DefaultComboBoxModel<>();
    private JComboBox<StedenTourAlgoritme> selection;

    private Wereld wereld;

    public TspDebugPanel(KaartDisplay kaartDisplay, Wereld wereld, List<StedenTourAlgoritme> choices) {
        super(kaartDisplay);
        this.wereld = wereld;
        this.setBorder(BorderFactory.createTitledBorder("Steden Tour"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        choices.stream().forEach(e -> model.addElement(e));
        selection = new JComboBox<>(model);
        this.add(new LabelPanel("Algoritme", selection));

        this.add(getButtonPanel());

    }

    @Override
    protected Runnable createRunnable() {
        StedenTourAlgoritme selectedItem = (StedenTourAlgoritme) selection.getSelectedItem();
        if (selectedItem != null) {
            linkDebugger(selectedItem);
            return new Runnable() {
                @Override
                public void run() {
                    selectedItem.bereken(wereld.getKaart(), wereld.getSteden());
                }
            };
        } else {
            return null;
        }
    }

}
