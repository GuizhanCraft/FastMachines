package net.guizhanss.fastmachines.core;

import java.util.ArrayList;
import java.util.List;

import net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine;

import lombok.Getter;

@Getter
public final class Registry {

    private final List<AbstractFastMachine> enabledFastMachines = new ArrayList<>();
}
