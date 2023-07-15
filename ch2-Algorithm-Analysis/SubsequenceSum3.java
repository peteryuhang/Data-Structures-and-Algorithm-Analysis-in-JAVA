public class SubsequenceSum3 {
  public static void main(String[] args) {
    int[] testArr = {-2, 11, -4, 13, -5, -2};
    int res = maxSubSum3(testArr, 0, testArr.length - 1);
    System.out.println(res);
  }

  public static int maxSubSum3(int[] a, int left, int right) {
    if (left >= right) {
      return a[left];
    }

    int center = (left + right) / 2;
    int leftMaxSum = maxSubSum3(a, left, center);
    int rightMaxSum = maxSubSum3(a, center + 1, right);

    int maxLeftBoardSum = 0;
    int curSum = 0;
    for (int i = center; i >= left; i--) {
      curSum += a[i];
      if (curSum > maxLeftBoardSum) {
        maxLeftBoardSum = curSum;
      }
    }

    int maxRightBoardSum = 0;
    curSum = 0;
    for (int i = center + 1; i <= right; i++) {
      curSum += a[i];
      if (curSum > maxRightBoardSum) {
        maxRightBoardSum = curSum;
      }
    }

    return Math.max(
      Math.max(rightMaxSum, leftMaxSum),
      maxLeftBoardSum + maxRightBoardSum
    );
  }
}