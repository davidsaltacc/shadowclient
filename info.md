# shadowclient 0.3.0 changelog

### version change
* moved to minecraft 1.21.4

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
* breadcrumbs
* auto crouch
* spoof view distance

### removed modules:
* super secret shaders, minecraft removed them 
* unfocused fps, vanilla has support for that (though sodium hides the option...?)

### new features:
* add corner setting to shadowhud
* entityculling mod doesn't mess up xray and esp 
* new mode to xray
* custom font renderer
* custom keybinding configuration system
* better ui ordering
* made all text in the client translatable, meaning this allows the entire client to be translated into other languages
* german translations
* add easings to sliders to make them easier to configure
* tracers show distance and name
* rewritten improved and more optimized rendering
* in airjump you can toggle between normal and "jetpack" mode (hold)
* heavily improved and bugfixed freecam

### fixes:
* fixed a lot of ui related bugs
* fixed sliders resetting to minimum values randomly
* fixed issues with rendering system
* fixed issue with rendering tracer to player in freecam
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
* fix setting change callbacks not being called if the set value exceeded the maximum
* others

### other misc. changes:
* made clickgui independent of the gui scale
* changed some of the UI colors slightly
* turn off vanilla spoof by default








## yet to do:
- sodium does not cause issues... remove the warning.
- is it possible to dynamically grow the font atlas?
- spectator mode - spectate someone. make the cameraEntity be someone else
- seedcrackerX integration? maybe
- seed xray / "oresim" 
- translatable setting names
- unhittable - move player around in a small area real quickly to make them harder to hit
- autosneak: make interactions be normal
* clean up the codebase... so much unused stuff
- rework most of the *Utils classes
* remove legit option from parkour, just default it
- wall interact !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
- noclip
- redo the splash texts. fuuucking hell
- less annotations, more constructor params
- wall phase. please
- SCFont.getWidth seems to be wrong slightly, does it count spaces properly?
- top bar right click simulator
- no fog
- reset breadcrumbs on leave
- optimize breadcrumbs rendering
- how do wardens detect sound? I need smt against them
- WARDEN STATUS CHECKER!!!!
- highlighter: is this chest looted (was the loot generated already)
- killaura filter
- more killing & trolling methods (hitting through wall works with killaura legit off)
- view dist spoof (set view dist high, but don't render the chunks far out. for shit like tracers and other stuff, so the server still gives the entity positions)
- bow shit (extreme speed before shooting gives big damage, aswell as aim shit)
- how does dolphins grace work?
- fix the fly kick bypass (just fly down for a bit then back up, look up the anticheat how it does it)
- investigate this new HorizontalCollison thing in the uhhhhhh packet, i think when you are bumping into shit you don't get flykickedd as easily? see if exploitable
- make night vision enabling/disabling and other effect stuff uhh trigger some kind of reload, it seems to only apply after a second or so
- mine patter finder - overlay - finds the most ideal legit looking pattern to mine in that gets you the most loot
- boat full turn camera in f5
- boatfly faster turning
- how do game detect if swimming? can be used to exploit? swimming -> freely moveable basically
- logoff spot visualizer
* click to dismiss is STILL unreadable.
* a way to reset ui positions (only positions, not the settings)
* add cooldown to air jump (configurable)
* make more things translatable
* CHANGE PACKET LOG TO NOT LOG INTO IN GAME CHAT - make new notification type, smaller, disappearing
* breadcrumbs render only mode
* sticky aim - decrease the mouse sensitivity when aiming near enemies
* fix searching for settings in the client settings screen, I think it is broken (modulebutton class is the only one that has uhh the thing)
* better module settings - dedicated screen?
* optimize the font rendering so it doesn't switch textures for each character
* optimize clickGui. insane lag on low-end devices - caching? maybe use a debugger/profiler
* rewrite some of the old modules
* rewrite some gui code
* auto updater?
* optimize light overlay with caching / just don't update every single fucking frame???
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
* nuker (just mine all blocks)
* highway builder? for anarchy servers
* fix autowalk not sprinting, even when sprint key is pressed
* instead of having entity type settings like [hostile: false, players: true, ...] instead just make a universal EntitiesSetting as well as BlocksSetting (for xray, + a whitelist/blacklist toggle)
* no jump cooldown (be able to hold space under trees)
* eased sliders (sliders, but with easing, so it's easier to configure some values)
* ui animations !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
* use setting change callbacks instead of manual changing, I didn't know we had callbacks lol








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


