import com.rnd.model.Shortcut;
import com.rnd.service.DesktopEntryBuilder;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DesktopEntryBuilderTest {


    // BASE
    @Test
    void startsWithDesktopEntryHeader() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);

        assertTrue(resultado.startsWith("[Desktop Entry]\n"));
        assertTrue(resultado.contains("Type=Application\n"));
    }

    @Test
    void writesNameAsTyped() {
        Shortcut shortcut = new Shortcut(
                "Meu Jogo (v2)!",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);

        assertTrue(resultado.contains("Name=Meu Jogo (v2)!\n"));
    }

    // ICON

    @Test
    void usesImagePathWhenProvided() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                Path.of("/home/user/icone.png"),
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertTrue(resultado.contains("Icon=/home/user/icone.png\n"));
        assertFalse(resultado.contains("application-x-executable"));
    }

    @Test
    void defaultIconWhenImageIsNull(){
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertTrue(resultado.contains("Icon=application-x-executable\n"));
    }

    // EXEC

    @Test
    void usingFlags() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                "game-performance prime-run mangohud"
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertTrue(resultado.contains("Exec=game-performance prime-run mangohud \"/home/user/jogo.AppImage\"\n"));
    }

    @Test
    void execHasOnlyQuotedPathWhenFlagsAreNull() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertTrue(resultado.contains("Exec=\"/home/user/jogo.AppImage\"\n"));
    }

    @Test
    void execIgnoresBlankFlags() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                "    "
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertTrue(resultado.contains("Exec=\"/home/user/jogo.AppImage\"\n"));
    }

    @Test
    void execStripsSpacesAroundFlags() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                "   prime-run mangohud   "
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertTrue(resultado.contains("Exec=prime-run mangohud \"/home/user/jogo.AppImage\"\n"));
    }

    @Test
    void execKeepsPathWithSpacesInsideQuotes() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/Meus Jogos/jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);

        assertTrue(resultado.contains("Exec=\"/home/user/Meus Jogos/jogo.AppImage\"\n"));
    }

    @Test
    void execUsesAbsolutePathWhenRelativeIsGiven() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);

        assertFalse(resultado.contains("Exec=\"jogo.AppImage\""));
        assertTrue(resultado.contains("/jogo.AppImage\"\n"));
    }

    // CATEGORIES

    @Test
    void categoryEndsWithSemicolon() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertTrue(resultado.contains("Categories=Game;\n"));
    }

    @Test
    void omitsCategoryWhenNull() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                null,
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertFalse(resultado.contains("Categories="));
    }

    @Test
    void omitsCategoryWhenBlank() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/jogo.AppImage"),
                null,
                "   ",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertFalse(resultado.contains("Categories="));
    }

    // PATH

    @Test
    void pathIsTheProgramFolder() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("/home/user/Meus Jogos/jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        // Path= não usa aspas, mesmo com espaços
        assertTrue(resultado.contains("Path=/home/user/Meus Jogos\n"));
    }

    @Test
    void pathIsNeverNullForRelativeProgram() {
        Shortcut shortcut = new Shortcut(
                "My shortcut",
                Path.of("jogo.AppImage"),
                null,
                "Game",
                null
        );

        String resultado = DesktopEntryBuilder.build(shortcut);
        assertFalse(resultado.contains("Path=null"));
    }
}
