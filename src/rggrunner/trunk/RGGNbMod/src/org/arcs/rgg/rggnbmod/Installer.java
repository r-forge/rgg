/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod;

import ca.beq.util.win32.registry.RegistryException;
import ca.beq.util.win32.registry.RegistryKey;
import ca.beq.util.win32.registry.RegistryValue;
import ca.beq.util.win32.registry.RootKey;
import java.io.File;
import java.util.Iterator;
import org.arcs.rgg.rggnbmod.common.RGGProcess;
import org.openide.modules.ModuleInstall;
import org.openide.util.NbPreferences;
import org.openide.util.Utilities;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // By default, do nothing.
        // Put your startup code here.
        if (Utilities.isWindows()) {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        String newestRversion = "";
                        RegistryKey newestR = null;
                        RegistryKey r = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "SOFTWARE\\R-core\\R");
                        if (r.hasSubkeys()) {
                            Iterator i = r.subkeys();
                            while (i.hasNext()) {
                                RegistryKey x = (RegistryKey) i.next();
                                if (x.getName().compareTo(newestRversion) > 0) {
                                    newestRversion = x.getName();
                                    newestR = x;
                                }
                                if (x.hasValue("InstallPath")) {
                                    RegistryValue v = x.getValue("InstallPath");
                                }
                            }
                        }
                        if (newestR.hasValue("InstallPath")) {
                            RegistryValue v = newestR.getValue("InstallPath");
                            NbPreferences.root().node("org/arcs/rgg/rggnbmod").put("RSCRIPTCMDDIR", v.getData().toString() + "\\bin");
                            RGGProcess.setRscriptCmdDir(NbPreferences.root().node("org/arcs/rgg/rggnbmod").get("RSCRIPTCMDDIR", ""));
                        }
                    } catch (RegistryException e) {
                    }
                }
            }).start();
        } else if (Utilities.isMac()) {
            String path1 = "/Library/Frameworks/R.framework/Resources/bin";
            String path2 = "/Library/Frameworks/R.framework/Versions";

            if (lookAtForRscript(path1)) {
                NbPreferences.root().node("org/arcs/rgg/rggnbmod").put("RSCRIPTCMDDIR", path1);
                RGGProcess.setRscriptCmdDir(path1);
            } else {
                File[] versions = new File(path2).listFiles();
                String latestVersion = "";
                for (File version : versions) {
                    if (version.getName().matches("\\d\\.\\d")) {
                        if (latestVersion.compareToIgnoreCase(version.getName()) < 0) {
                            latestVersion = version.getName();
                        }
                    }
                }
                if (lookAtForRscript(path2 + "/" + latestVersion + "/Resources/bin")) {
                    NbPreferences.root().node("org/arcs/rgg/rggnbmod").put("RSCRIPTCMDDIR", path2 + "/" + latestVersion + "/Resources/bin");
                    RGGProcess.setRscriptCmdDir(path2 + "/" + latestVersion + "/Resources/bin");
                }
            }
        } else if (Utilities.isUnix()) {
            new Thread(new Runnable() {

                public void run() {
                    String[] paths = {"/bin", "/sbin", "/usr/bin", "/usr/sbin"};
                    for (String path : paths) {
                        if (lookAtForRscript(path)) {
                            NbPreferences.root().node("org/arcs/rgg/rggnbmod").put("RSCRIPTCMDDIR", path);
                            RGGProcess.setRscriptCmdDir(path);
                            break;
                        }
                    }
                }
            }).start();

        }
    }

    private boolean lookAtForRscript(String path) {
        File bin = new File(path);
        File[] files = bin.listFiles();
        for (File f : files) {
            if (f.getName().equals("Rscript")) {
                return true;
            }
        }
        return false;
    }
}
