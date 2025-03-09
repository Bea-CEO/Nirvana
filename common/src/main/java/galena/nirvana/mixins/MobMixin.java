package galena.nirvana.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import galena.nirvana.index.NirvanaEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public abstract class MobMixin {

    @WrapWithCondition(
            method = "serverAiStep()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tickRunningGoals(Z)V",
                    ordinal = 0
            )
    )
    public boolean interruptTargetGoal(GoalSelector targetSelector, boolean argument) {
        @SuppressWarnings("DataFlowIssue")
        var self = (Mob) (Object) (this);
        return !self.hasEffect(NirvanaEffects.PEACE.get());
    }

    @WrapWithCondition(
            method = "serverAiStep()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tick()V",
                    ordinal = 0
            )
    )
    public boolean interruptTargetGoal(GoalSelector targetSelector) {
        @SuppressWarnings("DataFlowIssue")
        var self = (Mob) (Object) (this);
        return !self.hasEffect(NirvanaEffects.PEACE.get());
    }

}
