package algorithms.search;

public abstract class Astate {

    private String state;
    private double cost;
    private Astate camefrom;

    public Astate(String state){
        this.state=state;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}
        if (obj==null || getClass() != obj.getClass()){return false;}
        Astate state1= (Astate) obj;
        return this.state!=null ? this.state.equals(state1.state) : state1.state==null;

    }

    @Override
    public int hashCode() {
        return this.state!= null ? this.state.hashCode() : 0;
    }
}
