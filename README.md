# SQMC

SQMC is an MMORPG plugin and framework designed for the CraftBukkit server and Bukkit API. 

# Features

* A skills system
* Rework of the default crafting system
* New mob mechanics
* Custom item API
* New fishing, woodcutting, and mining mechanics
* Naturally respawning resources
* Multi-server support using a centralized database, unlike most MMO's where data is server-dependent. 
* JSON files describe items and entities so non-programmers can easily create new items and enenmies
* Item banking system
* ChestInterface API
* Rich particle animations library featuring everyone's favorite: parametric equations!
* Region-specific noteblock songs using NoteBlockAPI
* Quest system

# Dependencies

Unfortunately, SQMC doesn't currently use a build manager, though that will change in the next release.

* [CraftBukkit](https://www.spigotmc.org/) (latest version)
* [Bukkit](https://www.spigotmc.org/) (latest version)
* [NoteblockAPI](https://github.com/xxmicloxx/NoteBlockAPI)
* [DynMap](https://www.spigotmc.org/resources/dynmap.274/)
* [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
* [Fanciful](https://github.com/mkremins/fanciful) (shaded)
* [JSON-Java](https://github.com/stleary/JSON-java) (shaded)

# Installation

Right now, you have to compile from source with all the necessary depdencies. Sorry! (Build manager coming soon)

# TODO

* Modularize and isolate the API from the SQMC game plugin itself
* MySQL input sanitization (current queries are wildly unsafe for production, but functional in testing)
* Rework DataManager and PlayerData classes to include a greater level of modularization

