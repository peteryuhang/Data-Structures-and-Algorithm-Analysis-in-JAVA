public class SubsequenceSum1 {
  public static void main(String[] args) {
    int[] testArr = {-2, 11, -4, 13, -5, -2};
    int res = maxSubSum1(testArr);
    System.out.println(res);
  }

  public static int maxSubSum1(int[] a) {
    int maxSum = 0;

    for (int i = 0; i < a.length; i++) {
      for (int j = i; j < a.length; j++) {
        int curSum = 0;
        for (int k = i; k <= j; k++) {
          curSum += a[k];
        }

        if (curSum > maxSum) {
          maxSum = curSum;
        }
      }
    }
    return maxSum;
  }
}