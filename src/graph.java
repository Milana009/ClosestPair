import java.util.ArrayList;
import java.util.Arrays;

public class graph {
    public static final int N = 6;

    public static void main(String[] args) {

        graph t = new graph();

        double pointСoordinates[][] = new double[N][2];

        double arrayOfMissingPaths[][] = new double[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    arrayOfMissingPaths[i][j] = -1;
                } else {
                    arrayOfMissingPaths[i][j] = 0;
                }
            }
        }

        double array[][] = {{-1, 40, 50, 35, 30, 20},
                {40, -1, 40, 45, 45, 30},
                {50, 40, -1, 30, 35, 25},
                {35, 45, 30, -1, 20, 40},
                {30, 45, 35, 20, -1, 25},
                {20, 30, 25, 40, 25, -1}};

        int arrayPath[] = new int[N + 1];

        double[][] arrayCopy = new double[N][N];

        for (int i = 0; i < arrayCopy.length; i++) {
            for (int j = 0; j < arrayCopy.length; j++) {
                arrayCopy[i][j] = array[i][j];
            }
        }

        double[][] arrayScoreOfZeros = new double[N][N];

        double minLine[] = new double[N];
        Arrays.fill(minLine, Integer.MAX_VALUE);

        double minСolumn[] = new double[N];
        Arrays.fill(minСolumn, Integer.MAX_VALUE);

        for (int l = 0; l < N - 1; l++) {

            System.out.println("ШАГ " + l);

            minLine = minLine(arrayCopy);

            System.out.println("Вычитание строки. ШАГ" + l);

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if (arrayCopy[i][j] >= 0) {
                        arrayCopy[i][j] -= minLine[i];
                    }
                    System.out.print(arrayCopy[i][j] + "  ");
                }
                System.out.println();
            }


            minСolumn = minColumn(arrayCopy);

            System.out.println("Вычитание столбца. ШАГ" + l);

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if (arrayCopy[j][i] >= 0) {
                        arrayCopy[j][i] -= minСolumn[i];
                    }
                }
            }

            System.out.println();

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    System.out.print(arrayCopy[i][j] + "  ");
                }
                System.out.println();
            }

            System.out.println();

            if (l == (N - 2)) {
                for (int k = 0; k < arrayPath.length; k++) {
                    for (int i = 0; i < arrayCopy.length; i++) {
                        for (int j = 0; j < arrayCopy.length; j++) {
                            if ((arrayCopy[i][j] != -1) && (arrayPath[k] != j)) {
                                arrayPath[N - 1] = j;
                            }

                        }
                    }
                }

            } else {

                System.out.println("Вычисление оценки нулевых клеток. ШАГ" + l);

                for (int i = 0; i < arrayCopy.length; i++) {
                    for (int j = 0; j < arrayCopy.length; j++) {
                        if (arrayCopy[i][j] == 0) {
                            arrayScoreOfZeros[i][j] = minLineWithZero(arrayCopy, i, j)
                                    + minColumnWithZero(arrayCopy, i, j);
                        } else {
                            arrayScoreOfZeros[i][j] = 0;
                        }
                    }
                }

                for (int i = 0; i < arrayCopy.length; i++) {
                    for (int j = 0; j < arrayCopy.length; j++) {
                        System.out.print(arrayScoreOfZeros[i][j] + "  ");
                    }
                    System.out.println();
                }

                System.out.println();

                double max = Integer.MIN_VALUE;

                for (int i = 0; i < arrayScoreOfZeros.length; i++) {
                    for (int j = 0; j < arrayPath.length; j++) {
                        if ((arrayCopy[arrayPath[l]][i] >= 0) && (arrayScoreOfZeros[arrayPath[l]][i] > max) &&
                                (i != arrayPath[i])) {
                            max = arrayScoreOfZeros[arrayPath[l]][i];
                            arrayPath[l + 1] = i;
                        }
                    }
                }


                System.out.println();

                System.out.println("Путь. ШАГ" + l);

                for (int i = 0; i < arrayPath.length; i++) {
                    System.out.print(arrayPath[i] + "  ");
                }

                System.out.println("\n");

                System.out.println("Подготовка ко второму шагу\n ШАГ" + l);

                for (int i = 0; i < arrayOfMissingPaths.length; i++) {
                    for (int j = 0; j < arrayOfMissingPaths.length; j++) {
                        if ((i == arrayPath[l]) || (j == arrayPath[l + 1]) || ((i == arrayPath[l + 1]) && (j == arrayPath[l]))
                                || (i == j) || (arrayOfMissingPaths[i][j] == -1)) {
                            arrayOfMissingPaths[i][j] = -1;
                        } else {
                            arrayOfMissingPaths[i][j] = 0;
                        }
                        System.out.print(arrayOfMissingPaths[i][j] + "  ");
                    }
                    System.out.println();
                }

                System.out.println();

                for (int i = 0; i < arrayOfMissingPaths.length; i++) {
                    for (int j = 0; j < arrayOfMissingPaths.length; j++) {
                        if (arrayOfMissingPaths[i][j] == -1) {
                            arrayCopy[i][j] = -1;
                        } else {
                            arrayCopy[i][j] = array[i][j];
                        }
                        System.out.print(arrayCopy[i][j] + "  ");
                    }
                    System.out.println();
                }

                System.out.println();

            }
        }

        arrayPath[arrayPath.length - 1] = arrayPath[0];

        printArrayPath(arrayPath);

        System.out.println(printLengthPath(arrayPath, array));


        System.out.println("\nМИНИМАЛЬНОЕ ОСТОВНОЕ ДЕРЕВО\n");

        t.primMST(array);
    }

    public static double[] minLine(double[][] array) {
        double[] minLine = new double[N];
        Arrays.fill(minLine, Integer.MAX_VALUE);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if ((array[i][j] < minLine[i]) && (array[i][j] >= 0)) {
                    minLine[i] = array[i][j];
                }
            }
        }
        return minLine;
    }

    public static double[] minColumn(double[][] array) {
        double[] minColumn = new double[N];
        Arrays.fill(minColumn, Integer.MAX_VALUE);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if ((array[j][i] < minColumn[i]) && (array[j][i] >= 0)) {
                    minColumn[i] = array[j][i];
                }
            }
        }
        return minColumn;
    }

    public static double minLineWithZero(double[][] array, int m, int n) {
        double minLine = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if ((array[m][i] < minLine) && (i != n) && (i != m) && (array[m][i] >= 0)) {
                minLine = array[m][i];
            }
        }
        return minLine;
    }


    public static double minColumnWithZero(double[][] array, int m, int n) {
        double minColumn = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if ((array[i][n] < minColumn) && (i != n) && (i != m) && (array[i][n] >= 0)) {
                minColumn = array[i][n];
            }
        }
        return minColumn;
    }

    public static void printArrayPath(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println();
    }

    public static double printLengthPath(int[] arrayPath, double[][] array) {
        double lengthPath = 0;
        for (int k = 0; k < arrayPath.length - 1; k++) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if ((arrayPath[k] == i) && (arrayPath[k + 1] == j)) {
                        lengthPath += array[i][j];
                    }
                }
            }
        }
        return lengthPath;
    }

    int minKey(double key[], Boolean mstSet[]) {
        // Инициализировать минимальное значение
        double min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < N; v++) {
            if ((mstSet[v] == false) && (key[v] < min)) {
                min = key[v];
                min_index = v;
            }
        }
        return min_index;
    }

    ArrayList printMST(int parent[], double graph[][], ArrayList arrayPath, ArrayList okPath1, ArrayList okPath2) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < N; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
            arrayPath.add(parent[i]);
            arrayPath.add(i);
            okPath1.add(parent[i]);
            okPath2.add(i);
        }
        return arrayPath;
    }

    double printWeight(ArrayList arrayPath, double graph[][]) {
        double weight = 0;
        for (int i = 0; i < arrayPath.size() - 1; i++) {
            weight += graph[(int) arrayPath.get(i)][(int) arrayPath.get(i + 1)];
        }
        return weight;
    }

    void primMST(double graph[][]) {

        ArrayList okPath1 = new ArrayList();
        ArrayList okPath2 = new ArrayList();

        int parent[] = new int[N];

        double key[] = new double[N];

        Boolean mstSet[] = new Boolean[N];

        ArrayList arrayPath = new ArrayList();
        ArrayList copyArrayPath = new ArrayList();

        for (int i = 0; i < N; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < N - 1; i++) {

            int u = minKey(key, mstSet);

            mstSet[u] = true;

            for (int v = 0; v < N; v++) {

                if ((graph[u][v] != 0) && (mstSet[v] == false) && (graph[u][v] < key[v])) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        arrayPath = printMST(parent, graph, arrayPath, okPath1, okPath2);

        arrayPath.add(arrayPath.get(0));

        for (int i = 0; i < arrayPath.size() - 1; i++) {
            for (int j = 1; j < arrayPath.size() - 1; j++) {
                if ((arrayPath.get(i)) == arrayPath.get(j) && (i != j)) {
                    arrayPath.set(j, -1);
                }
            }
        }

        for (int i = 0; i < arrayPath.size() - 1; i++) {
            while ((int) arrayPath.get(i) == -1) {
                arrayPath.remove(arrayPath.get(i));
            }
        }

        System.out.println(arrayPath.toString());

        System.out.println(printWeight(arrayPath, graph));
    }
}
