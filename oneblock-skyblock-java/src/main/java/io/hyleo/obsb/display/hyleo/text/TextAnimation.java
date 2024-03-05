package io.hyleo.obsb.display.hyleo.text;

import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;
import java.util.function.Supplier;

@Builder(buildMethodName = "create")
@Data
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class TextAnimation {

    @Default
    boolean inverse = false;

    @Default
    @NonNull
    Supplier<String> text = () -> "";

    @NonNull
    Palette palette;

    @Singular
    @NonNull
    List<TextDecoration> decorations;

    @Default
    Pattern pattern = Pattern.FLASH;

    @Default
    Supplier<TextComponent> preText = Component::empty, subText = Component::empty;


    public int colors() {
        return palette().size();
    }

    public TextColor color(int color) {

        return palette().color(color);
    }

    public static TextAnimation none() {
        return TextAnimation.builder().palette(Palette.singular(NamedTextColor.GRAY)).create();
    }

    public static TextAnimation of(TextColor color, Supplier<TextComponent> preText, Supplier<String> text,
                                   Supplier<TextComponent> subText) {

        return of(Pattern.FLASH, Palette.singular(color), false, preText, text, subText);
    }

    public static TextAnimation of(Pattern pattern, Palette palette, Supplier<String> text,
                                   TextDecoration... decoration) {

        return of(pattern, palette, false, text, decoration);
    }

    public static TextAnimation of(Pattern pattern, Palette palette, Supplier<TextComponent> preText,
                                   Supplier<String> text, TextDecoration... decoration) {

        return of(pattern, palette, false, preText, text, decoration);
    }

    public static TextAnimation of(Pattern pattern, Palette palette, Supplier<TextComponent> preText,
                                   Supplier<String> text, Supplier<TextComponent> subText, TextDecoration... decoration) {

        return of(pattern, palette, false, preText, text, subText, decoration);
    }

    public static TextAnimation of(Pattern pattern, Palette palette, boolean inverse, Supplier<String> text,
                                   TextDecoration... decoration) {

        return builder().pattern(pattern).palette(palette).inverse(inverse).text(text).decorations(List.of(decoration))
                .create();
    }

    public static TextAnimation of(Pattern pattern, Palette palette, boolean inverse, Supplier<TextComponent> preText,
                                   Supplier<String> text, TextDecoration... decoration) {

        return of(pattern, palette, inverse, preText, text, () -> Component.text(""), decoration);
    }

    public static TextAnimation of(Pattern pattern, Palette palette, boolean inverse, Supplier<TextComponent> preText,
                                   Supplier<String> text, Supplier<TextComponent> subText, TextDecoration... decoration) {

        return builder().pattern(pattern).palette(palette).inverse(inverse).preText(preText).text(text).subText(subText)
                .decorations(List.of(decoration)).create();
    }

    public static TextAnimation buildRight(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.BUILD, palette, false, text, decoration);
    }

    public static TextAnimation buildLeft(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.BUILD, palette, true, text, decoration);
    }

    public static TextAnimation build(Palette palette, boolean inverse, Supplier<String> text,
                                      TextDecoration... decoration) {
        return of(Pattern.BUILD, palette, inverse, text, decoration);
    }

    public static TextAnimation dialate(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.DILATE, palette, text, decoration);
    }

    public static TextAnimation flash(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.FLASH, palette, text, decoration);
    }

    public static TextAnimation replaceRight(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.REPLACE, palette, false, text, decoration);
    }

    public static TextAnimation replaceLeft(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.REPLACE, palette, true, text, decoration);
    }

    public static TextAnimation replace(Palette palette, boolean inverse, Supplier<String> text,
                                        TextDecoration... decoration) {
        return of(Pattern.REPLACE, palette, inverse, text, decoration);
    }

    public static TextAnimation swipe(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.SWIPE, palette, text, decoration);
    }

    public static TextAnimation slide(Palette palette, Supplier<String> text, TextDecoration... decoration) {
        return of(Pattern.SLIDE, palette, text, decoration);
    }
}
