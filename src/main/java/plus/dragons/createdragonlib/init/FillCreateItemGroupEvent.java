package plus.dragons.createdragonlib.init;

import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.*;

public class FillCreateItemGroupEvent {

    public static class Inserter{
        private final NonNullList<ItemStack> items;
        private final Map<Item, List<ItemStack>> insertions = new IdentityHashMap<>();

        public Inserter(NonNullList<ItemStack> items) {
            this.items = items;
        }

        /**
         * Add an {@link ItemStack} after an {@link Item}, should only target Create's existing items in the tab.
         * @param target the item to target
         * @param stack the item stack to add
         */
        public void addInsertion(ItemLike target, ItemStack stack) {
            insertions.computeIfAbsent(target.asItem(), $ -> new ArrayList<>()).add(stack);
        }

        /**
         * Add some {@link ItemStack}s after an {@link Item}, should only target Create's existing items in the tab.
         * @param target the item to target
         * @param stacks the item stacks to add
         */
        public void addInsertions(ItemLike target, Collection<ItemStack> stacks) {
            insertions.computeIfAbsent(target.asItem(), $ -> new ArrayList<>()).addAll(stacks);
        }


        public void doneInsertion() {
            ListIterator<ItemStack> it = items.listIterator();
            while (it.hasNext()) {
                Item item = it.next().getItem();
                if (insertions.containsKey(item)) {
                    for (var inserted : insertions.get(item)) {
                        it.add(inserted);
                    }
                    insertions.remove(item);
                }
            }
        }
    }

    


    public interface CallBack {
        Event<CallBack> EVENT = EventFactory.createArrayBacked(CallBack.class,
                (listeners) -> (itemGroup, items) -> {
                    for (CallBack listener : listeners) {
                        InteractionResult result = listener.interact(itemGroup, items);

                        if (result != InteractionResult.PASS) {
                            return result;
                        }
                    }

                    return InteractionResult.PASS;
                });

        InteractionResult interact(CreateCreativeModeTab itemGroup, NonNullList<ItemStack> items);
    }

}
