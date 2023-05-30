import java.util.Arrays;

public class RadixFloatingPoint {
    // count number of negative value in original array
    static int CountNegative(double arr[], int size) {
        int negative = 0;
        for (int i = 0; i < size; i++) {
            if (arr[i] < 0)
                negative++;
        }

        return negative;
    }


    // Copy the value from original array and store negative and positive value in two separate array
    static void SplitArray(double arr[], int size, double PosArr[], double NegArr[]) {
        int P = 0;
        int N = 0;
        for (int i = 0; i < size; i++) {
            if (arr[i] >= 0) {
                PosArr[P] = arr[i];
                P++;
            }
            if (arr[i] < 0) {
                NegArr[N] = (arr[i] * -1); // multiply value with -1 to make it a positive value before storing into array
                N++;
            }
        }
    }

    // to print out the array
    static void print(double arr[], int n) {
        System.out.print("{ ");
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        System.out.print("}\n");
    }


    //Find the maximum digit of integer
    static int MaxIntegerValue(double arr[], int size) {
        String FloatStr = Double.toString(Math.abs(arr[0]));
        String[] strArr = FloatStr.split("\\.");
        int max_Integer = strArr[0].length();

        for (int i = 1; i < size; i++) {
            FloatStr = Double.toString(Math.abs(arr[i]));
            strArr = FloatStr.split("\\."); // split the string at '.' and store in array
            int IntegerPlace = strArr[0].length(); // number of integer digit is the length of string before '.'

            if (max_Integer < IntegerPlace) {
                max_Integer = IntegerPlace;
            }

        }

        return max_Integer;
    }

    // find maximum number of decimal places
    static int MaxDecimal(double arr[], int size) {
        String FloatStr = Double.toString(Math.abs(arr[0]));
        String[] strArr = FloatStr.split("\\.");
        int max_decimal = strArr[1].length();

        for (int i = 1; i < size; i++) {
            FloatStr = Double.toString(Math.abs(arr[i]));
            strArr = FloatStr.split("\\."); //split string at ".'
            int decimal = strArr[1].length(); // number of decimal places is length of string after '.'

            if (max_decimal < decimal) {
                max_decimal = decimal;
            }

        }

        return max_decimal;
    }

    // Counting sort for integer places
    static void CountingSort(double arr[], int n, double div) {


        double output[] = new double[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        for (i = 0; i < n; i++) {
            count[(int) ((arr[i] / div) % 10)]++;
        }

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--) {
            output[count[(int) ((arr[i] / div) % 10)] - 1] = arr[i]; // store value of array arr[i] at the position indicated
            // in the array count[i] - 1 because array index always start with 0
            count[(int) ((arr[i] / div) % 10)]--;
        }

        for (i = 0; i < n; i++)
            arr[i] = output[i];

    }

    // counting sort for decimal values
    static void CountingSortDecimal(double arr[], int n, double div) {
        double output[] = new double[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        for (i = 0; i < n; i++) {
            count[(int) (Math.round((arr[i] * div)) % 10)]++;
            // value is multiply by 10 power of max decimal places and the remainder when divided by 10 is the value at right most decimal place
            // div is increment by 10 each pass to move decimal places from right to left
        }

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--) {
            output[count[(int)(Math.round((arr[i] * div)) % 10)] - 1] = arr[i]; // store value of array arr[i] at the position indicated
            // in the array count[i] - 1 because array index always start with 0
            count[(int) (Math.round((arr[i] * div)) % 10)]--;
        }

        for (i = 0; i < n; i++)
            arr[i] = output[i];

    }


    static void RadixSort(double Arr[], int size, int num_integer, int Num_decimal) {
        int pass = 1;
        double div = Math.pow(10,Num_decimal);  //the decimal values is sorted first
        for (int i = 0; i < Num_decimal; i++) {
            CountingSortDecimal(Arr, size, div);
            div /= 10;
            System.out.print("Pass " + pass + " : ");
            print(Arr, size);
            pass++;
        }
        div = 1; // sorting for integer values
        for (int i = 0; i < num_integer; i++) {
            CountingSort(Arr, size, div);
            div *= 10;
            System.out.print("Pass " + pass + " : ");
            print(Arr, size);
            pass++;
        }
    }

    static void CopyNegativeArray(double Arr[],int size,double NegArr[],int Neg)
    {
        int N = Neg - 1;
        for ( int i = 0; i < Neg; i++)
        {
            Arr[i] = (NegArr[N]) * -1; // multiply value by -1 to return the value to negative
            N--;
        }
    }

    static void CombineArray(double Arr[], double PosArr[], double NegArr[], int size, int pos, int Neg) {
        int N = Neg - 1;
        int P = 0;
        int i;

        // copy the negative array in reverse order into original array first
        for (i = 0; i < Neg; i++) {
            Arr[i] = (NegArr[N]) * -1; // multiply value by -1 to return the value to negative
            N--;
        }
        for (i = Neg; i < size; i++) {
            Arr[i] = PosArr[P]; // copy value in positive array into original array after negative array
            P++;
        }
    }


    public static void main(String[] args) {
        double arr[] = {1.001,1.002,1.003, 1.231, 1, 2.001, 2.02,0.001,0.002,1.392,21.39};
        int n = arr.length; // find array size
        int N = CountNegative(arr, n); // count number of negative value
        int p = n - N; // get number of positive value
        double[] PosArr = new double[p]; // initialise array for storing positive values
        double[] NegArr = new double[N]; // initialise array for storing negative values
        SplitArray(arr, n, PosArr, NegArr);// fill in value for positive and negative arrays

        if (  N == 0 && p != 0 ) //if all values are negative
        {
            System.out.print("\n\nOriginal Array:");
            print(arr, n);
            int max_int = MaxIntegerValue(arr, n);
            int max_decimal = MaxDecimal(arr, n);
            RadixSort(arr, n, max_int, max_decimal);
            System.out.print("Final Sorted Array:");
            print(arr, n);

        }

        if (  N != 0 && p == 0 ) // if all values are positive
        {
            System.out.print("\n\nOriginal Array:");
            print(arr, n);
            int max_neg = MaxIntegerValue(NegArr, N);
            int decimal_neg = MaxDecimal(NegArr, N);
            RadixSort(NegArr, n, max_neg, decimal_neg);
            CopyNegativeArray(arr,n,NegArr,N);
            System.out.print("Final Sorted Array:");
            print(arr, n);
        }

        if (  N != 0 && p != 0 )
        {
            int max_pos = MaxIntegerValue(PosArr, p); // find max integer places for positive array
            int max_neg = MaxIntegerValue(NegArr, N); // find max integer places for negative array
            int decimal_pos = MaxDecimal(PosArr, p); // find max decimal places for positive array
            int decimal_neg = MaxDecimal(NegArr, N); // find max decimal places for negative array
            System.out.print("\n\nOriginal Array:");
            print(arr, n);
            System.out.println("\nSplit into two array for positive and negative value: ");
            System.out.print("Positive Number Array:");
            print(PosArr, p);
            System.out.print("Negative Number Array:");
            print(NegArr, N);
            System.out.println("\nSorting Positive Number Array :");
            RadixSort(PosArr, p, max_pos, decimal_pos); // use Radix sorting to sort positive array
            System.out.println("\nSorting Negative Number Array: ");
            RadixSort(NegArr, N, max_neg, decimal_neg); // use Radix sorting to sort negative array
            CombineArray(arr, PosArr, NegArr, n, p, N); // copy both the sorted arrays into original array
            System.out.println("\nMerge positive and negative array into original array:");
            System.out.print("Final Sorted Array:");
            print(arr, n);
        }

    }
}
