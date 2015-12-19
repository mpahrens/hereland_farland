# hereland_farland
An asymmetric multiplayer game

## Purpose
LibGDX Game Jam
http://itch.io/jam/libgdxjam

Theme: Life in Space

## Goals
I'll probably replace this with the elevator pitch of the game at some point. For now, the only goal is a very 'zoomed in' interaction mode with the game (single character, personal avatar) and a very 'zoomed out' interaction mode with the game (environmental, systemic) are available on the same persistent world.

#### Vested Interest

Two popular pieces of advice are very good

- don't make a game engine, make a game
- don't make an mmo, make a small multiplayer game

I think this is great advice, so I am going to take it (by not taking it).

For Boston Elixir, I purposed the idea that much like the real-time chat app is usurping the todo list as the goto "web framework hello world", applying the same advantages to game design can let making a multiplayer server augment the _pong_ _galaga_, and _jumper_ hello worlds of game engines.

So, in order to make progress towards that, and to maybe have something nice to demo at the next Boston Elixir, lets make a small game that holds these ideas as principles, and then abstract out the good bits into the start of a eDSL/framework. (Namely Yyzzy that is sitting in another repo)


## Todo

- make basic entity libGDX application
  - get some stuff moving on the screen
- sketch out high level specification of game / design decisions
  - text input [y/n]
  - physics entities [y/n]
  - client side vs server side computation / responsibilities
- write front end code for a few features from spec (java libGDX)
- write back end code for a few features from spec (elixir modules)
- write some scripting shortcuts for these features (exs / EEx)
