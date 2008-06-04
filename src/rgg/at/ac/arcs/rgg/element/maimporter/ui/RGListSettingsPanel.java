package at.ac.arcs.rgg.element.maimporter.ui;

import at.ac.arcs.rgg.element.maimporter.ui.inputselection.AdjustmentController;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputSelectorTable;
import at.ac.arcs.rgg.element.maimporter.ui.model.RGListTableModel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.util.DefaultUnitConverter;
import com.jgoodies.forms.util.UnitConverter;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author ilhami
 */
public class RGListSettingsPanel extends javax.swing.JPanel {

    private JXTable table;
    private InputSelectorTable inputSelectorTable;
    private RGListTableModel model;
    private UnitConverter converter = DefaultUnitConverter.getInstance();
    private int height=50;
    /** Creates new form RGListSettingsPanel */
    public RGListSettingsPanel() {
        initComponents();
    }

    public RGListSettingsPanel(int height) {
        this.height=height+15;
        initComponents();
    }
    
    public void setModel(RGListTableModel model) {
        this.model = model;
        table.setModel(model);
        inputSelectorTable.setOptions(model.getInputList());
    }
    
    public String getRHeader() {
        return inputSelectorTable.getColumnName(model.getArray().getR().getFirstColumn());
    }

    public String getRbHeader() {
        return inputSelectorTable.getColumnName(model.getArray().getRb().getFirstColumn());
    }

    public String getGHeader() {
        return inputSelectorTable.getColumnName(model.getArray().getG().getFirstColumn());
    }

    public String getGbHeader() {
        return inputSelectorTable.getColumnName(model.getArray().getGb().getFirstColumn());
    }

    public List<String> getAnnotationHeaders() {
        ArrayList<String> anns = new ArrayList<String>();
        for (Integer i : model.getArray().getAnnotations().getColumns()) {
            anns.add(inputSelectorTable.getColumnName(i));
        }
        return anns;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jXHeader1 = new org.jdesktop.swingx.JXHeader();
        inputSelectorScrollPane = new javax.swing.JScrollPane();
        tableScrollPane = new javax.swing.JScrollPane();

        jXHeader1.setDescription("Description");
        jXHeader1.setTitle("Panel 5");

        inputSelectorScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        inputSelectorScrollPane.getVerticalScrollBar().setEnabled(false);
        inputSelectorScrollPane.setBorder(null);
//        inputSelectorScrollPane.setVerticalScrollBar(new javax.swing.JScrollBar() {
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//
//            @Override
//            public boolean isVisible() {
//                return false;
//            }
//        });

        table = new JXTable();
        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
        table.setColumnControlVisible(true);
        
        tableScrollPane.setViewportView(table);
        tableScrollPane.setBorder(null);
        tableScrollPane.setPreferredSize(new Dimension(table.getPreferredScrollableViewportSize().width, height));
        
        inputSelectorTable = new InputSelectorTable(table);        
        table.setHorizontalScrollEnabled(true);
        inputSelectorScrollPane.setPreferredSize(
                new Dimension(inputSelectorTable.getPreferredScrollableViewportSize().width,
                converter.dialogUnitXAsPixel(inputSelectorTable.getFont().getSize() + 4, inputSelectorTable)));
        inputSelectorScrollPane.setViewportView(inputSelectorTable);
                
        AdjustmentController controller = new AdjustmentController();
        controller.registerScrollPane(tableScrollPane);
        controller.registerScrollPane(inputSelectorScrollPane);
        
        FormLayout layout = new FormLayout("fill:min:grow",//cols
                "min,2dlu,pref:grow,fill:pref:grow");
        setLayout(layout);
        CellConstraints cc = new CellConstraints();

        add(jXHeader1, cc.xy(1, 1));
        add(inputSelectorScrollPane, cc.xy(1, 3));
        add(tableScrollPane, cc.xy(1, 4));
    }
    private javax.swing.JScrollPane inputSelectorScrollPane;
    private org.jdesktop.swingx.JXHeader jXHeader1;
    private javax.swing.JScrollPane tableScrollPane;
}
