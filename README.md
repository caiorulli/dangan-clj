# dangan-clj

[![Build Status](https://cloud.drone.io/api/badges/caiorulli/dangan-clj/status.svg)](https://cloud.drone.io/caiorulli/dangan-clj)

> "Y'know, that thing where everyone stands in a circle and talks about the dead body..."
> - Monokuma

Dangan-clj is a work-in-progress game (lib? framework?) for writing investigative visual novels using Clojure.

It is freely based on the Danganronpa series (and Phoenix Wright: Ace Attorney series as well) narrative structure, in which there are different stages of investigation and trial. Also a very good excuse to learn better Clojure.

It was originally meant to be a CLI game, but I changed my mind and decided to have a go with Clojurescript. :)

## CLI version

### To run the example game so far...

Just go with `lein run`.
If you want to run the tests, it's `lein midje`.

### What does the framework support right now?

- Scenes
- Points of Interests (PoIs)
- Characters
- Dialogs
- Clues

To better understand how to create a game with it, check out the `src/dangan-clj/input/example.clj` game. You'll be able to create those entities and setup an investigation sequence. I haven't got to the trial part, though, so there'll be no closure to the story.
Yes, it should definitely be EDN instead of a source file.

## Contributing

I am just about to try and make it a Clojurescript app. I am expecting a lot of interface work to make it run.
If you would like to help, reach me so I can give you the general idea and we might be able to break some tasks to it.

## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
