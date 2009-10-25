/*
 * RGGMenu.java
 *
 * Created on 26. November 2006, 23:17
 */

package at.ac.arcs.rgg.jgr;

import at.ac.arcs.rgg.util.RGGFileExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosuda.JGR.JGR;

/**
 *
 * @author ilhami
 */
public class RGGMenu extends JMenu {
    
    private static JFileChooser chooser = new JFileChooser();
    private static RGGFileExtensionFilter filter = new RGGFileExtensionFilter("RGG File", "rgg");
    private Log log = LogFactory.getLog(RGGMenu.class);
    private JMenuItem mi;
    
    /** Creates a new instance of RGGMenu */
    public RGGMenu() {
        super("RGG");
        chooser.setFileFilter(filter);
        mi = new JMenuItem("Load...");
        mi.addActionListener(new ActionListener() {            
            public void actionPerformed(ActionEvent e){
                    load();
            }
        });
        add(mi);
    }
    
    public void load(){
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            log.info("Loading file " + chooser.getSelectedFile().getPath());
            try {
                new RGGDialog(chooser.getSelectedFile(),JGR.MAINRCONSOLE,false).setVisible(true);
            } catch (Exception ex) {
                log.fatal("Can't create RGGWindow Object:",ex);
            }
        }
    }
    
    public static String addRGGMenu() {
        try {
            JGR.MAINRCONSOLE.getJMenuBar().add(new RGGMenu());
        } catch (Exception rex) {
            String s = "";
            for (StackTraceElement el : rex.getStackTrace()) {
                s += el.toString() + "\n";
            }
            return s;
        }
        return "normal";
    }
}