package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class ObsidianBuster extends Enchantment {
    public ObsidianBuster(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 10; // You can adjust this value as needed
    }

    @Override
    public int getMaxLevel() {
        return 1; // Set the maximum enchantment level to 1
    }

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, RandomEnchants2.MOD_ID);

    public static final RegistryObject<Enchantment> OBSIDIAN_BUSTER =
            ENCHANTMENTS.register("obsidian_buster",
                    () -> new ObsidianBuster(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getItemInHand(player.getUsedItemHand());

        if (EnchantmentHelper.getItemEnchantmentLevel(OBSIDIAN_BUSTER.get(), heldItem) > 0
                && event.getState().getBlock() == Blocks.OBSIDIAN) {
            float newSpeed = event.getNewSpeed() + 100F; // Adjust the speed boost as needed
            event.setNewSpeed(newSpeed);
        }
    }
}
