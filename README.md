**Kernelpanic**
===========

## Milestone 2

### FUTURE SAVE XML

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
```   |   | * |   |
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