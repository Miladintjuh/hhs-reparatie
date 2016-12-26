package io.gameoftrades.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.HandelsPositie;

/**
 * Abstracte klasse voor het debuggen van algoritmen. 
 * Koppelt het algoritme aan het KaartPanel via de Debugger interface en 
 * maakt het stappen door het algoritme mogelijk. 
 */
public abstract class AbstractDebugPanel extends JPanel {

    public class GuiDebugger implements Debugger {

        @Override
        public void debugCoordinaten(Kaart kaart, List<Coordinaat> cs) {
            Map<Coordinaat, String> map = new HashMap<>();
            for (Coordinaat c : cs) {
                map.put(c, "X");
            }
            debugCoordinaten(kaart, map);
        }

        @Override
        public void debugCoordinaten(Kaart kaart, Map<Coordinaat, ?> open) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setCoordinaten(open);
            waitForStep();
        }

        @Override
        public void debugCoordinaten(Kaart kaart, Map<Coordinaat, ?> open, Map<Coordinaat, ?> closed) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setOpenClosed(open, closed);
            waitForStep();
        }

        @Override
        public void debugRaster(Kaart kaart, Integer[][] raster) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setOverlay(raster);
            waitForStep();
        }

        @Override
        public void debugPad(Kaart kaart, Coordinaat start, Pad pad) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setPad(start, pad);
            waitForStep();
        }

        @Override
        public void debugSteden(Kaart kaart, List<Stad> gedaan) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setSteden(gedaan);
            waitForStep();
        }

        @Override
        public void debugHandel(Kaart kaart, List<Handel> handel) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setHandel(handel);
            waitForStep();
        }

        @Override
        public void debugActies(Kaart kaart, HandelsPositie positie, List<Actie> acties) {
            kaartDisplay.setKaart(kaart);
            kaartDisplay.setHandel(null);
            kaartDisplay.setActies(positie, acties);
            waitForStep();
        }

        @Override
        public PlanControl speelPlanAf(Handelsplan plan, HandelsPositie initieel) {
            return kaartDisplay.setPlan(plan, initieel);
        }
    }

    private JButton startButton = new JButton(">> Start");
    private JButton stepButton = new JButton("> Step");
    private JButton stopButton = new JButton("[ ] Stop");
    private JCheckBox autoStep = new JCheckBox("Auto step");

    private KaartDisplay kaartDisplay;
    private Object stepLock = new Object();
    private Thread runner;
    private Thread watcher;
    private GuiDebugger debugger;

    public AbstractDebugPanel(KaartDisplay kaartDisplay) {
        this.kaartDisplay = kaartDisplay;
        this.debugger = new GuiDebugger();

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        stepButton.setEnabled(false);
        stepButton.setDefaultCapable(true);

        startButton.addActionListener((e) -> doStart());
        stepButton.addActionListener((e) -> doStep());
        stopButton.addActionListener((e) -> doStop());
    }

    protected Box getButtonPanel() {
        Box btns = new Box(BoxLayout.X_AXIS);
        btns.add(startButton);
        btns.add(Box.createHorizontalStrut(8));
        btns.add(stepButton);
        btns.add(Box.createHorizontalStrut(8));
        btns.add(stopButton);
        btns.add(Box.createHorizontalStrut(8));
        btns.add(autoStep);
        return btns;
    }

    protected void doStep() {
        synchronized (stepLock) {
            stepLock.notifyAll();
        }
    }

    protected void linkDebugger(Object target) {
        if (target instanceof Debuggable) {
            ((Debuggable) target).setDebugger(getDebugger());
        }
    }

    protected Debugger getDebugger() {
        return debugger;
    }

    private void doStart() {
        Runnable r = createRunnable();
        if (r != null) {
            kaartDisplay.reset();
            runner = new Thread(r);
            startButton.setEnabled(false);
            runner.start();
            stopButton.setEnabled(true);
            if (watcher == null) {
                startWatcher();
            }
        }
    }

    /**
     * the watcher presses the stop button when the runner has ended.
     */
    private void startWatcher() {
        if (watcher == null) {
            watcher = new Thread(() -> {
                while (true) {
                    if ((runner != null)) {
                        if (!runner.isAlive()) {
                            SwingUtilities.invokeLater(() -> {
                                if (stopButton.isEnabled()) {
                                    stopButton.doClick();
                                }
                            });
                            return;
                        }
                    } else {
                        return;
                    }
                    try {
                        Thread.sleep(100L);
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            watcher.setDaemon(true);
            watcher.start();
        }
    }

    protected abstract Runnable createRunnable();

    private void doStop() {
        stopButton.setEnabled(false);
        if (runner != null) {
            if (runner.isAlive()) {
                runner.stop();
            }
            if (watcher.isAlive()) {
                watcher.interrupt();
            }
            runner = null;
            watcher = null;
        }
        startButton.setEnabled(true);
    }

    protected void waitForStep() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                stepButton.setEnabled(true);
                if (autoStep.isSelected()) {
                    SwingUtilities.invokeLater(() -> {
                        if (stepButton.isEnabled()) {
                            stepButton.doClick();
                        }
                    });
                }
            });
            try {
                synchronized (stepLock) {
                    stepLock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                SwingUtilities.invokeAndWait(() -> {
                    stepButton.setEnabled(false);
                });
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
