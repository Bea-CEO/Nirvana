package galena.nirvana.world.effects;

import galena.nirvana.index.NirvanaEntities;
import galena.nirvana.index.NirvanaTags;
import galena.nirvana.platform.Services;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PeaceEffect extends MobEffect implements IStackingEffect {
    public PeaceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xaabf4b);
    }

    private static final int REEFER_MIN_RANGE = 8;
    private static final int REEFER_MAX_RANGE = 20;
    private static final int REEFER_CONVERSION_RANGE = 20;

    @Override
    public void onIncreasedTo(MobEffectInstance instance, ItemStack source, LivingEntity target, Level level) {
        if (!source.is(NirvanaTags.NAUSEATING)) return;
        var hitsTaken = instance.getAmplifier() + 1;

        if (hitsTaken >= Services.CONFIG.common().nauseaAfterHits()) {
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20, 0));
        }

        if (hitsTaken >= Services.CONFIG.common().reeferAfterHits()) {
            spawnReefers(target, level);
        }
    }

    private static void spawnReefers(LivingEntity target, Level level) {
        var chance = Services.CONFIG.common().reeferChance();
        if (chance <= 0) return;

        var rolls = level.getDifficulty().getId();
        for (int i = 0; i < rolls; i++) {
            if (target.getRandom().nextFloat() < chance) {
                var reefer = NirvanaEntities.REEFER.create(level);
                if (reefer == null) continue;

                var rangeAround = (REEFER_MAX_RANGE - REEFER_MIN_RANGE) / 2;
                var centerRange = REEFER_MIN_RANGE + rangeAround;
                var vec = new Vec3(
                        (target.getRandom().nextDouble() * 2 - 1),
                        (target.getRandom().nextDouble() * 2 - 1),
                        (target.getRandom().nextDouble() * 2 - 1)
                ).normalize().scale(centerRange);
                var reference = target.position().add(vec);

                level.findSupportingBlock(reefer, new AABB(reference, reference).inflate(rangeAround))
                        .map(it -> Vec3.upFromBottomCenterOf(it, 1))
                        .ifPresent(pos -> {
                            reefer.setPos(pos);
                            level.addFreshEntity(reefer);
                        });
            }
        }
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int i) {
        super.addAttributeModifiers(entity, attributes, i);
        if (entity instanceof Mob mob) {
            mob.setTarget(null);
        }
    }

}
