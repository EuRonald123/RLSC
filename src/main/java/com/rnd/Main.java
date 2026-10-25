package com.rnd;

import com.rnd.model.Category;
import com.rnd.model.Shortcut;
import com.rnd.service.ShortcutCreatorService;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        ShortcutCreatorService service = new ShortcutCreatorService();

        // Criando um atalho de teste (ex: abrindo a calculadora ou editor de texto)
        Shortcut teste = new Shortcut(
                "Meu Atalho Teste",
                Path.of("/mnt/Bau/Programas/uad-ng-linux"), // ou outro caminho válido no seu PC
                null, // sem ícone por enquanto
                Category.UTILITY,
                "game-performance prime-run mangohud"
        );

        try {
            service.createFileShortcut(teste);
            System.out.println("Deu bom! Olhe lá na pasta ~/.local/share/applications/");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}