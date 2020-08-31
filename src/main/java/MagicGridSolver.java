import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MagicGridSolver {
    private final int gridRows;
    private final int gridsToFind;
    private final int numbersInGrid;
    private final int targetSum;

    private final int[] input;
    private final int[] rowSums;
    private final int[] colSums;
    private final List<int[]> grids;

    private int found;
    private int slashDiagonal;
    private int backSlashDiagonal;

    public MagicGridSolver(
        int[] input,
        int gridRows,
        int gridsToFind,
        int targetSum
    ) {
        this.input = input;
        this.gridRows = gridRows;
        this.gridsToFind = gridsToFind;
        this.targetSum = targetSum;
        this.numbersInGrid = gridRows * gridRows;
        this.rowSums = new int[gridRows];
        this.colSums = new int[gridRows];
        this.grids = new ArrayList<>();
    }

    public List<String> findGrids() {
        findGrids(0);
        return grids.stream()
            .map(g -> convertToGrid(g, gridRows))
            .collect(Collectors.toList());
    }

    private boolean findGrids(int inputIdx) {
        int rowIdx = inputIdx / gridRows;
        int colIdx = inputIdx % gridRows;
        if (inputIdx > 0 && colIdx == 0) {
            if (isRowSumDifferentToTargetOrToPreviousRowSum(
                rowIdx - 1,
                rowSums[rowIdx - 1])
            ) {
                return false;
            }
        }
        if (inputIdx == numbersInGrid) {
            return isGridValid();
        }
        for (int i = inputIdx; i < input.length; ++i) {
            swap(input, i, inputIdx);
            int val = input[inputIdx];
            boolean isBackSlashDiagonal = rowIdx == colIdx;
            boolean isSlashDiagonal = gridRows - rowIdx - 1 == colIdx;
            updateSums(
                val,
                rowIdx,
                colIdx,
                isSlashDiagonal,
                isBackSlashDiagonal
            );
            if (findGrids(inputIdx + 1)) {
                return true;
            }
            updateSums(
                -val,
                rowIdx,
                colIdx,
                isSlashDiagonal,
                isBackSlashDiagonal
            );
            swap(input, i, inputIdx);
        }
        return false;
    }

    private boolean isRowSumDifferentToTargetOrToPreviousRowSum(int prevRowIdx, int rowSum) {
        return rowSum != targetSum || (prevRowIdx > 0 && rowSum != rowSums[prevRowIdx - 1]);
    }

    private boolean isGridValid() {
        if (areDiagonalsSumsValid() && areColumnsSumsValid()) {
            if (found++ < gridsToFind) {
                grids.add(Arrays.copyOf(input, numbersInGrid));
                return false;
            }
            return true;
        }
        return false;
    }

    private void updateSums(
        int val,
        int rowIdx,
        int colIdx,
        boolean isSlashDiagonal,
        boolean isBackSlashDiagonal
    ) {
        rowSums[rowIdx] += val;
        colSums[colIdx] += val;
        if (isBackSlashDiagonal) {
            backSlashDiagonal += val;
        }
        if (isSlashDiagonal) {
            slashDiagonal += val;
        }
    }

    private boolean areColumnsSumsValid() {
        for (int i = 0; i < gridRows; i++) {
            if (colSums[i] != targetSum || (i != 0 && colSums[i] != colSums[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private boolean areDiagonalsSumsValid() {
        return slashDiagonal == targetSum && slashDiagonal == backSlashDiagonal;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    private static String convertToGrid(int[] grid, int gridRows) {
        StringBuilder sb = new StringBuilder();
        int arrIdx = 0;
        for (int rowIdx = 0; rowIdx < gridRows; ++rowIdx) {
            for (int colIdx = 0; colIdx < gridRows; ++colIdx) {
                sb.append(String.format("%5d", grid[arrIdx++]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}