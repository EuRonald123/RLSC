package com.rnd.model;

import java.nio.file.Path;

//Vai ser os dados necessários para criar o meu atálho
public record Shortcut(
    String name,
    Path programPath,
    Path imagePath,
    String category
) {}
