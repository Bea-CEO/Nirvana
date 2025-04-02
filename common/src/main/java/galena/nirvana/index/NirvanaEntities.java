package galena.nirvana.index;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.EntityEntry;
import galena.nirvana.platform.Services;
import galena.nirvana.world.entity.MinecartThc;
import galena.nirvana.world.entity.PrimedThc;
import galena.nirvana.world.entity.Reefer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import galena.nirvana.world.entity.renderer.CustomTntRenderer;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.MinecartTNT;

public class NirvanaEntities {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final EntityEntry<? extends Mob> REEFER = REGISTRATE
            .entity("reefer", Reefer::new, MobCategory.MONSTER)
            .attributes(Reefer::createAttributes)
            .renderer(() -> ReeferRenderer::new)
            .register();

    public static final EntityEntry<? extends PrimedTnt> THC = REGISTRATE
            .<PrimedThc>entity("thc", PrimedThc::new, MobCategory.MISC)
            .lang("THC")
            .renderer(() -> CustomTntRenderer.of(NirvanaBlocks.THC))
            .register();

    public static final EntityEntry<? extends MinecartTNT> THC_MINECART = REGISTRATE
            .<MinecartThc>entity("thc_minecart", MinecartThc::new, MobCategory.MISC)
            .lang("THC Minecart")
            .renderer(() -> TntMinecartRenderer::new)
            .register();

    public static void register() {
        // loads this class
    }

}
