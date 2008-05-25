/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui;

import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JScrollPane;
import at.ac.arcs.rgg.element.maimporter.array.Array;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputSelectorTable;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author ilhami
 */
public class RGListSettingsPanelIIII extends javax.swing.JPanel {

    private Array array;
    private JXTable table;
    private InputSelectorTable inputSelectorTable;
    private JScrollPane inputSelectorScrollPane;
    private JScrollPane tableScrollPane;

    public RGListSettingsPanelIIII() {
        initComponents();
    }

    private void initComponents() {
        FormLayout layout = new FormLayout("pref:grow",
                "");
    }
}
