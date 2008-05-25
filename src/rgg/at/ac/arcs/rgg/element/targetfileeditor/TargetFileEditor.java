/*
 * TargetFileEditor.java
 *
 * Created on 27. Januar 2008, 14:19
 */
package at.ac.arcs.rgg.element.targetfileeditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.util.tablerowheader.LineNumberTable;
import at.ac.arcs.rgg.util.tablerowheader.RGGFileExtensionFilter;
import org.jdesktop.swingworker.SwingWorker;

/**
 *
 * @author  ilhami
 */
class TargetFileEditor extends javax.swing.JPanel {

    private final static String LASTUSEDDIR = "lastuseddir";
    private TargetFileTableModel model;
    private RGG rggInstance;

    /** Creates new form TargetFileEditor */
    public TargetFileEditor(RGG rggInstance) {
        this.rggInstance = rggInstance;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        targetFileEditorPanel = new javax.swing.JPanel();
        rowLabel = new javax.swing.JLabel();
        columnLabel = new javax.swing.JLabel();
        browseButton = new javax.swing.JButton();
        addRowButton = new javax.swing.JButton();
        removeRowButton = new javax.swing.JButton();
        addColumnButton = new javax.swing.JButton();
        removeColumnButton = new javax.swing.JButton();
        tableScrollPane = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();

        fileChooser.setFileFilter(new RGGFileExtensionFilter("Target file", new String[]{"txt"}));

        rowLabel.setText("Rows:");

        columnLabel.setText("Columns:");

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        addRowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arcs/rgg/element/targetfileeditor/icons/add-small.png"))); // NOI18N
        addRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowButtonActionPerformed(evt);
            }
        });

        removeRowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arcs/rgg/element/targetfileeditor/icons/remove-small.png"))); // NOI18N
        removeRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowButtonActionPerformed(evt);
            }
        });

        addColumnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arcs/rgg/element/targetfileeditor/icons/add-small.png"))); // NOI18N
        addColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addColumnButtonActionPerformed(evt);
            }
        });

        removeColumnButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arcs/rgg/element/targetfileeditor/icons/remove-small.png"))); // NOI18N
        removeColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeColumnButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout targetFileEditorPanelLayout = new org.jdesktop.layout.GroupLayout(targetFileEditorPanel);
        targetFileEditorPanel.setLayout(targetFileEditorPanelLayout);
        targetFileEditorPanelLayout.setHorizontalGroup(
            targetFileEditorPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, targetFileEditorPanelLayout.createSequentialGroup()
                .add(rowLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addRowButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(removeRowButton)
                .add(21, 21, 21)
                .add(columnLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addColumnButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(removeColumnButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 80, Short.MAX_VALUE)
                .add(browseButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        targetFileEditorPanelLayout.setVerticalGroup(
            targetFileEditorPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, targetFileEditorPanelLayout.createSequentialGroup()
                .add(targetFileEditorPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(browseButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(columnLabel))
                .add(117, 117, 117))
            .add(targetFileEditorPanelLayout.createSequentialGroup()
                .add(rowLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
            .add(targetFileEditorPanelLayout.createSequentialGroup()
                .add(addRowButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(targetFileEditorPanelLayout.createSequentialGroup()
                .add(removeRowButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(targetFileEditorPanelLayout.createSequentialGroup()
                .add(addColumnButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(targetFileEditorPanelLayout.createSequentialGroup()
                .add(removeColumnButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jxTable = new JTable();
        //jxTable = new JXTable();
        //jxTable.setHighlighters(new Highlighter[]{ HighlighterFactory.createAlternateStriping()});
        //jxTable.setSortable(false);
        //jxTable.setHorizontalScrollEnabled(true);
        //jxTable.setAutoStartEditOnKeyStroke(true);
        jxTable.setCellSelectionEnabled(true);
        jxTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Use \"Browse\" button to load a target file");
        tableScrollPane.setViewportView(jLabel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(targetFileEditorPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(tableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(tableScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(targetFileEditorPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed

        fileChooser.setCurrentDirectory((File) rggInstance.getProperty(LASTUSEDDIR));
        int status = fileChooser.showOpenDialog((JButton) evt.getSource());
        if (status == JFileChooser.APPROVE_OPTION) {
            try {
                rggInstance.setProperty(LASTUSEDDIR, fileChooser.getSelectedFile().getParentFile());
                setTableModel(fileChooser.getSelectedFile());
            } catch (IOException ex) {
                Logger.getLogger(TargetFileEditor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void addColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addColumnButtonActionPerformed
        if (model == null) {
            return;
        }

        String colName = JOptionPane.showInputDialog("Name of the new column?");
        if (StringUtils.isNotBlank(colName)) {
            model.addColumn(colName);
        }
    }//GEN-LAST:event_addColumnButtonActionPerformed

    private void removeColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeColumnButtonActionPerformed
        if (jxTable.getSelectedColumn() == -1 || model == null) {
            return;
        }
        model.removeColumnAndColumnData(jxTable.getSelectedColumn());
    }//GEN-LAST:event_removeColumnButtonActionPerformed

    private void addRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowButtonActionPerformed
        if (model == null) {
            return;
        }
        model.addRow(new Object[]{});
    }//GEN-LAST:event_addRowButtonActionPerformed

    private void removeRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRowButtonActionPerformed
        if (jxTable.getSelectedRow() == -1 || model == null) {
            return;
        }
        model.removeRow(jxTable.getSelectedRow());
    }//GEN-LAST:event_removeRowButtonActionPerformed

    public DefaultTableModel getTableModel() {
        return model;
    }

    public String[] getColumnTitles() {
        String[] titles = new String[jxTable.getColumnCount()];
        for (int i = 0; i < jxTable.getColumnCount(); i++) {
            titles[i] = model.getColumnName(i);
        }
        return titles;
    }

    private void setTableModel(final File targetFile) throws IOException {

        SwingWorker<TargetFileTableModel, Object> worker = new SwingWorker<TargetFileTableModel, Object>() {

            @Override
            protected TargetFileTableModel doInBackground() {
                TargetFileTableModel model = new TargetFileTableModel();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(targetFile));
                    boolean isColumnsSet = false;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (StringUtils.isNotBlank(line)) {
                            if (isColumnsSet) {
                                model.addRow(StringUtils.split(line));
                            } else {
                                String[] cols = StringUtils.split(line);
                                for (String col : cols) {
                                    model.addColumn(col);
                                }
                                isColumnsSet = true;
                            }
                        }
                    }
                    return model;
                } catch (IOException ex) {
                    Logger.getLogger(TargetFileEditor.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TargetFileEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    model = get();
                    if (model != null) {
                        jxTable.setModel(model);
                        LineNumberTable rowheader = new LineNumberTable(jxTable);
                        if (tableScrollPane.getViewport().getView() == jLabel1) {
                            tableScrollPane.setViewportView(jxTable);
                            tableScrollPane.setRowHeaderView(rowheader);
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(TargetFileEditor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(TargetFileEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        worker.execute();

    }
//    private JXTable jxTable;
    private JTable jxTable;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addColumnButton;
    private javax.swing.JButton addRowButton;
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel columnLabel;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton removeColumnButton;
    private javax.swing.JButton removeRowButton;
    private javax.swing.JLabel rowLabel;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JPanel targetFileEditorPanel;
    // End of variables declaration//GEN-END:variables
}
