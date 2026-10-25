package com.rnd.service;

import com.rnd.model.Shortcut;

import java.nio.file.Path;

public class DesktopEntryBuilder {
    public static String build(Shortcut shortcut) {
        Path programPath = shortcut.programPath().toAbsolutePath();

        String exec = "\"" + programPath + "\"";
        if (shortcut.launchFlags() != null && !shortcut.launchFlags().isBlank()) {
            exec = shortcut.launchFlags().strip() + " " + exec;
        }

        StringBuilder content = new StringBuilder();
        content.append("[Desktop Entry]\n");
        content.append("Version=1.0\n");
        content.append("Type=Application\n");
        content.append("Name=").append(shortcut.name()).append("\n");
        content.append("Exec=").append(exec).append("\n");
        if (shortcut.imagePath() != null) {
            content.append("Icon=").append(shortcut.imagePath()).append("\n");
        } else {
            content.append("Icon=application-x-executable\n");
        }
        if (shortcut.category() != null) {
            content.append("Categories=").append(shortcut.category().getDesktopValue()).append(";\n");
        }
        content.append("Path=").append(programPath.getParent()).append("\n");
        content.append("Terminal=false\n");
        content.append("StartupNotify=true\n");

        return  content.toString();
    }
}
