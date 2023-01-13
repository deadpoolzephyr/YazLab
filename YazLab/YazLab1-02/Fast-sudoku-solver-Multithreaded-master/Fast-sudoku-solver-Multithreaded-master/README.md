# Fast-sudoku-solver-Multithreaded
In this project, it is aimed to  develop a multi-threaded brute force Sudoku solver. The experiments will consist of repeatedly 
solving this puzzle using the same algorithm with a varying number of threads to observe the variation in performance obtained.
(One thread to Five threads)
Eventually, the sole objective of multithreading is to reduce the time taken to solve a problem and that requires repeated 
experimentation by the multithreaded method of solving the problem.
In this project, I have made use of implementing the Runnable Interface as it has many advantages over extending the Thread Class.

#Algorithm:

The algorithm uses recursive calls on a test-and-backtrack approach. All the cells of the board will be visited.
1.	Upon arriving, if the cell is not empty, it is skipped. Otherwise, a temporary value is assigned from 1 to 4.
2.	Afterwards, a test is performed to see if the attempted value is allowed there. By scanning the 3 dimensions of neighbors, the new
value is compared to all the elements contained in its row, its column and its sub square. If the same element is encountered, the next
value is tested and the algorithm repeats the test. If all the values have been exhausted, the algorithm backtracks to the previous cell.
3.	This process is repeated until the program finds an element that could potentially exist at that location 
4.	Once this condition is satisfied, the function is recursively called to the next location. To optimize the software, when the function 
is called a second time, the first element it looks at is the previous element incremented by 1. For example here, there since we just
guessed ‘3 to be at location (0,1), the algorithm will guess ‘4’ the next location
5.	Once the depth of the recursion reaches 16, we know that all the cells have been visited, so if no solution was found, we backtrack
one cell and keep trying all the possibilities on that one by recalling the recursion.
6.	This keeps happening until all the possibilities have been tried out. If no solution was bound in the mean time, a message is printed
on the board (for the purpose of this experiment, we know that there is a unique solution so it is always found sooner or later)
