package plus.dragons.createdragonlib.mixin;

import com.simibubi.create.foundation.events.CommonEvents;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.Iterate;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createdragonlib.fluid.FluidLavaReaction;

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = CommonEvents.class, remap = false)
public class CommonEventsMixin {

    @Inject(method = "whenFluidsMeet", at = @At("HEAD"), cancellable = true)
    private static void createDragonLib$whenFluidsMeet(LevelAccessor world, BlockPos pos, BlockState blockState, CallbackInfoReturnable<BlockState> cir) {
        FluidState fluidState = blockState.getFluidState();
        FluidVariant type = FluidVariant.of(FluidHelper.convertToStill(fluidState.getType()));
        FluidVariant type2 = FluidVariant.of(FluidHelper.convertToStill(world.getFluidState(pos.relative(Direction.UP)).getType()));
        BlockState blockState2 = null;
        if (type.getFluid() == Fluids.LAVA) {
            FluidLavaReaction reaction = FluidLavaReaction.get(type2);
            if (reaction != null) blockState2 = fluidState.isSource() ? reaction.withLava() : reaction.withFlowingLava();
        } else if (type2.getFluid() == Fluids.LAVA) {
            FluidLavaReaction reaction = FluidLavaReaction.get(type);
            if (reaction != null) blockState2 = reaction.lavaOnSelf();
        }
        if (blockState2 != null) {
            cir.setReturnValue(blockState2);
            return;
        }
        for (Direction direction : Iterate.horizontalDirections) {
            type2 = FluidVariant.of(FluidHelper.convertToStill(world.getFluidState(pos.relative(direction)).getType()));
            if (type.getFluid() == Fluids.LAVA) {
                FluidLavaReaction reaction = FluidLavaReaction.get(type2);
                if (reaction != null) blockState2 = fluidState.isSource() ? reaction.withLava() : reaction.withFlowingLava();
            }
            if (blockState2 != null) {
                cir.setReturnValue(blockState2);
                return;
            }
        }
    }
}
