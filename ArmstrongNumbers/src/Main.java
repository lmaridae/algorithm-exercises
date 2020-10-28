import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getNumbers(1234)));
    }

    public static long[] getNumbers(long N) {
        long[] result = null;
        List<Long> resultList = new ArrayList<>();
        isArmstrong(N);
        for (long i = 1; i < N; i++) {
            if (isArmstrong(i)) {
                resultList.add(i);
            }
        }
        return resultList.stream().mapToLong(number -> number).toArray();
    }

    private static boolean isArmstrong(long number) {
        if (number <= 0) {
            return false;
        }

        long[] digits = String.valueOf(number)
                .chars()
                .mapToLong(digit -> digit - '0')
                .toArray();

        long sum = Arrays.stream(digits)
                .map(digit -> (long) Math.pow(digit, digits.length))
                .sum();

        return sum == number;
    }
}
