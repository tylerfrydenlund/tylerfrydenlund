package io.hyleo.obsb.display.hyleo.text;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Data
@AllArgsConstructor
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
final class AnimationState {

    int frames;
    String text;
    int colors;

    @NonFinal
    int frame;

    public int textLength() {
        return text.length();
    }
}
