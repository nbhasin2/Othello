**Kernelpanic**
===========

## Milestone 1

1. Known Issues

1.1 These are the bugs in Othello:
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
 with this board if I place a black token on spot 4-1 

 
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