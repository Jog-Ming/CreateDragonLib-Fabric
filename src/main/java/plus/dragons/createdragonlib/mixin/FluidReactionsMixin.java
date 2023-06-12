package plus.dragons.createdragonlib.mixin;

import com.simibubi.create.content.fluids.FluidReactions;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.utility.BlockHelper;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createdragonlib.fluid.FluidLavaReaction;

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = FluidReactions.class, remap = false)
public class FluidReactionsMixin {

    @Inject(method = "handlePipeFlowCollision", at = @At("HEAD"), cancellable = true)
    private static void createDragonLib$handlePipeFlowCollision(Level world, BlockPos pos, FluidStack fluid, FluidStack fluid2, CallbackInfo ci) {
        FluidVariant type = fluid.getType();
        FluidVariant type2 = fluid2.getType();
        FluidLavaReaction reaction = null;
        if (type.getFluid() == Fluids.LAVA)
            reaction = FluidLavaReaction.get(type2);
        else if (type2.getFluid() == Fluids.LAVA)
            reaction = FluidLavaReaction.get(type);
        if (reaction != null) {
            AdvancementBehaviour.tryAward(world, pos, AllAdvancements.CROSS_STREAMS);
            BlockHelper.destroyBlock(world, pos, 1);
            world.setBlockAndUpdate(pos, reaction.withFlowingLava());
            ci.cancel();
        }
    }

    @Inject(method = "handlePipeSpillCollision", at = @At("HEAD"), cancellable = true)
    private static void createDragonLib$handleSpillCollision(Level world, BlockPos pos, Fluid pipeFluid, FluidState worldFluid, CallbackInfo ci) {
        FluidVariant typeP = FluidVariant.of(pipeFluid);
        FluidVariant typeW = FluidVariant.of(worldFluid.getType());
        BlockState blockState = null;
        if (typeW.getFluid() == Fluids.LAVA) {
            FluidLavaReaction reaction = FluidLavaReaction.get(typeP);
            if (reaction != null) blockState = worldFluid.isSource() ? reaction.withLava() : reaction.withFlowingLava();
        } else if (typeP.getFluid() == Fluids.LAVA) {
            FluidLavaReaction reaction = FluidLavaReaction.get(typeW);
            if (reaction != null) blockState = reaction.lavaOnSelf();
        }
        if (blockState != null) {
            world.setBlockAndUpdate(pos, blockState);
            ci.cancel();
        }
    }

}
