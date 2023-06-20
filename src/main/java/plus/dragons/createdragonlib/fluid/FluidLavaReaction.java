package plus.dragons.createdragonlib.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;

@SuppressWarnings("UnstableApiUsage")
public record FluidLavaReaction(BlockState withLava, BlockState withFlowingLava, BlockState lavaOnSelf) {

    private static final IdentityHashMap<FluidVariant, FluidLavaReaction> REACTIONS = new IdentityHashMap<>();

    public static void register(FluidVariant type, BlockState withLava, BlockState withFlowingLava, BlockState lavaOnSelf) {
        // FIXME it does not affect real world fluid interaction
//        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
//            type, fluidState -> fluidState.isSource() ? withLava : withFlowingLava
//        ));
//        FluidInteractionRegistry.addInteraction(type, new FluidInteractionRegistry.InteractionInformation(
//            ForgeMod.LAVA_TYPE.get(), lavaOnSelf
//        ));
        REACTIONS.put(type, new FluidLavaReaction(withLava, withFlowingLava, lavaOnSelf));
    }

    @Nullable
    public static FluidLavaReaction get(FluidVariant fluid) {
        return REACTIONS.get(fluid);
    }

}
