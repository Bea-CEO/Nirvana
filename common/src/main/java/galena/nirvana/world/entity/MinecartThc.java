package galena.nirvana.world.entity;

import galena.nirvana.world.THCCloud;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MinecartThc extends MinecartTNT {

    public MinecartThc(EntityType<? extends MinecartTNT> type, Level level) {
        super(type, level);
    }

    @Override
    protected void explode(@Nullable DamageSource damageSource, double d) {
        THCCloud.spawnCloud(level(), position(), 1.5F, 15, 60);
    }
}
