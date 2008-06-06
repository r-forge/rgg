/*
 * MAImporterPanel.java
 *
 * Created on 23. April 2008, 22:23
 */
package at.ac.arcs.rgg.element.maimporter.ui;

import at.ac.arcs.rgg.element.maimporter.ui.model.RGListTableModel;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import at.ac.arcs.rgg.element.maimporter.array.*;
import at.ac.arcs.rgg.element.maimporter.ui.model.MAImporterModel;
import at.ac.arcs.rgg.util.BusyDialog;
import at.ac.arcs.rgg.util.RGGFileExtensionFilter;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.util.DefaultUnitConverter;
import com.jgoodies.forms.util.UnitConverter;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.jdesktop.swingworker.SwingWorker;

/**
 *
 * @author  ilhami
 */
public class MAImporterPanel extends javax.swing.JPanel {

    private TargetFile targetfile;
    private String arraySource;
    private MAImporterModel mamodel;
    private PropertyChangeListener pclistener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName() == MAImporterModel.RGLISTTABLEMODELPROPERTY) {
                rgListPanel.setModel((RGListTableModel) evt.getNewValue());
            }
        }
    };

    /** Creates new form MAImporterPanel */
    public MAImporterPanel() {
        initComponents();
        addTabs();
    }

    public TargetFile getTargetFile() {
        return targetfile;
    }

    public String getRHeader() {
        return rgListPanel.getRHeader();
    }

    public String getRbHeader() {
        return rgListPanel.getRbHeader();
    }

    public String getGHeader() {
        return rgListPanel.getGHeader();
    }

    public String getGbHeader() {
        return rgListPanel.getGbHeader();
    }

    public List<String> getAnnotationHeaders() {
        return rgListPanel.getAnnotationHeaders();
    }

    public String getArraySource() {
        return arraySource;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        loadTargetFileXHyperlink = new org.jdesktop.swingx.JXHyperlink();
        chooseMicroArraysXHyperlink = new org.jdesktop.swingx.JXHyperlink();
        jXHeader1 = new org.jdesktop.swingx.JXHeader();
        targetFileChooser = new javax.swing.JFileChooser();
        arraysFileChooser = new javax.swing.JFileChooser();
        tabbedPane = new javax.swing.JTabbedPane();

        loadTargetFileXHyperlink.setClickedColor(new java.awt.Color(0, 51, 255));
        loadTargetFileXHyperlink.setText("Load Target File");
        loadTargetFileXHyperlink.setFont(new java.awt.Font("Tahoma", 0, 14));
        loadTargetFileXHyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loadTargetFileXHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTargetFileXHyperlinkActionPerformed(evt);
            }
        });

        chooseMicroArraysXHyperlink.setClickedColor(new java.awt.Color(0, 51, 255));
        chooseMicroArraysXHyperlink.setText("Choose Microarrays");
        chooseMicroArraysXHyperlink.setFont(new java.awt.Font("Tahoma", 0, 14));
        chooseMicroArraysXHyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chooseMicroArraysXHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseMicroArraysXHyperlinkActionPerformed(evt);
            }
        });

        jXHeader1.setDescription("Please use the ...");
        jXHeader1.setTitle("Tasks");

        targetFileChooser.setFileFilter(new RGGFileExtensionFilter("Target File", "txt"));

        arraysFileChooser.setDialogTitle("Microarrays");
        arraysFileChooser.setMultiSelectionEnabled(true);

        setLayout(new java.awt.BorderLayout());

        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        add(tabbedPane, java.awt.BorderLayout.CENTER);

        //#############Layout#################
        CellConstraints cc = new CellConstraints();
        FormLayout inputPanelLayout =
                new FormLayout("center:pref",//cols
                "min,5dlu,pref");//rows

        JPanel hyperLinkPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(hyperLinkPanel, BoxLayout.Y_AXIS);
        hyperLinkPanel.setLayout(boxlayout);
        hyperLinkPanel.add(loadTargetFileXHyperlink);
        hyperLinkPanel.add(chooseMicroArraysXHyperlink);

        DefaultFormBuilder builder = new DefaultFormBuilder(inputPanelLayout);
        builder.add(jXHeader1, cc.xy(1, 1));
