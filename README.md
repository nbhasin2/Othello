**Kernelpanic**
===========
## Milestone 4

<img src="http://i.imgur.com/lx7qgwh.png" width="200px" height="200px"/>
Jar exexcutable screenshot
<img src="http://i.imgur.com/a/CvnQj.png" width ="150px" height="200px"/>
Android port screen shot
#### How to run the game

There are two ways you can run the game. For this milestone the we have made two different launchers. 1. Console based and 2. Gui based (othello). This way you can 
run both games and test it out.

Running othello game using GUI is very simple. You can use the Othello_M4.jar to play or run it from LauncherOthelloGUI.java in play package. It has a void main method that runs the gui version of the game. Make sure you run the the jar from the unzipped package to make sure the Saved folder is avaible for the Load and Save methods.

To play with the android app:
Once the code has been pulled the OthelloApp.apk file can be found inside the OthelloApp_Apk folder. This file can be moved into any folder on any phone running android. Once the file is on the phone, the easiest way to locate it is to download a file explorer app and navigate to the apk file. Install the apk file on the phone, and run the app.

Running othello or any other console based game is done using Launcher class in Play package. Go to Play->Launcher.java and run it as java application. Below is how you run
othello in console version where in place of X X you enter a valid number 3 4 etc. and if number is invalid you get an error saying that its invalid and enter again.
```

Enter the game you want to play,
 1- Tic Tac Toe
 2- Othello
 3- Connect Four
 4- quit

2
Enter the type of AI you want to play against,
 1- Random
 2- Minimax
 3- Idiot
 4- Picks the middle the solutions
 5- Cancel
4
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   | O | * |   |   |   |
----------------
   |   |   | * | O |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
Black player move: X X

```
There are now commands that the console can answer too. Each are case sensitive.
```

Commands(Action -- Letter to enter)
Redo -- 'R'
Undo -- 'U'
Save -- 'S'
Load -- 'L'

```
In the case
of an invalid entry the console offers help, at the command of 'H'.
To make and action the letter much mactch meaning that it must be capitialized. 

Each of the commands work like so.

Redo- If the action undo has been called before the game will cancel the undo, if they are no undos that have been done or that you are the most recent action, redo will not do anything. Once a move it will act as if undo has never been done.

Undo- Undo will cancel the last move made, if the board is back to the starting position it will not do anything.

Save- Save will save the current state of the board and the moves that can be undone and redone.

Load- Load will load the saved state of the game and the moves that can be undone and redone.

#### How to run tests with JUnit 

1. go to Run->Run Configurations

2. Then find JUnit in the column on the left 

3. In JUnit there should be a unit called AllTests. Click  on it.

4. Now in the test tab on the right, the field Test Class should read: testing.AllTests click apply then Run

#### DESIGN DECISIONS AND REFACTORING 
Some major refactoring was done to every game packages(othello,com.connect.four,tictactoe)
GameBoard and BoardSpace are now in shared package, this gets rid of a lot of smell in ConnectFour and othello since they used the same idea, 
TicTacTo was also changed to make use of the GameBoard and BoardSpace from the shared package.

3 classes(GameBoardOth,GameBoardC4,GameBoardTic) were added to make sure that each game have their own unique GameBoards, each of these class inherit from GameBoard and they call the contructor and specify the size of the board. 

Each game also had new functions added to them those functions are Redo,Undo,Save,Load

Undo is done by creating a "stack" of GameBoards. Everytime the amove is made in a game a the board is added to the stack before the move is done so that every previous move is saved in the stack; When the function is called the GameBoard at the top of the stack is then made to be the current board and it is removed from the stack; Also everytime Redo is called it will place the redone board on top of the stack to ensure that no move is lost. 

Redo is also used by creating a stack of GameBoards. Everytime undo is called the board that is undone is added to the stack. Everytime Redo is called the board on top is set to the current board and the board that is redone is placed in the undo stack. The stack is reset eveytime a move is made because or else it would create a tree of moves to redo and would cause complications .

Save will save the current GameBoard as a serializable and it will aslo save the undoboard stack and redoboard stack to ensure that they are not lost when we load them back

Load will load the last GameBoard state that was saved. It will read the serialized files in the dedicated folder in save, to ensure that games don't load from othe games when a file is saved it is given a code to specify which game was saved, this code is also used to make sure the right file is loaded.

We also went trhough and got rid of all magic numbers and magic strings in our code to make it more understandable.


Design Desicions for android port

