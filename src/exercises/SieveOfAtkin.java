package exercises;


public class SieveOfAtkin {

    private final int n;
    private boolean[] array;
    private long[] times = new long[4];

    public static void main(String[] args) {
        int n = (args.length > 0) ? Integer.valueOf(args[0]) : 100000000;
        SieveOfAtkin sieve = new SieveOfAtkin(n);
        sieve.calculate();
        sieve.display((args.length > 1) ? Boolean.valueOf(args[1]) : false);
    }

    public SieveOfAtkin(int n) {
        this.n = n;
    }

    private boolean[] calculate() {
        int sqrtMax = (int) Math.sqrt(n) + 1;
        array = new boolean[n];
        times[0] = System.currentTimeMillis();

        // Find prime
        int[] sequence = { 2, 4 };
        int index = 0;
        int k1 = 0, k = 0;

        double xUpper = Math.sqrt(n / 4) + 1;
        int x = 1;
        int y = 0;

        while (x < xUpper) {
            index = 0;
            k1 = 4 * x * x;
            y = 1;
            if (x % 3 == 0) {
                while (true) {
                    k = k1 + y * y;
                    if (k >= n) {
                        break;
                    }
                    array[k] = !array[k];
                    y += sequence[(++index & 1)];
                }
            } else {
                while (true) {
                    k = k1 + y * y;
                    if (k >= n) {
                        break;
                    }
                    array[k] = !array[k];
                    y += 2;
                }
            }
            x++;
        }

        times[1] = System.currentTimeMillis();

        xUpper = Math.sqrt(n / 3) + 1;
        x = 1;
        y = 0;

        while (x < xUpper) {
            index = 1;
            k1 = 3 * x * x;
            y = 2;
            while (true) {
                k = k1 + y * y;
                if (k >= n) {
                    break;
                }
                array[k] = !array[k];
                y += sequence[(++index & 1)];
            }
            x += 2;
        }

        times[2] = System.currentTimeMillis();

        xUpper = (int) Math.sqrt(n);
        x = 1;
        y = 0;

        while (x < xUpper) {
            k1 = 3 * x * x;
            if ((x & 1) == 0) {
                y = 1;
                index = 0;
            } else {
                y = 2;
                index = 1;
            }
            while (y < x) {
                k = k1 - y * y;
                if (k < n) {
                    array[k] = !array[k];
                }
                y += sequence[(++index & 1)];
            }
            x++;
        }

        times[3] = System.currentTimeMillis();

        array[2] = true;
        array[3] = true;
        for (int l = 5; l <= sqrtMax; l++) {
            if (array[l]) {
                int l2 = l * l;
                for (k = l2; k < l; k += l2) {
                    array[k] = false;
                }
            }
        }

        return array;
    }


    private void display(boolean verbose) {
        long prims = 0;
        for (int i = 2; i < n; i++) {
            if (array[i]) {
                prims++;
                if (verbose)
                    System.out.println(i);
            }
        }
        double fin = System.currentTimeMillis();
        System.out.println("Max: " + n + " -- Prims:" + prims + " -- T: " + (fin - times[0]) / 1000d);
        System.out.println("1: " + (times[1] - times[0]) / 1000d + " -- 2: "
                + (times[2] - times[1]) / 1000d + " -- 3: "
                + (times[3] - times[2]) / 1000d + " -- 4: " + (fin - times[3])
                / 1000d);
    }
}
