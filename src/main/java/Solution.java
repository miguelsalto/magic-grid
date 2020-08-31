import java.util.List;

public class Solution {

    private static final int[] INPUT = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final int GRID_ROWS = 4;
    private static final int GRIDS_TO_FIND = 3;
    private static final int TARGET_SUM = 20;

    public static void main(String[] args) {
        System.out.println("Starting...");

        MagicGridSolver solver = new MagicGridSolver(
            INPUT,
            GRID_ROWS,
            GRIDS_TO_FIND,
            TARGET_SUM
        );
        List<String> grids = solver.findGrids();
        printGrids(grids);

        System.out.println("Done!!");
    }

    private static void printGrids(List<String> grids) {
        for (int gridIdx = 0; gridIdx < grids.size(); ++gridIdx) {
            System.out.printf("Grid %d:\n", gridIdx + 1);
            System.out.println(grids.get(gridIdx));
        }
    }
}