//        builder.add(loadTargetFileXHyperlink, cc.xy(1, 3));
//        builder.add(chooseMicroArraysXHyperlink, cc.xy(1, 5));
        builder.add(hyperLinkPanel, cc.xy(1, 3));
        inputPanel = builder.getPanel();
        UnitConverter converter = DefaultUnitConverter.getInstance();
        inputPanel.setPreferredSize(new Dimension(converter.dialogUnitXAsPixel(300, inputPanel),
                converter.dialogUnitXAsPixel(200, inputPanel)));

        FormLayout mainLayout = new FormLayout("fill:pref:grow", "fill:pref:grow");
        setLayout(mainLayout);
        add(tabbedPane, cc.xy(1, 1));
    }

    private void setPanels() {
        arrayheaderrowselectionpanel.setModel(mamodel.getArrayHeaderRowTableModel());
        tabbedPane.setComponentAt(1, arrayheaderrowselectionpanel);
        targetFilePanel.setModel(mamodel.getTargetFileModel());
        rgListPanel.setModel(mamodel.getRGListTableModel());
        tabbedPane.setComponentAt(3, rgListPanel);
    }

    private void loadTargetFileXHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        int status = targetFileChooser.showOpenDialog(loadTargetFileXHyperlink);
        if (status == JFileChooser.APPROVE_OPTION) {
            try {
                mamodel = createMAModel(1);
                mamodel.addPropertyChangeListener(pclistener);
                setPanels();
            } catch (ArrayDetectionException ex) {
                Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(loadTargetFileXHyperlink,
                        ex.getMessage(), "Array Detection Error", JOptionPane.ERROR_MESSAGE);
            } catch (TargetFileException ex) {
                Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(loadTargetFileXHyperlink,
                        ex.getMessage(), "Target File Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(loadTargetFileXHyperlink,
                        ex.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void chooseMicroArraysXHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        int status = arraysFileChooser.showOpenDialog(chooseMicroArraysXHyperlink);
        if (status == JFileChooser.APPROVE_OPTION) {
            try {
                mamodel = createMAModel(2);
                mamodel.addPropertyChangeListener(pclistener);
                setPanels();
            } catch (ArrayDetectionException ex) {
                Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(loadTargetFileXHyperlink,
                        ex.getMessage(), "Array Detection Error", JOptionPane.ERROR_MESSAGE);
            } catch (TargetFileException ex) {
                Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(loadTargetFileXHyperlink,
                        ex.getMessage(), "Target File Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(loadTargetFileXHyperlink,
                        ex.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void yapiste(final java.io.File in) {
        try {

            SwingWorker<MAImporterModel, Object> worker = new SwingWorker<MAImporterModel, Object>() {

                @Override
                protected MAImporterModel doInBackground() throws Exception {
                    return MAImporterModel.createModelFromArrays(new java.io.File[]{in});
                }

                @Override
                protected void done() {
                    super.done();
                }
            };

            worker.execute();

            mamodel = worker.get();
            mamodel.addPropertyChangeListener(pclistener);
            setPanels();
        } catch (InterruptedException ex) {
            Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MAImporterModel createMAModel(final int mod)
            throws TargetFileException, ArrayDetectionException, IOException {
        final BusyDialog busy = new BusyDialog(null, true,
                "Recognizing arrays...", BusyDialog.ACTION.CANCEL);
        SwingWorker<MAImporterModel, Object> worker = new SwingWorker<MAImporterModel, Object>() {

            @Override
            protected MAImporterModel doInBackground() throws Exception {
                switch (mod) {
                    case 1:
                        return MAImporterModel.createModelFromTargetFile(targetFileChooser.getSelectedFile());
                    case 2:
                        return MAImporterModel.createModelFromArrays(arraysFileChooser.getSelectedFiles());
                    default:
                        return null;
                }
            }

            @Override
            protected void done() {
                super.done();
                busy.setVisible(false);
            }
        };

        worker.execute();
        busy.setVisible(true);
        if (busy.getActionClicked() == BusyDialog.ACTION.CANCEL) {
            worker.cancel(true);
            return null;
        }

        try {
            return worker.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
            //TODO ist es gute Idee hier null zurÃƒÂ¼ckzugeben???
            return null;
        } catch (ExecutionException ex) {
            Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getCause() instanceof TargetFileException) {
                throw (TargetFileException) ex.getCause();
            } else if (ex.getCause() instanceof ArrayDetectionException) {
                throw (ArrayDetectionException) ex.getCause();
            } else {
                throw (IOException) ex.getCause();
            }
        }
    }

    private void addTabs() {
        int height = 0;
        VTextIcon textIcon = new VTextIcon(tabbedPane, "Select Microarray Data", VTextIcon.ROTATE_LEFT);
        height += textIcon.getIconHeight();
        tabbedPane.addTab(null, textIcon, inputPanel);

        textIcon = new VTextIcon(tabbedPane, "Set Header Line", VTextIcon.ROTATE_LEFT);
        height += textIcon.getIconHeight();
        tabbedPane.addTab(null, textIcon, arrayheaderrowselectionpanel);

        textIcon = new VTextIcon(tabbedPane, "Target File", VTextIcon.ROTATE_LEFT);
        tabbedPane.addTab(null, textIcon, targetFilePanel);

        textIcon = new VTextIcon(tabbedPane, "Settings", VTextIcon.ROTATE_LEFT);
        height += textIcon.getIconHeight();
        rgListPanel = new RGListSettingsPanel(height);
        tabbedPane.addTab(null, textIcon, rgListPanel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("sdkljfls");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                java.io.File input = new java.io.File("E:/projects/RGG/arrayImporter/arrays/Generic.txt");
                MAImporterPanel mapanel = new MAImporterPanel();
                mapanel.yapiste(input);
                f.setContentPane(mapanel);
                f.setSize(mapanel.getPreferredSize().width+200,mapanel.getPreferredSize().height+200);
                f.setVisible(true);
            }
        });

    }
    private ArrayHeaderRowSelectionPanel arrayheaderrowselectionpanel = new ArrayHeaderRowSelectionPanel();
    private TargetFilePanel targetFilePanel = new TargetFilePanel();
    private RGListSettingsPanel rgListPanel;
    private javax.swing.JFileChooser arraysFileChooser;
    private org.jdesktop.swingx.JXHyperlink chooseMicroArraysXHyperlink;
    private javax.swing.JPanel inputPanel;
    private org.jdesktop.swingx.JXHeader jXHeader1;
    private org.jdesktop.swingx.JXHyperlink loadTargetFileXHyperlink;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JFileChooser targetFileChooser;
}
