# User manual

## Contents

1. [Introduction](#um-1)
2. [Quick start guide](#um-2)
3. [Background story](#um-3)
4. [Functionality](#um-4)
5. [Commands index](#um-5) + description
6. [Error messages](#um-6)

<a name="um-1"/>
## Introduction

Presented by Team Anchovy, Fire Blanket is a nuclear power plant simulator... with a post-apocalyptic twist. Near the city of York, in the emerging shanty town of Town, the remnants of humanity fight for survival against a mutating horde of zombies, geese and the occasional St John's student. As the Power Plant operator, the responsibility falls to you to keep your people safe. Generate too much power and Town might explode, too little, and Town's defences may shut down.

<a name="um-2"/>
## Quick start guide

_This is a short guide to start playing immediately. To understand and maybe master the game, have a look at the [background story](#um-3) and at [functionalities](#um-4) sections._

**Step 1.** Extract `fireblanket.zip` and run `fireblanket.jar` (you might need to do this first `chmod +x fireblanket.jar`)

**Step 2.** Enter `new game <your name goes in here> or load <name of saved file>`

**Step 3.** Type in [commands](#um-5) to control the nuclear power plant.

Produce sufficient energy to sustain the infrastructure. Fail and you lose the game. You also lose if you overheat the reactor, so be careful.

### What you can do

* Lower or raise the control rods: `>> <REACTOR_NAME> rods set <INT_VALUE>`
  The rods level is proportional to how much steam the reactor is generating.
  Lowering the rods is a good way to try to avoid meltdown.

* Turn on and off the pumps: [cmd goes in here]
  The user can use the pumps to regulate the water accordning to the energy needs of the plant.
* Open and close the valves: [cmd goes in here]
  the user can open and close the valves to stop the flow of water.


<a name="um-3"/>
## Background story


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
## Functionality

### Diagram of the nuclear power plant's default components

![Plant diagram NOLABELS](http://i.imgur.com/SBLKwR2.png)
![Plant diagram](http://i.imgur.com/jW00sqR.jpg)

### Autosave
The game is autosaved every 7 seconds. The autosave file can be found in `saves/autosave.fg`.

### Load game
The user can load a saved game.

### Repair Failing Components
The user can repair the failed components. 

### Add Components
The user can at anytime add a new component to the game and use it in anyway desired.

### Connect Components
The gamer can connect two components that he is using currently in the game.

<a name="um-5"/>
## Game commands

`>> load <FILENAME>.fg`

* Will load from `FILENAME` ahttp://i.imgur.com/SBLKwR2.pngnd start a new game
* The previous game will end

`>> new game <NAME>`

* Starts a new game (the previous game will end, be careful)
* Uses the data from `save/newgame.fg`
* `NAME` will be the name of the plant operator
* The previous game will end

`>> <VALVE_NAME> close`

* Closes the valve `VALVE_NAME`

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

`>> show saves`
* Shows a list of all the saved games

`>> save`

* Saves to the current save file.

`>> save as <FILENAME>.fg`

* Saves using the FILENAME entered.


Saves the current state of the plant to a file. Doesn't interrupt the game (check the [autosave](#um-4) feature).


<a name="um-6"/>
## Error messages

* File not found or Cannot load file: when user is trying to a load a game file that does not exist.
* Component "componentName" does not exist: If the component name entered by the user in a command is incorrect.
* Invalid Command: If the command entered by the user is entered in a wrong format.
* Saving failed: the game is not able to save the current state.

&copy; 2013 Team Anchovy
