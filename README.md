# Sudoku-clone
A Sudoku clone made with java swing, as part of a semester assignment
in object oriented Programming ,ce325 University of Thessaly

####Usage: Start the file Sudoku.java

Assignment description

Create a application using javas swing environment that implements
a Sudoku clone. The application contains a menu bar , consisting of
the following entries:
-New Game containing the options Easy,Intermediate, Expert

Right after the horizontal menu , follow the 9x9 Sudoku squares,
(Sudoku grid).Below this grid there have to be:
- Buttons with numbers ranging from 1 to 9
- A button that erases the content of a square(field)
- An undo button for the users actions (undo Acton)
- A checkBox that compares the user input with the puzzles solution(the user can choose to use it or not)
- A button that solves the puzzle

To start a new game, the user chooses New Game from the horizontal
menu and then clicks on one of the options Easy,Intermediate,Expert,
depending on the difficulty of the Sudoku puzzle he wants to solve.
To get a new Sudoku game with starting numbers, you have to use one of
the following URLs, one for each difficulty.
- http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=easy
- http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=intermediate
- http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty=expert

From these URLs you can get the starting data, in the form of 9 lines with 9
digits each. Each line corresponds to a line of the Sudoku puzzle to be created.
The acccepted Digits are the numbers from 1-9 and the character '.'(dot). The
dot character corresponds to an empty field and the numbers correspond to fields
that will have these fixed values for the entire game length.

Fields when non-fixed values should have a white background and those with fixed
values should be gray. Fields with gray background have to keep that color for
the entire game length.

The game rules are as follows:
- The user chooses a field. The field changes its color to light yellow. Fields
with the same number also become yellow, whether their value is fixed or not.
- After choosing a field, the user can click any number button to write into it.
- If the user clicks on a field with a non-fixed value and then clicks the erase
button, the value of the field is erased. This should not happen when trying to
erase fixed values.
- If the user enters a value into a field that is against the Sudoku rules, the
field clicked and the other fields that are also part of this violation of rules,
should be marked with a red color. Sudoku rules state that any two same numbers
shall not be in the same row, column or box. The sudoku grid consists of 9 boxes.
- Choosing the undo button must result in undoing previous entries of the user
in the exact reverse order the user did them.
- If the user clicks on the checkox "Check against solution", then each field
that has the same number as the puzzle's solution must be marked with a blue
color background. The blue backgound of such fields must not change while the
checkbox remains active.
- If the user clicks on the solution button, the solution will be shown on the
screen and all other buttons must be deactivated.

Screenshots:

![empty](https://cloud.githubusercontent.com/assets/14819781/17597436/71e70146-5ffe-11e6-8c3c-9d14f264fbd8.png)

![empty1](https://cloud.githubusercontent.com/assets/14819781/17597450/7eba3c6c-5ffe-11e6-9792-1b1c70282be8.png)
