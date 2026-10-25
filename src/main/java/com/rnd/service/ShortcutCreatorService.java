package com.rnd.service;

import com.rnd.model.Shortcut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;

public class ShortcutCreatorService {
    public void createFileShortcut(Shortcut shortcut) throws IOException {
        String userHome = System.getProperty("user.home");
        String fileName = shortcut.name().toLowerCase().replaceAll("[^a-z0-9_-]", "_") + ".desktop";
        Path finalShortcutPath = Path.of(userHome, ".local/share/applications", fileName);
        Path programPath = shortcut.programPath().toAbsolutePath();

        String exec = "\"" + programPath + "\"";
        if(shortcut.launchFlags() != null && !shortcut.launchFlags().isBlank()) {
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
        }
        if (shortcut.category() != null && !shortcut.category().isBlank()){
            content.append("Categories=").append(shortcut.category()).append("\n");
        }
        content.append("Path=").append(programPath.getParent()).append("\n");
        content.append("Terminal=false\n");
        content.append("StartupNotify=true\n");

        String resultContent = content.toString();


        Files.createDirectories(finalShortcutPath.getParent());

        Files.writeString(
                finalShortcutPath,
                resultContent,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        Files.setPosixFilePermissions(finalShortcutPath, PosixFilePermissions.fromString("rwxr-xr-x"));
    }
}
