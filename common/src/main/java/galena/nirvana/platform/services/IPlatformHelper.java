package galena.nirvana.platform.services;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import galena.nirvana.platform.registrate.NirvanaRegistrate;
import galena.nirvana.world.item.JointItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public interface IPlatformHelper {

    NirvanaRegistrate<?> getRegistrate();

    default JointItem createJointItem(Item.Properties properties) {
        return new JointItem(properties);
    }

    default Item createSpawnEggItem(NonNullSupplier<? extends EntityType<? extends Mob>> type, int primary, int secodary, Item.Properties properties) {
        return new SpawnEggItem(type.get(), primary, secodary, properties);
    }

}
