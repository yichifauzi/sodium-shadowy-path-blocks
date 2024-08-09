package rynnavinx.sspb.mixin.indium;

import link.infra.indium.Indium;
import link.infra.indium.renderer.aocalc.AoCalculator;
import link.infra.indium.renderer.aocalc.AoConfig;
import link.infra.indium.renderer.render.BlockRenderInfo;

import net.minecraft.block.DirtPathBlock;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import rynnavinx.sspb.client.SSPBClientMod;


@Pseudo @Mixin(AoCalculator.class)
public abstract class MixinAoCalculator {

    @Final @Shadow(remap = false)
    private BlockRenderInfo blockInfo;


    @Unique
    private float sspb$modifyW1(float w1){
        if(SSPBClientMod.options().onlyAffectPathBlocks && !(blockInfo.blockState.getBlock() instanceof DirtPathBlock)){
            return w1;
        }
        return (w1 * SSPBClientMod.options().getShadowynessCompliment()) + (SSPBClientMod.options().getShadowyness());
    }


    @ModifyVariable(method = "blendedInsetFace", at = @At("STORE"), ordinal = 0, remap = false)
    private float modifyBlendedInsetFaceW1(float w1){
        return sspb$modifyW1(w1);
    }

    @ModifyVariable(method = "gatherInsetFace", at = @At("STORE"), ordinal = 0, remap = false)
    private float modifyGatherInsetFaceW1(float w1){
        return sspb$modifyW1(w1);
    }

    @Redirect(method = "compute", at = @At(value = "FIELD", target = "Llink/infra/indium/Indium;AMBIENT_OCCLUSION_MODE:Llink/infra/indium/renderer/aocalc/AoConfig;", opcode = Opcodes.GETSTATIC), remap = false)
    private AoConfig redirectAoMode(){
        if(SSPBClientMod.options().vanillaPathBlockLighting && blockInfo.blockState.getBlock() instanceof DirtPathBlock){
            return AoConfig.VANILLA;
        }
        else{
            return Indium.AMBIENT_OCCLUSION_MODE;
        }
    }
}