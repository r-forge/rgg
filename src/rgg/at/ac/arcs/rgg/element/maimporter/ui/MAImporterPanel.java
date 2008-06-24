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
import java.beans.PropertyChangeSupport;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import org.jdesktop.swingworker.SwingWorker;
import org.jdesktop.swingx.JXHyperlink;

/**
 *
 * @author  ilhami
 */
public class MAImporterPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private MAImporterModel mamodel;
    private String[] othercolumns;

    public MAImporterPanel(String[] othercolumns) {
        this.othercolumns = othercolumns;
        initComponents();
        addTabs();
        targetFilePanel.addTargetFileTableColumnListener(new TableColumnModelListener() {

            public void columnAdded(TableColumnModelEvent e) {
                propertyChange(new PropertyChangeEvent(this, MAImporterModel.PROP_TargetFile, null, mamodel.getTargetFile()));
            }

            public void columnRemoved(TableColumnModelEvent e) {
                propertyChange(new PropertyChangeEvent(this, MAImporterModel.PROP_TargetFile, null, mamodel.getTargetFile()));
            }

            public void columnMoved(TableColumnModelEvent e) {
            }

            public void columnMarginChanged(ChangeEvent e) {
            }

            public void columnSelectionChanged(ListSelectionEvent e) {
            }
        });
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MAImporterModel.RGLISTTABLEMODELPROPERTY)) {
            rgListPanel.setModel((RGListTableModel) evt.getNewValue());
        } else if (evt.getPropertyName().equals(Array.PROP_Annotation)) {
            changeSupport.firePropertyChange(evt);
        } else if (evt.getPropertyName().equals(MAImporterModel.PROP_TargetFile)) {
            changeSupport.firePropertyChange(evt);
        }
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

    public List<String> getOtherColumnHeaders() {
        return rgListPanel.getOtherColumnHeaders();
    }

    public String getArraySource() {
        return mamodel.getArrayInfos().get(0).getArraySource();
    }

    public MAImporterModel getMAModel() {
        return mamodel;
    }

    private void initComponents() {
        loadTargetFileXHyperlink = new org.jdesktop.swingx.JXHyperlink();
        chooseMicroArraysXHyperlink = new org.jdesktop.swingx.JXHyperlink();
        loadAffymetrixTargetFileXHyperlink = new JXHyperlink();
        jXHeader1 = new org.jdesktop.swingx.JXHeader();
        targetFileChooser = new javax.swing.JFileChooser();
        arraysFileChooser = new javax.swing.JFileChooser();
        tabbedPane = new javax.swing.JTabbedPane();

        ImageIcon folder = new ImageIcon(getClass().
                getResource("/at/ac/arcs/rgg/element/maimporter/ui/icon/folder.png"));

        loadTargetFileXHyperlink.setClickedColor(new java.awt.Color(0, 0, 0));
        loadTargetFileXHyperlink.setUnclickedColor(new java.awt.Color(0, 0, 0));
        loadTargetFileXHyperlink.setIcon(folder);
        loadTargetFileXHyperlink.setText("Sample annotation file");
        loadTargetFileXHyperlink.setFont(new java.awt.Font("Tahoma", 1, 11));
        loadTargetFileXHyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loadTargetFileXHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTargetFileXHyperlinkActionPerformed(evt);
            }
        });

        chooseMicroArraysXHyperlink.setClickedColor(new java.awt.Color(0, 0, 0));
        chooseMicroArraysXHyperlink.setUnclickedColor(new java.awt.Color(0, 0, 0));
        chooseMicroArraysXHyperlink.setIcon(folder);
        chooseMicroArraysXHyperlink.setText("Micro array files");
        chooseMicroArraysXHyperlink.setFont(new java.awt.Font("Tahoma", 1, 11));
        chooseMicroArraysXHyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chooseMicroArraysXHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseMicroArraysXHyperlinkActionPerformed(evt);
            }
        });

        loadAffymetrixTargetFileXHyperlink.setClickedColor(new java.awt.Color(0, 0, 0));
        loadAffymetrixTargetFileXHyperlink.setUnclickedColor(new java.awt.Color(0, 0, 0));
        loadAffymetrixTargetFileXHyperlink.setIcon(folder);
        loadAffymetrixTargetFileXHyperlink.setText("Sample annotation file for Affymetrix");
        loadAffymetrixTargetFileXHyperlink.setFont(new java.awt.Font("Tahoma", 1, 11));
        loadAffymetrixTargetFileXHyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        jXHeader1.setDescription("For none affymetrics please select \"microarray data files\"" +
                " or \"sample annotation file (target file url here) or " +
                "\"phenotypic data file (url and richtiger name)\" for affymetrics.");

        targetFileChooser.setFileFilter(new RGGFileExtensionFilter("Target File", "txt"));

        arraysFileChooser.setDialogTitle("Microarrays");
        arraysFileChooser.setMultiSelectionEnabled(true);

        setLayout(new java.awt.BorderLayout());

        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        add(tabbedPane, java.awt.BorderLayout.CENTER);

        //#############Layout#################
        CellConstraints cc = new CellConstraints();

        JPanel hyperLinkPanel = new JPanel(new FormLayout("left:pref",
                "25dlu,pref,5dlu,pref,5dlu,pref"));

