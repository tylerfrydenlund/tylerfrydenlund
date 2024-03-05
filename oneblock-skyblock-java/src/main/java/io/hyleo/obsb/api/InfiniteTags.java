package io.hyleo.obsb.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfiniteTags<T> {

    @NotNull
    final Map<T, @NotNull List<@NotNull Phase>> tags;

    @NotNull
    public static <T> InfiniteTagsBuilder<@NotNull T> builder() {
        return new InfiniteTagsBuilder<>();
    }

    @NotNull
    public List<@NotNull Phase> getTags(@NonNull T tag) {
        return tags.get(tag);
    }

    public static class InfiniteTagsBuilder<T> {
        @NotNull
        Map<@NotNull T, @NotNull List<@NotNull Phase>> tags = new HashMap<>();

        @NotNull
        public InfiniteTagsBuilder<T> tag(@NonNull T result, @NonNull Phase... phases) {
            tags.put(result, List.of(phases));
            return this;
        }

        @NotNull
        public InfiniteTags<T> build() {
            return new InfiniteTags<>(Collections.unmodifiableMap((Map<? extends T, ? extends List<Phase>>) tags));
        }

    }


}
