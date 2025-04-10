package algorithms.search;

public class MazeState {

    private String state;
    private double cost;
    private MazeState camefrom;

    public MazeState(String state){
        this.state=state;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}
        if (obj==null || getClass() != obj.getClass()){return false;}
        MazeState state1= (MazeState) obj;
        return this.state!=null ? this.state.equals(state1.state) : state1.state==null;

    }

    @Override
    public int hashCode() {
        return this.state!= null ? this.state.hashCode() : 0;
    }
}
