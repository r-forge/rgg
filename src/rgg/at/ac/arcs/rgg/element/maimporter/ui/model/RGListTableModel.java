package at.ac.arcs.rgg.element.maimporter.ui.model;

import at.ac.arcs.rgg.element.maimporter.array.Array;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ilhami
 */
public class RGListTableModel extends AbstractTableModel {

    private Array array;
    // contains the array header line
    private List<String> colNames = new ArrayList<String>();    // arraylist of arraylists
    private List<List<String>> data = new ArrayList<List<String>>();

    public RGListTableModel(Array array) throws IOException {
        this.array = array;
        colNames.addAll(array.getHeaders());
        data.addAll(array.readAssayData(50));
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return colNames.size();
    }

    @Override
    public String getColumnName(int col) {
        return colNames.get(col);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        List rowList = data.get(rowIndex);
        String result = null;
        if (columnIndex < rowList.size()) {
            result = rowList.get(columnIndex).toString();
        }
// _apparently_ it's ok to return null for a "blank" cell
        return (result);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setArray(Array array) {
        this.array = array;
    }

    public Array getArray() {
        return array;
    }
    
    public InputList getInputList(){
        InputList ilist = new InputList();
        ilist.put(array.getG());
        ilist.put(array.getGb());
        ilist.put(array.getR());
        ilist.put(array.getRb());
        ilist.put(array.getAnnotations());
        
        return ilist;
    }
}
