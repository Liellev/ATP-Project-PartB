package algorithms.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepthFirstSearchTest {

    @Test
    void getName() throws Exception{
        DepthFirstSearch dfs = new DepthFirstSearch();
        DepthFirstSearch nullDfs = null;
        assertEquals(dfs.getName(), "DepthFirstSearch");
        assertEquals(nullDfs.getName(), null);


    }

    @Test
    void solve() {
    }

    @Test
    void getNumberOfNodesEvaluated() {
    }
}