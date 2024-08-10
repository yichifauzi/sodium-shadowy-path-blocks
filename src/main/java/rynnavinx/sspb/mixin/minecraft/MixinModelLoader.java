package rynnavinx.sspb.mixin.minecraft;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.util.ModelIdentifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import rynnavinx.sspb.client.SSPBClientMod;
import rynnavinx.sspb.client.gui.SSPBGameOptionPages;
import rynnavinx.sspb.client.render.model.SSPBBakedModel;


// Using a mixin to do this instead of fabric api because the api that handled model wrapping changed between supported mc versions
@Mixin(ModelLoader.class)
public abstract class MixinModelLoader {

    @Unique
    private void sspb$wrapDirtPathModel(){
        ModelLoader thisModelLoader = ((ModelLoader)(Object) this);

        ModelIdentifier id = new ModelIdentifier(IdentifierAccessor.sspb$invokeInit("minecraft", "dirt_path"), "");
        BakedModel originalBakedModel = thisModelLoader.getBakedModelMap().get(id);

        if(originalBakedModel != null){
            SSPBGameOptionPages.setVanillaPathBlockLightingOptEnabled(true);

            // wrap if vanilla path block lighting is on and not using frapi
            if(SSPBClientMod.options().vanillaPathBlockLighting && originalBakedModel.isVanillaAdapter()){
                thisModelLoader.getBakedModelMap().replace(id, new SSPBBakedModel(originalBakedModel));
            }
        }
        else{
            SSPBGameOptionPages.setVanillaPathBlockLightingOptEnabled(false);

            SSPBClientMod.LOGGER.error("[SSPB] Something went wrong and the dirt path model was not found. Option to toggle vanilla path block lighting is disabled.");
        }
    }


    /* These three mixins target the same bit of code that got slightly refactored between versions,
     * so I'm injecting like this for compatibility reasons.
     */

    // Targets the "update" method
    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"}) // Suppress because the method is not in this project's MC version. Compiler still complains though...
    @Inject(method = "method_18177(Lnet/minecraft/class_1060;Lnet/minecraft/class_3695;)Lnet/minecraft/class_4724;", at = @At(value = "INVOKE", target = "Ljava/util/Set;forEach(Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER), require = 0)
    private void wrapDirtPathModel1_19(CallbackInfoReturnable<SpriteAtlasManager> cir){
        sspb$wrapDirtPathModel();
    }

    // Targets the "bake" method
    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"}) // Suppress because the method is not in this project's MC version. Compiler still complains though...
    @Inject(method = "method_45876(Ljava/util/function/BiFunction;)V", at = @At(value = "INVOKE", target = "Ljava/util/Set;forEach(Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER), require = 0)
    private void wrapDirtPathModel1_19_3(CallbackInfo ci){
        sspb$wrapDirtPathModel();
    }

    @Inject(method = "bake(Lnet/minecraft/client/render/model/ModelLoader$SpriteGetter;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V", shift = At.Shift.AFTER), require = 0)
    private void wrapDirtPathModel1_21(CallbackInfo ci){
        sspb$wrapDirtPathModel();
    }
}
