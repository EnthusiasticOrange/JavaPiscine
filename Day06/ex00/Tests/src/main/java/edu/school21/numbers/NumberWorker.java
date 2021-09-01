package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException();
        }
        if (number == 2 || number == 3)
            return true;
        if (number % 2 == 0 || number % 3 == 0)
            return false;
        for (int i = 5; i * i <= number; i += 6)
        {
            if (number % i == 0 || number % (i + 2) == 0)
                return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        long abs = Math.abs((long) number);
        int res = 0;

        while (abs != 0) {
            res += abs % 10;
            abs /= 10;
        }
        return res;
    }
}
