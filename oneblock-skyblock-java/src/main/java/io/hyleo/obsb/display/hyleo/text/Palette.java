package io.hyleo.obsb.display.hyleo.text;

import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.function.Function;

@Data
@Builder
@AllArgsConstructor
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class Palette {

    public static Palette singular(TextColor color) {
        return builder().color(color).build();
    }

    public static Palette of(int depth, TextColor... colors) {
        return builder().depth(depth).colors(List.of(colors)).build();
    }

    @Singular
    @NonNull
    List<TextColor> colors;

    @Default
    int depth = 1;

    public int size() {
        return colors().size() * depth();
    }

    public TextColor color(int color) {
        TextColor color1 = colors().get(firstColor(depth, color));

        TextColor color2 = colors().get(secondColor(colors.size(), depth, color));

        int red = color(color % depth, depth, color1, color2, TextColor::red);
        int green = color(color % depth, depth, color1, color2, TextColor::green);
        int blue = color(color % depth, depth, color1, color2, TextColor::blue);

        return TextColor.color(red, green, blue);

    }

    public static int firstColor(int depth, int color) {
        return color / depth;
    }

    public static int secondColor(int colors, int depth, int color) {
        return ((color + depth) / depth % colors);
    }

    public static int distance(int depth, TextColor color1, TextColor color2, Function<TextColor, Integer> function) {

        int x1 = function.apply(color1);
        int x2 = function.apply(color2);

        return (Integer.max(x1, x2) - Integer.min(x1, x2)) / depth;

    }

    public static int direction(TextColor color1, TextColor color2, Function<TextColor, Integer> function) {
        return Integer.compare(function.apply(color2), function.apply(color1));
    }

    public static int color(int color, int depth, TextColor color1, TextColor color2,
                            Function<TextColor, Integer> function) {
        return function.apply(color1)
                + color * distance(depth, color1, color2, function) * direction(color1, color2, function);
    }

}
