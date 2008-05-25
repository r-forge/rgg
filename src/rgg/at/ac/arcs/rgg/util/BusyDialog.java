/*
 * BusyDialog.java
 *
 * Created on 23. April 2008, 12:56
 */
package at.ac.arcs.rgg.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author  ilhami
 */
public class BusyDialog extends javax.swing.JDialog {

    public enum ACTION {

        CONTINUE, CANCEL;

        public String toString() {
            if (this == CONTINUE) {
                return "Continue";
            } else {
                return "Cancel";
            }
        }
    }
    private ACTION actionClicked = null;

    /** Creates new form BusyDialog */
    public BusyDialog(java.awt.Frame parent, boolean modal, String busyMsg, ACTION... actions) {
        super(parent, modal);
        initComponents();
        label = new JXBusyLabel(new Dimension(100, 84));
        label.setText(busyMsg);
        busyLabelPanel.add(label,BorderLayout.CENTER);

        if (actions != null) {
            if (actions.length == 2) {
                button1.setText(actions[0].toString());
                button1.setActionCommand(actions[0].toString());

                button2.setText(actions[1].toString());
                button2.setActionCommand(actions[1].toString());
            } else if (actions.length == 1) {
                button1.setText(actions[0].toString());
                button1.setActionCommand(actions[0].toString());
                button2.setVisible(false);
            }
        }
    }

    public ACTION getActionClicked() {
        return actionClicked;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button1 = new javax.swing.JButton();
        button2 = new javax.swing.JButton();
        busyLabelPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        button1.setText("button1");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setText("button2");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        busyLabelPanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, busyLabelPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(button2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(button1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(busyLabelPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(button2)
                    .add(button1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
actionClicked = ACTION.valueOf(button1.getActionCommand().toUpperCase());
    dispose();
}//GEN-LAST:event_button1ActionPerformed

private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
actionClicked = ACTION.valueOf(button2.getActionCommand().toUpperCase());
    dispose();
}//GEN-LAST:event_button2ActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
actionClicked = ACTION.CANCEL;
    dispose();
}//GEN-LAST:event_formWindowClosing

    @Override
    public void setVisible(boolean b) {
        label.setBusy(b);
        super.setVisible(b);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                BusyDialog dialog = new BusyDialog(new javax.swing.JFrame(), 
                        true,"Checking...", ACTION.CONTINUE, ACTION.CANCEL);
                dialog.addWindowListener(new java.awt.event 

                      .WindowAdapter  
                        () {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    JXBusyLabel label;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel busyLabelPanel;
    private javax.swing.JButton button1;
    private javax.swing.JButton button2;
    // End of variables declaration//GEN-END:variables
}
