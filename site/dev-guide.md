# Developer guide

This guide is a good place to start for developers wishing to use and expand Fire Blanket (the obvious application would be for SEPR students doing Assessment 3). Our system has been designed from the ground up with a focus on modularity and an easy transition to a GUI. In addition to the guide here, full system documentation including diagrams, dependencies and class overviews are available via the website.

## Contents

1. [Getting Started](#dg-1)
2. [Changing the UI](#dg-2)
3. [Adding a Component](#dg-3)
4. [How to Create a Software Component](#dg-4)

<a name="#dg-1"/>
## Getting Started

### Getting the code

If you want to use Github to manage the project, getting the code is as simple as [forking our repo](https://github.com/cjd515/fire-blanket) (what's [forking](https://help.github.com/articles/fork-a-repo), you ask?). Either way, to download a local copy you can do this (on a Unix-based system):

```bash
  bash-4.2$ cd ~/git
  bash-4.2$ git clone https://github.com/cjd515/fire-blanket.git
  Cloning into 'fire-blanket'...
  remote: Counting objects: 1275, done.
  remote: Compressing objects: 100% (438/438), done.
  remote: Total 1275 (delta 918), reused 1173 (delta 816)
  Receiving objects: 100% (1275/1275), 394.39 KiB | 234 KiB/s, done.
  Resolving deltas: 100% (918/918), done.
  bash-4.2$ cd fire-blanket/
```

For Windows, consider installing something like [http://msysgit.github.com/](Git for Windows).

### Compiling from source

Having the `.classpath` and `.project` files means the project is already configured for Eclipse, where you can compile and run it. Eclipse's biggest advantages are being cross-platform, git support via [egit](http://www.eclipse.org/egit/), and powerful refactoring. There is also a `build.xml` file if you want to build using Ant.

### Code organisation

Fire Blanket follows the [MVC pattern](http://www.codinghorror.com/blog/2008/05/understanding-model-view-controller.html). The code is structured in 5 packages:
* `model`: the hardware components
* `view`: the user interface (the text UI also has a command parser)
* `controller`: the game engine, gets user commands, updates the user interface, has the main game loop
* `util`: utilities such as InfoPacket
* `tests`: unit testing

To learn more about the underlying mechanics of our nuclear plant, check out the <a href="https://github.com/cjd515/fire-blanket/blob/master/site/user-manual.md" target="_blank">User Manual</a>.

<a name="#dg-2"/>
## Adding a User Interface

Assessment 3 requires development teams to change the basic textual user interface to a GUI. All of the game’s parsing and operation passing is taken care of in the game engine - so the interface is plug and play.

To change the interface:

1. Remove the UI class MainWindow from the source code
2. Create a new Java Class with your prefered design
3. Pass the GameEngine through the main function of the class to start the game
4. The function should also take a command from the gamer thus performing the actions the gamer wants it to
5. The class should pass the entered commands to the parser
6. The display should updates with every command entered by the game

<a name="#dg-3"/>
## Adding a `Component`

To add a hardware component:

1. Create the desired component class in the model package that extends the `WaterComponent` class
2. Add the component information to the InfoPacke
3. Add any necessary labels used in the new component class to the Pair class in the package util.
4. Set the maximum input and output limits for the component, and perform the function.
5. Add the functions to calculate and read the values of the component(like getInfo, takeInfo, calculate).
6. Make sure that the new component is connected to the right components passing the correct info.To add a hardware component:

<a name="#dg-4"/>
## How to Create a Software Component

This section describes the specifics of creating a new Software Component to the Power Plant as described in Assessment 3. For information on how to create a hardware component or how to add a newly created component to the power plant please see the following two sections.

To add a Software Component: there are many ways to change the software component of the game.

1. The user can add new functions:
  New methods performing different functions(totally dependent on the programmer) can be added to the GameEngine class.
  Make sure to update the infopackets after performing the necessary action using the `updateInterfaceComponents` function.

2. The parser can be amended
  Commands can rephrased, changed or be given different meanings.
  Also any new functions added in the gameEngine need to called and added in the parser.