When porting othello to android we ran into various small problems dealing with the execution of the code on the android platform. The flexibility of our initial framework design made the intial setup of the android build quite easy. The core game mechanics were ported easily but problems occured when trying to run the game on a phones processor rather than a PC's. Our game contained many do while loops to keep the game running and waiting for user input. When the phone ran the code, it timed out and no game interface was displayed. We were trying to do too much in one block of code, and the phone was struggling to power through all the instructions given. To ease the strain on the processor, we created threads to run different parts of the game which let the phone run the game without timing out. The refactoring we had done made it easy to determine what parts of the code we could separate and run in a thread to speed up the processing. 

Some new tests cases were added to test the new funcitons (undo,redo,save and load);

They are no other major changes from the previous versions for more information look at the desicions in previous milestone of this application.

## Milestone 3

#### How to run the game
There are two ways you can run the game. For this milestone the we have made two different launchers. 1. Console based and 2. Gui based (othello). This way you can 
run both games and test it out.

Running othello game using GUI is very simple. You can use the Othello_executablejar_m3.jar to play or run it from LauncherOthelloGUI.java in play package. It has a void main method that runs the gui version of the game. 

Running othello or any other console based game is done using Launcher class in Play package. Go to Play->Launcher.java and run it as java application. Below is how you run
othello in console version where in place of X X you enter a valid number 3 4 etc. and if number is invalid you get an error saying that its invalid and enter again.

```
Enter the game you want to play,
 1- Tic Tac Toe
 2- Othello
 3- Connect Four
 4- quit

2
Enter the type of AI you want to play against,
 1- Random
 2- Minimax
 3- Idiot
 4- Picks the middle the solutions
 5- Cancel
4
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   | O | * |   |   |   |
----------------
   |   |   | * | O |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
   |   |   |   |   |   |   |   |
----------------
Black player move: X X

```
#### How to run tests with JUnit 

1. go to Run->Run Configurations

2. Then find JUnit in the column on the left 

3. In JUnit there should be a unit called AllTests. Click  on it.

4. Now in the test tab on the right, the field Test Class should read: testing.AllTests click apply then Run

#### DESIGN DECISIONS AND REFACTORING (Per Package/class)

--com.connect.four : 
-ConnectFourConsole (Matt and Zach)

I decided to extract isGameOver() to a superclass called GameConsole so because it was the exact same code as the isGameOver() method of TicTacToe, also exracted setUpDirectionArray because it was the same as the one for OthelloConsole
 Also now makes use of a new function called consoleParser from it's super class to get info from the user
player move now uses the method moveset to see if moves are valid.

One refactoring design that I implemented was to move the PlayableItem.java and GameStatus.java files into the shared constants package. Doing so eliminated the need for Othello and tic tac toe to each have their own copies of these java files. Doing so makes it easier to implement any new games that use black and white tokens by simply importing the shared constants package. 

Connect four was created using the same tools as Othello. It was designed to be implemented very easily and to handle any AI stratgeies that will be created in the future. Every board is created with board spaces that will hold a playable item, either a black, white or empty piece. Connect four was able to be refactored quite easily once it reached a working state. Our goal of using the separate java classes
to build these games proved useful when we looked at refactoring options for our code base. 

--gameai (Zach and Matt)
-AI 
now implements the strategy pattern for its strategy
added a interface called AI Strategy and a class for every strategy we have
two new strategies were implemented:
-AIIdiot which is a reverse minimax
-AIPickMiddle move which pick the move in the middle of the list of avaible moves

--gamemodel (Zach)
-GameConsoleInterFace is now an appropriate Interface

Added an abstract class called GameConsole to handle some methods where code was being repeated in mutluple gameConsoles
-GameConsole aslo has a funciton that parses int from the console, since it's a method that every game makse use of it is appropriate to place it there

--gameui (Nishnat)
-Controller.java
-ButtonWithCoordinates.java
-Gameui.java
With the feedback received I decided to add a controller class that implements observabe interface and has access to both the Model (othelloconsole) and View (Gameui). Othelloconsole extends gameconsole abstract class that extends observable. This way when we need to update anything from othelloconsole we notify the observers and send the the object with appropriate information. For example now Gameui is not a part of othelloconsole but still gets updated using observer pattern. A lot of refactoring was done in Gameui and a new class called Controller.java was created to lower the load on Gameui and remove responsibility from Gameui. For example now if the Gameui requires available solutions it talks with Controller class and gets it from there and not directly from othelloConsole (model). Also if othelloconsole needs to update the grid it sends notification to the controller that takes care of updating the grid. This way Model doesn't know about the view and vice versa but they both talk to each other using observers. ButtonWithCoordinate class didn't need any refactoring and hence nothing much was done.

--othello:

