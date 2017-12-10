package org.unistacks.utils;


import org.apache.log4j.Logger;

import java.io.*;

/**
 * Simple tool to execute Linux/Unix shell/command locally
 * 
 * @author cliff
 *
 */
public class SimpleScriptExecutor {

    private static final Logger log = Logger.getLogger(SimpleScriptExecutor.class);
    private static final String DEFAULT_SCRIPT_FOLDER ="";

    private SimpleScriptExecutor() {
    }

    /**
     * execute shell
     * 
     * @param scriptFolder
     * @param shellName
     * @param params
     *            separate multiple params with space
     * @return
     */
    public static String executeShell(String scriptFolder, String shellName, String params) {

        StringBuilder output = new StringBuilder("output: ");

        try {
            String command = parseCommand(scriptFolder, shellName, params);
            log.info("gonna execute command : " + command);
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = stdOut.readLine()) != null) {
                output.append(line);
            }

            int exitValue = process.waitFor();
            log.info("exit value is " + exitValue);

        } catch (InterruptedException e) {
            log.error("InterruptedException when executing shell: " + shellName + ", params: " + params, e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            log.error("IO error when executing shell: " + shellName + ", params: " + params, e);
        }

        return output.toString();
    }

    /**
     * execute shell located in default script folder
     * 
     * @param shellName
     * @param params
     * @return
     */
    public static String executeShell(String shellName, String params) {
        return executeShell(DEFAULT_SCRIPT_FOLDER, shellName, params);
    }

    private static String parseCommand(String scriptFolder, String shellName, String params) throws FileNotFoundException {

        File folderFile = new File(scriptFolder);
        if (!folderFile.isDirectory() || !folderFile.exists()) {
            throw new FileNotFoundException("script folder does not exist : " + scriptFolder);
        }

        String shellFullPath = scriptFolder + File.separator + shellName + ".sh";
        File shellFile = new File(shellFullPath);
        if (!shellFile.isFile() || !shellFile.exists()) {
            throw new FileNotFoundException("shell file does not exist : " + shellFullPath);
        }

        StringBuilder finalCommand = new StringBuilder("sh");
        finalCommand.append(" ").append(shellFullPath);
        finalCommand.append(" ").append(params);
        return finalCommand.toString();
    }


}
