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

package io.github.silverandro.ufu.mixin;

import io.github.silverandro.ufu.UpdateFixerUpper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DefaultedRegistry.class)
public class RegistryGetHijackMixin<T> {
    @Inject(
            method = "get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/registry/DefaultedRegistry;defaultValue:Ljava/lang/Object;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    void fixMissingFromRegistry(@Nullable Identifier id, CallbackInfoReturnable<@NotNull T> cir, Object obj) {
        if (id == null) {
            //noinspection unchecked
            cir.setReturnValue((T) obj);
            return;
        }
        //noinspection unchecked
        cir.setReturnValue((T) UpdateFixerUpper.fixerMap.getOrDefault(id.toString(), id));
    }
}