package io.hyleo.obsb.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.generator.ChunkGenerator;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(staticName = "empty")
public class EasyWorldGenerator extends ChunkGenerator {

    @Builder.Default
    boolean noise = false, surface = false, bedrock = false, caves = false, decorations = false, mobs = false, structures = false;

    @Override
    public boolean shouldGenerateNoise() {
        return noise;
    }

    @Override
    public boolean shouldGenerateSurface() {
        return surface;
    }

    @Override
    public boolean shouldGenerateBedrock() {
        return bedrock;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return caves;
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return decorations;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return mobs;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return structures;
    }

    public static EasyWorldGenerator endDimension() {
        return EasyWorldGenerator.builder().structures(true).build();
    }
}
