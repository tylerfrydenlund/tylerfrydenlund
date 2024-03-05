package io.hyleo.obsb.phases;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.InfiniteTags;
import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.util.EntityUtil;
import io.hyleo.obsb.util.ItemBuilder;
import io.hyleo.obsb.util.Util;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * This class is fucking hell, enjoy <3
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Infinite {

    @NotNull
    public static final String NAME = "Infinite";

    @NotNull
    public static final String DISPLAY_NAME = "Infinite";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#E97451"));

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Infinity Chest").color(NamedTextColor.DARK_GRAY);

    //Blocks
    @NotNull
    static final Supplier<@NotNull ItemStack> oakLogStack = () -> new ItemStack(Material.OAK_LOG);
    @NotNull
    static final Supplier<@NotNull ItemStack> oakSapling = () -> new ItemStack(Material.OAK_SAPLING);
    @NotNull
    static final Supplier<@NotNull ItemStack> birchLogStack = () -> new ItemStack(Material.BIRCH_LOG);
    @NotNull
    static final Supplier<@NotNull ItemStack> birchSapling = () -> new ItemStack(Material.BIRCH_SAPLING);
    @NotNull
    static final Supplier<@NotNull ItemStack> spruceLogStack = () -> new ItemStack(Material.SPRUCE_LOG);
    @NotNull
    static final Supplier<@NotNull ItemStack> spruceSapling = () -> new ItemStack(Material.SPRUCE_SAPLING);
    @NotNull
    static final Supplier<@NotNull ItemStack> darkOakLogStack = () -> new ItemStack(Material.DARK_OAK_LOG);
    @NotNull
    static final Supplier<@NotNull ItemStack> darkOakSapling = () -> new ItemStack(Material.DARK_OAK_SAPLING);
    @NotNull
    static final Supplier<@NotNull ItemStack> acaciaLogStack = () -> new ItemStack(Material.ACACIA_LOG);
    @NotNull
    static final Supplier<@NotNull ItemStack> acaciaSapling = () -> new ItemStack(Material.ACACIA_SAPLING);
    @NotNull
    static final Supplier<@NotNull ItemStack> jungleLogStack = () -> new ItemStack(Material.JUNGLE_LOG);
    @NotNull
    static final Supplier<@NotNull ItemStack> jungleSapling = () -> new ItemStack(Material.JUNGLE_SAPLING);
    @NotNull
    static final Supplier<@NotNull ItemStack> mangroveLogStack = () -> new ItemStack(Material.MANGROVE_LOG);
    @NotNull
    static final Supplier<@NotNull ItemStack> mangrovePropagule = () -> new ItemStack(Material.MANGROVE_PROPAGULE);
    @NotNull
    static final Supplier<@NotNull ItemStack> warpedStemStack = () -> new ItemStack(Material.WARPED_STEM);
    @NotNull
    static final Supplier<@NotNull ItemStack> warpedFungus = () -> new ItemStack(Material.WARPED_FUNGUS);
    @NotNull
    static final Supplier<@NotNull ItemStack> crimsonStemStack = () -> new ItemStack(Material.CRIMSON_STEM);
    @NotNull
    static final Supplier<@NotNull ItemStack> crimsonFungus = () -> new ItemStack(Material.CRIMSON_FUNGUS);
    @NotNull
    static final Supplier<@NotNull ItemStack> tntStack = () -> new ItemStack(Material.TNT);
    @NotNull
    static final Supplier<@NotNull ItemStack> sculkStack = () -> new ItemStack(Material.SCULK);
    @NotNull
    static final Supplier<@NotNull ItemStack> sculkSensorStack = () -> new ItemStack(Material.SCULK_SENSOR);
    @NotNull
    static final Supplier<@NotNull ItemStack> sculkCatalystStack = () -> new ItemStack(Material.SCULK_CATALYST);

    //Hostile Mob Drops
    @NotNull
    static final Supplier<@NotNull ItemStack> rottenFlesh = () -> new ItemStack(Material.ROTTEN_FLESH);
    @NotNull
    static final Supplier<@NotNull ItemStack> bone = () -> new ItemStack(Material.BONE);
    @NotNull
    static final Supplier<@NotNull ItemStack> string = () -> new ItemStack(Material.STRING);
    @NotNull
    static final Supplier<@NotNull ItemStack> gunpowder = () -> new ItemStack(Material.GUNPOWDER);
    @NotNull
    static final Supplier<@NotNull ItemStack> slimeBall = () -> new ItemStack(Material.SLIME_BALL);
    @NotNull
    static final Supplier<@NotNull ItemStack> spiderEye = () -> new ItemStack(Material.SPIDER_EYE);
    @NotNull
    static final Supplier<@NotNull ItemStack> blazeRod = () -> new ItemStack(Material.BLAZE_ROD);
    @NotNull
    static final Supplier<@NotNull ItemStack> magmaCream = () -> new ItemStack(Material.MAGMA_CREAM);
    @NotNull
    static final Supplier<@NotNull ItemStack> ghastTear = () -> new ItemStack(Material.GHAST_TEAR);
    @NotNull
    static final Supplier<@NotNull ItemStack> enderPearl = () -> new ItemStack(Material.ENDER_PEARL);
    @NotNull
    static final Supplier<@NotNull ItemStack> shulkerShell = () -> new ItemStack(Material.SCULK_CATALYST);
    @NotNull
    static final Supplier<@NotNull ItemStack> phantomMembrane = () -> new ItemStack(Material.PHANTOM_MEMBRANE);
    @NotNull
    static final Supplier<@NotNull ItemStack> dragonBreath = () -> new ItemStack(Material.DRAGON_BREATH);

    //Passive Mob Drops
    @NotNull
    static final Supplier<@NotNull ItemStack> leather = () -> new ItemStack(Material.LEATHER);
    @NotNull
    static final Supplier<@NotNull ItemStack> rabbitHide = () -> new ItemStack(Material.RABBIT_HIDE);
    @NotNull
    static final Supplier<@NotNull ItemStack> feather = () -> new ItemStack(Material.FEATHER);
    @NotNull
    static final Supplier<@NotNull ItemStack> rabbitFoot = () -> new ItemStack(Material.RABBIT_FOOT);

    //Growable Items
    @NotNull
    static final Supplier<@NotNull ItemStack> wheatSeeds = () -> new ItemStack(Material.WHEAT_SEEDS);
    @NotNull
    static final Supplier<@NotNull ItemStack> potato = () -> new ItemStack(Material.POTATO);
    @NotNull
    static final Supplier<@NotNull ItemStack> carrot = () -> new ItemStack(Material.CARROT);
    @NotNull
    static final Supplier<@NotNull ItemStack> pumpkinSeeds = () -> new ItemStack(Material.PUMPKIN_SEEDS);
    @NotNull
    static final Supplier<@NotNull ItemStack> melonSeeds = () -> new ItemStack(Material.MELON_SEEDS);
    @NotNull
    static final Supplier<@NotNull ItemStack> cocoa = () -> new ItemStack(Material.COCOA);
    @NotNull
    static final Supplier<@NotNull ItemStack> sugarCane = () -> new ItemStack(Material.SUGAR_CANE);
    @NotNull
    static final Supplier<@NotNull ItemStack> beetrootSeeds = () -> new ItemStack(Material.BEETROOT_SEEDS);

    //Raw Foods
    @NotNull
    static final Supplier<@NotNull ItemStack> wheat = () -> new ItemStack(Material.WHEAT);
    @NotNull
    static final Supplier<@NotNull ItemStack> apple = () -> new ItemStack(Material.APPLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenApple = () -> new ItemStack(Material.GOLDEN_APPLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> enchantedGoldenApple = () -> new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenCarrot = () -> new ItemStack(Material.GOLDEN_CARROT);
    @NotNull
    static final Supplier<@NotNull ItemStack> glowBerries = () -> new ItemStack(Material.GLOW_BERRIES);
    @NotNull
    static final Supplier<@NotNull ItemStack> sweetBerries = () -> new ItemStack(Material.SWEET_BERRIES);
    @NotNull
    static final Supplier<@NotNull ItemStack> honeyBottle = () -> new ItemStack(Material.HONEY_BOTTLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> melonSlice = () -> new ItemStack(Material.MELON_SLICE);
    @NotNull
    static final Supplier<@NotNull ItemStack> beetroot = () -> new ItemStack(Material.BEETROOT);
    @NotNull
    static final Supplier<@NotNull ItemStack> chorusFruit = () -> new ItemStack(Material.CHORUS_FRUIT);
    @NotNull
    static final Supplier<@NotNull ItemStack> rabbitStack = () -> new ItemStack(Material.RABBIT);
    @NotNull
    static final Supplier<@NotNull ItemStack> beef = () -> new ItemStack(Material.BEEF);
    @NotNull
    static final Supplier<@NotNull ItemStack> mutton = () -> new ItemStack(Material.MUTTON);
    @NotNull
    static final Supplier<@NotNull ItemStack> chickenStack = () -> new ItemStack(Material.CHICKEN);
    @NotNull
    static final Supplier<@NotNull ItemStack> porkchop = () -> new ItemStack(Material.PORKCHOP);

    //Crafted & Smelted Foods
    @NotNull
    static final Supplier<@NotNull ItemStack> rabbitStew = () -> new ItemStack(Material.RABBIT_STEW);
    @NotNull
    static final Supplier<@NotNull ItemStack> mushroomStew = () -> new ItemStack(Material.MUSHROOM_STEW);
    @NotNull
    static final Supplier<@NotNull ItemStack> beetrootSoup = () -> new ItemStack(Material.BEETROOT_SOUP);
    @NotNull
    static final Supplier<@NotNull ItemStack> suspiciousStew = () -> new ItemStack(Material.SUSPICIOUS_STEW);
    @NotNull
    static final Supplier<@NotNull ItemStack> bakedPotato = () -> new ItemStack(Material.BAKED_POTATO);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookie = () -> new ItemStack(Material.COOKIE);
    @NotNull
    static final Supplier<@NotNull ItemStack> cake = () -> new ItemStack(Material.CAKE);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookedRabbit = () -> new ItemStack(Material.COOKED_RABBIT);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookedBeef = () -> new ItemStack(Material.COOKED_BEEF);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookedMutton = () -> new ItemStack(Material.COOKED_MUTTON);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookedChicken = () -> new ItemStack(Material.COOKED_CHICKEN);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookedPorkchop = () -> new ItemStack(Material.COOKED_PORKCHOP);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookedCod = () -> new ItemStack(Material.COOKED_COD);
    @NotNull
    static final Supplier<@NotNull ItemStack> cookedSalmon = () -> new ItemStack(Material.COOKED_SALMON);

    //Ores
    @NotNull
    static final Supplier<@NotNull ItemStack> coal = () -> new ItemStack(Material.COAL);
    @NotNull
    static final Supplier<@NotNull ItemStack> ironIngot = () -> new ItemStack(Material.IRON_INGOT);
    @NotNull
    static final Supplier<@NotNull ItemStack> copperIngot = () -> new ItemStack(Material.COPPER_INGOT);
    @NotNull
    static final Supplier<@NotNull ItemStack> goldIngot = () -> new ItemStack(Material.GOLD_INGOT);
    @NotNull
    static final Supplier<@NotNull ItemStack> redstone = () -> new ItemStack(Material.REDSTONE);
    @NotNull
    static final Supplier<@NotNull ItemStack> lapisLazuli = () -> new ItemStack(Material.LAPIS_LAZULI);
    @NotNull
    static final Supplier<@NotNull ItemStack> diamond = () -> new ItemStack(Material.DIAMOND);
    @NotNull
    static final Supplier<@NotNull ItemStack> amethystShard = () -> new ItemStack(Material.AMETHYST_SHARD);
    @NotNull
    static final Supplier<@NotNull ItemStack> emerald = () -> new ItemStack(Material.EMERALD);
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteScrap = () -> new ItemStack(Material.NETHERITE_SCRAP);
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteIngot = () -> new ItemStack(Material.NETHERITE_INGOT);

    //Utility & Misc.
    @NotNull
    static final Supplier<@NotNull ItemStack> torch = () -> new ItemStack(Material.TORCH);
    @NotNull
    static final Supplier<@NotNull ItemStack> waterBucket = () -> new ItemStack(Material.WATER_BUCKET);
    @NotNull
    static final Supplier<@NotNull ItemStack> lavaBucket = () -> new ItemStack(Material.LAVA_BUCKET);
    @NotNull
    static final Supplier<@NotNull ItemStack> nameTag = () -> new ItemStack(Material.NAME_TAG);
    @NotNull
    static final Supplier<@NotNull ItemStack> honeycomb = () -> new ItemStack(Material.HONEYCOMB);
    @NotNull
    static final Supplier<@NotNull ItemStack> anvil = () -> new ItemStack(Material.ANVIL);
    @NotNull
    static final Supplier<@NotNull ItemStack> prismarineShard = () -> new ItemStack(Material.PRISMARINE_SHARD);
    @NotNull
    static final Supplier<@NotNull ItemStack> prismarineCrystals = () -> new ItemStack(Material.PRISMARINE_CRYSTALS);
    @NotNull
    static final Supplier<@NotNull ItemStack> scute = () -> new ItemStack(Material.SCUTE);
    @NotNull
    static final Supplier<@NotNull ItemStack> nautilusShell = () -> new ItemStack(Material.NAUTILUS_SHELL);
    @NotNull
    static final Supplier<@NotNull ItemStack> codBucket = () -> new ItemStack(Material.COD_BUCKET);
    @NotNull
    static final Supplier<@NotNull ItemStack> salmonBucket = () -> new ItemStack(Material.SALMON_BUCKET);
    @NotNull
    static final Supplier<@NotNull ItemStack> tropicalFishBucket = () -> new ItemStack(Material.TROPICAL_FISH_BUCKET);
    @NotNull
    static final Supplier<@NotNull ItemStack> pufferfishBucket = () -> new ItemStack(Material.PUFFERFISH_BUCKET);
    @NotNull
    static final Supplier<@NotNull ItemStack> heartOfTheSea = () -> new ItemStack(Material.HEART_OF_THE_SEA);
    @NotNull
    static final Supplier<@NotNull ItemStack> brewingStand = () -> new ItemStack(Material.BREWING_STAND);
    @NotNull
    static final Supplier<@NotNull ItemStack> enchantingTable = () -> new ItemStack(Material.ENCHANTING_TABLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> totemOfUndying = () -> new ItemStack(Material.TOTEM_OF_UNDYING);
    @NotNull
    static final Supplier<@NotNull ItemStack> quartz = () -> new ItemStack(Material.QUARTZ);
    @NotNull
    static final Supplier<@NotNull ItemStack> glowstoneDust = () -> new ItemStack(Material.GLOWSTONE_DUST);
    @NotNull
    static final Supplier<@NotNull ItemStack> echoShard = () -> new ItemStack(Material.ECHO_SHARD);

    //Decoration
    @NotNull
    static final Supplier<@NotNull ItemStack> grass = () -> new ItemStack(Material.SHORT_GRASS);
    @NotNull
    static final Supplier<@NotNull ItemStack> tallGrass = () -> new ItemStack(Material.TALL_GRASS);
    @NotNull
    static final Supplier<@NotNull ItemStack> dandelion = () -> new ItemStack(Material.DANDELION);
    @NotNull
    static final Supplier<@NotNull ItemStack> poppy = () -> new ItemStack(Material.POPPY);
    @NotNull
    static final Supplier<@NotNull ItemStack> blueOrchid = () -> new ItemStack(Material.BLUE_ORCHID);
    @NotNull
    static final Supplier<@NotNull ItemStack> allium = () -> new ItemStack(Material.ALLIUM);
    @NotNull
    static final Supplier<@NotNull ItemStack> azureBluet = () -> new ItemStack(Material.AZURE_BLUET);
    @NotNull
    static final Supplier<@NotNull ItemStack> redTulip = () -> new ItemStack(Material.RED_TULIP);
    @NotNull
    static final Supplier<@NotNull ItemStack> orangeTulip = () -> new ItemStack(Material.ORANGE_TULIP);
    @NotNull
    static final Supplier<@NotNull ItemStack> pinkTulip = () -> new ItemStack(Material.PINK_TULIP);
    @NotNull
    static final Supplier<@NotNull ItemStack> whiteTulip = () -> new ItemStack(Material.WHITE_TULIP);
    @NotNull
    static final Supplier<@NotNull ItemStack> oxeyeDaisy = () -> new ItemStack(Material.OXEYE_DAISY);
    @NotNull
    static final Supplier<@NotNull ItemStack> cornflower = () -> new ItemStack(Material.CORNFLOWER);
    @NotNull
    static final Supplier<@NotNull ItemStack> lilyOfTheValley = () -> new ItemStack(Material.LILY_OF_THE_VALLEY);
    @NotNull
    static final Supplier<@NotNull ItemStack> sunflower = () -> new ItemStack(Material.SUNFLOWER);
    @NotNull
    static final Supplier<@NotNull ItemStack> lilac = () -> new ItemStack(Material.LILAC);
    @NotNull
    static final Supplier<@NotNull ItemStack> roseBush = () -> new ItemStack(Material.ROSE_BUSH);
    @NotNull
    static final Supplier<@NotNull ItemStack> peony = () -> new ItemStack(Material.PEONY);
    @NotNull
    static final Supplier<@NotNull ItemStack> witherRose = () -> new ItemStack(Material.WITHER_ROSE);
    @NotNull
    static final Supplier<@NotNull ItemStack> azalea = () -> new ItemStack(Material.AZALEA);
    @NotNull
    static final Supplier<@NotNull ItemStack> floweringAzalea = () -> new ItemStack(Material.FLOWERING_AZALEA);
    @NotNull
    static final Supplier<@NotNull ItemStack> sporeBlossom = () -> new ItemStack(Material.SPORE_BLOSSOM);
    @NotNull
    static final Supplier<@NotNull ItemStack> smallDripleaf = () -> new ItemStack(Material.SMALL_DRIPLEAF);
    @NotNull
    static final Supplier<@NotNull ItemStack> bigDripleaf = () -> new ItemStack(Material.BIG_DRIPLEAF);
    @NotNull
    static final Supplier<@NotNull ItemStack> glowLichen = () -> new ItemStack(Material.GLOW_LICHEN);
    @NotNull
    static final Supplier<@NotNull ItemStack> fern = () -> new ItemStack(Material.FERN);
    @NotNull
    static final Supplier<@NotNull ItemStack> largeFern = () -> new ItemStack(Material.LARGE_FERN);
    @NotNull
    static final Supplier<@NotNull ItemStack> redMushroom = () -> new ItemStack(Material.RED_MUSHROOM);
    @NotNull
    static final Supplier<@NotNull ItemStack> brownMushroom = () -> new ItemStack(Material.BROWN_MUSHROOM);
    @NotNull
    static final Supplier<@NotNull ItemStack> deadBush = () -> new ItemStack(Material.DEAD_BUSH);
    @NotNull
    static final Supplier<@NotNull ItemStack> rail = () -> new ItemStack(Material.RAIL);
    @NotNull
    static final Supplier<@NotNull ItemStack> poweredRail = () -> new ItemStack(Material.POWERED_RAIL);
    @NotNull
    static final Supplier<@NotNull ItemStack> activatorRail = () -> new ItemStack(Material.ACTIVATOR_RAIL);
    @NotNull
    static final Supplier<@NotNull ItemStack> detectorRail = () -> new ItemStack(Material.DETECTOR_RAIL);
    @NotNull
    static final Supplier<@NotNull ItemStack> tripwireHook = () -> new ItemStack(Material.TRIPWIRE_HOOK);
    @NotNull
    static final Supplier<@NotNull ItemStack> tubeCoral = () -> new ItemStack(Material.FIRE_CORAL);
    @NotNull
    static final Supplier<@NotNull ItemStack> brainCoral = () -> new ItemStack(Material.BRAIN_CORAL);
    @NotNull
    static final Supplier<@NotNull ItemStack> bubbleCoral = () -> new ItemStack(Material.BUBBLE_CORAL);
    @NotNull
    static final Supplier<@NotNull ItemStack> fireCoral = () -> new ItemStack(Material.FIRE_CORAL);
    @NotNull
    static final Supplier<@NotNull ItemStack> hornCoral = () -> new ItemStack(Material.HORN_CORAL);
    @NotNull
    static final Supplier<@NotNull ItemStack> tubeCoralFan = () -> new ItemStack(Material.TUBE_CORAL_FAN);
    @NotNull
    static final Supplier<@NotNull ItemStack> brainCoralFan = () -> new ItemStack(Material.BRAIN_CORAL_FAN);
    @NotNull
    static final Supplier<@NotNull ItemStack> bubbleCoralFan = () -> new ItemStack(Material.BUBBLE_CORAL_FAN);
    @NotNull
    static final Supplier<@NotNull ItemStack> fireCoralFan = () -> new ItemStack(Material.FIRE_CORAL_FAN);
    @NotNull
    static final Supplier<@NotNull ItemStack> hornCoralFan = () -> new ItemStack(Material.HORN_CORAL_FAN);
    @NotNull
    static final Supplier<@NotNull ItemStack> seagrass = () -> new ItemStack(Material.SEAGRASS);
    @NotNull
    static final Supplier<@NotNull ItemStack> kelp = () -> new ItemStack(Material.KELP);
    @NotNull
    static final Supplier<@NotNull ItemStack> caveVines = () -> new ItemStack(Material.CAVE_VINES);
    @NotNull
    static final Supplier<@NotNull ItemStack> lilyPad = () -> new ItemStack(Material.LILY_PAD);
    @NotNull
    static final Supplier<@NotNull ItemStack> mossCarpet = () -> new ItemStack(Material.MOSS_CARPET);
    @NotNull
    static final Supplier<@NotNull ItemStack> pointedDripstone = () -> new ItemStack(Material.POINTED_DRIPSTONE);
    @NotNull
    static final Supplier<@NotNull ItemStack> bell = () -> new ItemStack(Material.BELL);
    @NotNull
    static final Supplier<@NotNull ItemStack> warpedRoots = () -> new ItemStack(Material.WARPED_ROOTS);
    @NotNull
    static final Supplier<@NotNull ItemStack> twistingVines = () -> new ItemStack(Material.TWISTING_VINES);
    @NotNull
    static final Supplier<@NotNull ItemStack> netherSprouts = () -> new ItemStack(Material.NETHER_SPROUTS);
    @NotNull
    static final Supplier<@NotNull ItemStack> cobweb = () -> new ItemStack(Material.COBWEB);
    @NotNull
    static final Supplier<@NotNull ItemStack> lantern = () -> new ItemStack(Material.LANTERN);
    @NotNull
    static final Supplier<@NotNull ItemStack> lectern = () -> new ItemStack(Material.LECTERN);
    @NotNull
    static final Supplier<@NotNull ItemStack> netherWart = () -> new ItemStack(Material.NETHER_WART);
    @NotNull
    static final Supplier<@NotNull ItemStack> crimsonRoots = () -> new ItemStack(Material.CRIMSON_ROOTS);
    @NotNull
    static final Supplier<@NotNull ItemStack> weepingVines = () -> new ItemStack(Material.WEEPING_VINES);
    @NotNull
    static final Supplier<@NotNull ItemStack> sculkVein = () -> new ItemStack(Material.SCULK_VEIN);
    @NotNull
    static final Supplier<@NotNull ItemStack> candle = () -> new ItemStack(Material.CANDLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> whiteCandle = () -> new ItemStack(Material.WHITE_CANDLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> soulTorch = () -> new ItemStack(Material.SOUL_TORCH);
    @NotNull
    static final Supplier<@NotNull ItemStack> soulLantern = () -> new ItemStack(Material.SOUL_LANTERN);
    @NotNull
    static final Supplier<@NotNull ItemStack> redstoneTorch = () -> new ItemStack(Material.REDSTONE_TORCH);
    @NotNull
    static final Supplier<@NotNull ItemStack> repeater = () -> new ItemStack(Material.REPEATER);
    @NotNull
    static final Supplier<@NotNull ItemStack> comparator = () -> new ItemStack(Material.COMPARATOR);
    @NotNull
    static final Supplier<@NotNull ItemStack> skeletonSkull = () -> new ItemStack(Material.SOUL_TORCH);
    @NotNull
    static final Supplier<@NotNull ItemStack> chorusFlower = () -> new ItemStack(Material.CHORUS_FLOWER);
    @NotNull
    static final Supplier<@NotNull ItemStack> endRod = () -> new ItemStack(Material.END_ROD);
    @NotNull
    static final Supplier<@NotNull ItemStack> dragonHead = () -> new ItemStack(Material.DRAGON_HEAD);

    //Tools & Weapons
    @NotNull
    static final Supplier<@NotNull ItemStack> woodenCombatAxe = new ItemBuilder(Material.WOODEN_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> woodenUtilityAxe = new ItemBuilder(Material.WOODEN_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileWoodenCombatAxe = new ItemBuilder(Material.WOODEN_AXE).setIgnoringConflictingEnchants(true)
            .addEnchant(Enchantment.VANISHING_CURSE, 1, true).addQuantity(3, 0.125)
            .addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileWoodenUtilityAxe = new ItemBuilder(Material.WOODEN_AXE).setIgnoringConflictingEnchants(true)
            .addEnchant(Enchantment.VANISHING_CURSE, 1, true).addQuantity(3, 0.125)
            .addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> woodenPickaxe = new ItemBuilder(Material.WOODEN_PICKAXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileWoodenPickaxe = new ItemBuilder(Material.WOODEN_PICKAXE).setIgnoringConflictingEnchants(true)
            .addEnchant(Enchantment.VANISHING_CURSE, 1, true).addQuantity(3, 0.125)
            .addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> woodenHoe = new ItemBuilder(Material.WOODEN_HOE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileWoodenHoe = new ItemBuilder(Material.WOODEN_HOE).setIgnoringConflictingEnchants(true)
            .addEnchant(Enchantment.VANISHING_CURSE, 1, true).addQuantity(3, 0.125)
            .addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> woodenShovel = new ItemBuilder(Material.WOODEN_SHOVEL).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileWoodenShovel = new ItemBuilder(Material.WOODEN_SHOVEL).setIgnoringConflictingEnchants(true)
            .addEnchant(Enchantment.VANISHING_CURSE, 1, true).addQuantity(3, 0.125)
            .addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> woodenSword = new ItemBuilder(Material.WOODEN_SWORD).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addQuantity(5, 0.03125).addQuantity(6, 0.015625).addQuantity(7, 0.0078125)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileWoodenSword = new ItemBuilder(Material.WOODEN_SWORD).setIgnoringConflictingEnchants(true)
            .addEnchant(Enchantment.VANISHING_CURSE, 1, true).addQuantity(4, 0.0625)
            .addQuantity(5, 0.03125).addQuantity(6, 0.015625).addQuantity(7, 0.0078125)
            .addQuantity(8, 0.00390625).addQuantity(9, 0.001953125)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(4, 0.125), Weight.of(5, 0.0625), Weight.of(6, 0.03125))
            .addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> stoneCombatAxe = new ItemBuilder(Material.STONE_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> stoneUtilityAxe = new ItemBuilder(Material.STONE_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> stonePickaxe = new ItemBuilder(Material.STONE_PICKAXE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> stoneHoe = new ItemBuilder(Material.STONE_HOE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> stoneShovel = new ItemBuilder(Material.STONE_SHOVEL).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> stoneSword = new ItemBuilder(Material.STONE_SWORD).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironCombatAxe = new ItemBuilder(Material.IRON_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironUtilityAxe = new ItemBuilder(Material.IRON_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironPickaxe = new ItemBuilder(Material.IRON_PICKAXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironHoe = new ItemBuilder(Material.IRON_HOE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironShovel = new ItemBuilder(Material.IRON_SHOVEL).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironSword = new ItemBuilder(Material.IRON_SWORD).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenCombatAxe = new ItemBuilder(Material.GOLDEN_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenUtilityAxe = new ItemBuilder(Material.GOLDEN_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenPickaxe = new ItemBuilder(Material.GOLDEN_PICKAXE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenHoe = new ItemBuilder(Material.GOLDEN_HOE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenShovel = new ItemBuilder(Material.GOLDEN_SHOVEL).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenSword = new ItemBuilder(Material.GOLDEN_SWORD).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondCombatAxe = new ItemBuilder(Material.DIAMOND_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondUtilityAxe = new ItemBuilder(Material.DIAMOND_AXE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondPickaxe = new ItemBuilder(Material.DIAMOND_PICKAXE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondHoe = new ItemBuilder(Material.DIAMOND_HOE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondShovel = new ItemBuilder(Material.DIAMOND_SHOVEL).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondSword = new ItemBuilder(Material.DIAMOND_SWORD).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteCombatAxe = new ItemBuilder(Material.NETHERITE_AXE).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteUtilityAxe = new ItemBuilder(Material.NETHERITE_AXE).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheritePickaxe = new ItemBuilder(Material.NETHERITE_PICKAXE).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteHoe = new ItemBuilder(Material.NETHERITE_HOE).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteShovel = new ItemBuilder(Material.NETHERITE_SHOVEL).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0)).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteSword = new ItemBuilder(Material.NETHERITE_SWORD).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> bow = new ItemBuilder(Material.BOW).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.ARROW_FIRE, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.ARROW_INFINITE, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> crossbow = new ItemBuilder(Material.CROSSBOW).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.MULTISHOT, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> arrow = () -> new ItemStack(Material.ARROW);
    @NotNull
    static final Supplier<@NotNull ItemStack> spectralArrow = () -> new ItemStack(Material.SPECTRAL_ARROW);
    @NotNull
    static final Supplier<@NotNull ItemStack> trident = new ItemBuilder(Material.TRIDENT).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.CHANNELING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.IMPALING, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.LOYALTY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.RIPTIDE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();

    //Armor
    @NotNull
    static final Supplier<@NotNull ItemStack> turtleHelmet = new ItemBuilder(Material.TURTLE_HELMET).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> leatherHelmet = new ItemBuilder(Material.LEATHER_HELMET).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileLeatherHelmet = new ItemBuilder(Material.LEATHER_HELMET).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, true)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> leatherChestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileLeatherChestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, true)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> leatherLeggings = new ItemBuilder(Material.LEATHER_LEGGINGS).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileLeatherLeggings = new ItemBuilder(Material.LEATHER_LEGGINGS).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, true)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> leatherBoots = new ItemBuilder(Material.LEATHER_BOOTS).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fragileLeatherBoots = new ItemBuilder(Material.LEATHER_BOOTS).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, true)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125).addQuantity(6, 0.015625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 0.25), Weight.of(4, 0.125), Weight.of(5, 0.0625))
            .addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> chainmailHelmet = new ItemBuilder(Material.CHAINMAIL_HELMET).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> chainmailChestplate = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> chainmailLeggings = new ItemBuilder(Material.CHAINMAIL_LEGGINGS).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> chainmailBoots = new ItemBuilder(Material.CHAINMAIL_BOOTS).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addQuantity(4, 0.0625).addQuantity(5, 0.03125)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironHelmet = new ItemBuilder(Material.IRON_HELMET).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironChestplate = new ItemBuilder(Material.IRON_CHESTPLATE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironLeggings = new ItemBuilder(Material.IRON_LEGGINGS).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addQuantity(3, 0.125).addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> ironBoots = new ItemBuilder(Material.IRON_BOOTS).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenHelmet = new ItemBuilder(Material.GOLDEN_HELMET).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenChestplate = new ItemBuilder(Material.GOLDEN_CHESTPLATE).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenLeggings = new ItemBuilder(Material.GOLDEN_LEGGINGS).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenBoots = new ItemBuilder(Material.GOLDEN_BOOTS).addQuantity(0, 1.0).addQuantity(1, 0.5)
            .addQuantity(2, 0.25).addQuantity(3, 0.125).addQuantity(4, 0.0625)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondHelmet = new ItemBuilder(Material.DIAMOND_HELMET).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondChestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondLeggings = new ItemBuilder(Material.DIAMOND_LEGGINGS).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondBoots = new ItemBuilder(Material.DIAMOND_BOOTS).addQuantity(0, 1.0).addQuantity(1, 0.5).addQuantity(2, 0.25)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteHelmet = new ItemBuilder(Material.NETHERITE_HELMET).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteChestplate = new ItemBuilder(Material.NETHERITE_CHESTPLATE).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteLeggings = new ItemBuilder(Material.NETHERITE_LEGGINGS).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> netheriteBoots = new ItemBuilder(Material.NETHERITE_BOOTS).addQuantity(0, 1.0).addQuantity(1, 0.25)
            .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 1.0), Weight.of(2, 0.5))
            .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
            .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25), Weight.of(4, 0.125))
            .addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0), Weight.of(2, 0.5), Weight.of(3, 0.25))
            .asStackSupplier();

    //Horse Equipment
    @NotNull
    static final Supplier<@NotNull ItemStack> lead = () -> new ItemStack(Material.LEAD);
    @NotNull
    static final Supplier<@NotNull ItemStack> saddle = () -> new ItemStack(Material.SADDLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> ironHorseArmor = () -> new ItemStack(Material.IRON_HORSE_ARMOR);
    @NotNull
    static final Supplier<@NotNull ItemStack> goldenHorseArmor = () -> new ItemStack(Material.GOLDEN_HORSE_ARMOR);
    @NotNull
    static final Supplier<@NotNull ItemStack> diamondHorseArmor = () -> new ItemStack(Material.DIAMOND_HORSE_ARMOR);

    //Enchanting
    @NotNull
    static final Supplier<@NotNull ItemStack> experienceBottle = () -> new ItemStack(Material.EXPERIENCE_BOTTLE);
    @NotNull
    static final Supplier<@NotNull ItemStack> book = () -> new ItemStack(Material.BOOK);
    @NotNull
    static final Supplier<@NotNull ItemStack> mendingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> unbreakingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> curseOfVanishingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.VANISHING_CURSE, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> aquaAffinityBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> blastProtectionBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> curseOfBindingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.BINDING_CURSE, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> depthStriderBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DEPTH_STRIDER, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> featherFallingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fireProtectionBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> frostWalkerBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> projectileProtectionBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> protectionBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> respirationBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> soulSpeedBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> swiftSneakBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> thornsBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> baneOfArthropodsBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fireAspectBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> lootingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> impalingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.IMPALING, Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> knockbackBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> sharpnessBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 32), Weight.of(2, 16), Weight.of(3, 8), Weight.of(4, 4), Weight.of(5, 2), Weight.of(6, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> smiteBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> sweepingEdgeBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> flameBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_FIRE, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> infinityBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_INFINITE, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> loyaltyBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOYALTY, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> riptideBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.RIPTIDE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> multishotBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MULTISHOT, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> piercingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> powerBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 32), Weight.of(2, 16), Weight.of(3, 8), Weight.of(4, 4), Weight.of(5, 2), Weight.of(6, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> punchBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> quickChargeBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> efficiencyBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 32), Weight.of(2, 16), Weight.of(3, 8), Weight.of(4, 4), Weight.of(5, 2), Weight.of(6, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> fortuneBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> luckOfTheSeaBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LUCK, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> lureBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LURE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> silkTouchBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> rollingHillsTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(RollingHills.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> yawningCavernTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(YawningCavern.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> undergrowthTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(TheUndergrowth.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> crackedWastelandTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(CrackedWasteland.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> cambrianEstuaryTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(CambrianEstuary.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> boggedCloudforestTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(BoggedCloudforest.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> gleamingDepthsTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(GleamingDepths.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> desolatePeaksTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(DesolatePeaks.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> kaleidoscopeLibraryTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(KaleidoscopeLibrary.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> scorchedTerrorscapeTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(ScorchedTerrorscape.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> unfathomableAbyssTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(UnfathomableAbyss.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();
    @NotNull
    static final Supplier<@NotNull ItemStack> groundZeroTargetingBook = new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(OneblockSkyblock.getTargetingEnchantment(GroundZero.PHASE), Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier();


    //Brewing
    @NotNull
    static final Supplier<@NotNull ItemStack> blazePowder = () -> new ItemStack(Material.BLAZE_POWDER);
    @NotNull
    static final Supplier<@NotNull ItemStack> fermentedSpiderEye = () -> new ItemStack(Material.FERMENTED_SPIDER_EYE);
    @NotNull
    static final Supplier<@NotNull ItemStack> sugar = () -> new ItemStack(Material.SUGAR);
    @NotNull
    static final Supplier<@NotNull ItemStack> glisteringMelonSlice = () -> new ItemStack(Material.GLISTERING_MELON_SLICE);
    @NotNull
    static final Supplier<@NotNull ItemStack> waterBottle = Util.potion(PotionType.WATER, false, false);
    @NotNull
    static final Supplier<@NotNull ItemStack> potion = Util.randomPotion();

    //Music Disc
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscChirp = () -> new ItemStack(Material.MUSIC_DISC_CHIRP);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscBlocks = () -> new ItemStack(Material.MUSIC_DISC_BLOCKS);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDisc13 = () -> new ItemStack(Material.MUSIC_DISC_13);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscCat = () -> new ItemStack(Material.MUSIC_DISC_CAT);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscMall = () -> new ItemStack(Material.MUSIC_DISC_MALL);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscStrad = () -> new ItemStack(Material.MUSIC_DISC_STRAD);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscMellohi = () -> new ItemStack(Material.MUSIC_DISC_MELLOHI);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscStal = () -> new ItemStack(Material.MUSIC_DISC_STAL);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscFar = () -> new ItemStack(Material.MUSIC_DISC_FAR);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscWait = () -> new ItemStack(Material.MUSIC_DISC_WAIT);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscPigstep = () -> new ItemStack(Material.MUSIC_DISC_PIGSTEP);
    @NotNull
    static final Supplier<@NotNull ItemStack> discFragment5 = () -> new ItemStack(Material.DISC_FRAGMENT_5);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscOtherside = () -> new ItemStack(Material.MUSIC_DISC_OTHERSIDE);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDiscWard = () -> new ItemStack(Material.MUSIC_DISC_WARD);
    @NotNull
    static final Supplier<@NotNull ItemStack> musicDisc11 = () -> new ItemStack(Material.MUSIC_DISC_11);

    public static final LootDigest LOOT = LootDigest.builder("INFINITE", Weight.of(6), Weight.of(9), Weight.of(12))
            //Blocks
            .item(oakLogStack, 1, 5.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(oakSapling, 1, 2.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(birchLogStack, 1, 4.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(birchSapling, 1, 1.5, Weight.of(1, 4), Weight.of(2, 2))
            .item(spruceLogStack, 1, 4.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(spruceSapling, 1, 1.5, Weight.of(1, 4), Weight.of(2, 2))
            .item(darkOakLogStack, 1, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(darkOakSapling, 1, 1.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(acaciaLogStack, 1, 4.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(acaciaSapling, 1, 1.5, Weight.of(1, 4), Weight.of(2, 2))
            .item(jungleLogStack, 1, 4.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(jungleSapling, 1, 1.5, Weight.of(1, 4), Weight.of(2, 2))
            .item(mangroveLogStack, 1, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(mangrovePropagule, 1, 1.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(warpedStemStack, 1, 5.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(warpedFungus, 1, 2.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(crimsonStemStack, 1, 5.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(crimsonFungus, 1, 2.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(tntStack, 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(sculkStack, 1, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(sculkSensorStack, 2, 1.0, Weight.of(1, 2), Weight.of(2, 1))
            .item(sculkCatalystStack, 2, 1.0, Weight.of(1, 2), Weight.of(2, 1))

            //Hostile Mob Drops
            .item(rottenFlesh, 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(bone, 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(string, 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(gunpowder, 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(slimeBall, 1, 3.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(spiderEye, 1, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(blazeRod, 3, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(magmaCream, 3, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(ghastTear, 3, 0.75)
            .item(enderPearl, 3, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(shulkerShell, 4, 1.0, Weight.of(1, 1), Weight.of(2, 1))
            .item(phantomMembrane, 4, 0.75, Weight.of(1, 1), Weight.of(2, 1))
            .item(dragonBreath, 5, 0.5)

            //Passive Mob Drops
            .item(leather, 1, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(rabbitHide, 1, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(feather, 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(rabbitFoot, 4, 0.75)

            //Growable Item
            .item(wheatSeeds, 1, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(potato, 1, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(carrot, 1, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(pumpkinSeeds, 2, 1.0, Weight.of(1, 2), Weight.of(2, 1))
            .item(melonSeeds, 2, 1.0, Weight.of(1, 2), Weight.of(2, 1))
            .item(cocoa, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(sugarCane, 2, 1.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(beetrootSeeds, 2, 1.0, Weight.of(1, 2), Weight.of(2, 1))

            //Raw Foods
            .item(wheat, 1, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(apple, 2, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(goldenApple, 4, 1.0, Weight.of(1, 1), Weight.of(2, 2))
            .item(enchantedGoldenApple, 6, 0.25)
            .item(goldenCarrot, 3, 1.0, Weight.of(1, 1), Weight.of(2, 2))
            .item(glowBerries, 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(sweetBerries, 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(honeyBottle, 2, 1.5)
            .item(melonSlice, 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(beetroot, 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(chorusFruit, 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(rabbitStack, 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(beef, 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(mutton, 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(chickenStack, 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(porkchop, 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            //Crafted & Smelted Foods
            .item(rabbitStew, 2, 1.5)
            .item(mushroomStew, 3, 1.5)
            .item(beetrootSoup, 2, 1.5)
            .item(suspiciousStew, 3, 1.5)
            .item(bakedPotato, 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cookie, 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cake, 3, 1.0)
            .item(cookedRabbit, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cookedBeef, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cookedMutton, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cookedChicken, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cookedPorkchop, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cookedCod, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(cookedSalmon, 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            //Ores
            .item(coal, 2, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 2), Weight.of(12, 1))
            .item(ironIngot, 3, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(copperIngot, 2, 1.75, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(goldIngot, 4, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(redstone, 4, 1.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 2), Weight.of(12, 1))
            .item(lapisLazuli, 4, 1.5, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(diamond, 5, 1.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(amethystShard, 3, 1.5, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(emerald, 5, 1.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(netheriteScrap, 6, 0.75, Weight.of(1, 2), Weight.of(2, 2), Weight.of(4, 1))
            .item(netheriteIngot, 7, 0.25, Weight.of(1, 4), Weight.of(2, 1))

            //Utility & Misc.
            .item(torch, 1, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(waterBucket, 1, 1.0)
            .item(lavaBucket, 2, 1.0)
            .item(nameTag, 3, 0.5)
            .item(honeycomb, 2, 1.5, Weight.of(3, 4), Weight.of(6, 1))
            .item(anvil, 4, 0.5)
            .item(prismarineShard, 2, 3.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(prismarineCrystals, 3, 1.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(scute, 3, 2.0, Weight.of(1, 4), Weight.of(2, 1))
            .item(nautilusShell, 4, 2.0, Weight.of(1, 4), Weight.of(2, 1))
            .item(codBucket, 2, 1.0)
            .item(salmonBucket, 2, 1.0)
            .item(tropicalFishBucket, 2, 1.5)
            .item(pufferfishBucket, 2, 0.5)
            .item(heartOfTheSea, 5, 0.5)
            .item(brewingStand, 4, 0.5)
            .item(enchantingTable, 4, 0.5)
            .item(totemOfUndying, 5, 0.5)
            .item(quartz, 2, 5.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(glowstoneDust, 2, 5.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(echoShard, 3, 1.25, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            //Decoration
            .item(grass, 1, 1.0)
            .item(tallGrass, 1, 0.5)
            .item(dandelion, 1, 0.5)
            .item(poppy, 1, 0.5)
            .item(blueOrchid, 1, 0.5)
            .item(allium, 1, 0.5)
            .item(azureBluet, 1, 0.5)
            .item(redTulip, 1, 1.0)
            .item(orangeTulip, 1, 1.0)
            .item(pinkTulip, 1, 1.0)
            .item(whiteTulip, 1, 1.0)
            .item(oxeyeDaisy, 1, 0.5)
            .item(cornflower, 1, 0.5)
            .item(lilyOfTheValley, 1, 0.5)
            .item(sunflower, 1, 0.5)
            .item(lilac, 1, 0.5)
            .item(roseBush, 1, 0.5)
            .item(peony, 1, 0.5)
            .item(witherRose, 3, 0.25)
            .item(azalea, 1, 0.5)
            .item(floweringAzalea, 1, 0.25)
            .item(sporeBlossom, 1, 0.25)
            .item(smallDripleaf, 1, 0.5)
            .item(bigDripleaf, 1, 0.25)
            .item(glowLichen, 1, 1.0)
            .item(fern, 1, 1.0)
            .item(largeFern, 1, 0.5)
            .item(redMushroom, 1, 0.75)
            .item(brownMushroom, 1, 0.75)
            .item(deadBush, 1, 1.0)
            .item(rail, 1, 0.5, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(poweredRail, 2, 0.25, Weight.of(1, 2), Weight.of(2, 1))
            .item(activatorRail, 2, 0.25, Weight.of(1, 2), Weight.of(2, 1))
            .item(detectorRail, 2, 0.25, Weight.of(1, 2), Weight.of(2, 1))
            .item(tripwireHook, 1, 0.25)
            .item(tubeCoral, 1, 0.125)
            .item(brainCoral, 1, 0.125)
            .item(bubbleCoral, 1, 0.125)
            .item(fireCoral, 1, 0.125)
            .item(hornCoral, 1, 0.125)
            .item(tubeCoralFan, 1, 0.125)
            .item(brainCoralFan, 1, 0.125)
            .item(bubbleCoralFan, 1, 0.125)
            .item(fireCoralFan, 1, 0.125)
            .item(hornCoralFan, 1, 0.125)
            .item(seagrass, 1, 0.5)
            .item(kelp, 1, 0.5)
            .item(caveVines, 1, 0.5)
            .item(lilyPad, 1, 0.5)
            .item(mossCarpet, 1, 0.5)
            .item(pointedDripstone, 1, 0.5)
            .item(bell, 1, 0.5)
            .item(warpedRoots, 1, 0.5)
            .item(twistingVines, 1, 0.5)
            .item(netherSprouts, 1, 0.5)
            .item(cobweb, 1, 1.0)
            .item(lantern, 1, 0.5)
            .item(lectern, 1, 0.5)
            .item(netherWart, 1, 0.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(crimsonRoots, 1, 0.5)
            .item(weepingVines, 1, 0.5)
            .item(sculkVein, 1, 1.0)
            .item(candle, 1, 0.5)
            .item(whiteCandle, 1, 0.5)
            .item(soulTorch, 1, 0.5)
            .item(soulLantern, 1, 0.25)
            .item(redstoneTorch, 2, 0.5)
            .item(repeater, 3, 0.5)
            .item(comparator, 3, 0.5)
            .item(skeletonSkull, 4, 0.25)
            .item(chorusFlower, 1, 1.0)
            .item(endRod, 2, 0.5)
            .item(dragonHead, 3, 0.125)

            //Tools
            .item(woodenCombatAxe, 3, 1.0)
            .item(woodenUtilityAxe, 3, 1.0)
            .item(fragileWoodenCombatAxe, 6, 0.125)
            .item(fragileWoodenUtilityAxe, 6, 0.125)
            .item(woodenPickaxe, 3, 1.0)
            .item(fragileWoodenPickaxe, 6, 0.125)
            .item(woodenHoe, 3, 1.0)
            .item(fragileWoodenHoe, 6, 0.125)
            .item(woodenShovel, 3, 1.0)
            .item(fragileWoodenShovel, 6, 0.125)
            .item(woodenSword, 3, 1.0)
            .item(fragileWoodenSword, 6, 0.125)
            .item(stoneCombatAxe, 4, 0.5)
            .item(stoneUtilityAxe, 4, 0.5)
            .item(stonePickaxe, 4, 0.5)
            .item(stoneHoe, 4, 0.5)
            .item(stoneShovel, 4, 0.5)
            .item(stoneSword, 4, 0.5)
            .item(ironCombatAxe, 6, 0.25)
            .item(ironUtilityAxe, 6, 0.25)
            .item(ironPickaxe, 6, 0.25)
            .item(ironHoe, 6, 0.25)
            .item(ironShovel, 6, 0.25)
            .item(ironSword, 6, 0.25)
            .item(goldenCombatAxe, 6, 0.25)
            .item(goldenUtilityAxe, 6, 0.25)
            .item(goldenPickaxe, 6, 0.25)
            .item(goldenHoe, 6, 0.25)
            .item(goldenShovel, 6, 0.25)
            .item(goldenSword, 6, 0.25)
            .item(diamondCombatAxe, 7, 0.125)
            .item(diamondUtilityAxe, 7, 0.125)
            .item(diamondPickaxe, 7, 0.125)
            .item(diamondHoe, 7, 0.125)
            .item(diamondShovel, 7, 0.125)
            .item(diamondSword, 7, 0.125)
            .item(netheriteCombatAxe, 8, 0.05)
            .item(netheriteUtilityAxe, 8, 0.05)
            .item(netheritePickaxe, 8, 0.05)
            .item(netheriteHoe, 8, 0.05)
            .item(netheriteShovel, 8, 0.05)
            .item(netheriteSword, 8, 0.05)
            .item(bow, 2, 0.5)
            .item(crossbow, 3, 0.5)
            .item(arrow, 1, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 2), Weight.of(16, 1))
            .item(spectralArrow, 3, 1.25, Weight.of(1, 2), Weight.of(2, 1))
            .item(trident, 3, 0.5)


            //Armor
            .item(turtleHelmet, 6, 0.25)
            .item(leatherHelmet, 3, 1.0)
            .item(fragileLeatherHelmet, 6, 0.125)
            .item(leatherChestplate, 3, 1.0)
            .item(fragileLeatherChestplate, 6, 0.125)
            .item(leatherLeggings, 3, 1.0)
            .item(fragileLeatherLeggings, 6, 0.125)
            .item(leatherBoots, 3, 1.0)
            .item(fragileLeatherBoots, 6, 0.125)
            .item(chainmailHelmet, 4, 0.5)
            .item(chainmailChestplate, 4, 0.5)
            .item(chainmailLeggings, 4, 0.5)
            .item(chainmailBoots, 4, 0.5)
            .item(ironHelmet, 6, 0.25)
            .item(ironChestplate, 6, 0.25)
            .item(ironLeggings, 6, 0.25)
            .item(ironBoots, 6, 0.25)
            .item(goldenHelmet, 6, 0.25)
            .item(goldenChestplate, 6, 0.25)
            .item(goldenLeggings, 6, 0.25)
            .item(goldenBoots, 6, 0.25)
            .item(diamondHelmet, 7, 0.125)
            .item(diamondChestplate, 7, 0.125)
            .item(diamondLeggings, 7, 0.125)
            .item(diamondBoots, 7, 0.125)
            .item(netheriteHelmet, 8, 0.05)
            .item(netheriteChestplate, 8, 0.05)
            .item(netheriteLeggings, 8, 0.05)
            .item(netheriteBoots, 8, 0.05)

            //Horse Equipment
            .item(lead, 2, 1.0)
            .item(saddle, 3, 0.5)
            .item(ironHorseArmor, 3, 0.5)
            .item(goldenHorseArmor, 4, 0.5)
            .item(diamondHorseArmor, 5, 0.25)

            //Enchanting
            .item(experienceBottle, 3, 1.25, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(book, 2, 1.25, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(mendingBook, 6, 0.2)
            .item(unbreakingBook, 6, 0.2)
            .item(curseOfVanishingBook, 6, 0.2)
            .item(aquaAffinityBook, 6, 0.2)
            .item(blastProtectionBook, 6, 0.2)
            .item(curseOfBindingBook, 6, 0.2)
            .item(depthStriderBook, 6, 0.2)
            .item(featherFallingBook, 6, 0.2)
            .item(fireProtectionBook, 6, 0.2)
            .item(frostWalkerBook, 6, 0.2)
            .item(projectileProtectionBook, 6, 0.2)
            .item(protectionBook, 6, 0.2)
            .item(respirationBook, 6, 0.2)
            .item(soulSpeedBook, 6, 0.2)
            .item(swiftSneakBook, 6, 0.2)
            .item(thornsBook, 6, 0.2)
            .item(baneOfArthropodsBook, 6, 0.2)
            .item(fireAspectBook, 6, 0.2)
            .item(lootingBook, 6, 0.2)
            .item(impalingBook, 6, 0.2)
            .item(knockbackBook, 6, 0.2)
            .item(sharpnessBook, 6, 0.2)
            .item(smiteBook, 6, 0.2)
            .item(sweepingEdgeBook, 6, 0.2)
            .item(flameBook, 6, 0.2)
            .item(infinityBook, 6, 0.2)
            .item(loyaltyBook, 6, 0.2)
            .item(riptideBook, 6, 0.2)
            .item(multishotBook, 6, 0.2)
            .item(piercingBook, 6, 0.2)
            .item(powerBook, 6, 0.2)
            .item(punchBook, 6, 0.2)
            .item(quickChargeBook, 6, 0.2)
            .item(efficiencyBook, 6, 0.2)
            .item(fortuneBook, 6, 0.2)
            .item(luckOfTheSeaBook, 6, 0.2)
            .item(lureBook, 6, 0.2)
            .item(silkTouchBook, 6, 0.2)
            .item(rollingHillsTargetingBook, 6, 0.5)
            .item(yawningCavernTargetingBook, 6, 0.5)
            .item(undergrowthTargetingBook, 6, 0.5)
            .item(crackedWastelandTargetingBook, 6, 0.5)
            .item(cambrianEstuaryTargetingBook, 6, 0.5)
            .item(boggedCloudforestTargetingBook, 6, 0.5)
            .item(gleamingDepthsTargetingBook, 6, 0.5)
            .item(desolatePeaksTargetingBook, 6, 0.5)
            .item(kaleidoscopeLibraryTargetingBook, 6, 0.5)
            .item(scorchedTerrorscapeTargetingBook, 6, 0.5)
            .item(unfathomableAbyssTargetingBook, 6, 0.5)
            .item(groundZeroTargetingBook, 6, 0.5)

            //Brewing
            .item(blazePowder, 3, 2.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(fermentedSpiderEye, 3, 1.0)
            .item(sugar, 2, 1.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(glisteringMelonSlice, 3, 1.0, Weight.of(1, 4), Weight.of(2, 1))
            .item(waterBottle, 2, 5.0)
            .item(potion, 4, 2.5)

            //Music Disc
            .item(musicDiscChirp, 3, 0.05)
            .item(musicDiscBlocks, 3, 0.05)
            .item(musicDisc13, 3, 0.05)
            .item(musicDiscCat, 3, 0.05)
            .item(musicDiscMall, 3, 0.05)
            .item(musicDiscStrad, 3, 0.05)
            .item(musicDiscMellohi, 3, 0.05)
            .item(musicDiscStal, 3, 0.05)
            .item(musicDiscFar, 3, 0.05)
            .item(musicDiscWait, 3, 0.05)
            .item(musicDiscPigstep, 3, 0.05)
            .item(discFragment5, 3, 0.05)
            .item(musicDiscOtherside, 3, 0.05)
            .item(musicDiscWard, 3, 0.05)
            .item(musicDisc11, 3, 0.05)

            .build();

    //Chest
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> infiniteChest = (e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c);

    //Phase 1
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> grassBlock = Util.defaultData(Material.GRASS_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> oakLog = Util.defaultData(Material.OAK_LOG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> birchLog = Util.defaultData(Material.BIRCH_LOG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> gravel = Util.defaultData(Material.GRAVEL);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sand = Util.defaultData(Material.SAND);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> clay = Util.defaultData(Material.CLAY);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cow = EntityUtil.spawnEntity(EntityType.COW);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sheep = EntityUtil.spawnEntity(EntityType.SHEEP);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> rabbit = EntityUtil.spawnEntity(EntityType.RABBIT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> slime = EntityUtil.spawnSlime();

    //Phase 2
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> stone = Util.defaultData(Material.STONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> dirt = Util.defaultData(Material.DIRT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> granite = Util.defaultData(Material.GRANITE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> diorite = Util.defaultData(Material.DIORITE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> andesite = Util.defaultData(Material.ANDESITE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> coalOre = Util.defaultData(Material.COAL_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ironOre = Util.defaultData(Material.IRON_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> copperOre = Util.defaultData(Material.COPPER_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> calcite = Util.defaultData(Material.CALCITE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> amethystBlock = Util.defaultData(Material.AMETHYST_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> rootedDirt = Util.defaultData(Material.ROOTED_DIRT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mossBlock = Util.defaultData(Material.MOSS_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> azaleaLeaves = Util.leaves(Material.AZALEA_LEAVES);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> floweringAzaleaLeaves = Util.leaves(Material.FLOWERING_AZALEA_LEAVES);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> bat = EntityUtil.spawnEntity(EntityType.BAT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> zombie = EntityUtil.spawnZombie();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> skeleton = EntityUtil.spawnSkeleton();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> axolotl = EntityUtil.spawnEntity(EntityType.AXOLOTL);

    //Phase 3
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> podzol = Util.defaultData(Material.PODZOL);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> coarseDirt = Util.defaultData(Material.COARSE_DIRT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mossyCobblestone = Util.defaultData(Material.MOSSY_COBBLESTONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> spruceLog = Util.defaultData(Material.SPRUCE_LOG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> darkOakLog = Util.defaultData(Material.DARK_OAK_LOG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> beeNest = Util.defaultData(Material.BEE_NEST);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> pumpkin = Util.defaultData(Material.PUMPKIN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> honeycombBlock = Util.defaultData(Material.HONEYCOMB_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redMushroomBlock = Util.defaultData(Material.RED_MUSHROOM_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> brownMushroomBlock = Util.defaultData(Material.BROWN_MUSHROOM_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mushroomStem = Util.defaultData(Material.MUSHROOM_STEM);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> bee = EntityUtil.spawnEntity(EntityType.BEE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> wolf = EntityUtil.spawnEntity(EntityType.WOLF);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> fox = EntityUtil.spawnEntity(EntityType.FOX);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> chicken = EntityUtil.spawnEntity(EntityType.CHICKEN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> spider = EntityUtil.spawnEntity(EntityType.SPIDER);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> babyZombie = EntityUtil.spawnBabyZombie();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> creeper = EntityUtil.spawnCreeper();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> vindicator = EntityUtil.spawnEntity(EntityType.VINDICATOR);

    //Phase 4
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redSand = Util.defaultData(Material.RED_SAND);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redSandstone = Util.defaultData(Material.RED_SANDSTONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> terracotta = Util.defaultData(Material.TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redTerracotta = Util.defaultData(Material.RED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> orangeTerracotta = Util.defaultData(Material.ORANGE_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> yellowTerracotta = Util.defaultData(Material.YELLOW_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> brownTerracotta = Util.defaultData(Material.BROWN_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> whiteTerracotta = Util.defaultData(Material.WHITE_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lightGrayTerracotta = Util.defaultData(Material.LIGHT_GRAY_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> acaciaLog = Util.defaultData(Material.ACACIA_LOG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cactus = Util.defaultData(Material.CACTUS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> boneBlock = Util.defaultData(Material.BONE_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> goldOre = Util.defaultData(Material.GOLD_ORE);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> horse = EntityUtil.spawnEntity(EntityType.HORSE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> llama = EntityUtil.spawnEntity(EntityType.LLAMA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> donkey = EntityUtil.spawnEntity(EntityType.DONKEY);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> husk = EntityUtil.spawnHusk();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> babyHusk = EntityUtil.spawnBabyHusk();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> pillager = EntityUtil.spawnEntity(EntityType.PILLAGER);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> zombieVillager = EntityUtil.spawnZombieVillager();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> babyZombieVillager = EntityUtil.spawnBabyZombieVillager();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> wanderingTrader = EntityUtil.spawnEntity(EntityType.WANDERING_TRADER);

    //Phase 5
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sandstone = Util.defaultData(Material.SANDSTONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> wetSponge = Util.defaultData(Material.WET_SPONGE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> tubeCoralBlock = Util.defaultData(Material.TUBE_CORAL_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> brainCoralBlock = Util.defaultData(Material.BRAIN_CORAL_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> bubbleCoralBlock = Util.defaultData(Material.BUBBLE_CORAL_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> fireCoralBlock = Util.defaultData(Material.FIRE_CORAL_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> hornCoralBlock = Util.defaultData(Material.HORN_CORAL_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> prismarine = Util.defaultData(Material.PRISMARINE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> prismarineBricks = Util.defaultData(Material.PRISMARINE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> darkPrismarine = Util.defaultData(Material.DARK_PRISMARINE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> seaLantern = Util.defaultData(Material.SEA_LANTERN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> magmaBlock = Util.defaultData(Material.MAGMA_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedSpruceStairs = Util.waterlogged(Material.SPRUCE_STAIRS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedOakStairs = Util.waterlogged(Material.OAK_STAIRS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedDarkOakStairs = Util.waterlogged(Material.DARK_OAK_STAIRS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedSpruceFence = Util.waterlogged(Material.SPRUCE_FENCE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedOakFence = Util.waterlogged(Material.OAK_FENCE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedDarkOakFence = Util.waterlogged(Material.DARK_OAK_FENCE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedSpruceSlab = Util.waterlogged(Material.SPRUCE_SLAB);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedOakSlab = Util.waterlogged(Material.OAK_SLAB);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedDarkOakSlab = Util.waterlogged(Material.DARK_OAK_SLAB);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cod = EntityUtil.spawnEntity(EntityType.COD);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> salmon = EntityUtil.spawnEntity(EntityType.SALMON);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> pufferfish = EntityUtil.spawnEntity(EntityType.PUFFERFISH);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> tropicalFish = EntityUtil.spawnEntity(EntityType.TROPICAL_FISH);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> squid = EntityUtil.spawnEntity(EntityType.SQUID);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> turtle = EntityUtil.spawnEntity(EntityType.TURTLE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> dolphin = EntityUtil.spawnEntity(EntityType.DOLPHIN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> drowned = EntityUtil.spawnDrowned();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> babyDrowned = EntityUtil.spawnBabyDrowned();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> guardian = EntityUtil.spawnEntity(EntityType.GUARDIAN);

    //Phase 6
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mud = Util.defaultData(Material.MUD);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mycelium = Util.defaultData(Material.MYCELIUM);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> soulSand = Util.defaultData(Material.SOUL_SAND);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> jungleLog = Util.defaultData(Material.JUNGLE_LOG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mangroveLog = Util.defaultData(Material.MANGROVE_LOG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mangroveRoots = Util.defaultData(Material.MANGROVE_ROOTS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> waterloggedMangroveRoots = Util.waterlogged(Material.MANGROVE_ROOTS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> muddyMangroveRoots = Util.defaultData(Material.MUDDY_MANGROVE_ROOTS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> melon = Util.defaultData(Material.MELON);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> pearlescentFroglight = Util.defaultData(Material.PEARLESCENT_FROGLIGHT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ochreFroglight = Util.defaultData(Material.OCHRE_FROGLIGHT);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> pig = EntityUtil.spawnEntity(EntityType.PIG);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mooshroom = EntityUtil.spawnEntity(EntityType.MUSHROOM_COW);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> tadpole = EntityUtil.spawnEntity(EntityType.TADPOLE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> warmFrog = EntityUtil.spawnFrog(Frog.Variant.WARM);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> temperateFrog = EntityUtil.spawnFrog(Frog.Variant.TEMPERATE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ocelot = EntityUtil.spawnEntity(EntityType.OCELOT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> parrot = EntityUtil.spawnEntity(EntityType.PARROT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> panda = EntityUtil.spawnEntity(EntityType.PANDA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> bigSlime = EntityUtil.spawnBigSlime();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> witch = EntityUtil.spawnEntity(EntityType.WITCH);

    //Phase 7
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslate = Util.defaultData(Material.DEEPSLATE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> tuff = Util.defaultData(Material.TUFF);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateCoalOre = Util.defaultData(Material.DEEPSLATE_COAL_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateIronOre = Util.defaultData(Material.DEEPSLATE_IRON_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateCopperOre = Util.defaultData(Material.DEEPSLATE_COPPER_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateGoldOre = Util.defaultData(Material.DEEPSLATE_GOLD_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redstoneOre = Util.defaultData(Material.REDSTONE_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateRedstoneOre = Util.defaultData(Material.DEEPSLATE_REDSTONE_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lapisOre = Util.defaultData(Material.LAPIS_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateLapisOre = Util.defaultData(Material.DEEPSLATE_LAPIS_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> diamondOre = Util.defaultData(Material.DIAMOND_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateDiamondOre = Util.defaultData(Material.DEEPSLATE_DIAMOND_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> dripstoneBlock = Util.defaultData(Material.DRIPSTONE_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> obsidian = Util.defaultData(Material.OBSIDIAN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> tnt = Util.defaultData(Material.TNT);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> glowSquid = EntityUtil.spawnEntity(EntityType.GLOW_SQUID);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedZombie1 = EntityUtil.spawnUpgradedZombie1();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedZombie2 = EntityUtil.spawnUpgradedZombie2();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedSkeleton1 = EntityUtil.spawnUpgradedSkeleton1();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedSkeleton2 = EntityUtil.spawnUpgradedSkeleton2();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> caveSpider = EntityUtil.spawnEntity(EntityType.CAVE_SPIDER);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> spiderJockey = EntityUtil.spawnSpiderJockey();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> chickenJockey = EntityUtil.spawnChickenJockey();

    //Phase 8
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> infestedStone = Util.defaultData(Material.INFESTED_STONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> infestedDeepslate = Util.defaultData(Material.INFESTED_DEEPSLATE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> emeraldOre = Util.defaultData(Material.EMERALD_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateEmeraldOre = Util.defaultData(Material.DEEPSLATE_EMERALD_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> dirtPath = Util.defaultData(Material.DIRT_PATH);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> stoneBricks = Util.defaultData(Material.STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> crackedStoneBricks = Util.defaultData(Material.CRACKED_STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> chiseledStoneBricks = Util.defaultData(Material.CHISELED_STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> infestedStoneBricks = Util.defaultData(Material.INFESTED_STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> infestedCrackedStoneBricks = Util.defaultData(Material.INFESTED_CRACKED_STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> infestedChiseledStoneBricks = Util.defaultData(Material.INFESTED_CHISELED_STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ice = Util.defaultData(Material.ICE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> packedIce = Util.defaultData(Material.PACKED_ICE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blueIce = Util.defaultData(Material.BLUE_ICE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> snowBlock = Util.defaultData(Material.SNOW_BLOCK);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> goat = EntityUtil.spawnEntity(EntityType.GOAT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> polarBear = EntityUtil.spawnEntity(EntityType.POLAR_BEAR);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedHusk1 = EntityUtil.spawnUpgradedHusk1();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> stray = EntityUtil.spawnStray();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedStray1 = EntityUtil.spawnUpgradedStray1();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> villager = EntityUtil.spawnEntity(EntityType.VILLAGER);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ironGolem = EntityUtil.spawnEntity(EntityType.IRON_GOLEM);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> snowGolem = EntityUtil.spawnEntity(EntityType.SNOWMAN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> snowMan = EntityUtil.spawnSnowman();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedPillager = EntityUtil.spawnUpgradedPillager();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedVindicator = EntityUtil.spawnUpgradedVindicator();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> evoker = EntityUtil.spawnEntity(EntityType.EVOKER);

    //Phase 9
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> whiteGlazedTerracotta = Util.defaultData(Material.WHITE_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> orangeGlazedTerracotta = Util.defaultData(Material.ORANGE_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> magentaGlazedTerracotta = Util.defaultData(Material.MAGENTA_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lightBlueGlazedTerracotta = Util.defaultData(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> yellowGlazedTerracotta = Util.defaultData(Material.YELLOW_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> limeGlazedTerracotta = Util.defaultData(Material.LIME_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> pinkGlazedTerracotta = Util.defaultData(Material.PINK_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> grayGlazedTerracotta = Util.defaultData(Material.GRAY_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lightGrayGlazedTerracotta = Util.defaultData(Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cyanGlazedTerracotta = Util.defaultData(Material.CYAN_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> purpleGlazedTerracotta = Util.defaultData(Material.PURPLE_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blueGlazedTerracotta = Util.defaultData(Material.BLUE_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> brownGlazedTerracotta = Util.defaultData(Material.BROWN_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> greenGlazedTerracotta = Util.defaultData(Material.GREEN_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redGlazedTerracotta = Util.defaultData(Material.RED_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blackGlazedTerracotta = Util.defaultData(Material.BLACK_GLAZED_TERRACOTTA);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> whiteStainedGlass = Util.defaultData(Material.WHITE_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> orangeStainedGlass = Util.defaultData(Material.ORANGE_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> magentaStainedGlass = Util.defaultData(Material.MAGENTA_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lightBlueStainedGlass = Util.defaultData(Material.LIGHT_BLUE_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> yellowStainedGlass = Util.defaultData(Material.YELLOW_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> limeStainedGlass = Util.defaultData(Material.LIME_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> pinkStainedGlass = Util.defaultData(Material.PINK_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> grayStainedGlass = Util.defaultData(Material.GRAY_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lightGrayStainedGlass = Util.defaultData(Material.LIGHT_GRAY_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cyanStainedGlass = Util.defaultData(Material.CYAN_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> purpleStainedGlass = Util.defaultData(Material.PURPLE_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blueStainedGlass = Util.defaultData(Material.BLUE_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> brownStainedGlass = Util.defaultData(Material.BROWN_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> greenStainedGlass = Util.defaultData(Material.GREEN_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redStainedGlass = Util.defaultData(Material.RED_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blackStainedGlass = Util.defaultData(Material.BLACK_STAINED_GLASS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> strippedOakWood = Util.defaultData(Material.STRIPPED_OAK_WOOD);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> strippedSpruceWood = Util.defaultData(Material.STRIPPED_SPRUCE_WOOD);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> strippedBirchWood = Util.defaultData(Material.STRIPPED_BIRCH_WOOD);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> strippedDarkOakWood = Util.defaultData(Material.STRIPPED_DARK_OAK_WOOD);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> oakPlanks = Util.defaultData(Material.OAK_PLANKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sprucePlanks = Util.defaultData(Material.SPRUCE_PLANKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> birchPlanks = Util.defaultData(Material.BIRCH_PLANKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> darkOakPlanks = Util.defaultData(Material.DARK_OAK_PLANKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> bookshelf = Util.defaultData(Material.BOOKSHELF);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> warpedNylium = Util.defaultData(Material.WARPED_NYLIUM);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> warpedStem = Util.defaultData(Material.WARPED_STEM);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> warpedWartBlock = Util.defaultData(Material.WARPED_WART_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> shroomlight = Util.defaultData(Material.SHROOMLIGHT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cauldron = Util.defaultData(Material.CAULDRON);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> filledCauldron = Util.cauldron();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lapisBlock = Util.defaultData(Material.LAPIS_BLOCK);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> allay = EntityUtil.spawnEntity(EntityType.ALLAY);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cat = EntityUtil.spawnEntity(EntityType.CAT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ascendedSkeleton = EntityUtil.spawnAscendedSkeleton();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> skeletonHorse = EntityUtil.spawnEntity(EntityType.SKELETON_HORSE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> witherSkeleton = EntityUtil.spawnEntity(EntityType.WITHER_SKELETON);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> silverfishMob = EntityUtil.spawnSilverfishMob();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> endermiteMob = EntityUtil.spawnEndermiteMob();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ascendedWitch = EntityUtil.spawnAscendedWitch();

    //Phase 10
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> netherrack = Util.defaultData(Material.NETHERRACK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> flamingNetherrack = Util.flamingBlock(Material.NETHERRACK, false);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> glowstone = Util.defaultData(Material.GLOWSTONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> netherBricks = Util.defaultData(Material.NETHER_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> soulSoil = Util.defaultData(Material.SOUL_SOIL);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> flamingSoulSoil = Util.flamingBlock(Material.SOUL_SOIL, true);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> crimsonNylium = Util.defaultData(Material.CRIMSON_NYLIUM);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> crimsonStem = Util.defaultData(Material.CRIMSON_STEM);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> netherWartBlock = Util.defaultData(Material.NETHER_WART_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> basalt = Util.defaultData(Material.BASALT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> polishedBasalt = Util.defaultData(Material.POLISHED_BASALT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blackstone = Util.defaultData(Material.BLACKSTONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> gildedBlackstone = Util.defaultData(Material.GILDED_BLACKSTONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> polishedBlackstoneBricks = Util.defaultData(Material.POLISHED_BLACKSTONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> crackedPolishedBlackstoneBricks = Util.defaultData(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> chiseledPolishedBlackstone = Util.defaultData(Material.CHISELED_POLISHED_BLACKSTONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> quartzBlock = Util.defaultData(Material.QUARTZ_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> smoothQuartz = Util.defaultData(Material.SMOOTH_QUARTZ);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> netherQuartzOre = Util.defaultData(Material.NETHER_QUARTZ_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> netherGoldOre = Util.defaultData(Material.NETHER_GOLD_ORE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ancientDebris = Util.defaultData(Material.ANCIENT_DEBRIS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> goldBlock = Util.defaultData(Material.GOLD_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cryingObsidian = Util.defaultData(Material.CRYING_OBSIDIAN);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> piglin = EntityUtil.spawnPiglin();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> piglinBrute = EntityUtil.spawnPiglinBrute();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> zombifiedPiglin = EntityUtil.spawnEntity(EntityType.ZOMBIFIED_PIGLIN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> hoglin = EntityUtil.spawnHoglin();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> zoglin = EntityUtil.spawnEntity(EntityType.ZOGLIN);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> ghast = EntityUtil.spawnGhast();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> magmaCube = EntityUtil.spawnEntity(EntityType.MAGMA_CUBE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blaze = EntityUtil.spawnEntity(EntityType.BLAZE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> strider = EntityUtil.spawnEntity(EntityType.STRIDER);

    //Phase 11
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> coalBlock = Util.defaultData(Material.COAL_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> rawIronBlock = Util.defaultData(Material.RAW_IRON_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sculk = Util.defaultData(Material.SCULK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sculkSensor = Util.defaultData(Material.SCULK_SENSOR);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sculkCatalyst = Util.defaultData(Material.SCULK_CATALYST);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> sculkShrieker = Util.defaultData(Material.SCULK_SHRIEKER);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cobbledDeepslate = Util.defaultData(Material.COBBLED_DEEPSLATE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> polishedDeepslate = Util.defaultData(Material.POLISHED_DEEPSLATE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateBricks = Util.defaultData(Material.DEEPSLATE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> crackedDeepslateBricks = Util.defaultData(Material.CRACKED_DEEPSLATE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> deepslateTiles = Util.defaultData(Material.DEEPSLATE_TILES);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> crackedDeepslateTiles = Util.defaultData(Material.CRACKED_DEEPSLATE_TILES);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> chiseledDeepslate = Util.defaultData(Material.CHISELED_DEEPSLATE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> smoothBasalt = Util.defaultData(Material.SMOOTH_BASALT);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redstoneBlock = Util.defaultData(Material.REDSTONE_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> redstoneLamp = Util.defaultData(Material.REDSTONE_LAMP);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> target = Util.defaultData(Material.TARGET);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> noteBlock = Util.defaultData(Material.NOTE_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> grayWool = Util.defaultData(Material.GRAY_WOOL);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> blueWool = Util.defaultData(Material.BLUE_WOOL);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> cyanWool = Util.defaultData(Material.CYAN_WOOL);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> lightBlueWool = Util.defaultData(Material.LIGHT_BLUE_WOOL);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedZombie3 = EntityUtil.spawnUpgradedZombie3();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedSkeleton3 = EntityUtil.spawnUpgradedSkeleton3();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedHusk2 = EntityUtil.spawnUpgradedHusk2();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedStray2 = EntityUtil.spawnUpgradedStray2();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> upgradedCaveSpider = EntityUtil.spawnUpgradedCaveSpider();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> caveSpiderJockey = EntityUtil.spawnCaveSpiderJockey();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> chargedCreeper = EntityUtil.spawnChargedCreeper();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> enderman = EntityUtil.spawnEntity(EntityType.ENDERMAN);

    //Phase 12
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> endStone = Util.defaultData(Material.END_STONE);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> endStoneBricks = Util.defaultData(Material.END_STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> purpurBlock = Util.defaultData(Material.PURPUR_BLOCK);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> purpurPillar = Util.defaultData(Material.PURPUR_PILLAR);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> mossyStoneBricks = Util.defaultData(Material.MOSSY_STONE_BRICKS);
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> infestedMossyStoneBricks = Util.defaultData(Material.INFESTED_MOSSY_STONE_BRICKS);

    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> shulker = EntityUtil.spawnShulker();
    @NotNull
    static final BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> phantom = EntityUtil.spawnPhantom();

    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BossBar.Color.GREEN).blocks(Long.MAX_VALUE).boss((e, t) -> null).bossDeath((e, t) -> {
            }).rewards(LootDigest.builder("INFINITE_REWARDS").build())

            .result(infiniteChest, Weight.of(4, 5.0))

            //Phase 1
            .result(grassBlock, Weight.of(1, 15.0))
            .result(oakLog, Weight.of(1, 2.25))
            .result(birchLog, Weight.of(1, 1.5))
            .result(gravel, Weight.of(1, 1.5))
            .result(sand, Weight.of(1, 1.5))
            .result(clay, Weight.of(1, 1.5))

            .result(cow, Weight.of(2, 0.075))
            .result(sheep, Weight.of(2, 0.075))
            .result(rabbit, Weight.of(2, 0.075))
            .result(slime, Weight.of(2, 0.075))

            //Phase 2
            .result(stone, Weight.of(1, 16.0))
            .result(dirt, Weight.of(1, 5d / 3))
            .result(granite, Weight.of(1, 1.0))
            .result(diorite, Weight.of(1, 1.0))
            .result(andesite, Weight.of(1, 1.0))
            .result(gravel, Weight.of(1, 4d / 3))
            .result(coalOre, Weight.of(2, 1.0))
            .result(ironOre, Weight.of(3, 5d / 6))
            .result(copperOre, Weight.of(2, 2d / 3))
            .result(calcite, Weight.of(3, 0.5))
            .result(amethystBlock, Weight.of(3, 0.5))
            .result(rootedDirt, Weight.of(1, 1.0))
            .result(mossBlock, Weight.of(1, 1.0))
            .result(azaleaLeaves, Weight.of(1, 2d / 3))
            .result(floweringAzaleaLeaves, Weight.of(1, 2d / 3))
            .result(clay, Weight.of(1, 2d / 3))

            .result(bat, Weight.of(1, 0.5))
            .result(zombie, Weight.of(2, 0.25))
            .result(skeleton, Weight.of(2, 0.25))
            .result(axolotl, Weight.of(4, 1d / 30))

            //Phase 3
            .result(podzol, Weight.of(1, 6.25))
            .result(coarseDirt, Weight.of(1, 3.75))
            .result(mossyCobblestone, Weight.of(1, 3.75))
            .result(grassBlock, Weight.of(1, 6.25))
            .result(birchLog, Weight.of(1, 1.25))
            .result(spruceLog, Weight.of(1, 1.25))
            .result(darkOakLog, Weight.of(1, 1.0))
            .result(beeNest, Weight.of(3, 0.125))
            .result(pumpkin, Weight.of(3, 0.75))
            .result(honeycombBlock, Weight.of(2, 0.75))
            .result(coalOre, Weight.of(2, 0.875))
            .result(ironOre, Weight.of(3, 0.75))
            .result(redMushroomBlock, Weight.of(2, 0.75))
            .result(brownMushroomBlock, Weight.of(2, 0.75))
            .result(mushroomStem, Weight.of(2, 0.75))

            .result(bee, Weight.of(3, 0.0625))
            .result(wolf, Weight.of(3, 0.0625))
            .result(fox, Weight.of(3, 0.0625))
            .result(chicken, Weight.of(2, 0.1))
            .result(rabbit, Weight.of(2, 0.1))
            .result(spider, Weight.of(2, 0.25))
            .result(babyZombie, Weight.of(3, 0.075))
            .result(creeper, Weight.of(3, 0.075))
            .result(vindicator, Weight.of(3, 0.125))

            //Phase 4
            .result(redSand, Weight.of(1, 5.0))
            .result(redSandstone, Weight.of(1, 2.5))
            .result(terracotta, Weight.of(1, 2.5))
            .result(redTerracotta, Weight.of(1, 2.5))
            .result(orangeTerracotta, Weight.of(1, 2.5))
            .result(yellowTerracotta, Weight.of(1, 2.5))
            .result(brownTerracotta, Weight.of(1, 2.5))
            .result(whiteTerracotta, Weight.of(1, 2.5))
            .result(lightGrayTerracotta, Weight.of(1, 2.5))
            .result(acaciaLog, Weight.of(1, 2.5))
            .result(cactus, Weight.of(1, 1.25))
            .result(coarseDirt, Weight.of(1, 1.25))
            .result(boneBlock, Weight.of(3, 0.875))
            .result(coalOre, Weight.of(2, 0.875))
            .result(ironOre, Weight.of(3, 0.75))
            .result(goldOre, Weight.of(4, 0.625))

            .result(horse, Weight.of(2, 0.05))
            .result(llama, Weight.of(3, 0.025))
            .result(donkey, Weight.of(3, 0.025))
            .result(husk, Weight.of(2, 0.125))
            .result(babyHusk, Weight.of(3, 0.02))
            .result(skeleton, Weight.of(2, 0.15))
            .result(creeper, Weight.of(2, 0.1))
            .result(pillager, Weight.of(2, 0.15))
            .result(vindicator, Weight.of(2, 0.1))
            .result(zombieVillager, Weight.of(2, 0.125))
            .result(babyZombieVillager, Weight.of(3, 0.02))
            .result(wanderingTrader, Weight.of(4, 0.0125))

            //Phase 5
            .result(sand, Weight.of(1, 12.0))
            .result(sandstone, Weight.of(1, 6.0))
            .result(gravel, Weight.of(1, 2.0))
            .result(clay, Weight.of(1, 2.0))
            .result(wetSponge, Weight.of(3, 0.4))
            .result(tubeCoralBlock, Weight.of(1, 0.6))
            .result(brainCoralBlock, Weight.of(1, 0.6))
            .result(bubbleCoralBlock, Weight.of(1, 0.6))
            .result(fireCoralBlock, Weight.of(1, 0.6))
            .result(hornCoralBlock, Weight.of(1, 0.6))
            .result(prismarine, Weight.of(2, 1.2))
            .result(prismarineBricks, Weight.of(2, 1.2))
            .result(darkPrismarine, Weight.of(3, 0.4))
            .result(seaLantern, Weight.of(3, 0.4))
            .result(magmaBlock, Weight.of(2, 0.6))
            .result(waterloggedSpruceStairs, Weight.of(1, 0.2))
            .result(waterloggedOakStairs, Weight.of(1, 0.2))
            .result(waterloggedDarkOakStairs, Weight.of(1, 0.2))
            .result(waterloggedSpruceFence, Weight.of(1, 0.2))
            .result(waterloggedOakFence, Weight.of(1, 0.2))
            .result(waterloggedDarkOakFence, Weight.of(1, 0.2))
            .result(waterloggedSpruceSlab, Weight.of(1, 0.2))
            .result(waterloggedOakSlab, Weight.of(1, 0.2))
            .result(waterloggedDarkOakSlab, Weight.of(1, 0.2))

            .result(cod, Weight.of(2, 0.08))
            .result(salmon, Weight.of(2, 0.08))
            .result(pufferfish, Weight.of(3, 0.06))
            .result(tropicalFish, Weight.of(2, 0.1))
            .result(squid, Weight.of(2, 0.08))
            .result(turtle, Weight.of(3, 0.06))
            .result(dolphin, Weight.of(3, 0.06))
            .result(drowned, Weight.of(2, 0.34))
            .result(babyDrowned, Weight.of(2, 0.06))
            .result(guardian, Weight.of(2, 0.2))

            //Phase 6
            .result(grassBlock, Weight.of(1, 6.0))
            .result(podzol, Weight.of(1, 4.0))
            .result(mossyCobblestone, Weight.of(1, 2.0))
            .result(mud, Weight.of(1, 6.0))
            .result(clay, Weight.of(1, 4.0))
            .result(mycelium, Weight.of(1, 1.0))
            .result(soulSand, Weight.of(1, 0.4))
            .result(jungleLog, Weight.of(1, 1.2))
            .result(mangroveLog, Weight.of(1, 1.2))
            .result(mangroveRoots, Weight.of(1, 0.4))
            .result(waterloggedMangroveRoots, Weight.of(1, 0.4))
            .result(muddyMangroveRoots, Weight.of(1, 0.4))
            .result(coalOre, Weight.of(1, 0.8))
            .result(ironOre, Weight.of(1, 0.7))
            .result(goldOre, Weight.of(1, 0.6))
            .result(melon, Weight.of(1, 0.4))
            .result(pearlescentFroglight, Weight.of(1, 0.2))
            .result(ochreFroglight, Weight.of(1, 0.2))

            .result(pig, Weight.of(1, 0.05))
            .result(mooshroom, Weight.of(4, 0.015))
            .result(tadpole, Weight.of(1, 0.03))
            .result(warmFrog, Weight.of(1, 0.03))
            .result(temperateFrog, Weight.of(1, 0.03))
            .result(ocelot, Weight.of(3, 0.05))
            .result(parrot, Weight.of(2, 0.04))
            .result(panda, Weight.of(3, 0.04))
            .result(bigSlime, Weight.of(1, 0.2))
            .result(drowned, Weight.of(1, 0.17))
            .result(babyDrowned, Weight.of(2, 0.03))
            .result(spider, Weight.of(1, 0.2))
            .result(creeper, Weight.of(2, 0.12))
            .result(witch, Weight.of(3, 0.12))

            //Phase 7
            .result(stone, Weight.of(1, 10.0))
            .result(deepslate, Weight.of(1, 10.0))
            .result(tuff, Weight.of(2, 5d / 3))
            .result(granite, Weight.of(1, 5d / 3))
            .result(andesite, Weight.of(1, 5d / 3))
            .result(diorite, Weight.of(1, 5d / 3))
            .result(coalOre, Weight.of(1, 0.5))
            .result(deepslateCoalOre, Weight.of(2, 0.5))
            .result(ironOre, Weight.of(2, 5d / 12))
            .result(deepslateIronOre, Weight.of(3, 5d / 12))
            .result(copperOre, Weight.of(1, 1d / 3))
            .result(deepslateCopperOre, Weight.of(2, 1d / 3))
            .result(goldOre, Weight.of(3, 0.25))
            .result(deepslateGoldOre, Weight.of(4, 0.25))
            .result(redstoneOre, Weight.of(3, 0.25))
            .result(deepslateRedstoneOre, Weight.of(4, 0.25))
            .result(lapisOre, Weight.of(3, 0.25))
            .result(deepslateLapisOre, Weight.of(4, 0.25))
            .result(diamondOre, Weight.of(4, 1d / 6))
            .result(deepslateDiamondOre, Weight.of(5, 1d / 6))
            .result(amethystBlock, Weight.of(2, 1d / 3))
            .result(calcite, Weight.of(2, 1d / 3))
            .result(dripstoneBlock, Weight.of(2, 1d / 3))
            .result(obsidian, Weight.of(3, 2d / 3))
            .result(tnt, Weight.of(3, 2d / 3))

            .result(bat, Weight.of(1, 1d / 3))
            .result(glowSquid, Weight.of(3, 1d / 30))
            .result(zombie, Weight.of(2, 1d / 12))
            .result(upgradedZombie1, Weight.of(2, 1d / 12))
            .result(upgradedZombie2, Weight.of(2, 1d / 12))
            .result(skeleton, Weight.of(2, 1d / 12))
            .result(upgradedSkeleton1, Weight.of(2, 1d / 12))
            .result(upgradedSkeleton2, Weight.of(2, 1d / 12))
            .result(caveSpider, Weight.of(3, 1d / 6))
            .result(creeper, Weight.of(3, 1d / 6))
            .result(spiderJockey, Weight.of(4, 1d / 12))
            .result(chickenJockey, Weight.of(4, 1d / 12))

            //Phase 8
            .result(stone, Weight.of(1, 8.0))
            .result(deepslate, Weight.of(1, 2.0))
            .result(infestedStone, Weight.of(2, 0.4))
            .result(infestedDeepslate, Weight.of(1, 0.4))
            .result(coalOre, Weight.of(1, 0.72))
            .result(deepslateCoalOre, Weight.of(2, 0.36))
            .result(ironOre, Weight.of(2, 0.6))
            .result(deepslateIronOre, Weight.of(3, 0.3))
            .result(diamondOre, Weight.of(4, 0.2))
            .result(deepslateDiamondOre, Weight.of(5, 0.1))
            .result(emeraldOre, Weight.of(4, 0.2))
            .result(deepslateEmeraldOre, Weight.of(5, 0.1))
            .result(dirt, Weight.of(1, 2.0))
            .result(gravel, Weight.of(1, 2.0))
            .result(dirtPath, Weight.of(1, 0.8))
            .result(stoneBricks, Weight.of(2, 0.8))
            .result(crackedStoneBricks, Weight.of(3, 0.8))
            .result(chiseledStoneBricks, Weight.of(4, 0.8))
            .result(infestedStoneBricks, Weight.of(3, 0.2))
            .result(infestedCrackedStoneBricks, Weight.of(4, 0.2))
            .result(infestedChiseledStoneBricks, Weight.of(3, 0.2))
            .result(ice, Weight.of(4, 2.0))
            .result(packedIce, Weight.of(4, 1.4))
            .result(blueIce, Weight.of(5, 0.8))
            .result(snowBlock, Weight.of(2, 8.0))

            .result(goat, Weight.of(2, 0.06))
            .result(rabbit, Weight.of(1, 0.12))
            .result(wolf, Weight.of(2, 0.06))
            .result(polarBear, Weight.of(3, 0.04))
            .result(husk, Weight.of(2, 0.08))
            .result(upgradedHusk1, Weight.of(3, 0.08))
            .result(stray, Weight.of(2, 0.08))
            .result(upgradedStray1, Weight.of(3, 0.08))
            .result(villager, Weight.of(3, 0.08))
            .result(ironGolem, Weight.of(4, 0.04))
            .result(snowGolem, Weight.of(4, 0.02))
            .result(snowMan, Weight.of(4, 0.02))
            .result(pillager, Weight.of(3, 0.08))
            .result(upgradedPillager, Weight.of(4, 0.08))
            .result(vindicator, Weight.of(3, 0.08))
            .result(upgradedVindicator, Weight.of(4, 0.08))
            .result(evoker, Weight.of(4, 0.04))

            //Phase 9
            .result(whiteGlazedTerracotta, Weight.of(1, 0.75))
            .result(orangeGlazedTerracotta, Weight.of(1, 0.75))
            .result(magentaGlazedTerracotta, Weight.of(1, 0.75))
            .result(lightBlueGlazedTerracotta, Weight.of(1, 0.75))
            .result(yellowGlazedTerracotta, Weight.of(1, 0.75))
            .result(limeGlazedTerracotta, Weight.of(1, 0.75))
            .result(pinkGlazedTerracotta, Weight.of(1, 0.75))
            .result(grayGlazedTerracotta, Weight.of(1, 0.75))
            .result(lightGrayGlazedTerracotta, Weight.of(1, 0.75))
            .result(cyanGlazedTerracotta, Weight.of(1, 0.75))
            .result(purpleGlazedTerracotta, Weight.of(1, 0.75))
            .result(blueGlazedTerracotta, Weight.of(1, 0.75))
            .result(brownGlazedTerracotta, Weight.of(1, 0.75))
            .result(greenGlazedTerracotta, Weight.of(1, 0.75))
            .result(redGlazedTerracotta, Weight.of(1, 0.75))
            .result(blackGlazedTerracotta, Weight.of(1, 0.75))
            .result(whiteStainedGlass, Weight.of(1, 0.225))
            .result(orangeStainedGlass, Weight.of(1, 0.225))
            .result(magentaStainedGlass, Weight.of(1, 0.225))
            .result(lightBlueStainedGlass, Weight.of(1, 0.225))
            .result(yellowStainedGlass, Weight.of(1, 0.225))
            .result(limeStainedGlass, Weight.of(1, 0.225))
            .result(pinkStainedGlass, Weight.of(1, 0.225))
            .result(grayStainedGlass, Weight.of(1, 0.225))
            .result(lightGrayStainedGlass, Weight.of(1, 0.225))
            .result(cyanStainedGlass, Weight.of(1, 0.225))
            .result(purpleStainedGlass, Weight.of(1, 0.225))
            .result(blueStainedGlass, Weight.of(1, 0.225))
            .result(brownStainedGlass, Weight.of(1, 0.225))
            .result(greenStainedGlass, Weight.of(1, 0.225))
            .result(redStainedGlass, Weight.of(1, 0.225))
            .result(blackStainedGlass, Weight.of(1, 0.225))
            .result(strippedOakWood, Weight.of(1, 1.2))
            .result(strippedSpruceWood, Weight.of(1, 1.2))
            .result(strippedBirchWood, Weight.of(1, 1.2))
            .result(strippedDarkOakWood, Weight.of(1, 1.2))
            .result(oakPlanks, Weight.of(1, 1.2))
            .result(sprucePlanks, Weight.of(1, 1.2))
            .result(birchPlanks, Weight.of(1, 1.2))
            .result(darkOakPlanks, Weight.of(1, 1.2))
            .result(bookshelf, Weight.of(2, 1.5))
            .result(warpedNylium, Weight.of(1, 3.0))
            .result(warpedStem, Weight.of(1, 1.5))
            .result(warpedWartBlock, Weight.of(1, 1.5))
            .result(shroomlight, Weight.of(2, 0.75))
            .result(cauldron, Weight.of(1, 0.0375))
            .result(filledCauldron, Weight.of(1, 0.1125))
            .result(deepslateRedstoneOre, Weight.of(3, 0.2625))
            .result(deepslateLapisOre, Weight.of(3, 0.225))
            .result(lapisBlock, Weight.of(4, 0.075))
            .result(deepslateDiamondOre, Weight.of(4, 0.1875))

            .result(allay, Weight.of(3, 0.075))
            .result(cat, Weight.of(4, 0.0375))
            .result(spider, Weight.of(1, 0.225))
            .result(skeleton, Weight.of(3, 0.1125))
            .result(upgradedSkeleton1, Weight.of(4, 0.1125))
            .result(ascendedSkeleton, Weight.of(5, 0.1125))
            .result(skeletonHorse, Weight.of(2, 0.075))
            .result(witherSkeleton, Weight.of(1, 0.075))
            .result(silverfishMob, Weight.of(1, 0.075))
            .result(endermiteMob, Weight.of(1, 0.075))
            .result(witch, Weight.of(3, 0.0375))
            .result(ascendedWitch, Weight.of(3, 0.0375))
            .result(evoker, Weight.of(2, 0.075))

            //Phase 10
            .result(netherrack, Weight.of(1, 7.5))
            .result(flamingNetherrack, Weight.of(1, 1.25))
            .result(gravel, Weight.of(1, 1.0))
            .result(glowstone, Weight.of(2, 1.0))
            .result(netherBricks, Weight.of(1, 2.5))
            .result(magmaBlock, Weight.of(2, 1.0))
            .result(soulSand, Weight.of(1, 2.5))
            .result(soulSoil, Weight.of(1, 2.5))
            .result(flamingSoulSoil, Weight.of(1, 0.5))
            .result(boneBlock, Weight.of(3, 0.5))
            .result(crimsonNylium, Weight.of(1, 2.5))
            .result(crimsonStem, Weight.of(1, 1.25))
            .result(netherWartBlock, Weight.of(1, 1.25))
            .result(shroomlight, Weight.of(2, 0.5))
            .result(basalt, Weight.of(1, 2.5))
            .result(polishedBasalt, Weight.of(1, 1.25))
            .result(blackstone, Weight.of(1, 1.25))
            .result(gildedBlackstone, Weight.of(2, 0.25))
            .result(polishedBlackstoneBricks, Weight.of(1, 0.375))
            .result(crackedPolishedBlackstoneBricks, Weight.of(1, 0.375))
            .result(chiseledPolishedBlackstone, Weight.of(1, 0.375))
            .result(quartzBlock, Weight.of(2, 0.25))
            .result(smoothQuartz, Weight.of(2, 0.25))
            .result(netherQuartzOre, Weight.of(2, 1.0))
            .result(netherGoldOre, Weight.of(2, 1.0))
            .result(ancientDebris, Weight.of(5, 0.0625))
            .result(goldBlock, Weight.of(3, 0.25))
            .result(obsidian, Weight.of(2, 1.25))
            .result(cryingObsidian, Weight.of(3, 0.25))

            .result(piglin, Weight.of(1, 0.25))
            .result(piglinBrute, Weight.of(2, 0.125))
            .result(zombifiedPiglin, Weight.of(1, 0.125))
            .result(hoglin, Weight.of(2, 0.0625))
            .result(zoglin, Weight.of(3, 0.0625))
            .result(ghast, Weight.of(3, 0.0625))
            .result(magmaCube, Weight.of(2, 0.25))
            .result(witherSkeleton, Weight.of(1, 0.125))
            .result(blaze, Weight.of(2, 0.125))
            .result(strider, Weight.of(2, 0.0625))

            //Phase 11
            .result(deepslate, Weight.of(1, 11.25))
            .result(blackstone, Weight.of(2, 1.875))
            .result(tuff, Weight.of(1, 1.875))
            .result(packedIce, Weight.of(1, 3.75))
            .result(blueIce, Weight.of(2, 1.875))
            .result(coalBlock, Weight.of(2, 0.28125))
            .result(rawIronBlock, Weight.of(3, 0.28125))
            .result(deepslateRedstoneOre, Weight.of(4, 0.375))
            .result(deepslateLapisOre, Weight.of(4, 0.375))
            .result(deepslateDiamondOre, Weight.of(5, 0.28125))
            .result(sculk, Weight.of(2, 7.5))
            .result(sculkSensor, Weight.of(2, 1.875))
            .result(sculkCatalyst, Weight.of(3, 0.75))
            .result(sculkShrieker, Weight.of(3, 0.375))
            .result(cobbledDeepslate, Weight.of(1, 0.375))
            .result(polishedDeepslate, Weight.of(1, 0.1875))
            .result(deepslateBricks, Weight.of(1, 0.375))
            .result(crackedDeepslateBricks, Weight.of(1, 0.1875))
            .result(deepslateTiles, Weight.of(1, 0.375))
            .result(crackedDeepslateTiles, Weight.of(1, 0.1875))
            .result(chiseledDeepslate, Weight.of(1, 0.28125))
            .result(soulSand, Weight.of(1, 0.28125))
            .result(polishedBasalt, Weight.of(1, 0.28125))
            .result(smoothBasalt, Weight.of(1, 0.28125))
            .result(darkOakLog, Weight.of(1, 0.375))
            .result(darkOakPlanks, Weight.of(1, 0.375))
            .result(bookshelf, Weight.of(2, 0.375))
            .result(redstoneBlock, Weight.of(4, 0.375))
            .result(redstoneLamp, Weight.of(3, 0.075))
            .result(target, Weight.of(2, 0.075))
            .result(noteBlock, Weight.of(2, 0.075))
            .result(grayWool, Weight.of(1, 0.15))
            .result(blueWool, Weight.of(1, 0.075))
            .result(cyanWool, Weight.of(1, 0.075))
            .result(lightBlueWool, Weight.of(1, 0.075))

            .result(bat, Weight.of(1, 0.1875))
            .result(zombie, Weight.of(1, 0.06375))
            .result(babyZombie, Weight.of(2, 0.01125))
            .result(upgradedZombie2, Weight.of(3, 0.05625))
            .result(upgradedZombie3, Weight.of(4, 0.0375))
            .result(skeleton, Weight.of(1, 0.075))
            .result(upgradedSkeleton2, Weight.of(3, 0.05625))
            .result(upgradedZombie3, Weight.of(4, 0.0375))
            .result(husk, Weight.of(1, 0.06375))
            .result(babyHusk, Weight.of(2, 0.01125))
            .result(upgradedHusk1, Weight.of(3, 0.05625))
            .result(upgradedHusk2, Weight.of(4, 0.0375))
            .result(stray, Weight.of(1, 0.075))
            .result(upgradedStray1, Weight.of(3, 0.05625))
            .result(upgradedStray2, Weight.of(4, 0.0375))
            .result(caveSpider, Weight.of(2, 0.075))
            .result(upgradedCaveSpider, Weight.of(3, 0.05625))
            .result(caveSpiderJockey, Weight.of(3, 0.05625))
            .result(chargedCreeper, Weight.of(4, 0.0375))
            .result(silverfishMob, Weight.of(2, 0.05625))
            .result(enderman, Weight.of(3, 0.05625))

            //Phase 12
            .result(endStone, Weight.of(1, 10.0))
            .result(obsidian, Weight.of(1, 1.5))
            .result(cryingObsidian, Weight.of(2, 0.5))
            .result(endStoneBricks, Weight.of(1, 1.0))
            .result(purpurBlock, Weight.of(1, 1.5))
            .result(purpurPillar, Weight.of(1, 0.5))
            .result(magentaStainedGlass, Weight.of(1, 0.25))
            .result(purpleStainedGlass, Weight.of(1, 0.125))
            .result(cyanStainedGlass, Weight.of(1, 0.125))
            .result(blueStainedGlass, Weight.of(1, 0.125))
            .result(sculk, Weight.of(1, 2.5))
            .result(sculkSensor, Weight.of(2, 0.625))
            .result(sculkCatalyst, Weight.of(3, 0.125))
            .result(sculkShrieker, Weight.of(3, 0.0625))
            .result(blueIce, Weight.of(2, 2.5))
            .result(ancientDebris, Weight.of(5, 0.125))
            .result(boneBlock, Weight.of(3, 0.5))
            .result(stoneBricks, Weight.of(1, 0.5))
            .result(crackedStoneBricks, Weight.of(1, 0.25))
            .result(mossyStoneBricks, Weight.of(1, 0.25))
            .result(infestedStoneBricks, Weight.of(1, 0.25))
            .result(infestedCrackedStoneBricks, Weight.of(1, 0.125))
            .result(infestedMossyStoneBricks, Weight.of(1, 0.125))
            .result(bookshelf, Weight.of(2, 0.25))

            .result(enderman, Weight.of(1, 0.625))
            .result(endermiteMob, Weight.of(1, 0.25))
            .result(shulker, Weight.of(2, 0.25))
            .result(phantom, Weight.of(3, 0.25))

            .build();


    public static final InfiniteTags<Supplier<@NotNull ItemStack>> ITEM_TAGS = new InfiniteTags.InfiniteTagsBuilder<Supplier<@NotNull ItemStack>>()
            //Blocks
            .tag(oakLogStack, RollingHills.PHASE)
            .tag(oakSapling, RollingHills.PHASE)
            .tag(birchLogStack, RollingHills.PHASE, TheUndergrowth.PHASE)
            .tag(birchSapling, TheUndergrowth.PHASE)
            .tag(spruceLogStack, TheUndergrowth.PHASE)
            .tag(spruceSapling, TheUndergrowth.PHASE)
            .tag(darkOakLogStack, TheUndergrowth.PHASE)
            .tag(darkOakSapling, TheUndergrowth.PHASE)
            .tag(acaciaLogStack, CrackedWasteland.PHASE)
            .tag(acaciaSapling, CrackedWasteland.PHASE)
            .tag(jungleLogStack, BoggedCloudforest.PHASE)
            .tag(jungleSapling, BoggedCloudforest.PHASE)
            .tag(mangroveLogStack, BoggedCloudforest.PHASE)
            .tag(mangrovePropagule, BoggedCloudforest.PHASE)
            .tag(warpedStemStack, KaleidoscopeLibrary.PHASE)
            .tag(warpedFungus, KaleidoscopeLibrary.PHASE)
            .tag(crimsonStemStack, ScorchedTerrorscape.PHASE)
            .tag(crimsonFungus, ScorchedTerrorscape.PHASE)
            .tag(tntStack, GleamingDepths.PHASE)
            .tag(sculkStack, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(sculkSensorStack, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(sculkCatalystStack, UnfathomableAbyss.PHASE, GroundZero.PHASE)

            //Hostile Mob Drops
            .tag(rottenFlesh, YawningCavern.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, YawningCavern.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(bone, TheUndergrowth.PHASE, CrackedWasteland.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, YawningCavern.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(string, TheUndergrowth.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(gunpowder, TheUndergrowth.PHASE, CrackedWasteland.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(slimeBall, RollingHills.PHASE, BoggedCloudforest.PHASE)
            .tag(spiderEye, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(blazeRod, ScorchedTerrorscape.PHASE)
            .tag(magmaCream, ScorchedTerrorscape.PHASE)
            .tag(ghastTear, ScorchedTerrorscape.PHASE)
            .tag(enderPearl, GroundZero.PHASE)
            .tag(shulkerShell, GroundZero.PHASE)
            .tag(phantomMembrane, GroundZero.PHASE)
            .tag(dragonBreath, GroundZero.PHASE)

            //Passive Mob Drops
            .tag(leather, RollingHills.PHASE, CrackedWasteland.PHASE, BoggedCloudforest.PHASE, ScorchedTerrorscape.PHASE)
            .tag(rabbitHide, RollingHills.PHASE, TheUndergrowth.PHASE, DesolatePeaks.PHASE)
            .tag(feather, TheUndergrowth.PHASE)
            .tag(rabbitFoot, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE)

            //Growable Items
            .tag(wheatSeeds, RollingHills.PHASE)
            .tag(potato, RollingHills.PHASE, CrackedWasteland.PHASE, DesolatePeaks.PHASE)
            .tag(carrot, RollingHills.PHASE, CrackedWasteland.PHASE)
            .tag(pumpkinSeeds, TheUndergrowth.PHASE)
            .tag(melonSeeds, BoggedCloudforest.PHASE)
            .tag(cocoa, BoggedCloudforest.PHASE)
            .tag(sugarCane, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(beetrootSeeds, DesolatePeaks.PHASE, GroundZero.PHASE)

            //Raw Foods
            .tag(wheat, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, GleamingDepths.PHASE)
            .tag(apple, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(goldenApple, CrackedWasteland.PHASE, CambrianEstuary.PHASE, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(enchantedGoldenApple, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenCarrot, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(glowBerries, YawningCavern.PHASE, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(sweetBerries, TheUndergrowth.PHASE)
            .tag(honeyBottle, TheUndergrowth.PHASE)
            .tag(melonSlice, BoggedCloudforest.PHASE)
            .tag(beetroot, DesolatePeaks.PHASE, GroundZero.PHASE)
            .tag(chorusFruit, GroundZero.PHASE)
            .tag(rabbitStack, RollingHills.PHASE, TheUndergrowth.PHASE, DesolatePeaks.PHASE)
            .tag(beef, RollingHills.PHASE, BoggedCloudforest.PHASE)
            .tag(mutton, RollingHills.PHASE)
            .tag(chickenStack, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(porkchop, BoggedCloudforest.PHASE, ScorchedTerrorscape.PHASE)

            //Crafted & Smelted Foods
            .tag(rabbitStew, DesolatePeaks.PHASE)
            .tag(mushroomStew, TheUndergrowth.PHASE)
            .tag(beetrootSoup, DesolatePeaks.PHASE)
            .tag(suspiciousStew, UnfathomableAbyss.PHASE)
            .tag(bakedPotato, UnfathomableAbyss.PHASE)
            .tag(cookie, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(cake, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(cookedRabbit, TheUndergrowth.PHASE, DesolatePeaks.PHASE)
            .tag(cookedBeef, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(cookedMutton, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(cookedChicken, TheUndergrowth.PHASE)
            .tag(cookedPorkchop, ScorchedTerrorscape.PHASE)
            .tag(cookedCod, CambrianEstuary.PHASE)
            .tag(cookedSalmon, CambrianEstuary.PHASE)

            //Ores
            .tag(coal, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(ironIngot, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(copperIngot, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(goldIngot, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, ScorchedTerrorscape.PHASE, GroundZero.PHASE)
            .tag(redstone, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(lapisLazuli, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(diamond, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(emerald, DesolatePeaks.PHASE, GroundZero.PHASE)
            .tag(netheriteScrap, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteIngot, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)

            //Utility & Misc.
            .tag(torch, RollingHills.PHASE, YawningCavern.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE)
            .tag(waterBucket, RollingHills.PHASE, CambrianEstuary.PHASE)
            .tag(lavaBucket, YawningCavern.PHASE, GleamingDepths.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(nameTag, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE)
            .tag(honeycomb, TheUndergrowth.PHASE)
            .tag(anvil, TheUndergrowth.PHASE, DesolatePeaks.PHASE)
            .tag(prismarineShard, CambrianEstuary.PHASE)
            .tag(prismarineCrystals, CambrianEstuary.PHASE)
            .tag(scute, CambrianEstuary.PHASE)
            .tag(nautilusShell, CambrianEstuary.PHASE)
            .tag(codBucket, CambrianEstuary.PHASE)
            .tag(salmonBucket, CambrianEstuary.PHASE)
            .tag(tropicalFishBucket, CambrianEstuary.PHASE)
            .tag(pufferfishBucket, CambrianEstuary.PHASE)
            .tag(heartOfTheSea, CambrianEstuary.PHASE)
            .tag(brewingStand, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(enchantingTable, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(totemOfUndying, TheUndergrowth.PHASE, CrackedWasteland.PHASE, DesolatePeaks.PHASE)
            .tag(quartz, ScorchedTerrorscape.PHASE)
            .tag(glowstoneDust, ScorchedTerrorscape.PHASE)
            .tag(echoShard, UnfathomableAbyss.PHASE)

            //Decorations
            .tag(grass, RollingHills.PHASE)
            .tag(tallGrass, RollingHills.PHASE)
            .tag(dandelion, RollingHills.PHASE, TheUndergrowth.PHASE, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(poppy, RollingHills.PHASE, TheUndergrowth.PHASE, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(blueOrchid, RollingHills.PHASE, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(allium, TheUndergrowth.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(azureBluet, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(redTulip, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(orangeTulip, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(pinkTulip, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(whiteTulip, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(oxeyeDaisy, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(cornflower, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(lilyOfTheValley, TheUndergrowth.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(sunflower, RollingHills.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(lilac, TheUndergrowth.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(roseBush, TheUndergrowth.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(peony, TheUndergrowth.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(witherRose, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(azalea, YawningCavern.PHASE)
            .tag(floweringAzalea, YawningCavern.PHASE)
            .tag(sporeBlossom, YawningCavern.PHASE)
            .tag(smallDripleaf, YawningCavern.PHASE)
            .tag(bigDripleaf, YawningCavern.PHASE)
            .tag(glowLichen, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(fern, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(largeFern, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(redMushroom, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(brownMushroom, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(deadBush, CrackedWasteland.PHASE, DesolatePeaks.PHASE)
            .tag(rail, CrackedWasteland.PHASE, GleamingDepths.PHASE)
            .tag(poweredRail, CrackedWasteland.PHASE, GleamingDepths.PHASE)
            .tag(activatorRail, GleamingDepths.PHASE)
            .tag(detectorRail, GleamingDepths.PHASE)
            .tag(tripwireHook, CrackedWasteland.PHASE, DesolatePeaks.PHASE)
            .tag(tubeCoral, CambrianEstuary.PHASE)
            .tag(brainCoral, CambrianEstuary.PHASE)
            .tag(bubbleCoral, CambrianEstuary.PHASE)
            .tag(fireCoral, CambrianEstuary.PHASE)
            .tag(hornCoral, CambrianEstuary.PHASE)
            .tag(tubeCoralFan, CambrianEstuary.PHASE)
            .tag(brainCoralFan, YawningCavern.PHASE)
            .tag(bubbleCoralFan, CambrianEstuary.PHASE)
            .tag(fireCoralFan, YawningCavern.PHASE)
            .tag(hornCoralFan, CambrianEstuary.PHASE)
            .tag(seagrass, CambrianEstuary.PHASE)
            .tag(kelp, YawningCavern.PHASE)
            .tag(caveVines, BoggedCloudforest.PHASE)
            .tag(lilyPad, BoggedCloudforest.PHASE)
            .tag(mossCarpet, BoggedCloudforest.PHASE)
            .tag(pointedDripstone, GleamingDepths.PHASE)
            .tag(bell, DesolatePeaks.PHASE, ScorchedTerrorscape.PHASE)
            .tag(warpedRoots, KaleidoscopeLibrary.PHASE)
            .tag(twistingVines, KaleidoscopeLibrary.PHASE)
            .tag(netherSprouts, KaleidoscopeLibrary.PHASE)
            .tag(cobweb, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(lantern, KaleidoscopeLibrary.PHASE)
            .tag(lectern, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(netherWart, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE)
            .tag(crimsonRoots, ScorchedTerrorscape.PHASE)
            .tag(weepingVines, ScorchedTerrorscape.PHASE)
            .tag(sculkVein, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(candle, UnfathomableAbyss.PHASE)
            .tag(whiteCandle, UnfathomableAbyss.PHASE)
            .tag(soulTorch, UnfathomableAbyss.PHASE)
            .tag(soulLantern, UnfathomableAbyss.PHASE)
            .tag(redstoneTorch, UnfathomableAbyss.PHASE)
            .tag(repeater, UnfathomableAbyss.PHASE)
            .tag(comparator, UnfathomableAbyss.PHASE)
            .tag(skeletonSkull, UnfathomableAbyss.PHASE)
            .tag(chorusFlower, GroundZero.PHASE)
            .tag(endRod, GroundZero.PHASE)
            .tag(dragonHead, GroundZero.PHASE)

            //Tools & Weapons
            .tag(woodenCombatAxe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(woodenUtilityAxe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileWoodenCombatAxe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileWoodenUtilityAxe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(woodenPickaxe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileWoodenPickaxe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(woodenHoe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileWoodenHoe, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(woodenShovel, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileWoodenShovel, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(woodenSword, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileWoodenSword, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(stoneCombatAxe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(stoneUtilityAxe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(stonePickaxe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(stoneHoe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(stoneShovel, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(stoneSword, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironCombatAxe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironUtilityAxe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironPickaxe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironHoe, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironShovel, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironSword, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenCombatAxe, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenUtilityAxe, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenPickaxe, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenHoe, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenShovel, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenSword, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondCombatAxe, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondUtilityAxe, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondPickaxe, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondHoe, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondShovel, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondSword, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteCombatAxe, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteUtilityAxe, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheritePickaxe, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteHoe, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteShovel, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteSword, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(bow, CrackedWasteland.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(crossbow, CrackedWasteland.PHASE, DesolatePeaks.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(arrow, CrackedWasteland.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(spectralArrow, ScorchedTerrorscape.PHASE)
            .tag(trident, CambrianEstuary.PHASE)

            //Armor
            .tag(turtleHelmet, CambrianEstuary.PHASE)
            .tag(leatherHelmet, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileLeatherHelmet, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(leatherChestplate, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileLeatherChestplate, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(leatherLeggings, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileLeatherLeggings, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(leatherBoots, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fragileLeatherHelmet, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(chainmailHelmet, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(chainmailChestplate, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(chainmailLeggings, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(chainmailBoots, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironHelmet, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironChestplate, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironLeggings, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironBoots, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenHelmet, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenChestplate, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenLeggings, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(goldenBoots, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondHelmet, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondChestplate, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondLeggings, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(diamondBoots, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteHelmet, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteChestplate, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteLeggings, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(netheriteBoots, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)

            //Horse Equipment
            .tag(lead, CrackedWasteland.PHASE, UnfathomableAbyss.PHASE)
            .tag(saddle, CrackedWasteland.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(ironHorseArmor, CrackedWasteland.PHASE, ScorchedTerrorscape.PHASE, GroundZero.PHASE)
            .tag(goldenHorseArmor, CrackedWasteland.PHASE, ScorchedTerrorscape.PHASE)
            .tag(diamondHorseArmor, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)

            //Enchanting
            .tag(experienceBottle, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(book, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(mendingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(unbreakingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(curseOfVanishingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(aquaAffinityBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(blastProtectionBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(curseOfBindingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(depthStriderBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(featherFallingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fireProtectionBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(frostWalkerBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(projectileProtectionBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(protectionBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(respirationBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(soulSpeedBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(swiftSneakBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(thornsBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(baneOfArthropodsBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fireAspectBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(lootingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(impalingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(knockbackBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(sharpnessBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(smiteBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(sweepingEdgeBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(flameBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(infinityBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(loyaltyBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(riptideBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(multishotBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(piercingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(powerBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(punchBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(quickChargeBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(efficiencyBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(fortuneBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(luckOfTheSeaBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(lureBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(silkTouchBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(rollingHillsTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(yawningCavernTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(undergrowthTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(crackedWastelandTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(cambrianEstuaryTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(boggedCloudforestTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(gleamingDepthsTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(desolatePeaksTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(kaleidoscopeLibraryTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(scorchedTerrorscapeTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(unfathomableAbyssTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(groundZeroTargetingBook, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)

            //Brewing
            .tag(blazePowder, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(fermentedSpiderEye, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(sugar, KaleidoscopeLibrary.PHASE)
            .tag(glisteringMelonSlice, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE)
            .tag(waterBottle, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(potion, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)

            //Music Disc
            .tag(musicDiscChirp, RollingHills.PHASE)
            .tag(musicDiscBlocks, YawningCavern.PHASE)
            .tag(musicDisc13, TheUndergrowth.PHASE)
            .tag(musicDiscCat, TheUndergrowth.PHASE)
            .tag(musicDiscMall, CrackedWasteland.PHASE)
            .tag(musicDiscStrad, CambrianEstuary.PHASE)
            .tag(musicDiscMellohi, BoggedCloudforest.PHASE)
            .tag(musicDiscStal, GleamingDepths.PHASE)
            .tag(musicDiscFar, DesolatePeaks.PHASE)
            .tag(musicDiscWait, KaleidoscopeLibrary.PHASE)
            .tag(musicDiscPigstep, ScorchedTerrorscape.PHASE)
            .tag(discFragment5, UnfathomableAbyss.PHASE)
            .tag(musicDiscOtherside, UnfathomableAbyss.PHASE)
            .tag(musicDiscWard, GroundZero.PHASE)
            .tag(musicDisc11, GroundZero.PHASE)


            .build();

    public static final InfiniteTags<BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData>> BLOCK_TAGS = new InfiniteTags.InfiniteTagsBuilder<BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData>>()
            //Chest
            .tag(infiniteChest, RollingHills.PHASE, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, CambrianEstuary.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)

            //Phase 1
            .tag(grassBlock, RollingHills.PHASE, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(oakLog, RollingHills.PHASE)
            .tag(birchLog, RollingHills.PHASE, TheUndergrowth.PHASE)
            .tag(gravel, RollingHills.PHASE, YawningCavern.PHASE, CambrianEstuary.PHASE, DesolatePeaks.PHASE, ScorchedTerrorscape.PHASE)
            .tag(sand, RollingHills.PHASE, CambrianEstuary.PHASE)
            .tag(clay, RollingHills.PHASE, YawningCavern.PHASE, CambrianEstuary.PHASE)

            .tag(cow, RollingHills.PHASE)
            .tag(sheep, RollingHills.PHASE)
            .tag(rabbit, RollingHills.PHASE, TheUndergrowth.PHASE, DesolatePeaks.PHASE)
            .tag(slime, RollingHills.PHASE)

            //Phase 2
            .tag(stone, YawningCavern.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE)
            .tag(dirt, YawningCavern.PHASE, DesolatePeaks.PHASE)
            .tag(granite, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(diorite, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(andesite, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(coalOre, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE)
            .tag(ironOre, YawningCavern.PHASE, TheUndergrowth.PHASE, CrackedWasteland.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE, DesolatePeaks.PHASE)
            .tag(copperOre, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(calcite, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(amethystBlock, YawningCavern.PHASE, GleamingDepths.PHASE)
            .tag(rootedDirt, YawningCavern.PHASE)
            .tag(mossBlock, YawningCavern.PHASE)
            .tag(azaleaLeaves, YawningCavern.PHASE)
            .tag(floweringAzaleaLeaves, YawningCavern.PHASE)

            .tag(bat, YawningCavern.PHASE, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(zombie, YawningCavern.PHASE, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(skeleton, YawningCavern.PHASE, CrackedWasteland.PHASE, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(axolotl, YawningCavern.PHASE)

            //Phase 3
            .tag(podzol, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(coarseDirt, TheUndergrowth.PHASE, CrackedWasteland.PHASE)
            .tag(mossyCobblestone, TheUndergrowth.PHASE, BoggedCloudforest.PHASE)
            .tag(spruceLog, TheUndergrowth.PHASE)
            .tag(darkOakLog, TheUndergrowth.PHASE, UnfathomableAbyss.PHASE)
            .tag(beeNest, TheUndergrowth.PHASE)
            .tag(pumpkin, TheUndergrowth.PHASE)
            .tag(honeycombBlock, TheUndergrowth.PHASE)
            .tag(redMushroomBlock, TheUndergrowth.PHASE)
            .tag(brownMushroomBlock, TheUndergrowth.PHASE)
            .tag(mushroomStem, TheUndergrowth.PHASE)

            .tag(bee, TheUndergrowth.PHASE)
            .tag(wolf, TheUndergrowth.PHASE, DesolatePeaks.PHASE)
            .tag(fox, TheUndergrowth.PHASE)
            .tag(chicken, TheUndergrowth.PHASE)
            .tag(spider, TheUndergrowth.PHASE, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(babyZombie, TheUndergrowth.PHASE)
            .tag(creeper, TheUndergrowth.PHASE, CrackedWasteland.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE)
            .tag(vindicator, TheUndergrowth.PHASE, CrackedWasteland.PHASE, DesolatePeaks.PHASE)

            //Phase 4
            .tag(redSand, CrackedWasteland.PHASE)
            .tag(redSandstone, CrackedWasteland.PHASE)
            .tag(terracotta, CrackedWasteland.PHASE)
            .tag(redTerracotta, CrackedWasteland.PHASE)
            .tag(orangeTerracotta, CrackedWasteland.PHASE)
            .tag(yellowTerracotta, CrackedWasteland.PHASE)
            .tag(brownTerracotta, CrackedWasteland.PHASE)
            .tag(whiteTerracotta, CrackedWasteland.PHASE)
            .tag(lightGrayTerracotta, CrackedWasteland.PHASE)
            .tag(acaciaLog, CrackedWasteland.PHASE)
            .tag(cactus, CrackedWasteland.PHASE)
            .tag(boneBlock, CrackedWasteland.PHASE, ScorchedTerrorscape.PHASE, GroundZero.PHASE)
            .tag(goldOre, CrackedWasteland.PHASE, BoggedCloudforest.PHASE, GleamingDepths.PHASE)

            .tag(horse, CrackedWasteland.PHASE)
            .tag(llama, CrackedWasteland.PHASE)
            .tag(donkey, CrackedWasteland.PHASE)
            .tag(husk, CrackedWasteland.PHASE, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE)
            .tag(babyHusk, CrackedWasteland.PHASE, UnfathomableAbyss.PHASE)
            .tag(pillager, CrackedWasteland.PHASE, DesolatePeaks.PHASE)
            .tag(zombieVillager, CrackedWasteland.PHASE)
            .tag(babyZombieVillager, CrackedWasteland.PHASE)
            .tag(wanderingTrader, CrackedWasteland.PHASE)

            //Phase 5
            .tag(sandstone, CambrianEstuary.PHASE)
            .tag(wetSponge, CambrianEstuary.PHASE)
            .tag(tubeCoralBlock, CambrianEstuary.PHASE)
            .tag(brainCoralBlock, CambrianEstuary.PHASE)
            .tag(bubbleCoralBlock, CambrianEstuary.PHASE)
            .tag(fireCoralBlock, CambrianEstuary.PHASE)
            .tag(hornCoralBlock, CambrianEstuary.PHASE)
            .tag(prismarine, CambrianEstuary.PHASE)
            .tag(prismarineBricks, CambrianEstuary.PHASE)
            .tag(darkPrismarine, CambrianEstuary.PHASE)
            .tag(seaLantern, CambrianEstuary.PHASE)
            .tag(magmaBlock, CambrianEstuary.PHASE, ScorchedTerrorscape.PHASE)
            .tag(waterloggedSpruceStairs, CambrianEstuary.PHASE)
            .tag(waterloggedOakStairs, CambrianEstuary.PHASE)
            .tag(waterloggedDarkOakStairs, CambrianEstuary.PHASE)
            .tag(waterloggedSpruceFence, CambrianEstuary.PHASE)
            .tag(waterloggedOakFence, CambrianEstuary.PHASE)
            .tag(waterloggedDarkOakStairs, CambrianEstuary.PHASE)
            .tag(waterloggedSpruceSlab, CambrianEstuary.PHASE)
            .tag(waterloggedOakSlab, CambrianEstuary.PHASE)
            .tag(waterloggedDarkOakSlab, CambrianEstuary.PHASE)

            .tag(cod, CambrianEstuary.PHASE)
            .tag(salmon, CambrianEstuary.PHASE)
            .tag(pufferfish, CambrianEstuary.PHASE)
            .tag(tropicalFish, CambrianEstuary.PHASE)
            .tag(squid, CambrianEstuary.PHASE)
            .tag(turtle, CambrianEstuary.PHASE)
            .tag(dolphin, CambrianEstuary.PHASE)
            .tag(drowned, CambrianEstuary.PHASE, BoggedCloudforest.PHASE)
            .tag(babyDrowned, CambrianEstuary.PHASE, BoggedCloudforest.PHASE)
            .tag(guardian, CambrianEstuary.PHASE)

            //Phase 6
            .tag(mud, BoggedCloudforest.PHASE)
            .tag(mycelium, BoggedCloudforest.PHASE)
            .tag(soulSand, BoggedCloudforest.PHASE, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(jungleLog, BoggedCloudforest.PHASE)
            .tag(mangroveLog, BoggedCloudforest.PHASE)
            .tag(mangroveRoots, BoggedCloudforest.PHASE)
            .tag(waterloggedMangroveRoots, BoggedCloudforest.PHASE)
            .tag(muddyMangroveRoots, BoggedCloudforest.PHASE)
            .tag(melon, BoggedCloudforest.PHASE)
            .tag(pearlescentFroglight, BoggedCloudforest.PHASE)
            .tag(ochreFroglight, BoggedCloudforest.PHASE)

            .tag(pig, BoggedCloudforest.PHASE)
            .tag(mooshroom, BoggedCloudforest.PHASE)
            .tag(tadpole, BoggedCloudforest.PHASE)
            .tag(warmFrog, BoggedCloudforest.PHASE)
            .tag(temperateFrog, BoggedCloudforest.PHASE)
            .tag(ocelot, BoggedCloudforest.PHASE)
            .tag(parrot, BoggedCloudforest.PHASE)
            .tag(panda, BoggedCloudforest.PHASE)
            .tag(bigSlime, BoggedCloudforest.PHASE)
            .tag(witch, BoggedCloudforest.PHASE, KaleidoscopeLibrary.PHASE)

            //Phase 7
            .tag(deepslate, GleamingDepths.PHASE, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE)
            .tag(tuff, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(deepslateCoalOre, GleamingDepths.PHASE, DesolatePeaks.PHASE)
            .tag(deepslateIronOre, GleamingDepths.PHASE, DesolatePeaks.PHASE)
            .tag(deepslateCopperOre, GleamingDepths.PHASE)
            .tag(deepslateGoldOre, GleamingDepths.PHASE)
            .tag(redstoneOre, GleamingDepths.PHASE)
            .tag(deepslateRedstoneOre, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(lapisOre, GleamingDepths.PHASE)
            .tag(deepslateLapisOre, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(diamondOre, GleamingDepths.PHASE, DesolatePeaks.PHASE)
            .tag(deepslateDiamondOre, GleamingDepths.PHASE, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(dripstoneBlock, GleamingDepths.PHASE)
            .tag(obsidian, GleamingDepths.PHASE, ScorchedTerrorscape.PHASE, GroundZero.PHASE)
            .tag(tnt, GleamingDepths.PHASE)

            .tag(glowSquid, GleamingDepths.PHASE)
            .tag(upgradedZombie1, GleamingDepths.PHASE)
            .tag(upgradedZombie2, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(upgradedSkeleton1, GleamingDepths.PHASE, KaleidoscopeLibrary.PHASE)
            .tag(upgradedSkeleton2, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(caveSpider, GleamingDepths.PHASE, UnfathomableAbyss.PHASE)
            .tag(spiderJockey, GleamingDepths.PHASE)
            .tag(chickenJockey, GleamingDepths.PHASE)

            //Phase 8
            .tag(infestedStone, DesolatePeaks.PHASE)
            .tag(infestedDeepslate, DesolatePeaks.PHASE)
            .tag(emeraldOre, DesolatePeaks.PHASE)
            .tag(deepslateEmeraldOre, DesolatePeaks.PHASE)
            .tag(dirtPath, DesolatePeaks.PHASE)
            .tag(stoneBricks, DesolatePeaks.PHASE, GroundZero.PHASE)
            .tag(crackedStoneBricks, DesolatePeaks.PHASE, GroundZero.PHASE)
            .tag(chiseledStoneBricks, DesolatePeaks.PHASE)
            .tag(infestedStoneBricks, DesolatePeaks.PHASE, GroundZero.PHASE)
            .tag(infestedCrackedStoneBricks, DesolatePeaks.PHASE, GroundZero.PHASE)
            .tag(infestedChiseledStoneBricks, DesolatePeaks.PHASE)
            .tag(ice, DesolatePeaks.PHASE)
            .tag(packedIce, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE)
            .tag(blueIce, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(snowBlock, DesolatePeaks.PHASE)

            .tag(goat, DesolatePeaks.PHASE)
            .tag(polarBear, DesolatePeaks.PHASE)
            .tag(upgradedHusk1, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE)
            .tag(stray, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE)
            .tag(upgradedStray1, DesolatePeaks.PHASE, UnfathomableAbyss.PHASE)
            .tag(villager, DesolatePeaks.PHASE)
            .tag(ironGolem, DesolatePeaks.PHASE)
            .tag(snowGolem, DesolatePeaks.PHASE)
            .tag(snowMan, DesolatePeaks.PHASE)
            .tag(upgradedPillager, DesolatePeaks.PHASE)
            .tag(upgradedVindicator, DesolatePeaks.PHASE)
            .tag(evoker, DesolatePeaks.PHASE, KaleidoscopeLibrary.PHASE)

            //Phase 9
            .tag(whiteGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(orangeGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(magentaGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(lightBlueGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(yellowGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(limeGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(pinkGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(grayGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(lightGrayGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(cyanGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(purpleGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(blueGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(brownGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(greenGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(redGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(blackGlazedTerracotta, KaleidoscopeLibrary.PHASE)
            .tag(whiteStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(orangeStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(magentaStainedGlass, KaleidoscopeLibrary.PHASE, GroundZero.PHASE)
            .tag(lightBlueStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(yellowStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(limeStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(pinkStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(grayStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(lightGrayStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(cyanStainedGlass, KaleidoscopeLibrary.PHASE, GroundZero.PHASE)
            .tag(purpleStainedGlass, KaleidoscopeLibrary.PHASE, GroundZero.PHASE)
            .tag(blueStainedGlass, KaleidoscopeLibrary.PHASE, GroundZero.PHASE)
            .tag(brownStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(greenStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(redStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(blackStainedGlass, KaleidoscopeLibrary.PHASE)
            .tag(strippedOakWood, KaleidoscopeLibrary.PHASE)
            .tag(strippedSpruceWood, KaleidoscopeLibrary.PHASE)
            .tag(strippedBirchWood, KaleidoscopeLibrary.PHASE)
            .tag(strippedDarkOakWood, KaleidoscopeLibrary.PHASE)
            .tag(oakPlanks, KaleidoscopeLibrary.PHASE)
            .tag(sprucePlanks, KaleidoscopeLibrary.PHASE)
            .tag(birchPlanks, KaleidoscopeLibrary.PHASE)
            .tag(darkOakPlanks, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(bookshelf, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(warpedNylium, KaleidoscopeLibrary.PHASE)
            .tag(warpedStem, KaleidoscopeLibrary.PHASE)
            .tag(warpedWartBlock, KaleidoscopeLibrary.PHASE)
            .tag(shroomlight, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE)
            .tag(cauldron, KaleidoscopeLibrary.PHASE)
            .tag(filledCauldron, KaleidoscopeLibrary.PHASE)
            .tag(lapisBlock, KaleidoscopeLibrary.PHASE)

            .tag(allay, KaleidoscopeLibrary.PHASE)
            .tag(cat, KaleidoscopeLibrary.PHASE)
            .tag(ascendedSkeleton, KaleidoscopeLibrary.PHASE)
            .tag(skeletonHorse, KaleidoscopeLibrary.PHASE)
            .tag(witherSkeleton, KaleidoscopeLibrary.PHASE, ScorchedTerrorscape.PHASE)
            .tag(silverfishMob, KaleidoscopeLibrary.PHASE, UnfathomableAbyss.PHASE)
            .tag(endermiteMob, KaleidoscopeLibrary.PHASE, GroundZero.PHASE)
            .tag(ascendedWitch, KaleidoscopeLibrary.PHASE)

            //Phase 10
            .tag(netherrack, ScorchedTerrorscape.PHASE)
            .tag(flamingNetherrack, ScorchedTerrorscape.PHASE)
            .tag(glowstone, ScorchedTerrorscape.PHASE)
            .tag(netherBricks, ScorchedTerrorscape.PHASE)
            .tag(soulSoil, ScorchedTerrorscape.PHASE)
            .tag(flamingSoulSoil, ScorchedTerrorscape.PHASE)
            .tag(crimsonNylium, ScorchedTerrorscape.PHASE)
            .tag(crimsonStem, ScorchedTerrorscape.PHASE)
            .tag(netherWartBlock, ScorchedTerrorscape.PHASE)
            .tag(basalt, ScorchedTerrorscape.PHASE)
            .tag(polishedBasalt, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(blackstone, ScorchedTerrorscape.PHASE, UnfathomableAbyss.PHASE)
            .tag(gildedBlackstone, ScorchedTerrorscape.PHASE)
            .tag(polishedBlackstoneBricks, ScorchedTerrorscape.PHASE)
            .tag(crackedPolishedBlackstoneBricks, ScorchedTerrorscape.PHASE)
            .tag(chiseledPolishedBlackstone, ScorchedTerrorscape.PHASE)
            .tag(quartzBlock, ScorchedTerrorscape.PHASE)
            .tag(smoothQuartz, ScorchedTerrorscape.PHASE)
            .tag(netherQuartzOre, ScorchedTerrorscape.PHASE)
            .tag(netherGoldOre, ScorchedTerrorscape.PHASE)
            .tag(ancientDebris, ScorchedTerrorscape.PHASE, GroundZero.PHASE)
            .tag(goldBlock, ScorchedTerrorscape.PHASE)
            .tag(cryingObsidian, ScorchedTerrorscape.PHASE, GroundZero.PHASE)

            .tag(piglin, ScorchedTerrorscape.PHASE)
            .tag(piglinBrute, ScorchedTerrorscape.PHASE)
            .tag(zombifiedPiglin, ScorchedTerrorscape.PHASE)
            .tag(hoglin, ScorchedTerrorscape.PHASE)
            .tag(zoglin, ScorchedTerrorscape.PHASE)
            .tag(ghast, ScorchedTerrorscape.PHASE)
            .tag(magmaCube, ScorchedTerrorscape.PHASE)
            .tag(blaze, ScorchedTerrorscape.PHASE)
            .tag(strider, ScorchedTerrorscape.PHASE)

            //Phase 11
            .tag(coalBlock, UnfathomableAbyss.PHASE)
            .tag(rawIronBlock, UnfathomableAbyss.PHASE)
            .tag(sculk, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(sculkSensor, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(sculkCatalyst, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(sculkShrieker, UnfathomableAbyss.PHASE, GroundZero.PHASE)
            .tag(cobbledDeepslate, UnfathomableAbyss.PHASE)
            .tag(polishedDeepslate, UnfathomableAbyss.PHASE)
            .tag(deepslateBricks, UnfathomableAbyss.PHASE)
            .tag(crackedDeepslateBricks, UnfathomableAbyss.PHASE)
            .tag(deepslateTiles, UnfathomableAbyss.PHASE)
            .tag(crackedDeepslateTiles, UnfathomableAbyss.PHASE)
            .tag(chiseledDeepslate, UnfathomableAbyss.PHASE)
            .tag(smoothBasalt, UnfathomableAbyss.PHASE)
            .tag(redstoneBlock, UnfathomableAbyss.PHASE)
            .tag(redstoneLamp, UnfathomableAbyss.PHASE)
            .tag(target, UnfathomableAbyss.PHASE)
            .tag(noteBlock, UnfathomableAbyss.PHASE)
            .tag(grayWool, UnfathomableAbyss.PHASE)
            .tag(blueWool, UnfathomableAbyss.PHASE)
            .tag(cyanWool, UnfathomableAbyss.PHASE)
            .tag(lightBlueWool, UnfathomableAbyss.PHASE)

            .tag(upgradedZombie3, UnfathomableAbyss.PHASE)
            .tag(upgradedSkeleton3, UnfathomableAbyss.PHASE)
            .tag(upgradedHusk2, UnfathomableAbyss.PHASE)
            .tag(upgradedStray2, UnfathomableAbyss.PHASE)
            .tag(upgradedCaveSpider, UnfathomableAbyss.PHASE)
            .tag(caveSpiderJockey, UnfathomableAbyss.PHASE)
            .tag(chargedCreeper, UnfathomableAbyss.PHASE)
            .tag(enderman, UnfathomableAbyss.PHASE, GroundZero.PHASE)

            //Phase 12
            .tag(endStone, GroundZero.PHASE)
            .tag(endStoneBricks, GroundZero.PHASE)
            .tag(purpurBlock, GroundZero.PHASE)
            .tag(purpurPillar, GroundZero.PHASE)
            .tag(mossyStoneBricks, GroundZero.PHASE)
            .tag(infestedMossyStoneBricks, GroundZero.PHASE)

            .tag(shulker, GroundZero.PHASE)
            .tag(phantom, GroundZero.PHASE)

            .build();
}
