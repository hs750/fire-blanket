# User manual

## meta

###Resources
* [Tips to create a useful user manual /stackoverflow](http://stackoverflow.com/questions/241422/tips-to-create-a-useful-user-manual)
* [Slides](http://www.cdf.utoronto.ca/~csc207h/summer/lectures/UserGuide.pdf)

## Contents

1. [Introduction](#um-1) details about the game, a bit of backstory, and how it works
2. [Quick start guide](#um-2) this will go on the site's front page as well
3. [Background story](#um-3) [optional] not totally sure this belongs in the user manual
4. [Main section](#um-4)
  1. How the plant works, what each component does etc. + diagrams
  2. How to win (game mechanics detailed)
5. [Commands index](#um-5) + description
6. [Reference listing](#um-6) (general index)
7. [Error messages](#um-7)

<a name="um-1"/>
## 1. Introduction

<a name="um-2"/>
## 2. How to play

_This is a short guide to start playing immediately. To understand and maybe master the game, have a look at the [background story](#um-3) and at [functionalities](#um-4) sections._

**Step 1.** Run `fireblanket.jar`

**Step 2.** Enter `new game <NAME>`. Replace `<NAME>` with your name.

**Step 3.** Enter `load` <NAME of saved file>. 

**Step 4.** Type in [commands](#um-5) to control the nuclear power plant.

Produce sufficient energy to sustain the infrastructure. Fail and you lose the game. You also lose if you overheat the reactor, so be careful.

### What you can do

* Lower or raise the control rods: `>> <REACTOR_NAME> rods set <INT_VALUE>`
  The rods level is proportional to how much steam the reactor is generating.
  Lowering the rods is a good way to try to avoid meltdown.

* Turn on and off the pumps: The user can use the pumps to regulate the water accordning to the energy needs of the plant.
* OPen and close the valves: the user can open and close the valves to stop the flow of water.

* TODO add more commands


<a name="um-3"/>
## 3. Background story

On the 8th April 2013, students at the University of York prepared to demonstrate their second year software projects. A challenging task, several teams had prepared video game simulations of nuclear power plants for their professor’s approval. Some projects were simple stand-alone affairs powered only by the department’s computers. Others utilised the internet to provide automatic updates. One such system would change the world forever. 

Precisely one week from the initial deployment of the SEPR solutions, disaster shook the world. Propagating from one server to the next using obscure net technologies, an unidentified program began initiating the update protocols of the world’s major nuclear power plants. Inexplicably replacing SCADA systems with the student’s simulations, the world’s power plants began melting down in quick succession.
The ensuing blanket of fire engulfed the world, reducing civilisation to smouldering ash.
But the human race proved more resilient than could have been hoped, and pockets of life survived. One such region, spared of destruction by the recent decommission of its own power plant was none other than the city that spawned its downfall. York had survived, and with it, it’s university.
In the weeks that followed, urgent discussion took place across what was left of the city. Unified by an impromptu council of community leaders and prominent academics, the course was set for humanity’s fate. The survivors would require food, shelter, infrastructure and means of self-protection, and for this they would require electricity.

Enlisting the might of the nearby garrison of Northern Command, the council lead its citizens on a journey to re-commission its fatefully dormant nuclear facility. 
Months passed, and the decision to mobilise Northern Command proved to be a crucial one. The first signs of mutation arose in the form of rodents emerging from the irradiated ground. Whilst of little danger to humans, these rodents would herald the coming of a much greater threat to humanity.
With dwindling supplies, and rumours of larger mutating creatures converging towards the sounds of construction, the military remnants began a fevered fortification of the power plant. Enmeshed within electric fences and weapons systems, humanity’s final revival would likely be carried out against the shadow of constant siege. And to some, lingering memories of the destructive potential of nuclear power outweighed the protection that it afforded...

Six months have passed since the blanket of fire descended and the newly established outpost of Town has been born. The nuclear facility for which the town was created has been successfully commissioned by a team of surviving engineers lead by Professor Richard Paige, and in which you were involved. Following the untimely digestion of a senior operator by a stray mutated goose, you have been assigned a more prominent role in the functioning of the plant. Your presence in the control room has been requested by the Professor to explain your new responsibilities...

<a name="um-4"/>
## 4. Functionalities
### Autosave
The game is autosaved every 7 seconds. The autosave file can be found in `saves/autosave.fg`.

### Load game
The user can load a saved game.

### Repair Failing Components
The user can repair the failed components. 

### Item
Item description

### Item
Item description

<a name="um-5"/>
## 5. Game commands

`>> <VALVE_NAME> close`

* Closes the valve `VALVE_NAME`

`>> load <FILENAME>.fg`

* Will load from `FILENAME` and start a new game
* The previous game will end

`>> new game <NAME>`

* Starts a new game (the previous game will end, be careful)
* Uses the data from `save/newgame.fg`
* `NAME` will be the name of the plant operator
* The previous game will end

`>> <VALVE_NAME> open`

* Opens the valve `VALVE_NAME`

`>> <REACTOR_NAME> rods set <INT_VALUE>`

* This is used to control the rods - the higher this value is, the more power the reactor will produce
* Example: `Reactor rods set 80`
* `INT_VALUE` must be in [0,100]

`>> <PUMP_NAME> rpm <INT_VALUE>`

* This assigns a new RPM to a pump
* Example: `Pump 1 rpm 25` or `Coolant Pump rpm 250`

`>> <PUMP_NAME> on`
* Turns on the pump

`>> <PUMP_NAME> off`
* Turns off the pump

`>> <VALVE_NAME> close`
* Closes the valve

`>> show saves`
* Shows a list of all the saved games

`>> save`

* Saves to the current save file.

`>> save as <FILENAME>.fg`

* Saves using the FILENAME provided.


Saves the current state of the plant to a file. Doesn't interrupt the game (check the [autosave](#um-autosave) feature).


<a name="um-6"/>
## 6. Reference listing

<a name="um-7"/>
## 7. Error messages



&copy; 2013 Team Anchovy
