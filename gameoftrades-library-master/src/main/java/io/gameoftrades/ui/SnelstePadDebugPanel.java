package io.gameoftrades.ui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Stad;

/**
 * Debugger GUI voor het SnelstePad algoritme.
 */
public class SnelstePadDebugPanel extends AbstractDebugPanel {

    private DefaultComboBoxModel<SnelstePadAlgoritme> model = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Stad> startStadModel = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Stad> eindStadModel = new DefaultComboBoxModel<>();
    private JComboBox<SnelstePadAlgoritme> selection;
    private JComboBox<Stad> startStad;
    private JComboBox<Stad> eindStad;

    private Wereld wereld;

    public SnelstePadDebugPanel(KaartDisplay kaartDisplay, Wereld wereld, List<SnelstePadAlgoritme> choices) {
        super(kaartDisplay);
        this.wereld = wereld;
        this.setBorder(BorderFactory.createTitledBorder("Snelste Pad"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        choices.stream().forEach(e -> model.addElement(e));
        wereld.getSteden().stream().forEach(e -> startStadModel.addElement(e));
        wereld.getSteden().stream().forEach(e -> eindStadModel.addElement(e));
        selection = new JComboBox<>(model);
        startStad = new JComboBox<>(startStadModel);
        eindStad = new JComboBox<>(eindStadModel);
        this.add(new LabelPanel("Algoritme", selection));
        this.add(new LabelPanel("Van", startStad));
        this.add(new LabelPanel("Naar", eindStad));

        this.add(getButtonPanel());

    }

    @Override
    protected Runnable createRunnable() {
        SnelstePadAlgoritme selectedItem = (SnelstePadAlgoritme) selection.getSelectedItem();
        if (selectedItem != null) {
            linkDebugger(selectedItem);
            Coordinaat start = ((Stad) startStad.getSelectedItem()).getCoordinaat();
            Coordinaat eind = ((Stad) eindStad.getSelectedItem()).getCoordinaat();
            return new Runnable() {
                @Override
                public void run() {
                    selectedItem.bereken(wereld.getKaart(), start, eind);
                }
            };
        } else {
            return null;
        }
    }

}
