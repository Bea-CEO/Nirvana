package galena.nirvana.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import galena.nirvana.world.item.ArmorLike;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Inject(
            method = "hurtArmor(Lnet/minecraft/world/damagesource/DamageSource;F[I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z"
            )
    )
    private void hurtDeerStalker(DamageSource damageSource, float f, int[] is, CallbackInfo ci, @Local(ordinal = 0) ItemStack stack) {
        if (stack.getItem() instanceof ArmorLike equipable) {
            var self = (Inventory) (Object) this;
            stack.hurtAndBreak((int) f, self.player, (player) -> player.broadcastBreakEvent(equipable.getEquipmentSlot()));
        }
    }

}
