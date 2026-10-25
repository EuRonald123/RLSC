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

        String resultContent = DesktopEntryBuilder.build(shortcut);

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
