package galena.nirvana.index;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.EntityEntry;
import galena.nirvana.platform.Services;
import galena.nirvana.world.entity.Reefer;
import galena.nirvana.world.entity.renderer.ReeferRenderer;
import net.minecraft.world.entity.MobCategory;

public class NirvanaEntities {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    public static final EntityEntry<?> REEFER = REGISTRATE
            .entity("reefer", Reefer::new, MobCategory.MONSTER)
            .attributes(Reefer::createAttributes)
            .renderer(() -> ReeferRenderer::new)
            .register();

    public static void register() {
        // loads this class
    }

}
