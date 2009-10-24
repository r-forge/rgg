/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.filetype;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.XMLDataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class RGGDataObject extends XMLDataObject implements Lookup.Provider {

    final InstanceContent ic;
    private AbstractLookup lookup;

    public RGGDataObject(FileObject pf, RGGDataLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
        ic.add(RGGEditorSupport.create(this));
        ic.add(this);
//        CookieSet cookies = getCookieSet();
//        cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));

    }

    protected Node createNodeDelegate() {
        DataNode n = new DataNode(this, Children.LEAF);
        n.setIconBaseWithExtension(
                "org/netbeans/modules/manifesteditorsupport/manifest.png");
        return n;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public Node.Cookie getCookie(Class type) {
        Object o = lookup.lookup(type);
        return o instanceof Node.Cookie ? (Node.Cookie) o : null;
    }
//    protected Node createNodeDelegate() {
//        return new RGGDataNode(this, getLookup());
//    }
//
//    public
//    @Override
//    Lookup getLookup() {
//        return getCookieSet().getLookup();
//    }
//
//    @Override
//    protected DesignMultiViewDesc[] getMultiViewDesc() {
//        return new DesignMultiViewDesc[]{new DesignView(this, TYPE_TOOLBAR)};
//    }
//
//    @Override
//    protected String getPrefixMark() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//    private static class DesignView extends DesignMultiViewDesc {
//
//        private int type;
//
//        DesignView(RGGDataObject dObj, int type) {
//            super(dObj, "Design");
//            this.type = type;
//        }
//
//        public org.netbeans.core.spi.multiview.MultiViewElement createElement() {
//            RGGDataObject dObj = (RGGDataObject) getDataObject();
//
//        }
//
//        public java.awt.Image getIcon() {
//            return org.openide.util.Utilities.loadImage("org/netbeans/modules/coolmultieditor/icon-16.png"); //NOI18N
//        }
//
//        public String preferredID() {
//            return "Toc_multiview_design" + String.valueOf(type);
//        }
//    }
//    private static final int TYPE_TOOLBAR = 0;
//
//    Toc getToc() {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
