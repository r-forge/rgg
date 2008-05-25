/*
 * RGGComboBoxFactory.java
 *
 * Created on 12. November 2006, 01:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.combobox;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.perl.Perl5Util;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;

/**
 *
 * @author ilhami
 */
public class RGGComboBoxFactory extends RElementFactory {

    public RElement createRGGElement(Element element,RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        }
        
        Perl5Util util = new Perl5Util();
        
        VComboBox vComboBox = new VComboBox();
        RComboBox rComboBox = new RComboBox(vComboBox);

        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String items = element.getAttribute(RGG.getConfiguration().getString("ITEMS"));
        String selectedindex = element.getAttribute(RGG.getConfiguration().getString("SELECTED-INDEX"));
        String selecteditem = element.getAttribute(RGG.getConfiguration().getString("SELECTED-ITEM"));
        String datatype = element.getAttribute(RGG.getConfiguration().getString("DATA-TYPE"));
        String enabled = element.getAttribute(RGG.getConfiguration().getString("ENABLED"));
        /***********************************************************************************************/

        if (StringUtils.isNotBlank(var)) {
            rComboBox.setVar(var);
        }

        if (StringUtils.isNotBlank(label)) {
            vComboBox.setLabelText(label);
        }

        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                rComboBox.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN"))) {
                rComboBox.setColumnSpan(LayoutInfo.FULL_SPAN);
            } else {
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN") + " seems not to be a number: " + colspan + "nor a known keyword!");
            }
        }

        if (StringUtils.isNotBlank(items)) {
            rComboBox.setItems(StringUtils.split(items, ','));
        }

        if (StringUtils.isNotBlank(selectedindex)) {
            if (StringUtils.isNumeric(selectedindex)) {
                rComboBox.setSelectedIndex(Integer.parseInt(selectedindex));
            } else {
                throw new NumberFormatException(RGG.getConfiguration().getString("SELECTED-INDEX") + " seems not to be a number: " + selectedindex);
            }
        }

        if (StringUtils.isNotBlank(selecteditem)) {
            rComboBox.setSelectedItem(selecteditem);
        }

        if (StringUtils.isNotBlank(datatype)) {
            if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("NUMERIC"), datatype)) {
                rComboBox.setNumeric(true);
            }
        }
        
        if (StringUtils.isNotBlank(enabled)) {
            if (util.match("/(\\w+)\\./", enabled)) {
                String id = util.group(1);
                enabled = util.substitute("s/" + id + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                        rggInstance.getObject(id), // source of value
                        ELProperty.create(enabled), // the property to get
                        vComboBox, // the "backing bean"
                        BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }
        }

        return rComboBox;
    }
}