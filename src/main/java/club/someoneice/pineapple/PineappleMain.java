package club.someoneice.pineapple;

import club.someoneice.pineapple.event.VanillaEvent;
import club.someoneice.pineapple.gem.WildPineapple;
import club.someoneice.pineapple.init.BlockList;
import club.someoneice.pineapple.init.ItemList;
import club.someoneice.pineapple.data.ItemsGroup;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

@Mod(PineappleMain.MODID)
public class PineappleMain
{
    public static final String MODID = "pineapple_delight";
    public static boolean SEASON_INSTALL = false;

    public PineappleMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemsGroup.init();

        modEventBus.addListener(this::onRenderTypeSetup);
        ItemList.ITEMS.register(modEventBus);
        BlockList.BLOCKS.register(modEventBus);
        BlockList.BLOCK_ITEMS.register(modEventBus);

        WildPineapple.FEATURES.register(modEventBus);
        WildPineapple.PATCHES.register(modEventBus);

        // MinecraftForge.EVENT_BUS.register(new WorldEvent());
        MinecraftForge.EVENT_BUS.register(new VanillaEvent());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommonSetupevent(FMLCommonSetupEvent event) {
        SEASON_INSTALL = FMLLoader.getLoadingModList().getModFileById("sereneseasons") != null;

        ComposterBlock.COMPOSTABLES.put(BlockList.PINEAPPLE_CROP.get(), 0.65f);
        ComposterBlock.COMPOSTABLES.put(ItemList.PINEAPPLE.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ItemList.PINEAPPLE_SIDE.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ItemList.PINEAPPLE_PIE_SIDE.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(BlockList.PINEAPPLE_PIE_ITEM.get(), 1.0f);
    }

    @SubscribeEvent
    public void onRenderTypeSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockList.PINEAPPLE_WILD_CROP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockList.PINEAPPLE_CROP.get(), RenderType.cutout());
        });
    }
}
