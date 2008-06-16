package at.ac.arcs.rgg.element.maimporter.ui.model;

import at.ac.arcs.rgg.element.maimporter.array.Array;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ilhami
 */
public class RGListTableModel extends AbstractTableModel implements PropertyChangeListener {

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private ArrayList<Integer> columns = new ArrayList<Integer>();
    private ArrayList<Integer> annotations;
    private ArrayList<Integer> othercolumns = new ArrayList<Integer>();
    private InputList ilist;
    private Array array;
    // contains the array header line
    private List<String> colNames = new ArrayList<String>();    // arraylist of arraylists
    private List<List<String>> data = new ArrayList<List<String>>();

    public RGListTableModel(Array array) throws IOException {
        this.array = array;
        ilist = new InputList();
        ilist.put(array.getG());
        array.getG().addPropertyChangeListener(this);
        ilist.put(array.getGb());
        array.getGb().addPropertyChangeListener(this);
        ilist.put(array.getR());
        array.getR().addPropertyChangeListener(this);
        ilist.put(array.getRb());
        array.getRb().addPropertyChangeListener(this);
        ilist.put(array.getAnnotations());
        array.getAnnotations().addPropertyChangeListener(this);
        annotations = array.getAnnotations().getColumns();

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

    public InputList getInputList() {
        return ilist;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Array.PROP_Annotation)) {
            changeSupport.firePropertyChange(Array.PROP_Annotation, evt.getOldValue(), evt.getNewValue());
        } else {
            System.out.println("1");
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
}