//        BoxLayout boxlayout = new BoxLayout(hyperLinkPanel, BoxLayout.Y_AXIS);        
//        hyperLinkPanel.setLayout(boxlayout);        
        hyperLinkPanel.add(chooseMicroArraysXHyperlink, cc.xy(1, 2));
        hyperLinkPanel.add(loadTargetFileXHyperlink, cc.xy(1, 4));
        hyperLinkPanel.add(loadAffymetrixTargetFileXHyperlink, cc.xy(1, 6));

        FormLayout inputPanelLayout =
                new FormLayout("center:pref",//cols
                "min,5dlu,pref");//rows

        DefaultFormBuilder builder = new DefaultFormBuilder(inputPanelLayout);
        builder.add(jXHeader1, cc.xy(1, 1));
//        builder.add(loadTargetFileXHyperlink, cc.xy(1, 3));
//        builder.add(chooseMicroArraysXHyperlink, cc.xy(1, 5));
        builder.add(hyperLinkPanel, cc.xy(1, 3));
        inputPanel = builder.getPanel();
        UnitConverter converter = DefaultUnitConverter.getInstance();
        inputPanel.setPreferredSize(new Dimension(converter.dialogUnitXAsPixel(300, inputPanel),
                converter.dialogUnitXAsPixel(200, inputPanel)));

        FormLayout mainLayout = new FormLayout("pref", "pref");
        setLayout(mainLayout);
        add(tabbedPane, cc.xy(1, 1));
