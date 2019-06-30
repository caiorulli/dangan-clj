# dangan-clj

> "Y'know, that thing where everyone stands in a circle and talks about the dead body..."
> - Monokuma

Dangan-clj is a (work-in-progress/halted) game framework for writing investigative visual novels using Clojure.

It is freely based on the Danganronpa series (and Phoenix Wright: Ace Attorney series as well) narrative structure, in which there are different stages of investigation and trial. Also a very good excuse to learn better Clojure.

It's meant to be an text-based CLI game, but that is subject to change. I have been developing an example game along with it, so I can clearly separate game from library in afterwards.

## State of development

Halted. I figured that, as though only vanilla terminal input might work fine for the investigation stage, I would require a more powerful output mechanism for the trial sequences (such as presenting maps and diagrams).

That said, I could extract the underlying domain logic to a different library and start the UI over in another project, but more and more I realize there's not really much domain logic involved in either the investigation and trial sections, while there is, in the other hand, a lot of application/UI logic. The extracted library might end up containing five functions or so.

So this is on hold until a better UI option presents itself.

## What does the framework support right now?

- Scenes
- Points of Interests (PoIs)
- Characters
- Dialogs
- Clues

To better understand how to create a game with it, check out the `src/dangan-clj/input/example.clj` game. You'll be able to create those entities and setup an investigation sequence. I haven't got to the trial part, though, so there'll be no closure to the story.

## To run the example game so far...

Just go with `lein run`.
If you want to run the tests, it's `lein midje`.

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
