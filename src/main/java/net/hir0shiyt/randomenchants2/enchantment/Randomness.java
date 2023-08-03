package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class Randomness extends Enchantment {

    public Randomness(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level) {
        return 5 + 10 * (level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, RandomEnchants2.MOD_ID);

    public static final RegistryObject<Enchantment> RANDOMNESS_ENCHANT =
            ENCHANTMENTS.register("randomness",
                    () -> new Randomness(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));

    public static Enchantment getRandomnessEnchant() {
        return RANDOMNESS_ENCHANT.get();
    }

    public static List<ItemStack> getRandomItems(Random random, int level) {
        List<ItemStack> drops = new ArrayList<>();
        // Add normal block items
        drops.addAll(getNormalBlockItems(random, level));
        // Add random items from the enchantment
        drops.addAll(getRandomEnchantmentItems(random, level));
        return drops;
    }

    private static List<ItemStack> getNormalBlockItems(Random random, int level) {
        // Add normal block items implementation (currently empty)
        return new ArrayList<>();
    }

    private static List<ItemStack> getRandomEnchantmentItems(Random random, int level) {
        List<ItemStack> drops = new ArrayList<>();
        for (int i = 0; i < level; i++) {
            Item randomItem = getRandomItem(random);
            if (randomItem != null) {
                int randomCount = 1 + random.nextInt(level); // Random drop count based on enchantment level
                ItemStack drop = new ItemStack(randomItem, randomCount);
                drops.add(drop);
            }
        }
        return drops;
    }

    private static Item getRandomItem(Random random) {
        List<Item> items = new ArrayList<>();
        for (Item item : Registry.ITEM) {
            items.add(item);
        }
        return items.isEmpty() ? null : items.get(random.nextInt(items.size()));
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getMainHandItem();

        // Check if the enchantment is enabled
        if (EnchantmentHelper.getItemEnchantmentLevel(Randomness.getRandomnessEnchant(), stack) <= 0 || event.getWorld().isClientSide()) {
            return;
        }

        Level world = (Level) event.getWorld();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Randomness.getRandomnessEnchant(), stack);

        // Get the block state before it's broken
        BlockState blockState = world.getBlockState(event.getPos());

        // Cancel the original block break event
        event.setCanceled(true);

        // Destroy the block without dropping block items
        world.destroyBlock(event.getPos(), false);

        // Prevent the normal block drops from appearing
        event.setExpToDrop(0);

        // Choose between normal block drops and random drops
        List<ItemStack> drops = Randomness.getRandomItems(world.random, level);

        // Spawn the chosen drops
        for (ItemStack drop : drops) {
            if (!drop.isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(player, drop); // Give the item to the player
            }
        }
    }
}
