# Developer guide

This guide is a good place to start for developers wishing to use and expand Fire Blanket.

## Contents

1. [Getting started](#dg-1)
2. [Code oraganisation](#dg-2)
3. [Adding a "view"](#dg-3)
4. [Adding a Component](#dg-4)

<a href="#dg-1"/>
## Getting started

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
