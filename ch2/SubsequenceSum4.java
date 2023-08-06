public class SubsequenceSum4 {
  public static void main(String[] args) {
    int[] testArr = {-2, 11, -4, 13, -5, -2};
    int res = maxSubSum4(testArr);
    System.out.println(res);
  }

  public static int maxSubSum4(int[] a) {
    int maxSum = 0, curSum = 0;

    for (int i = 0; i < a.length; i++) {
      curSum += a[i];
      if (curSum > maxSum) {
        maxSum = curSum;
      }
      if (curSum < 0) {
        curSum = 0;
      }
    }
    return maxSum;
  }
}