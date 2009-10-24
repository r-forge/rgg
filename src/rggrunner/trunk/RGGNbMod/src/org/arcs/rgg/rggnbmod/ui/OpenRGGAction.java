/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.ui;

import at.ac.arcs.rgg.util.RGGFileExtensionFilter;
import java.io.File;
import javax.swing.JFileChooser;
import org.apache.commons.lang.StringUtils;
import org.arcs.rgg.rggnbmod.filetype.RGGEditorSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.util.actions.CallableSystemAction;

public final class OpenRGGAction extends CallableSystemAction {

    private JFileChooser chooser;

    public OpenRGGAction() {
        String lastOpenedDir = NbPreferences.root().node("org/arcs/rgg/rggnbmod/ui").get(
                NbBundle.getMessage(OpenRGGAction.class, "LAST_OPENED_DIR"), "");
        if (StringUtils.isNotBlank(lastOpenedDir)) {
            File f = new File(lastOpenedDir);
            if (f.exists()) {
                chooser = new JFileChooser(f);
            } else {
                chooser = new JFileChooser();
            }
        } else {
            chooser = new JFileChooser();
        }
        chooser.setFileFilter(new RGGFileExtensionFilter("RGG File", "rgg"));
    }

    //TODO RGG.newInstance'i Swingworker'in icinde calistir
    public void performAction() {
        int returnVal = chooser.showOpenDialog(getToolbarPresenter());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            try {
//                RGGTopComponent rggWin = RGGTopComponent.findInstance();
//                rggWin.setRGG(RGG.newInstance(chooser.getSelectedFile()));
//                rggWin.open();
//                rggWin.requestActive();
//            } catch (ParserConfigurationException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (SAXException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (ClassNotFoundException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (InstantiationException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (IllegalAccessException ex) {
//                Exceptions.printStackTrace(ex);
//            }

            File f = chooser.getSelectedFile();
            FileObject fobj = FileUtil.toFileObject(f);
            DataObject dobj = null;
            try {
                dobj = DataObject.find(fobj);
                RGGEditorSupport es = dobj.getCookie(RGGEditorSupport.class);
                es.open();
            } catch (DataObjectNotFoundException ex) {
                ex.printStackTrace();
            }
            
            NbPreferences.root().node("org/arcs/rgg/rggnbmod/ui").put(
                NbBundle.getMessage(OpenRGGAction.class, "LAST_OPENED_DIR"), f.getParent());
        }
    }

    public String getName() {
        return NbBundle.getMessage(OpenRGGAction.class, "CTL_OpenRGGAction");
    }

    @Override
    protected String iconResource() {
        return "org/arcs/rgg/rggnbmod/resources/openRGG.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
