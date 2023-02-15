package Avianca;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Avianca {
    static int countries;
    static int[][] distance;
    static boolean[] visitCountry;

    // create findHamiltonianCycle() method to get minimum weighted cycle
    static long findHamiltonianCycle(int currPos, int count, int cost, long hamiltonianCycle) {
        if (count == countries && distance[currPos][0] > 0) {
            hamiltonianCycle = Math.min(hamiltonianCycle, cost + distance[currPos][0]);
            return hamiltonianCycle;
        }

        // Skip infinite values
        // BACKTRACKING STEP
        for (int i = 0; i < countries; i++) {
            if (distance[currPos][i] == Integer.MAX_VALUE) {
                continue;
            }
            if (visitCountry[i] == false) {
                // Mark as visited
                visitCountry[i] = true;
                hamiltonianCycle = findHamiltonianCycle(i, count + 1, cost + distance[currPos][i], hamiltonianCycle);

                // Mark ith node as unvisited
                visitCountry[i] = false;
            }
        }
        return hamiltonianCycle;
    }

    private static void loadData(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] numbers = line.split(",");
                int i = Integer.parseInt(numbers[0])-1;
                int j = Integer.parseInt(numbers[1])-1;
                int c = Integer.parseInt(numbers[2].trim());
                distance[i][j] = c;
                distance[j][i] = c;
            }
            for (int i = 0; i < countries; i++) {
                for (int j = 0; j < countries; j++) {
                    if (i != j && distance[i][j] == 0) {
                        distance[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            for (int i = 0; i < countries; i++) {
                for (int j = 0; j < countries; j++) {
                    System.out.print(distance[i][j] + " ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    static int calculateNewCost(int currPos, int i) {
        int newCost = (int) (distance[currPos][i] * 1.1);
        return newCost;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int i = 0;
        int count = 1;
        int cost = 0;


        System.out.println("Ingrese el numero de paises a recorrer: ");
        countries = sc.nextInt();

        distance = new int[countries][countries];
        visitCountry = new boolean[countries];

        loadData("TRAVBloque__FINAL.txt");
        

        long hamiltonianCycle = Integer.MAX_VALUE;

        System.out.println("Ingrese el pais de referencia:  ");
        int startingVertex = sc.nextInt();
        int currPos = startingVertex;
        

        visitCountry[startingVertex] = true;
        hamiltonianCycle = findHamiltonianCycle(currPos, count + 1, cost + calculateNewCost(currPos, i), hamiltonianCycle);

        visitCountry[startingVertex] = false;
        System.out.println("\nEl costo minimo de gasolina desde el pais " +startingVertex+" es: " + hamiltonianCycle);
        
        sc.close();
    }
}

