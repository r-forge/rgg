/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.filetype;

import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

public class RGGDataNode extends DataNode {

    private static final String IMAGE_ICON_BASE = "org/arcs/rgg/rggnbmod/filetype/wi0063-16.gif";

    public RGGDataNode(RGGDataObject obj) {
        super(obj, Children.LEAF);
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

    RGGDataNode(RGGDataObject obj, Lookup lookup) {
        super(obj, Children.LEAF, lookup);
        setIconBaseWithExtension(IMAGE_ICON_BASE);
    }

//    /** Creates a property sheet. */
//    @Override
//    protected Sheet createSheet() {
//        Sheet s = super.createSheet();
//        Sheet.Set ss = s.get(Sheet.PROPERTIES);
//        if (ss == null) {
//            ss = Sheet.createPropertiesSet();
//            s.put(ss);
//        }
//        // TODO add some relevant properties: ss.put(...)
//        return s;
//    }
}
