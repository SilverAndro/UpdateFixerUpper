/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 TheoreticallyUseful
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package updatefixerupper.updatefixerupper.mixin;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import updatefixerupper.updatefixerupper.UpdateFixerUpper;

@Mixin(DefaultedRegistry.class)
public class RegistryGetHijackMixin {
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
