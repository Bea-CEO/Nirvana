package galena.nirvana.world.item;

import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class CustomMinecartItem extends MinecartItem {
    
    private final NonNullSupplier<EntityType<? extends AbstractMinecart>> entity;
    
    public CustomMinecartItem(Properties properties, EntityEntry<? extends AbstractMinecart> entity) {
        super(AbstractMinecart.Type.TNT, properties);
        this.entity = entity::get;
    }

    private AbstractMinecart place(Level level, double x, double y, double z) {
        var minecart = entity.get().create(level);
        if(minecart == null) return null;
        
        minecart.setPos(x, y, z);
        minecart.xo = x;
        minecart.yo = y;
        minecart.zo = z;
        return minecart;
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);

        if (!state.is(BlockTags.RAILS)) {
            return InteractionResult.FAIL;
        } else {
            ItemStack stack = context.getItemInHand();
            if (!level.isClientSide) {
                var railshape = state.getBlock() instanceof BaseRailBlock rail ? state.getValue(rail.getShapeProperty()) : RailShape.NORTH_SOUTH;
                var yOffset = railshape.isAscending() ? 0.5F : 0F;

                var minecart = place(level, pos.getX() + 0.5F, pos.getY() + 0.0625F + yOffset, pos.getZ() + 0.5F);
                if(minecart == null) return InteractionResult.FAIL;

                if (stack.hasCustomHoverName()) {
                    minecart.setCustomName(stack.getHoverName());
                }

                level.addFreshEntity(minecart);
                level.gameEvent(GameEvent.ENTITY_PLACE, pos, GameEvent.Context.of(context.getPlayer(), level.getBlockState(pos.below())));
            }

            stack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }
    
}
