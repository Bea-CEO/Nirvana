package galena.nirvana.world;

import galena.nirvana.index.NirvanaEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class THCCloud {

    public static AreaEffectCloud spawnCloud(Level level, Vec3 at, float size, int nauseaSeconds, int peaceSeconds) {
        level.explode(null, at.x, at.y, at.z, size / 10, Level.ExplosionInteraction.NONE);

        var cloud = new AreaEffectCloud(level, at.x, at.y, at.z);

        cloud.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 0, 20 * nauseaSeconds));
        cloud.addEffect(new MobEffectInstance(NirvanaEffects.PEACE.get(), 0, 20 * peaceSeconds));

        cloud.setParticle(ParticleTypes.CLOUD);
        cloud.setRadius(1.5F * size);
        cloud.setRadiusPerTick(-0.01F);
        cloud.setDuration(200);

        level.addFreshEntity(cloud);
        return cloud;
    }

}
