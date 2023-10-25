# FastMachines

[English](README.md) | [中文](README.zh_CN.md)

This Slimefun addon extracts the manual machines from [FinalTECH](https://github.com/ecro-fun/FinalTECH) and made some changes to put them into a separate addon.  
Fast machines are Slimefun basic machines, but they cost energy to run and can craft items without recipe to be in order.

Credit: Final_Root

## Download

(WIP)

## Configuration

### General Config (config.yml)

- `auto-update`: Whether to enable auto update from TheBusyBiscuit's builds page. (default: `true`)
- `lang`: The language of the addon, check available languages [here](LOCALES.md). (default: `en-US`)
- `enable-researches`: Whether to enable researches for fast machines. (default: `true`)
- `debug`: Whether to enable debug mode. (default: `false`)
- `fast-machines.use-energy`: Whether to enable energy cost of Fast Machines' crafting. (default: `true`)

### Item-specific config (/plugins/Slimefun/Items.yml)

The following settings are available for each individual Fast Machines:

- `energy-per-use`: The energy cost of each crafting operation. (default: `8`, range: `0` - `33554431`(2^25-1))

## Thanks

Thanks to anyone who helped me during the development of this addon.

Thanks to [minecraft-heads.com](https://minecraft-heads.com/) for the heads used in this addon.

[![](https://minecraft-heads.com/images/banners/minecraft-heads_fullbanner_468x60.png)](https://minecraft-heads.com/) 

