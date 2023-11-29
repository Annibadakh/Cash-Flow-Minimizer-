import java.util.Scanner;

class CashFlowMin {
    static int N;

    static int getMin(int arr[]) {
        int minInd = 0;
        for (int i = 1; i < N; i++)
            if (arr[i] < arr[minInd])
                minInd = i;
        return minInd;
    }

    static int getMax(int arr[]) {
        int maxInd = 0;
        for (int i = 1; i < N; i++)
            if (arr[i] > arr[maxInd])
                maxInd = i;
        return maxInd;
    }

    static int minOf2(int x, int y) {
        return (x < y) ? x : y;
    }

    static void minCashFlowRec(int amount[]) {
        int mxCredit = getMax(amount), mxDebit = getMin(amount);

        if (amount[mxCredit] == 0 && amount[mxDebit] == 0)
            return;

        int min = minOf2(-amount[mxDebit], amount[mxCredit]);
        amount[mxCredit] -= min;
        amount[mxDebit] += min;

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        char[] A = str.toCharArray();

        System.out.println("Person " + A[mxDebit] + " pays " + min + " to " + "Person " + A[mxCredit]);

        minCashFlowRec(amount);
    }

    static void minCashFlow(int graph[][]) {
        int amount[] = new int[N];

        for (int p = 0; p < N; p++)
            for (int i = 0; i < N; i++)
                amount[p] += (graph[i][p] - graph[p][i]);

        minCashFlowRec(amount);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of persons: ");
        N = scanner.nextInt();

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        char[] A = str.toCharArray();

        int graph[][] = new int[N][N];
        System.out.println("Enter transaction:");
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++){
                if(A[i]!=A[j]){
                    System.out.print("Money treansfer from "+A[i]+" to "+A[j]+" :");
                    graph[i][j] = scanner.nextInt();
                }
            }
        minCashFlow(graph);
    }
}