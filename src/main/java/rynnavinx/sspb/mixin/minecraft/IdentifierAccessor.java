package rynnavinx.sspb.mixin.minecraft;

import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Identifier.class)
public interface IdentifierAccessor {
    @Invoker("<init>")
    static Identifier invokeInit(String namespace, String path) { throw new AssertionError(); };
}
