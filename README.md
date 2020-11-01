# GeoEssentials
A Minecraft plugin with a variety of functions

## Homes
| Command | Permission Node | Aliases | Usage | Description |
|----|----|----|----|----|
| Home | GeoEssentials.player.home | h | /home [name] [get/set/remove/update] [target] | Lets you create player-specific locations that can be teleported back to. |

  ### Player Functions:
  | Subcommand | Description |
  |----|----|
  | /home | Returns a list of all of the user's homes. |
  | /home [name] | Teleports the player to the location of the given home. |
  | /home [name] [get] | Returns the location of the given home. |
  | /home [name] [set] | Creates a new home with the given name at the user's current location. |
  | /home [name] [remove] | Removes a given home. |
  | /home [name] [update] | Updates an existing home to the user's current location. |

  Note: The home teleport will bring your current mount with you when you teleport.

  ### Staff Functions:
  *** Requires the GeoEssentials.staff.home permission node to use the following ***
  | Subcommand | Description |
  |----|----|
  | /home [name] [get/set/remove/update] [target] | Does the same functions as the above commands but allows the user to run them on the target's homes. |

  Note: All of the data from player homes is stored in ./plugins/GeoEssentials/homes/[playerUUID].yml in plain text if you need to access that for administration purposes.

## Markers
| Command | Permission Node | Aliases | Usage | Description |
|----|----|----|----|----|
| Mark | GeoEssentials.player.mark | /loc | /mark [name] [set/remove/update/track] | Creates a waypoint at the user's current location. |

  ### Subcommand Functions:
  | Subcommand | Description |
  |----|----|
  | /mark | Returns a list of all of the user's marks. |
  | /mark [name] | Returns the location of the given mark. |
  | /mark [name] [set] | Creates a new mark with the given name at the user's current location. |
  | /mark [name] [remove] | Removes a given mark. |
  | /mark [name] [update] | Updates an existing mark to the user's current location. |
  | /mark [name] [track] | WIP: Creates a visual indication of where the mark is relative to you. |
  
  ### Staff Functions:
  Note: All of the data from player marks is stored in ./plugins/GeoEssentials/marks/[playerUUID].yml in plain text if you need to access that for administration purposes.

## Warps
| Command | Permission Node | Aliases | Usage | Description |
|----|----|----|----|----|
| /warp | GeoEssentials.player.warp || /warp [name] [get/set/remove/update] | Creates a server-wide location that can be teleported back to. |

  ### Player Functions:
  | Subcommand | Description |
  |----|----|
  | /warp | Returns a list of all warps. |
  | /warp [name] | Teleports the player to the location of the given warp. |

  Note: The warp teleport will bring your current mount with you when you teleport.

  ### Staff Functions:
  *** Requires the GeoEssentials.staff.warp permission node to use the following ***
  | Subcommand | Description |
  |----|----|
  | /warp [name] [get] | Returns the location of the given warp. |
  | /warp [name] [set] | Creates a new mark with the given name at the user's current location. |
  | /warp [name] [remove] | Removes a given warp. |
  | /warp [name] [update] | Updates an existing warp to the user's current location. |

  Note: All of the data from the warps is stored in ./plugins/GeoEssentials/warps.yml in plain text if you need to access that for administration purposes.

## Other Commands
| Command | Permission Node | Aliases | Usage | Description |
|----|----|----|----|----|
| Back | GeoEssentials.player.back | /return | /back | Teleport the user back to where they last TP'd from or their death location, whichever is more recent. |
| GeoTP | GeoEssentials.staff.geotp | /gtp | /geotp <x> <y> <z> [dimension ID] [target] | Teleports the user to the target location. Can be used to cross dimension teleport. Also allows targeting of another player. GeoTP will check for safe footing at the location, this can be disabled in the config file. |
| Spawn | GeoEssentials.player.spawn || /spawn | Teleports the user back to the overworld's spawnpoint. |
| Sudo | GeoEssentials.staff.sudo || /sudo <target> <command> [command arguments] | Forces the target to run the given command with any given arguments. |

## Other Features

### Percentage Sleeping
This feature allows night to be skipped with only a percentage of player sleeping. The default percentage is 40% and this can be changed in the config file.

  ### Right Click Harvesting
  This feature allows fully grown crops to be harvested and immediately replanted by right clicking them.
  Included crops are Wheat, Carrots, Potatoes, Beetroots and Nether Warts.

  ### Lily Pad 'Growing'
  This features allows lilypads to be 'grown' by using bonemeal on them. Each bonemeal use has a 1 in 3 chance to drop a new lily pad.
  WIP : Add the same function for sugar cane and cacti

  ### Magnet
  This features adds a magnet in the form of either a nether star or a wither rose. The nether star/wither rose must be held in the user's off-hand slot to work. When held in the off-hand the magnet will instantly move all item entities and experience orbs within a 5 block radius of the user to the user's feet.
  WIP : Configurable Radius
