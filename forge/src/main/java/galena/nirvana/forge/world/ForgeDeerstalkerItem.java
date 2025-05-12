package galena.nirvana.forge.world;

import galena.nirvana.client.CustomItemModel;
import galena.nirvana.forge.client.CustomModelExtensions;
import galena.nirvana.world.item.DeerStalkerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ForgeDeerstalkerItem extends DeerStalkerItem {

    public ForgeDeerstalkerItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new CustomModelExtensions(CustomItemModel.DEERSTALKER));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment)
                || enchantment.canApplyAtEnchantingTable(new ItemStack(Items.LEATHER_HELMET));
    }

}
