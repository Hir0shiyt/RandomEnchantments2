package net.hir0shiyt.randomenchants2.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static class ServerConfig {

        public static ForgeConfigSpec.EnumValue<Restriction> solarEnchantConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> obsidianBusterConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> randomnessConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> magneticConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> stoneLoverConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> eternalConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> dungeoneeringConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> grapplingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> snatchingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> resistantConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> teleportationConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> torchesConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> trueShotConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> equalMineConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> assimilationConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> transpositionConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> ricochetConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> explodingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> backToTheChamberConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> quickDrawConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> phasingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> discordConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> swiftConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> disarmConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> shatteringConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> homingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> paralysisConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> cursedJumpingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> deflectConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> floatingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> harvestConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> instantDeathConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> lightningConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> lumberjackConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> trueLifeStealConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> reflectConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> stoneBoundConfig;

        //curses
        public static ForgeConfigSpec.EnumValue<Restriction> breakingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> butterFingersConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> fumblingConfig;
        public static ForgeConfigSpec.EnumValue<Restriction> shadowsConfig;

        ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("enchantments");
            solarEnchantConfig = builder.comment("Restriction for Solar Enchantment")
                    .defineEnum("solarEnchantConfig", ModConfig.Restriction.NORMAL);
            obsidianBusterConfig = builder.comment("Restriction for Obsidian Buster Enchantment")
                    .defineEnum("obsidianBusterConfig", ModConfig.Restriction.NORMAL);
            randomnessConfig = builder.comment("Restriction for Randomness Enchantment")
                    .defineEnum("randomnessConfig", ModConfig.Restriction.NORMAL);
            magneticConfig = builder.comment("Restriction for Magnetic Enchantment")
                    .defineEnum("magneticConfig", ModConfig.Restriction.NORMAL);
            stoneLoverConfig = builder.comment("Restriction for Stone Lover Enchantment")
                    .defineEnum("stoneLoverConfig", ModConfig.Restriction.NORMAL);
            eternalConfig = builder.comment("Restriction for Eternal Enchantment")
                    .defineEnum("eternalConfig", ModConfig.Restriction.NORMAL);
            dungeoneeringConfig = builder.comment("Restriction for Dungeoneering Enchantment")
                    .defineEnum("dungeoneeringConfig", ModConfig.Restriction.NORMAL);
            grapplingConfig = builder.comment("Restriction for Grappling Enchantment")
                    .defineEnum("grapplingConfig", ModConfig.Restriction.NORMAL);
            snatchingConfig = builder.comment("Restriction for Snatching Enchantment")
                    .defineEnum("snatchingConfig", ModConfig.Restriction.NORMAL);
            resistantConfig = builder.comment("Restriction for Resistant Enchantment")
                    .defineEnum("resistantConfig", ModConfig.Restriction.NORMAL);
            teleportationConfig = builder.comment("Restriction for Teleportation Enchantment")
                    .defineEnum("teleportationConfig", ModConfig.Restriction.NORMAL);
            torchesConfig = builder.comment("Restriction for Torches Enchantment")
                    .defineEnum("torchesConfig", ModConfig.Restriction.NORMAL);
            trueShotConfig = builder.comment("Restriction for True Shot Enchantment")
                    .defineEnum("trueShotConfig", ModConfig.Restriction.NORMAL);
            equalMineConfig = builder.comment("Restriction for Equal Mine Enchantment")
                    .defineEnum("equalMineConfig", ModConfig.Restriction.NORMAL);
            assimilationConfig = builder.comment("Restriction for Assimilation Enchantment")
                    .defineEnum("assimilationConfig", ModConfig.Restriction.NORMAL);
            transpositionConfig = builder.comment("Restriction for Transposition Enchantment")
                    .defineEnum("transpositionConfig", ModConfig.Restriction.NORMAL);
            ricochetConfig = builder.comment("Restriction for Ricochet Enchantment")
                    .defineEnum("ricochetConfig", ModConfig.Restriction.NORMAL);
            explodingConfig = builder.comment("Restriction for Exploding Enchantment")
                    .defineEnum("explodingConfig", ModConfig.Restriction.NORMAL);
            backToTheChamberConfig = builder.comment("Restriction for Back To The Chamber Enchantment")
                    .defineEnum("backToTheChamberConfig", ModConfig.Restriction.NORMAL);
            quickDrawConfig = builder.comment("Restriction for Quick Draw Enchantment")
                    .defineEnum("quickDrawConfig", ModConfig.Restriction.NORMAL);
            phasingConfig = builder.comment("Restriction for Phasing Enchantment")
                    .defineEnum("phasingConfig", ModConfig.Restriction.NORMAL);
            discordConfig = builder.comment("Restriction for Discord Enchantment")
                    .defineEnum("discordConfig", ModConfig.Restriction.NORMAL);
            swiftConfig = builder.comment("Restriction for Swift Enchantment")
                    .defineEnum("swiftConfig", ModConfig.Restriction.NORMAL);
            disarmConfig = builder.comment("Restriction for Disarm Enchantment")
                    .defineEnum("disarmConfig", ModConfig.Restriction.NORMAL);
            shatteringConfig = builder.comment("Restriction for Shattering Enchantment")
                    .defineEnum("shatteringConfig", ModConfig.Restriction.NORMAL);
            homingConfig = builder.comment("Restriction for Homing Enchantment")
                    .defineEnum("homingConfig", ModConfig.Restriction.NORMAL);
            paralysisConfig = builder.comment("Restriction for Paralysis Enchantment")
                    .defineEnum("paralysisConfig", ModConfig.Restriction.NORMAL);
            cursedJumpingConfig = builder.comment("Restriction for Cursed Jumping Enchantment")
                    .defineEnum("cursedJumpConfig", ModConfig.Restriction.NORMAL);
            deflectConfig = builder.comment("Restriction for Deflect Enchantment")
                    .defineEnum("reflectConfig", Restriction.NORMAL);
            floatingConfig = builder.comment("Restriction for Floating Enchantment")
                    .defineEnum("floatingConfig", Restriction.NORMAL);
            harvestConfig = builder.comment("Restriction for Harvest Enchantment")
                    .defineEnum("harvestConfig", Restriction.NORMAL);
            instantDeathConfig = builder.comment("Restriction for Instant Death Enchantment")
                    .defineEnum("instantDeathConfig", Restriction.NORMAL);
            lightningConfig = builder.comment("Restriction for Lighting Enchantment")
                    .defineEnum("lightningConfig", Restriction.NORMAL);
            lumberjackConfig = builder.comment("Restriction for Lumberjack Enchantment")
                    .defineEnum("lumberjackConfig", Restriction.NORMAL);
            trueLifeStealConfig = builder.comment("Restriction for True Life Steal Enchantment")
                    .defineEnum("trueLifeStealConfig", Restriction.NORMAL);
            reflectConfig = builder.comment("Restriction for Reflect Enchantment")
                    .defineEnum("reflectConfig", Restriction.NORMAL);
            stoneBoundConfig = builder.comment("Restriction for Stone Bound Enchantment")
                    .defineEnum("stoneBoundConfig", Restriction.NORMAL);

            builder.push("curses");
            breakingConfig = builder.comment("Restriction for Breaking Curse")
                    .defineEnum("breakingConfig", Restriction.NORMAL);
            butterFingersConfig = builder.comment("Restriction for Butter Fingers Curse")
                    .defineEnum("butterFingersConfig", Restriction.NORMAL);
            fumblingConfig = builder.comment("Restriction for Fumbling Curse")
                    .defineEnum("fumblingConfig", Restriction.NORMAL);
            shadowsConfig = builder.comment("Restriction for Shadows Curse")
                    .defineEnum("shadowsConfig", Restriction.NORMAL);

            builder.pop();
        }
    }
    public enum Restriction {
        DISABLED, NORMAL, ANVIL
    }
}