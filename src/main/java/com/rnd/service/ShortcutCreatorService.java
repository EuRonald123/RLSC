package com.rnd.service;

import com.rnd.model.Shortcut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.concurrent.TimeUnit;

public class ShortcutCreatorService {
    public void createFileShortcut(Shortcut shortcut, boolean makeExecutable) throws IOException {

        if (!Files.isRegularFile(shortcut.programPath())) {
            throw new  IllegalArgumentException("Invalid executable: file does not exist!");
        }

        if(shortcut.imagePath() != null && !Files.isRegularFile(shortcut.imagePath())) {
            throw new  IllegalArgumentException("Invalid icon: file does not exist or is a directory!");
        }

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

        if (makeExecutable) {
            try {
                Files.setPosixFilePermissions(shortcut.programPath(), PosixFilePermissions.fromString("rwxr-xr-x"));
            }catch (IOException e) {
                throw new IOException("Could not set executable permission (+x) on file. Check if you own this file!", e);
            }

        }

        updateSystemMenu(finalShortcutPath.getParent());
    }

    private void updateSystemMenu(Path applicationsFolder) {
        runCommand("update-desktop-database", applicationsFolder.toString());
        runCommand("kbuildsycoca6", "--noincremental");
    }

    private void runCommand(String... command) {
        try {
            new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .start()
                    .waitFor(5, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            System.out.println("Aviso ao atualizar menu: " + e.getMessage());
        }
    }
}
