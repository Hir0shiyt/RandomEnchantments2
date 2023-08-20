package net.hir0shiyt.randomenchants2.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class ModConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final EnchantmentConfigEntry solarEnchantConfig;
    public static final EnchantmentConfigEntry obsidianBusterConfig;
    public static final EnchantmentConfigEntry randomnessConfig;
    public static final EnchantmentConfigEntry magneticConfig;

    static {
        Builder builder = new ForgeConfigSpec.Builder();
        solarEnchantConfig = new EnchantmentConfigEntry(builder, "solar_enchant", true);
        obsidianBusterConfig = new EnchantmentConfigEntry(builder, "obsidian_buster", true);
        randomnessConfig = new EnchantmentConfigEntry(builder, "randomness", true);
        magneticConfig = new EnchantmentConfigEntry(builder, "magnetic", true);

        COMMON_SPEC = builder.build();
    }

    public static class EnchantmentConfigEntry {
        public final BooleanValue isEnabled;

        public EnchantmentConfigEntry(Builder builder, String enchantmentName, boolean defaultValue) {
            builder.push(enchantmentName);
            isEnabled = builder.comment("Enable or disable the " + enchantmentName + " Enchantment")
                    .define("Enabled", defaultValue);
            builder.pop();
        }
    }
}