//        setLayout(new BorderLayout());
//        add(tabbedPane);
    }

    private void setPanels() {
        arrayheaderrowselectionpanel.setModel(mamodel.getArrayHeaderRowTableModel());
        tabbedPane.setComponentAt(1, arrayheaderrowselectionpanel);
        targetFilePanel.setModel(mamodel.getTargetFileModel());
        rgListPanel.setModel(mamodel.getRGListTableModel());
        tabbedPane.setComponentAt(3, rgListPanel);
        
        tabbedPane.setEnabledAt(1, true);
        tabbedPane.setEnabledAt(2, true);
        tabbedPane.setEnabledAt(3, true);
    }

    private void loadTargetFileXHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        int status = targetFileChooser.showOpenDialog(loadTargetFileXHyperlink);
        if (status == JFileChooser.APPROVE_OPTION) {
            try {
                mamodel = createMAModel(1);
                mamodel.addPropertyChangeListener(this);
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }

    private void chooseMicroArraysXHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        int status = arraysFileChooser.showOpenDialog(chooseMicroArraysXHyperlink);
        if (status == JFileChooser.APPROVE_OPTION) {
            try {
                mamodel = createMAModel(2);
                mamodel.addPropertyChangeListener(this);
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

    private MAImporterModel createMAModel(final int mod)
            throws TargetFileException, ArrayDetectionException, IOException {
        final BusyDialog busy = new BusyDialog(null, true,
                "Recognizing arrays...", BusyDialog.ACTION.CANCEL);
        SwingWorker<MAImporterModel, Object> worker = new SwingWorker<MAImporterModel, Object>() {

            @Override
            protected MAImporterModel doInBackground() throws Exception {
                switch (mod) {
                    case 1:
                        return MAImporterModel.createModelFromTargetFile(targetFileChooser.getSelectedFile(), othercolumns);
                    case 2:
                        return MAImporterModel.createModelFromArrays(arraysFileChooser.getSelectedFiles(), othercolumns);
                    default:
                        return null;
                }
            }

            @Override
            protected void done() {
                super.done();
                busy.setVisible(false);
                try {
                    changeSupport.firePropertyChange(MAImporterModel.PROP_TargetFile, null, get().getTargetFile());
                } catch (InterruptedException ex) {
                    Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            //TODO ist es gute Idee hier null zurÃ¼ckzugeben???
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
        VTextIcon textIcon = new VTextIcon(tabbedPane, "Input Files", VTextIcon.ROTATE_LEFT);
        height += textIcon.getIconHeight();
        tabbedPane.addTab(null, textIcon, inputPanel);

        VTextIcon disabledTextIcon = new VTextIcon(tabbedPane, "Header Line", VTextIcon.ROTATE_LEFT);
        disabledTextIcon.setEnabled(false);
        textIcon = new VTextIcon(tabbedPane, "Header Line", VTextIcon.ROTATE_LEFT);
        height += textIcon.getIconHeight();        
        tabbedPane.addTab(null, textIcon, arrayheaderrowselectionpanel);
        tabbedPane.setDisabledIconAt(1, disabledTextIcon);
        tabbedPane.setEnabledAt(1, false);

        disabledTextIcon = new VTextIcon(tabbedPane, "Sample Annotation", VTextIcon.ROTATE_LEFT);
        disabledTextIcon.setEnabled(false);
        textIcon = new VTextIcon(tabbedPane, "Sample Annotation", VTextIcon.ROTATE_LEFT);
        tabbedPane.addTab(null, textIcon, targetFilePanel);
        tabbedPane.setDisabledIconAt(2, disabledTextIcon);
        tabbedPane.setEnabledAt(2, false);

        disabledTextIcon = new VTextIcon(tabbedPane, "Parameters", VTextIcon.ROTATE_LEFT);
        disabledTextIcon.setEnabled(false);
        textIcon = new VTextIcon(tabbedPane, "Parameters", VTextIcon.ROTATE_LEFT);
        height += textIcon.getIconHeight();
        rgListPanel = new RGListSettingsPanel(height);
        rgListPanel.addPropertyChangeListener(this);
        tabbedPane.addTab(null, textIcon, rgListPanel);
        tabbedPane.setDisabledIconAt(3, disabledTextIcon);
        tabbedPane.setEnabledAt(3, false);

        targetFilePanel.setPrefferedHeight(height); 
    }

    public void yapiste(final java.io.File in) {
        try {

            SwingWorker<MAImporterModel, Object> worker = new SwingWorker<MAImporterModel, Object>() {

                @Override
                protected MAImporterModel doInBackground() throws Exception {
                    return MAImporterModel.createModelFromArrays(new java.io.File[]{in}, null);
                }

                @Override
                protected void done() {
                    super.done();
                }
            };

            worker.execute();

            mamodel = worker.get();
            mamodel.addPropertyChangeListener(this);
            setPanels();
        } catch (InterruptedException ex) {
            Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MAImporterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                MAImporterPanel mapanel = new MAImporterPanel(null);
                mapanel.yapiste(input);
                f.setContentPane(mapanel);
                f.setSize(mapanel.getPreferredSize().width + 100, mapanel.getPreferredSize().height + 100);
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
    private org.jdesktop.swingx.JXHyperlink loadAffymetrixTargetFileXHyperlink;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JFileChooser targetFileChooser;
}
