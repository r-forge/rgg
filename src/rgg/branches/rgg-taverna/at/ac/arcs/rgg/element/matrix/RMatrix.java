package at.ac.arcs.rgg.element.matrix;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;

/**
 *
 * @author ilhami
 */
public class RMatrix extends RElement {

    private static final String BINDINGPOINT = "matrix";
    private String var;
    private VMatrix vMatrix;
    private VisualComponent[][] visualcomponents;

    public RMatrix() {
    }

    public String getRCode() {
        StringBuffer sbuf = new StringBuffer();
        if (StringUtils.isNotBlank(var)) {
            sbuf.append(var + "=");
        }
        if (vMatrix.getColumnCount() == 0) {
            return sbuf.toString();
        }

        Object[] values;
        sbuf.append("matrix(c(");
        for (int i = 0; i < vMatrix.getColumnCount(); i++) {
            values = vMatrix.getValuesAtColumn(i);

            if (isNumeric(values)) {
                for (Object value : values) {
                    sbuf.append(value == null ? "" : value.toString() + ",");
                }
            } else {
                for (Object value : vMatrix.getValuesAtColumn(i)) {
                    sbuf.append('"');
                    sbuf.append(value == null ? "" : value.toString());
                    sbuf.append("\",");
                }
            }
        }
        sbuf.deleteCharAt(sbuf.length() - 1);
        sbuf.append("), ncol=" + vMatrix.getColumnCount() + ")");

        return sbuf.toString();
    }

    public void setVMatrix(VMatrix vMatrix) {
        this.vMatrix = vMatrix;
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vMatrix}};
        }
        return visualcomponents;
    }

    public boolean isChildAddable() {
        return false;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return vMatrix.getSwingComponents();
    }

    private boolean isNumeric(Object[] values) {
        for (Object v : values) {
            if (!(v instanceof Integer)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addInputPort(String portName, String bindTo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
