package galena.nirvana.mixins;

import galena.nirvana.index.NirvanaEffects;
import galena.nirvana.index.NirvanaTags;
import galena.nirvana.world.THCCloud;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {

    @Unique
    private static final int RANGE = 2;

    @Inject(
            method = "particleTick",
            cancellable = true,
            at = @At("HEAD")
    )
    private static void clientTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity be, CallbackInfo ci) {
        if (!level.getBlockState(pos.below()).is(NirvanaTags.SMOKING_CRATES)) return;
        if (level.getRandom().nextFloat() < 0.1F) return;

        THCCloud.tickCloud(level, pos, RANGE);

        ci.cancel();
    }

    @Unique
    private static void applyEffect(LivingEntity target, MobEffectInstance instance) {
        if (instance.getEffect().isInstantenous()) {
            instance.getEffect().applyInstantenousEffect(null, null, target, instance.getAmplifier(), 1.0);
        } else {
            target.addEffect(instance);
        }
    }

    @Inject(
            method = "cookTick",
            at = @At("HEAD")
    )
    private static void serverTick(Level level, BlockPos pos, BlockState state, CampfireBlockEntity be, CallbackInfo ci) {
        if (!level.getBlockState(pos.below()).is(NirvanaTags.SMOKING_CRATES)) return;

        var box = new AABB(pos).inflate(RANGE);
        var targets = level.getEntitiesOfClass(LivingEntity.class, box);

        targets.forEach(it -> {
            applyEffect(it, new MobEffectInstance(NirvanaEffects.PEACE.get(), 20 * 2));
            applyEffect(it, new MobEffectInstance(MobEffects.CONFUSION, 20 * 2));
        });
    }

}
