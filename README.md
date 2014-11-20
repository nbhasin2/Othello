**Kernelpanic**
===========

### Branch Switched to Develop 
This branch is obsolete since milestone 2 please use develop branch. Thanks.

## Milestone 2

#### How to run the game
There are two ways you can run the game. For this milestone the console based othello is disabled. But if necessary one can uncomment the code and play in console.

- Console Play - Tictactoe
1. Import the project.
2. Go to package play->Launcher.java that contains the main method. Use this class to launch the commandline. 
3. Once in command line you will get the following options 

```
Enter the game you want to play,
 1- Tic Tac Toe
 2- Othello
 3- quit
1
Enter the type of AI you want to play against,
 1- Random
 2- Minimax
 3- Cancel

 ```
 Now you can chose 1 for tictactoe 2 for Othello and then once game is chosen you can select the strategy. 1 for random 2 for minimax. Othello will fire up a gui where you can play the game in GUI mode but we don't have gui for tictactoe as according to the guidelines we needed to make gui for only othello.
 
 - GUI play without the hassle.
 There is an executable java launcher for Othello Game. Its called Othello_executable.jar. Double click it and select the strategy you want to play the game in and same gui will pop up that will allow you to play othello.
 
#### How to run tests with JUnit 

1. go to Run->Run Configurations

2. Then find JUnit in the column on the left 

3. In JUnit there should be a unit called AllTests. Click  on it.

4. Now in the test tab on the right, the field Test Class should read: testing.AllTests click apply then Run

#### DESIGN DECISIONS 
Console based Othello was designed using five different classes. The basis of the game is created using the gameBoard class. Each gameboard object will be a 2D array made of up boardSpace objects. Each boardSpace object can contain one playableItem (black or white game token). 

Othello uses two enum classes, gameStatus and playableItem. gameStatus is used to determine whether the game should end or not. playableItem is used to determine where on the board each player has placed a token. 

The main incentive behind designing Othello using multiple classes was maintainability. 
By not grouping everything together into one Othello class, it is easier to build the game in steps. Every object can be tested individually, then brought together inside the OthelloConsole class. 

Having these multiple classes will also make it easier in the future to build other games in the same style as Othello. For example, connect four would be easy to implement using the same class files as othello. The only thing that will need to be changed is the logic to determine a win.

The goal of having multiple classes was to break down the work each one did. This will lead to low cohesion and code that is easier to maintain. As the milestones progress we will look into refactoring our code, to create an even more robust framework that any board game can be created from. 

Furthurmore once we refactor and create a stable framwork, the games will be easier
to port to the Android platform, or any other systems. 

Ultimatley the main idea behind the design style of Othello was to make a loosely coupled
code base that has code which can be re-used in similar games. 

The point of the AI was to make a player that can play by only knowing two things:
 -What moves are legal
 -Which moves to pick
Another challenge that we had to keep in mind was to design an AI that could work with any games(Board type game).

So to solve these problems we first decided to design a GameConsoleInterface(GCI) that is an abstract class 
since the AI will does not know exactly which game it is playing it knows it?s playing a game. 
So all our other game console(TicTacToeConsole and OthelloConsole) have to extend GCI. 
And the AI will work based on what GCI can do.
The first method added to GCI was used to solve our first problem of knowing which move are legal.  
The method getAvailableSolutions() was added to the interface and this method return the available solutions to the AI so it knows which moves to pick from. 
Since every game is extending GCI they implement the method based on the rules of their own game.


The second problem is a bit more complicated, which moves to pick. 
For the AI to know which move to pick it needs to have a strategy, 
thankfully we didn?t have to think of which strategies we would implement 
because a random strategy and a minimax strategy was required by our client(SYSC 3110 project).
For the random strategy the design is simple, get a list of valid moves and pick one at random. 
For the minimax needs to test out all the moves and find which one is best; 
with that all we needed:
-to make a test move , 
-check wether or not it was a good move
-undo the previous move so that another test move can be tried 
But that is not sufficient enough for minimax, minimax needs to look ahead and anticipate a 
move by the human. To solve that problem we decided to make minimax recursive and have it go through 
until the game is over so the AI needs to know that game is over. 
We realised that waiting for the game to be over in Othello was way too long 
so we added a depth search limit in the minimax.

These were implemented in GCI to help minimax based on our design decisions: 
-isGameOver, return a Boolean true if the game is over,
-evaluate, returns a result that ?translate? the visual form of the game to a form the AI can understand (integer)
-moveSet,  will make a test move and see what the result is 
-undoMove, will undo the previous test move so the board is not crowded with test moves
with these function implemented for every game the minimax becomes possible, 
see the javaDoc for gameAI.AI#minimaxStrategy to see how it was implemented in details
Not much was changed for tictactoe class. We added more tests and added minimax ai. Thus there is not much design decison that we can discuss in read me.

Note - We don't really know the format / guideline of the design decison of how to put it so we wrote it all in one big paragraph. Please let us know if there is any special format that we need to follow and we will update it in milestone 3. 

### FUTURE SAVE XML

We are adding the basic design of our future XML save and reload strategy. This keeps track of column and row along with AIMOVE and Player and also the type of game we are playing. More information will be updated later as we progress through the assignment.

```XML
  <GAME>
      <GAMETYPE>Othello</GAMETYPE>
      <ID>1</ID>
      <PLAYERMOVE>1-1</PLAYERMOVE>
      <AIMOVE>1-1</AIMOVE>
      <GRID>
          <ROW>
              <cell>value1</cell>
          </ROW>
          <COL>
              <cell>value1</cell>
          </COL>
      </GRID>
  </GAME>
```

## Milestone 1

### How to play

1. Go to Play package 
2. Launch the game from main method in Launcher.java.

```
Enter the game you want to play,
 1- Tic Tac Toe
 2- Othello
 3- quit
1
Welcome to TicTacToc Console Game !!
To start playing please enter your cell cordinate between [1-3]
like 1 1, 2 2. Make sure there is space between the numbers.Enjoy your game.

What player would you like to be X or O :
o
Game Started ... 

   |   |   
-----------
   |   |   
-----------
   |   |   
   
```

3. To play othello simply enter 2 instead of 1.

```
Enter the game you want to play,
 1- Tic Tac Toe
 2- Othello
 3- quit
2
   |   |   |   |
----------------
   | O | * |   |
----------------
   | * | O |   |
----------------
   |   |   |   |
----------------
Enter your move player Black

```


## Known Issues

- These are the bugs in Othello:
- Sometimes the tokenChange method will try to look out of the array bounds 
- The availableSolutions method does not know the exact coordinates of the token it needs to change, 
(See validRow = row - globalCounter) right now the counter is set as a default value it will need to be changed for the next itteration
- Again in the tokenChange method, when it only changes one row of tokens rather than needing to change all the rows that are flanked
an example fot this bug is given as an attachment in a .txt file  

.txt File contents 
{
For example

```

   |   | * |   |
----------------
 * | * | * | O |
----------------
 O | O | O | O |
----------------
   | O | * | O |
----------------

```

 then this is what the board should be outputted 
 
```

|   | * |   |
----------------
 * | * | * | O |
----------------
 * | * | O | O |
----------------
 * | * | * | O |
----------------

```

How ever the game will output something like this

```

   |   | * |   |
----------------
 * | * | * | O |
----------------
 O | O | O | O |
----------------
 * | * | * | O |
 
 ```
 You see the difference is that it only changed one row 
 this happens because there are three possible solutions that share the same coordinates
 the game has to then go through and change the token in a different way,
 I have an idea on how to fix it but I don't have much time to work on it today. }
