/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.ui;

import at.ac.arcs.rgg.RGG;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.SwingUtilities;
import org.arcs.rgg.rggnbmod.common.RGGProcess;
import org.arcs.rgg.rggnbmod.filetype.RGGEditorSupport;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.openide.windows.TopComponent;

public final class RunRGGAction extends CallableSystemAction {

    public RunRGGAction() {
        RGGProcess.setRscriptCmdDir(
                NbPreferences.root().node("org/arcs/rgg/rggnbmod").get("RSCRIPTCMDDIR", ""));
    }

    public void performAction() {
        RGG rgg=null;
        Node[] n = TopComponent.getRegistry().getActivatedNodes();
        if (n.length == 1) {
            EditorCookie ec = (EditorCookie) n[0].getCookie(EditorCookie.class);
            if (ec != null && ec instanceof RGGEditorSupport) {
                rgg=((RGGEditorSupport)ec).getEditingRGG();
            }
        }


//        RGGTopComponent rggTopComponent = RGGTopComponent.findInstance();
        if (rgg == null) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        try {
            setEnabled(false);
            final RGGProcess rggP = RGGProcess.newInstance(rgg);
            final InputOutput io = IOProvider.getDefault().getIO("R Output", false);
            io.select();

            final ProgressHandle progHandl = ProgressHandleFactory.createHandle("Running...");
            Thread mainTh = new Thread(new Runnable() {

                public void run() {
                    try {
                        progHandl.start();
                        new Thread(createOutputRunnable(io.getErr(), rggP.getErrorStream())).start();
                        new Thread(createOutputRunnable(io.getOut(), rggP.getInputStream())).start();

                        rggP.startAndWait();
                    } catch (InterruptedException ex) {
                        Exceptions.printStackTrace(ex);
                    } finally {
                        progHandl.finish();
                        io.getOut().write("\nFinished!\n");
                        SwingUtilities.invokeLater(new Runnable() {

                            public void run() {
                                RunRGGAction.this.setEnabled(true);
                            }
                        });
                    }
                }
            });
            mainTh.start();
        } catch (IOException ex) {
            DialogDisplayer.getDefault().
                    createDialog(createSimpleRscriptNotFoundDialogDescriptor()).
                    setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    RunRGGAction.this.setEnabled(true);
                }
            });
        }
    }

    private Runnable createOutputRunnable(final OutputWriter out, final InputStream inputStream) {
        return new Runnable() {

            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.write(line + "\n");
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        };
    }

    private DialogDescriptor createSimpleRscriptNotFoundDialogDescriptor() {
        return new DialogDescriptor(
                new ErrorDialogPane(),
                NbBundle.getMessage(RunRGGAction.class, "RunRGGAction_RscriptCMDNotFound_TITLE"));
    }

    private DialogDescriptor createRscriptNotFoundDialogDescriptor() {
        final RscriptCMDLocatorDialogPane rscriptLocatorPane = new RscriptCMDLocatorDialogPane();
        ActionListener finishButtonListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equalsIgnoreCase("finish")) {
                    RGGProcess.setRscriptCmdDir(
                            rscriptLocatorPane.getRscriptCMDDir().getAbsolutePath());
                }
            }
        };
        rscriptLocatorPane.getFinishButton().addActionListener(finishButtonListener);

        DialogDescriptor rscriptDialogDescriptor = new DialogDescriptor(
                rscriptLocatorPane,
                NbBundle.getMessage(RunRGGAction.class, "RunRGGAction_RscriptCMDNotFound_TITLE"),
                true,
                new Object[]{rscriptLocatorPane.getFinishButton()},
                rscriptLocatorPane.getFinishButton(),
                DialogDescriptor.BOTTOM_ALIGN,
                null,
                finishButtonListener);

        rscriptDialogDescriptor.setClosingOptions(null);

        return rscriptDialogDescriptor;
    }

    public String getName() {
        return NbBundle.getMessage(RunRGGAction.class, "CTL_RunRGGAction");
    }

    @Override
    protected String iconResource() {
        return "org/arcs/rgg/rggnbmod/resources/runRGG.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
