import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

class MagicGridSolverTest {

    private static Stream<Arguments> parametersForFindGrids() {
        return Stream.of(
            Arguments.of(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, // input
                3, // number of rows or cols
                15, // target sum
                5 //grids to find
            ),
            Arguments.of(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9}, // input
                4, // number of rows or cols
                20, // target sum
                5 //grids to find
            )
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForFindGrids")
    void testFindGrids(
        int[] input,
        int gridRows,
        int targetSum,
        int gridsToFind
    ) {
        MagicGridSolver solver = new MagicGridSolver(
            input,
            gridRows,
            gridsToFind,
            targetSum
        );
        List<String> results = solver.findGrids();
        for (String result : results) {
            int[][] grid = convertToGrid(result, gridRows);
            assertThat(isGridValid(grid, gridRows, targetSum)).isTrue();
        }
    }

    private static boolean isGridValid(int[][] grid, int n, int targetSum) {
        return areRowsAndColsSumsValid(grid, n, targetSum) &&
            isBackSlashDiagonalSumValid(grid, n, targetSum) &&
            isSlashDiagonalSumValid(grid, n, targetSum);
    }

    private static boolean areRowsAndColsSumsValid(int[][] grid, int n, int targetSum) {
        int[] rowSums = new int[n];
        int[] colSums = new int[n];
        for (int rowIdx = 0; rowIdx < n; ++rowIdx) {
            for (int colIdx = 0; colIdx < n; ++colIdx) {
                int val = grid[rowIdx][colIdx];
                rowSums[rowIdx] += val;
                colSums[colIdx] += val;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (rowSums[i] != targetSum || colSums[i] != targetSum) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBackSlashDiagonalSumValid(int[][] grid, int n, int targetSum) {
        int sum = 0;
        for (int i = 0; i < n; ++i) {
            sum += grid[i][i];
        }
        return sum == targetSum;
    }

    private static boolean isSlashDiagonalSumValid(int[][] grid, int n, int targetSum) {
        int rowIdx = n - 1;
        int colIdx = 0;
        int sum = 0;
        for (int i = 0; i < n; ++i) {
            sum += grid[rowIdx--][colIdx++];
        }
        return sum == targetSum;
    }

    private static int[][] convertToGrid(String data, int n) {
        String[] rows = data.split("\n");
        int[][] grid = new int[n][n];
        for (int rowIdx = 0; rowIdx < n; ++rowIdx) {
            List<Integer> values = extractNumbers(rows[rowIdx]);
            for (int colIdx = 0; colIdx < n; ++colIdx) {
                grid[rowIdx][colIdx] = values.get(colIdx);
            }
        }
        return grid;
    }

    private static List<Integer> extractNumbers(String row) {
        return stream(row.split("\\s+"))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .collect(toList());
    }
}