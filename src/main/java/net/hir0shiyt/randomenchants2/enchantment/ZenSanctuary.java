package net.hir0shiyt.randomenchants2.enchantment;

import net.hir0shiyt.randomenchants2.RandomEnchants2;
import net.hir0shiyt.randomenchants2.config.ModConfig;
import net.hir0shiyt.randomenchants2.util.EnchantUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = RandomEnchants2.MOD_ID)
public class ZenSanctuary extends Enchantment {
    public ZenSanctuary(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMinCost(int level){
        return  30;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModConfig.ServerConfig.zenSanctuaryConfig.get() != ModConfig.Restriction.DISABLED && super.canEnchant(stack);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModConfig.ServerConfig.zenSanctuaryConfig.get() != ModConfig.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    public boolean isAllowedOnBooks() {
        return ModConfig.ServerConfig.zenSanctuaryConfig.get() == ModConfig.Restriction.ENABLED;
    }

    public boolean isTreasureOnly() {
        return ModConfig.ServerConfig.zenSanctuaryConfig.get() == ModConfig.Restriction.TREASURE;
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingTickEvent event){
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (EnchantUtils.hasEnch(stack, ModEnchantments.ZEN_SANCTUARY.get())) {
                double radius = 64.0;
                Vec3 playerPos = player.position();
                AABB boundingBox = new AABB(playerPos.x - radius, playerPos.y - radius, playerPos.z - radius,
                        playerPos.x + radius, playerPos.y + radius, playerPos.z + radius);
                List<Mob> mobs = player.getCommandSenderWorld().getEntitiesOfClass(Mob.class, boundingBox);
                for (Mob mob : mobs) {
                    if (mob.getTarget() != null && mob.getTarget() instanceof Player) {
                        mob.setLastHurtByMob(null);
                        mob.setTarget(null);
                        mob.getNavigation().stop();
                    }
                }
            }
        }
    }
}
