package com.rnd.service;

import com.rnd.model.Shortcut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ShortcutCreatorService {
    public void createFileShortcut(Shortcut shortcut) throws IOException {
        String userHome = System.getProperty("user.home");
        String fileName = shortcut.name().toLowerCase().replace(" ", "_") + ".desktop";
        //Funciona concatenando varias strings para montar o path
        Path finalShortcutPath = Path.of(userHome, ".local/share/applications", fileName);

        String icon = "";
        if (shortcut.imagePath() != null) {
            icon = shortcut.imagePath().toString();
        }

        String category = "";
        if (shortcut.category() != null && !shortcut.category().isBlank()) {
            category = shortcut.category() + ";";
        }

        String content = """
                [Desktop Entry]
                Version=1.0
                Type=Application
                Name=%s
                Exec="%s"
                Icon=%s
                Categories=%s
                Terminal=false
                StartupNotify=true
                """.formatted(
                        shortcut.name(),
                        shortcut.programPath(),
                        icon,
                        category
        );

        Files.writeString(finalShortcutPath,
                content,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        finalShortcutPath.toFile().setExecutable(true);
    }
}
