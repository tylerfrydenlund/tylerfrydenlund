# Oneblock Skyblock

This is a portfolio of my work on the Oneblock Skyblock project.
While this project was written for personal use with friends, it should still serve as a good example of my work.
This project is a custom Minecraft server plugin that allows players to play a unique version of the popular Skyblock
game mode.

The plugin is written in Java with Project [Lombok](https://projectlombok.org) and contains some Kotlin as well.
It uses the Spigot API to interact with the Minecraft server and runs on PaperMC.

If you would like to see how the game balanced, check here: [Oneblock Balancing (Google Sheets)](https://docs.google.com/spreadsheets/d/1nL6PoOnbkTRZqsOfK_wJQcEoRozZuZo9KAnirAAKVCE/edit?usp=sharing)

## How it works



## Some of my best work in the project

- [Util.java (GitHub)](https://github.com/tylerfrydenlund/oneblock/blob/master/src/main/java/io/hyleo/obsb/util/Util.java)
- [EntityUtil.java (GitHub)](https://github.com/tylerfrydenlund/oneblock/blob/master/src/main/java/io/hyleo/obsb/util/EntityUtil.java)
- [ScoreboardUtil.java (GitHub)](https://github.com/tylerfrydenlund/oneblock/blob/master/src/main/java/io/hyleo/obsb/display/ScoreboardUtil.java)
- [Display.java (GitHub)](https://github.com/tylerfrydenlund/oneblock/blob/master/src/main/java/io/hyleo/obsb/api/display/Display.java)
- [AnimationBufer.java (Github)](https://github.com/tylerfrydenlund/oneblock/blob/master/src/main/java/io/hyleo/obsb/api/display/AnimationBuffer.java)

## Description of the game

In this spin off of Oneblock Skyblock, players are placed on a single block in the sky in a team
called an "Empire". The goal is to expand the island and build a base while competing with others to be the best empire.

There are currently 12 "defined" phases in the game, each with their own unique blocks, entities, items, boss(es) and
rewards. 

After players mine the block, it will regenerate with a new block from the next phase. After they have mined the oneblock
so many times in a phase, they will be presented with a boss fight. After defeating the boss, they will be rewarded with
a chest of items and the next phase will begin.

After the 12 defined phases have been completed, players will enter the infinite phase where they can continue to play
indefinitely. The Infinite Phase is a combination of all defined phases in the game and includes additional content.

## Oneblock Skyblock video (YouTube)

[![Oneblock Skyblock video on YouTube](https://img.youtube.com/vi/PNc9k4mJzKo/1.jpg)](https://www.youtube.com/watch?v=PNc9k4mJzKo)

## Key Features

- Weighted randomness in all aspects of the game
- Custom display systems with advanced and colorful animations
- Custom block regeneration system
- Custom entity spawning system
- Bosses and boss fights
- Loot Tables for spawning chests
