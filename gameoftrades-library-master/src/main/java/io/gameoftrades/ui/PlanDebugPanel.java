package io.gameoftrades.ui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import io.gameoftrades.debug.Debugger.PlanControl;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.actie.HandelsPositie;

/**
 * Debugger GUI voor het Handelsplan algoritme. 
 */
public class PlanDebugPanel extends AbstractDebugPanel {

    private DefaultComboBoxModel<HandelsplanAlgoritme> model = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Stad> startStadModel = new DefaultComboBoxModel<>();
    private JComboBox<HandelsplanAlgoritme> selection;
    private JComboBox<Stad> startStad;
    private JTextField tfKapitaal;
    private JTextField tfCapaciteit;
    private JTextField tfTijd;

    private Wereld wereld;

    public PlanDebugPanel(KaartDisplay kaartDisplay, Wereld wereld, List<HandelsplanAlgoritme> choices) {
        super(kaartDisplay);
        this.wereld = wereld;
        this.setBorder(BorderFactory.createTitledBorder("Handels Plan"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        choices.stream().forEach(e -> model.addElement(e));
        wereld.getSteden().stream().forEach(e -> startStadModel.addElement(e));
        selection = new JComboBox<>(model);
        startStad = new JComboBox<>(startStadModel);
        tfKapitaal = new JTextField("150");
        tfCapaciteit = new JTextField("10");
        tfTijd = new JTextField("100");
        this.add(new LabelPanel("Algoritme", selection));
        this.add(new LabelPanel("Stad", startStad));
        this.add(new LabelPanel("$", tfKapitaal));
        this.add(new LabelPanel("C", tfCapaciteit));
        this.add(new LabelPanel("T", tfTijd));

        this.add(getButtonPanel());

    }

    @Override
    protected Runnable createRunnable() {
        HandelsplanAlgoritme selectedItem = (HandelsplanAlgoritme) selection.getSelectedItem();
        if (selectedItem != null) {
            linkDebugger(selectedItem);
            int capaciteit = Integer.parseInt(tfCapaciteit.getText());
            int kapitaal = Integer.parseInt(tfKapitaal.getText());
            int tijd = Integer.parseInt(tfTijd.getText());
            Stad start = ((Stad) startStad.getSelectedItem());
            return new Runnable() {
                @Override
                public void run() {
                    HandelsPositie init = new HandelsPositie(wereld, start, kapitaal, capaciteit, tijd);
                    PlanControl planCtl = getDebugger().speelPlanAf(selectedItem.bereken(wereld, init), init);
                    while (planCtl.hasNextStep()) {
                        planCtl.nextStep();
                        waitForStep();
                    }
                }
            };
        } else {
            return null;
        }
    }

    
}
