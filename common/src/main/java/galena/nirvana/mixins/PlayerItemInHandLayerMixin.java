package galena.nirvana.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import galena.nirvana.index.NirvanaItems;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin {

    @WrapOperation(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    public boolean isUsingJoint(ItemStack instance, Item item, Operation<Boolean> original) {
        return NirvanaItems.JOINT.isIn(instance) || original.call(instance, item);
    }

}