-OthelloConsole got rid of all the if else if else statements in the following methods: isValid,availableSolution, and tokenChangeWithDirection
for new way to minimize lines and confusion, the new was makes uses of an array of coordinates and depending on the index(which direction it is looking at) it will have a different coordinate parsed the array is implemented in GameConsole and passes it down to OthelloConsole now makes use of consoleParser player move now uses the method moveset to see if moves are valid.
Not much was there to refactor in BoardSpace and GameBoard. Gameboard however extends observable and sends notifications to update the grid in gameui but it doesn't know about gameui. 

--play: (Nishant Matt and Zach)
-Launcher now as more choice of AI and more choice of games,
also the AI being passed to games is no longer a string but an AIStrategy class
-LauncherOthelloGui is created specifically for OthelloGUI. This way you have the Launcher that deals only with Console and LauncherOthelloGui that takes care of GUI part.

--tictactoe (Nishant(initially made the game) and Zach (refactored, added AI etc.))
-Tictactoe console was refactored to make use of the methods evauluate and isGameOver to determine the winner by consequence hasWon() and isDraw() were removed because they are no longer needed so they were removed
Titactoe evaluateLine method got rid of all the if else if statements and replaced it with a pre determined array similar to how ConnectFourConsole's evaulateToken look like
player move now uses the method moveset to see if moves are valid.
now makes use of consoleParser

--Testing (Adhi)
As for JUnit tests, the following information was taken into account: the pickmiddleMove AI, random AI and minimax AI was tested thoroughly and all the public methods were checked. The Idiot AI was not checked because it was mentioned that it did not count as a third AI for the project. Therefore, it is implemented as an extra AI in our game. The tests conducted in all three games were similar. For testing of the AI, the testMakeMove() method was tested. Solutions were set and the testMakeMove was tested to see if the AI identified it as a valid move or not. The only exception in the minimax AI was the minimax AI utilized a third column which is set to 1 as during play, the AI is assigned the player 1 and human is assigned the player 0. As for the PickMiddleMove test, the size of the valid moves is cut in half, therefore we only expect the middle solution to be a valid move for this AI. Furthmore, testSetAIType is a self explanatory test that just looks into weather the AI strategy can be selected or not. It is a basic test and quite straightforward. Again please note that the toString was not tested as it is a basic function that just returns the name of the AI that is in play at that moment. In the testSetAIType, we use the implementation of the toSTring method when we use assert equals to see if we received the correct name of the AI or not. Now for the game class tests, all the game classes had the same methods, therefore all 3 of the game test classes utilized the same testing concept. The games are set up first, othello and connect4 are set up by simply instantiating a game and using the minimax strategy for AI. The tic tac toe game is initialized first by inputting a X and then instantiating the game with a Minimax AI. The games are then tested to see if the game can correctly process that an avaialble move is possible to be made. Test evaluates just evaluates the score for each player. TestMoveSet() tests moves that are out of range, should be valid and ones that should not be valid as a move like that has been previously made. TestUndoMoves tests out of range row and columns, places that have already been set, ones that have not been set yet and ones that have already been removed. Lastly, testIsGameOver is tested to see if a game is over once the player wins, once then AI wins or once there is a draw.


*Note - Again we don't really know the exact format so as how to tell what was added / removed during refactoring so we did our best to put it in paragraph format perpackage and class. We will add more and take into consideration any suggestions made to improve this. Also we took into consideration all the issues opened and tried to resolve them. If any issue you think is missed then please let us know and we will fix it in next milestone.

Also make sure you have java 8 else the executable won't run.
===========
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
 There is an executable java launcher for Othello Game. Its called Othello_executable.jar. Double click it and select the strategy you want to play the game in and same gui will pop up that will allow you to play othello. You must have jre1.8 lastest isnstalled on your computer to run that jar otherwise it will give you errors. 
 
#### How to run tests with JUnit 

1. go to Run->Run Configurations

2. Then find JUnit in the column on the left 

3. In JUnit there should be a unit called AllTests. Click  on it.

4. Now in the test tab on the right, the field Test Class should read: testing.AllTests click apply then Run

##### Lines to comment in OthelloConsole.java (Line no. 97) (Console play)

```java 

//					int item = 0;
//					System.out.print("Enter your move player Black\n"); 
//					do{
//						item++;
//						if(item >= Integer.MAX_VALUE-10000){
//							item = 0;
//							//System.out.print(gameuiMove +"\n"); 
//						}
//						if(gameuiMove){
//							
//							System.out.println("X - " + gameuiMoveX + " -- " + "Y - " + gameuiMoveY);
//							row = getGameuiMoveX();
//							col = getGameuiMoveY();
//						}
//						else{
//							System.out.println("Skipping --");
							row = playerMove.nextInt() -1;
							col = playerMove.nextInt() -1;
//						}
//					}while(!gameuiMove);
//					gameuiMove = false;

```

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