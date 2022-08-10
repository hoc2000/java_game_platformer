# Java Game - 2D Platformer Game
# Table of contents
- [Description](#description)
- [Folder Structure](#Folder-Structure)
- [Getting Started](#Getting-started)
    - [Our Team](#our-team)
    - [Dependencies](#dependencies)
    - [Installation](#installation)
    - [Contributing](#contributing)
- [About this project](#about-project)
    - [Game Thread](game-thread)
    - [Character Animations](#character-animation)
    - [Collision Detection](#collision)

## Description <a name="description"></a>
A simple action game which can move character (run, jump, attack) between point in the environment 
![menu](./res//menuGame.png)
![view](./res/view.png)

## Folder Structure <a name="Folder-Structure"></a>
.
├── .idea                   
├── res                     
│   ├── entities            
│   ├── inputs              
│   ├── level              
│   ├── game states             
│   ├── main             
│   ├── ui 
│   └──  utilz  
└── README.md

## Getting Started <a name="Getting-started"></a>
### 1. Our Team <a name="our-team"></a>
|No  | Name |
|-----|-------|
|1     | Vu Tu Hoc|
|2     | Le Minh Hung|
|3     | Le Pham Thuy Tien|
### 2. Dependencies <a name="dependencies"></a>
- Programming language: Java
- JDK: 17
### 3. Installation <a name="installation"></a>
```
# Clone this repository
$ git clone https://github.com/hoc2000/java_game_platformer.git
```

### 4. Contributing <a name="contributing"></a>
1. Fork it
2. Create your feature branch (`git checkout - b develop_branch`)
3. Commit your changes (`git commit -m "New_commit"`)
4. Push to the branch (`git push origin develop_branch`)
5. Create a new Pull Request

## About this project <a name="about-project"></a>
### a. Game Thread <a name="game-thread"></a>
- ```Game loop```: A game loop runs continuously during gameplay. Each turn of the loop, it processes user input without blocking, updates the game state, and renders the game. 
- ```FPS``` (render/frames):
    - Draw the games scene, player, enemies,..
- ```UPS``` (update/tick):
    - Takes care of logic: player move, mouse/keyboard events
- Methods:
    - ```update```: update position,...
    - ``` repaint```: call method paintComponent()
- Time system: We used nano time ```System.nanoTime()``` (returns the current value of the running JVM's high-resolution time source in nanoseconds) to calculate the time restriction. We used "Delta/Accumulator" method
(Read more in [Understanding Delta Time](https://drewcampbell92.medium.com/understanding-delta-time-b53bf4781a03))
### b. Character Animations <a name="character-animation"></a>
### c. Collision Detection <a name="collision"></a>