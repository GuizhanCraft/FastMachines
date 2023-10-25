package net.guizhanss.fastmachines.utils;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import org.bukkit.NamespacedKey;

import net.guizhanss.fastmachines.FastMachines;

import lombok.experimental.UtilityClass;

@SuppressWarnings("ConstantConditions")
@UtilityClass
public final class Keys {
    public static NamespacedKey get(@Nonnull String name) {
        Preconditions.checkArgument(name != null, "name cannot be null");

        return new NamespacedKey(FastMachines.getInstance(), name);
    }
}
