Kernelpanic
===========

Milestone 1 
-----------

1. Known Issues

1.1 These are the bugs in Othello:
- Sometimes the tokenChange method will try to look out of the array bounds 
- The availableSolutions method does not know the exact coordinates of the token it needs to change, 
(See validRow = row - globalCounter) right now the counter is set as a default value it will need to be changed for the next itteration
- Again in the tokenChange method, when it only changes one row of tokens rather than needing to change all the rows that are flanked
an example fot this bug is given as an attachment in a .txt file  
