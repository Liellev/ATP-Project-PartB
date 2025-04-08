package algorithms.mazeGenerators;

import java.util.Random;


public class SimpleMazeGenerator extends AMazeGenerator {

    private Random rand;

    public SimpleMazeGenerator(){
        this.rand=new Random();
    }
    @Override
    public Maze generate(int rows, int cols) {
        Maze simple=new Maze(rows,cols);
        for(int i=0; i< simple.getRows();i++){
            for(int j=0;j< simple.getCols();j++){
                simple.getMatrix()[i][j]=this.rand.nextBoolean() ? 1 : 0;
            }
        }
        return simple;
    }

    private int getRandomNumber(){
        Random random = new Random();
        return random.ints(0, 1).findFirst().getAsInt();
    }
}
