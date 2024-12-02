import java.util.Scanner;
import java.util.Stack;

public class Maze {
    public enum Direction {UP, RIGHT, DOWN, LEFT}
    private final int maxRows;
    private final int maxCols;

    private final int foodRow;
    private final int foodCol;

    //set to true if food is found or there is no solution
    private boolean isSolved;

    private final int [][] maze;
    private final int [][] visited;
    private final Stack<Mouse> stack;
    private Mouse workingMouse;

    
    private static class Mouse {
        int r, c;
        Direction dir;

        public Mouse(int r, int c) {
            this.r = r;
            this.c = c;
            this.dir = Direction.UP;
        }

        @Override public String toString() {
            return "( " + this.r + ", " + this.c + ", " + this.dir + " )";
        }
    }

    public Maze(int maxRows, int maxCols, int[][] maze, int foodRow, int foodCol) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
        this.maze = maze;
        this.foodRow = foodRow;
        this.foodCol = foodCol;
        this.isSolved = false;
        this.visited = new int [maxRows][maxCols];
        this.stack = new Stack<>();
        this.workingMouse = new Mouse(0, 0);
        initializeVisited();
    }


    //makes all cells in shadow maze false
    private void initializeVisited() {
        for(int r = 0; r < this.maxRows; r++) {
            for(int c = 0; c < this.maxCols; c++) {
                this.visited[r][c] = 0;
            }
        }
    }


    //checks to see if four conditions are all met
    private boolean isValid(int row, int col) {
        if(row >= this.maxRows || row < 0) {
            return false;
        } else if(col >= this.maxCols || col < 0) {
            return false;
        } else if (this.maze[row][col] == 0) {
            return false;
        } else return wasVisited(row, col);
    }
    //checks shadow maze to see if space already visited
    private boolean wasVisited(int row, int col) {
        return visited[row][col] == 0;
    }

    public void solve() {
        while(!this.isSolved) {
            if(this.workingMouse.r == this.foodRow && this.workingMouse.c == this.foodCol) {
                //if found workingMouse is on the cheese
                this.visited[workingMouse.r][workingMouse.c] = 1;
                this.isSolved = true;
            } else if(isValid(this.workingMouse.r - 1, this.workingMouse.c)) {
                //if space above the working mouse isValid
                this.stack.push(this.workingMouse);
                //put workingMouse on stack
                this.visited[this.workingMouse.r][this.workingMouse.c] = 1;
                //marks current tile as visited
                this.workingMouse = new Mouse(this.workingMouse.r - 1, this.workingMouse.c);
                //sets workingMouse as a new mouse on the space above
            } else if(isValid(this.workingMouse.r, this.workingMouse.c + 1)) {
                //if space to the right of workingMouse isValid
                this.stack.push(this.workingMouse);
                this.visited[this.workingMouse.r][this.workingMouse.c] = 1;
                workingMouse.dir = Direction.RIGHT;
                this.workingMouse = new Mouse(this.workingMouse.r, this.workingMouse.c + 1);
            } else if(isValid(this.workingMouse.r + 1, this.workingMouse.c)) {
                //if space below workingMouse is visited
                this.stack.push(this.workingMouse);
                this.visited[this.workingMouse.r][this.workingMouse.c] = 1;
                workingMouse.dir = Direction.DOWN;
                this.workingMouse = new Mouse(this.workingMouse.r + 1, this.workingMouse.c);
            } else if(isValid(this.workingMouse.r, this.workingMouse.c - 1)) {
                //if space to the left of workingMouse isValid
                this.stack.push(this.workingMouse);
                this.visited[this.workingMouse.r][this.workingMouse.c] = 1;
                workingMouse.dir = Direction.LEFT;
                this.workingMouse = new Mouse(this.workingMouse.r, this.workingMouse.c - 1);
            } else {
                this.visited[this.workingMouse.r][this.workingMouse.c] = -1;
                //sets current tile as a dead end
                workingMouse.dir = null;
                this.workingMouse = this.stack.pop();
                //pops current workingMouse off of stack and makes previous Mouse workingMouse
            }
        }


        //debug
        printMatrix(this.maze);
        //pause();
        printMatrix(this.visited);
        //debug
        System.out.println(stack);

    }

    //debug method to pause program to check on progress
    public static void pause() {
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }

    //utility method to print a matrix
    public void printMatrix(int [][] matrix) {
        for (int r = 0; r < matrix.length; r++) {
            System.out.println();
            for (int c = 0; c < matrix[r].length; c++) {
                System.out.printf("%3d", matrix[r][c]);
            }
        }
        System.out.println();
    }


}