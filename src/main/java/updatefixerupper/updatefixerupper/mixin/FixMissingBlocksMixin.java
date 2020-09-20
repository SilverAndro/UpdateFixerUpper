package updatefixerupper.updatefixerupper.mixin;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import updatefixerupper.updatefixerupper.UpdateFixerUpper;

@Mixin(DefaultedRegistry.class)
public class FixMissingBlocksMixin<T> {
    @ModifyVariable(at = @At("HEAD"), method = "get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;")
    Identifier fixMissingFromRegistry(@Nullable Identifier id) {
        if (id != null) {
            if (UpdateFixerUpper.fixerMap.containsKey(id.toString())) {
                return UpdateFixerUpper.fixerMap.get(id.toString());
            }
        }
        return id;
    }
}
