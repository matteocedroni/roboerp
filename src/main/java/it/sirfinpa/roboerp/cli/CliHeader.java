package it.sirfinpa.roboerp.cli;


import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class CliHeader {

    public static String buildApplicationHeader() throws IOException {
        Manifest manifest = loadManifest();
        StringBuilder header = new StringBuilder("RoboErp v. ")
                .append(manifest.getMainAttributes().getValue("Implementation-Version"))
                .append(" (built on ")
                .append(manifest.getMainAttributes().getValue("Built-on"))
                .append(")")
                .append("\n------------------------------------------");
        return header.toString();
    }

    public static void showHeader() throws IOException {
        System.out.println(buildApplicationHeader());
    }

    public static Manifest loadManifest() throws IOException {
        return new JarFile(new File(CliHeader.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getManifest();
    }

}
