package galena.nirvana.forge.client;

import galena.nirvana.client.JointModels;
import galena.nirvana.index.NirvanaParticles;
import galena.nirvana.world.particle.SmokeRingParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ForgeClientEntrypoint {

    public static void init() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(ForgeClientEntrypoint::registerModels);
        modBus.addListener(ForgeClientEntrypoint::registerParticles);
    }

    private static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(JointModels.HAND_MODEL);
        event.register(JointModels.FLAT_MODEL);
    }

    private static void registerParticles(RegisterParticleProvidersEvent event) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;

        engine.register(NirvanaParticles.SMOKE_RING.get(), SmokeRingParticle.Provider::new);
    }

}
