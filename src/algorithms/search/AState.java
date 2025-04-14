package algorithms.search;

public abstract class AState {
    private AState cameFrom;

    public AState getCameFrom(){
        return this.cameFrom;
    }

    public void setCameFrom(AState cameFrom){
        this.cameFrom=cameFrom;
    }

    public abstract double getCost();
    public abstract void setCost(double cost);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}
