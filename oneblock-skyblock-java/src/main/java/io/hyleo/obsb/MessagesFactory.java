package io.hyleo.obsb;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class MessagesFactory {

    @NotNull
    public final TextColor RED = NamedTextColor.RED;
    @NotNull
    public final TextColor PURPLE = NamedTextColor.DARK_PURPLE;

    @NotNull
    public final Component WELL_BE_BACK = Component.text("Restarting -", NamedTextColor.RED)
            .append(Component.text(" We'll be back in a bit", NamedTextColor.AQUA));
    @NotNull
    public final TextComponent BED_ENTER = Component.text("Sleep is for the weak! The insomnia sets in...", RED);

    @NotNull
    public final TextComponent COMMAND_ONLY_PLAYERS = Component.text("This command can only be used by players.", RED);

    @NotNull
    public TextComponent commandRequiresArguments(@NonNull String... arguments) {
        for (var i = 0; i < arguments.length; i++) {
            arguments[i] = "<" + arguments[i] + ">";
        }
        return Component.text("This command requires " + arguments.length + " arguments" + String.join(" ", arguments), RED);
    }

    @NotNull
    public TextComponent playerNotFound(@NonNull String player) {
        return Component.text("Player \"" + player + "\" not found.", RED);
    }

    @NotNull
    public TextComponent playerNotInEmpire(@NonNull String player) {
        return Component.text("Player \"" + player + "\" is not in an empire.", RED);
    }
    @NonNull
    public TextComponent BLOCKS_MUST_BE_INT = Component.text("Blocks must be an integer.", RED);

    @NonNull
    public TextComponent blocksMustBeBetween(long max) {
        return Component.text("Blocks must be between 0  and " + max + ".", RED);
    }

}
