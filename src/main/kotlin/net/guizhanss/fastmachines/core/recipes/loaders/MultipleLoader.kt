package net.guizhanss.fastmachines.core.recipes.loaders

import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine

/**
 * A [RecipeLoader] that loads recipes from multiple [RecipeLoader]s.
 */
class MultipleLoader(
    machine: BaseFastMachine,
    vararg loaders: RecipeLoader,
) : RecipeLoader(machine) {

    private val loaders = loaders.toList()

    override fun load() {
        for (loader in loaders) {
            loader.load()
        }
    }
}
