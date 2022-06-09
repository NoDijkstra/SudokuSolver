public class test {
    public static void main(String[] args) {
        int[] data = new int[10];

        for (int i = 0; i < data.length-1; i+=2) {
            data[i] = i;
            data[i+1] = i;
            System.out.print(data[i] + " ");

        }
        System.out.println();
        int size = -5;
        int n = Math.abs(size);
        int[][] result = new int[n][n];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j]    = (i+j)%n;
                System.out.print(result[i][j] + " ");

            }
        }
        System.out.println();
        System.out.print(div(2, 4));


    }

    public static int div(int a, int b){
        if (a<b){
            return 0;
        } else {
            return 1 + div(a, b+b);
        }
    }
}
