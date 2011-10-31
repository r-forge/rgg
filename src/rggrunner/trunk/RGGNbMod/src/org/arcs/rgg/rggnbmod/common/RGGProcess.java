package org.arcs.rgg.rggnbmod.common;

import at.ac.arcs.rgg.RGG;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ilhami
 */
public class RGGProcess {

    private RGG rgg;
    private Process process;
    private static String rscriptCmdDir = "";
    private static String rscriptCmdName = "Rscript";
    private static String rscriptCmd = "Rscript";

    private RGGProcess(RGG rgg) {
        this.rgg = rgg;
    }

    public static RGGProcess newInstance(RGG rgg) throws IOException {
        RGGProcess rggP = new RGGProcess(rgg);
        rggP.createProcess();
        return rggP;
    }

    public static String getRscriptCmdDir() {
        return rscriptCmdDir;
    }

    public static void setRscriptCmdDir(String rscriptCmdDir) {
        RGGProcess.rscriptCmdDir = rscriptCmdDir;
        RGGProcess.rscriptCmd = rscriptCmdDir + "/" + rscriptCmdName;
    }

    public int startAndWait() throws InterruptedException {
        return process.waitFor();
    }

    public InputStream getInputStream() {
        return process.getInputStream();
    }

    public InputStream getErrorStream() {
        return process.getErrorStream();
    }

    private void createProcess() throws IOException {
        this.process = Runtime.getRuntime().exec(
                new String[]{rscriptCmd,
                    createTempRScript(rgg.generateRScript()).getAbsolutePath()
                });
    }

    private File createTempRScript(String code) throws IOException {
        File temp = File.createTempFile("RGG", ".R");
        temp.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
        writer.write(code);
        writer.flush();
        writer.close();
        return temp;
    }
}
