package rynnavinx.sspb.client.render.model;

import net.minecraft.client.render.model.BakedModel;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;


public class SSPBBakedModel extends ForwardingBakedModel {

    public SSPBBakedModel(BakedModel bakedModel){
        wrapped = bakedModel;
    }


    @Override
    public boolean isVanillaAdapter() {
        return false;
    }
}
