# shadowclient 0.3.0

### newly added modules:
* no overlay
* chest steal
* enable all modules (careful...)
* weather control
* flat items
* click tp
* light overlay
* unfocused fps
* hide shield
* zoom
* no bob
* autohit

### new features:
* add corner setting to shadowhud
* entityculling mod doesn't mess up xray and esp 
* new mode to xray
* custom font renderer
* custom keybinding configuration system
* made all text in the client translatable, meaning this allows the entire client to be translated into other languages
* german translations

### fixes:
* fix shadowhud not showing everything when loaded
* remove auto respawn as it seems to crash sometimes
* more crash fixes
* fix not being able to fly with elytra when nofall is enabled
* fix gamma being forced to 1600% on startup
* xray properly renders fluids now
* fix bug with fast block break
* fix settings not saving for some modules
* fix xray not rendering all blocks
* fix xray not reloading the world renderer upon mode change
* fix xray not working without sodium
* fix issues when holding a key while disabling freecam
* fix reach hack not working properly - nerfed until proper implementation
* fix module descriptions sometimes rendering underneath module buttons
* fix modules going off-screen on small screens
* update trajectories to treat water like a solid block for the fishing rod
* fix a crash with meteor (if you for some reason were to use them together)
* vanilla spoof also now doesn't change the window name

### other misc. changes:
* changed some of the UI colors slightly
* turn off vanilla spoof by default






## yet to do:
* sticky aim - decrease the mouse sensitivity when aiming near enemies
* fix searching for settings in the client settings screen, I think its broken
* better module settings - dedicated screen?
* optimize the font rendering so it doesn't switch textures for each character
* optimize clickGui. insane lag on low-end devices - caching? maybe use a debugger/profiler
* rewrite some of the old modules
* rewrite some gui code
* auto updater?
* optimize light overlay with caching
* positionSetting (+ setHere button)
* killaura mob types
* voidESP
* fix blink
* simple shadowcl <-> shadowcl encrypted chat (simple maybe key-based encoding so messages don't show in server logs)
* different modes for flight (creative flight-like)
* arrow dodge
* anti ice
* fix clickTP teleporting wrongly
* aimbot/aimassist
* auto update button
* implement proper reach hack
* hack list in hud
* blink mode so it prioritizes blinks in hidden spots
* breadcrumbs/trails
* anti book/shulker ban (if possible)
* stepdown / fast fall (like stepup but reverse)
* fastswim
* more ice speed
* jesus
* speed
* viewlock (lock your camera rotation)
* automine (hold left click)
* scaffold
* make some mixins that call a module (especially if there are more than 1 module) specifically events instead
* bugfixes, obviously
* nuker (just mine all blocks)
* highway builder? for anarchy servers
* fix autowalk not sprinting, even when sprint key is pressed
* instead of having entity type settings like [hostile: false, players: true, ...] instead just make a universal EntitiesSetting as well as BlocksSetting (for xray, + a whitelist/blacklist toggle)
* no jump cooldown (be able to hold space under trees)








# Just me testing on a spigot server with NC+ testing what's detected

### ANTICHEAT BYPASS PROGRESS:
#### TESTED, NOT DETECTED
* No Knockback
* Reach
* Chest Steal (with high enough delay, else "tried to move items in their inventory too quickly, Tags: clickspeed")
* Fast Break (sometimes doesn't break, my server lagging?)
* Hotbar Cycle
* Auto Fish 
* Fast Place
* Flat Items
* All Modules (Undetected but starts every other module lol)
* Sneak Spam
* Rainbow GUI
* Derpy (on some server software apparently also rotates it client-side?)
* Secret Shaders
* Dinnerbonify All
* * ALL OF RENDER
* * ALL OF OTHER
* * ALL OF WORLD
* Parkour (with legit)
* Autosprint
* No slowdown
* BHopping
* Safe Walk

#### TESTED, DETECTED, NOT FIXED
* Killaura (Unlikely fast clicking)
* Criticals (rarely) ("illegal critical", Tags: falldist_mismatch)
* Auto Crystal (Unlikely fast clicking)
* Airjump (failed SurvivalFly: tried to move unexpectedly)
* Parkour (without legit) (failed SurvivalFly: tried to move unexpectedly)
* Stepup (failed SurvivalFly: tried to move unexpectedly)
* Boatfly (no output, just throws you out of the boat)
* No fall Damage (no output, you just get damage)
* Fastclimb (failed SurvivalFly: tried to move unexpectedly)
* High jump (failed SurvivalFly: tried to move unexpectedly) (at every jump height slightly under or above 1)
* ClickTP (failed SurvivalFly: tried to perform an illegal move)

#### TESTED, DETECTED, FIXED
* nothing yet

TODO CONTINUE WITH TESTING; FLY IS NEXT


