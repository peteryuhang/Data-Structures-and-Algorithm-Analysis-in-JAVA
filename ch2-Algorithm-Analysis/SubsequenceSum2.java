public class SubsequenceSum2 {
  public static void main(String[] args) {
    int[] testArr = {-2, 11, -4, 13, -5, -2};
    int res = maxSubSum2(testArr);
    System.out.println(res);
  }

  public static int maxSubSum2(int[] a) {
    int maxSum = 0;

    for (int i = 0; i < a.length; i++) {
      int curSum = 0;
      for (int j = i; j < a.length; j++) {
        curSum += a[j];

        if (curSum > maxSum) {
          maxSum = curSum;
        }
      }
    }
    return maxSum;
  }
}