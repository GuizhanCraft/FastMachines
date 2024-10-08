# FastMachines 快捷机器

[English](README.md) | [中文](README.zh_CN.md)

该粘液科技附属将快速机器的玩法从[乱序技艺](https://github.com/ecro-fun/FinalTECH)中提取出来，并进行了一些改动，使其作为一个单独的附属。
快捷机器是手动的消耗电力来进行合成的机器，无需将配方按顺序摆放，支持批量合成。

鸣谢：Final_Root

## 下载

[![Build Status](https://builds.guizhanss.com/ybw0014/FastMachines/master/badge.svg)](https://builds.guizhanss.com/ybw0014/FastMachines/master)

## 配置

### 通用配置 (config.yml)

- `auto-update`: 是否启用自动更新（默认值：`true`）
- `lang`: 插件的语言，可在[此处](LOCALES.md)查看可用语言（默认值：`en-US`）
- `enable-researches`: 是否启用快速机器的研究（默认值：`true`）
- `debug`: 是否启用调试模式（默认值：`false`）
- `fast-machines.use-energy`: 是否启用快捷机器的能量消耗（默认值：`true`）

### 物品特定配置（/plugins/Slimefun/Items.yml）

以下设置适用于每个独立的快捷机器（默认值可能因原合成机器的电力消耗而改变）：

- `energy-per-use`：每次制作操作的电力消耗（默认值：`8`，范围：`0` - `2,147,483,647` (2^31-1)）
- `energy-capacity`：机器可存储的电力（默认值：`8`，范围：`0` - `2,147,483,647` (2^31-1)）

## 感谢

感谢在开发此插件过程中帮助我的所有人。

感谢[minecraft-heads.com](https://minecraft-heads.com/)提供此插件中使用的头颅。

[![](https://minecraft-heads.com/images/banners/minecraft-heads_fullbanner_468x60.png)](https://minecraft-heads.com/)

