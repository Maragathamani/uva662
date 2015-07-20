import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Maragathamani on 3/1/14.
 *
 * Refernce: Dynamic Programming - Matrix Chain Algorithm (Cormen, Leiserson, Rivest, and Stein, Introduction to Algorithms, Third Edition, MIT Press.)
 */

class Main{

    public static Integer n = 1;    // Total number of Restaurants.
    public static Integer k = 1;    // Total number of depots.
    public static int[][] MatrixChain = new int[220][220];
    public static int[][] possibleTotalSum = new int[220][55];
    public static int[][] possibleDepotPosition = new int[220][55];
    public static int test = 0;

    public static void main(String[] args) {
        String num = null;
        BufferedReader cn = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (n != 0 && k != 0) {
                num = cn.readLine();
                String narr[] = num.trim().split(" ");
                n = Integer.parseInt(narr[0].trim());
                k = Integer.parseInt(narr[1].trim());
				
                if(n==0 && k==0){                                           // Condition to terminate
                    break;
                }
				
                int restaurants[] = new int[n + 1];
                int depotPosition[] = new int[n + 1];
                for (int i = 1; i <= n; i++) {
                    String s = cn.readLine();                                // Getting the distance of each restaurants
                    restaurants[i] = Integer.parseInt(s);
                }

                for (int j = 1; j <= n; j++)
                    MatrixChain[j][j] = 0;                                   // Initializing the diagonal of the matrix with zero (i.e) the minimum costs for chains of length 1
                
                for(int l=2; l<=n;l++){
                    for(int i=1;i<=n-l+1;i++){
                        int j =i+l-1;
                        MatrixChain[i][j] = MatrixChain[i][j - 1] + restaurants[j] - restaurants[(i + j) / 2];
                    }
                }
				
                for (int x = 0; x < 205; x++) {
                    for (int y = 0; y < 35; y++) {
                        possibleTotalSum[x][y] = 21474836;                   // Initializing some positive (like infinite) value to the matrix to avoid negative calculations.
                    }
                }
				
                for (int i = 1; i <= n; i++) {
                    possibleTotalSum[i][1] = MatrixChain[1][i];
                }
				
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= k && j <= i; j++) {
                        for (int l = 1; l < i; l++) {
                            if (possibleTotalSum[i][j] > possibleTotalSum[l][j - 1] + MatrixChain[l + 1][i]) {
                                possibleTotalSum[i][j] = possibleTotalSum[l][j - 1] + MatrixChain[l + 1][i];
                                possibleDepotPosition[i][j] = l;
                            }
                        }
                    }
                }
				
                int t = 0;
                if (n != 0 && k != 0)
                    System.out.println("Chain " + ++test);
                t = n;
                for (int i = k; i >= 1; i--) {
                    depotPosition[i] = t;
                    t = possibleDepotPosition[t][i];
                }
				
                depotPosition[0] = 0;
                for (int i = 1; i <= k; i++) {
                    if (depotPosition[i - 1] + 1 < depotPosition[i]) {
                        if (n != 0 && k != 0) {
                            System.out.println("Depot " + i + " at restaurant " + ((depotPosition[i] + depotPosition[i - 1] + 1) / 2) + " serves restaurants " + (depotPosition[i - 1] + 1) + " to " + depotPosition[i]);
                        }
                    } else {
                        if (n != 0 && k != 0) {
                            System.out.println("Depot " + i + " at restaurant " + depotPosition[i] + " serves restaurant " + depotPosition[i]);
                        }
                    }
                }

                if (n != 0 && k != 0) {
                    System.out.println("Total distance sum = " + possibleTotalSum[n][k]);
                    System.out.println();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}