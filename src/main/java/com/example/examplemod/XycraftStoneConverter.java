package com.example.xycraftoverride;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("xycraft_stone_converter")
public class XycraftStoneConverter {

    public XycraftStoneConverter() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Init stuff if needed
    }

    @Mod.EventBusSubscriber(modid = "xycraft_stone_converter", value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onScreenInit(ScreenEvent.Init.Post event) {
            if (event.getScreen() instanceof StonecutterScreen screen) {
                event.addListener(Button.builder(Component.literal("↺ Convert All Stone"), btn -> {
                    Minecraft mc = Minecraft.getInstance();
                    if (mc.player == null) return;

                    Inventory inv = mc.player.getInventory();
                    Item xyItem = BuiltInRegistries.ITEM.get(new ResourceLocation("xycraft_override", "smooth_smooth_stone"));

                    for (int slot = 0; slot < inv.getContainerSize(); slot++) {
                        ItemStack stack = inv.getItem(slot);
                        if (stack.getItem() == Items.STONE) {
                            int count = stack.getCount();
                            inv.setItem(slot, ItemStack.EMPTY);
                            inv.placeItemBackInInventory(new ItemStack(xyItem, count));
                        }
                    }
                }).bounds(screen.width / 2 + 60, screen.height / 2 - 60, 120, 20).build());
            }
        }
    }
} 
