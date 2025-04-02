package galena.nirvana.forge.services;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.forge.ForgeEntrypoint;
import galena.nirvana.forge.world.ForgeJointItem;
import galena.nirvana.platform.registrate.NirvanaRegistrate;
import galena.nirvana.platform.services.IPlatformHelper;
import galena.nirvana.world.item.JointItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public NirvanaRegistrate<?> getRegistrate() {
        return ForgeEntrypoint.REGISTRATE.get();
    }

    @Override
    public JointItem createJointItem(Item.Properties properties) {
        return new ForgeJointItem(properties);
    }

    @Override
    public Item createSpawnEggItem(NonNullSupplier<? extends EntityType<? extends Mob>> type, int primary, int secodary, Item.Properties properties) {
        return new ForgeSpawnEggItem(type, primary, secodary, properties);
    }

}
