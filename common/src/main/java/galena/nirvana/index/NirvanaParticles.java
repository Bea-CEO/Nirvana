package galena.nirvana.index;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import galena.nirvana.platform.Services;
import galena.nirvana.world.particle.ModdedParticleType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class NirvanaParticles {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final RegistryEntry<ModdedParticleType> SMOKE_RING = REGISTRATE
            .generic("smoke_ring", Registries.PARTICLE_TYPE, ModdedParticleType::new)
            .register();

    public static final RegistryEntry<ModdedParticleType> HERBAL_SALVE = REGISTRATE
            .generic("herbal_salve", Registries.PARTICLE_TYPE, ModdedParticleType::new)
            .register();

    public static final Supplier<ParticleOptions> THC_SMOKE = () -> ParticleTypes.CAMPFIRE_COSY_SMOKE;

    public static void spawnRing(Level level, LivingEntity user) {
        if(user.getRandom().nextInt(0, 3) != 0) return;
        if (level instanceof ServerLevel serverLevel) {
            var pos = user.getEyePosition();
            var motion = user.getLookAngle();
            serverLevel.sendParticles(SMOKE_RING.get(), pos.x, pos.y, pos.z, 0, motion.x, motion.y, motion.z, 0.1);
        }
    }

    public static void register() {
        // loads this class
    }

}
