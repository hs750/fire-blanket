# Developer guide

This guide is a good place to start for developers wishing to use and expand Fire Blanket (the obvious application would be for SEPR students doing Assessment 3). Our system has been designed from the ground up with a focus on modularity and an easy transition to a GUI. In addition to the guide here, full system documentation including diagrams, dependencies and class overviews are available via the website.

## Contents

1. [Getting started](#dg-1)
2. [Code oraganisation](#dg-2)
3. [Adding a "view"](#dg-3)
4. [Adding a Component](#dg-4)

<a href="#dg-1"/>
## Getting started

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

<a href="#dg=-2"/>
## Code organisation

Fire Blanket follows the MVC pattern. There are 5 packages used:
* `model`: the hardware components
* `view`: the user interface (the text UI also has a command parser)
* `controller`: the game engine, gets user commands, updates the user interface, has the main game loop
* `util`: utilities such as InfoPacket
* `tests`: unit testing

To learn more about how the physics of the nuclear plant work, check out the <a href="https://github.com/cjd515/fire-blanket/blob/master/site/user-manual.md" target="_blank">User Manual</a>.

<a href="#dg-3"/>
## Adding a "view"

<a href="#dg-4"/>
## Adding a `Component`
