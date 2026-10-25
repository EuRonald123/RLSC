package com.rnd.model;

import java.nio.file.Path;

public record Shortcut(
    String name,
    Path programPath,
    Path imagePath,
    String category,
    String launchFlags
) {}
