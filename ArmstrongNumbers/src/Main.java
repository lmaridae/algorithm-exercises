import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static Map<Integer, Long> digitsPowers = new HashMap<>();
    private static Set<Long> results = new TreeSet<>();

    // recursive values
    private static Deque<Integer> currentDigits = new LinkedList<>();
    private static long currentValue;

//    public static void main(String[] args) {
//        System.out.println(Arrays.toString(getNumbers(1234)));
//    }

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        var result = getNumbers(100_000_000_000L);
        System.out.println(Arrays.toString(result));
        System.out.println("size = " + result.length);
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 128 * 1024));
        System.out.println("time = " + (b - a) / 1000);
    }

    public static long[] getNumbers(long N) {
        int digitAmount = String.valueOf(N).length();

        for (int i = 1; i <= digitAmount; i++) {
            fillDigitsPowers(i);
            currentDigits.clear();
            currentValue = 0;
            recursiveSearch(i);
        }

        return results.stream()
                .mapToLong(value -> value)
                .filter(value -> value < N)
                .toArray();
    }

    private static void fillDigitsPowers(int digitCount) {
        for (int digit = 0; digit < 10; digit++) {
            digitsPowers.put(digit, (long) Math.pow(digit, digitCount));
        }
    }

    private static void recursiveSearch(int digitAmount) {

        int currentValueLength = String.valueOf(currentValue).length();
        int lastDigit = currentDigits.peekLast() == null ? 0 : currentDigits.peekLast();

        if (currentValueLength > digitAmount || currentDigits.size() == digitAmount) {
            if (currentValueLength == digitAmount) {

                List<Integer> digitsInQueue = new ArrayList<>(currentDigits);

                List<Integer> digitsInNumber = String.valueOf(currentValue)
                        .chars()
                        .map(value -> value - '0')
                        .boxed()
                        .collect(Collectors.toList());

                Collections.sort(digitsInQueue);
                Collections.sort(digitsInNumber);

                if (digitsInNumber.equals(digitsInQueue)) {
                    results.add(currentValue);
                }
            }
            return;
        }

        for (int i = lastDigit; i < 10; i++) {
            currentDigits.add(i);
            currentValue += digitsPowers.get(i);
            recursiveSearch(digitAmount);
            currentValue -= digitsPowers.get(i);
            currentDigits.pollLast();
        }
    }


//    public static long[] getNumbers(long N) {
//        List<Long> resultList = new ArrayList<>();
//        isArmstrong(N);
//        for (long i = 1; i < N; i++) {
//            if (isArmstrong(i)) {
//                resultList.add(i);
//            }
//        }
//        return resultList.stream().mapToLong(number -> number).toArray();
//    }
//
//    private static boolean isArmstrong(long number) {
//        if (number <= 0) {
//            return false;
//        }
//
//        long[] digits = String.valueOf(number)
//                .chars()
//                .mapToLong(digit -> digit - '0')
//                .toArray();
//
//        long sum = Arrays.stream(digits)
//                .map(digit -> (long) Math.pow(digit, digits.length))
//                .sum();
//
//        return sum == number;
//    }
}
