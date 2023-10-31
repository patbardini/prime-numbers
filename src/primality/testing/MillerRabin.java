package primality.testing;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MillerRabin {

    /**
     * Testa se um número é composto ou provavelmente primo, com base no algoritmo de Miller-Rabin.
     * @param n número a ser testado.
     * @param t quantidade de rodadas de teste a serem realizadas. Quanto maior t, menor a probabilidade de erro.
     * @return false se n for composto e true se n for provavelmente primo.
     */
    public static boolean isPrime(BigInteger n, int t) {
        // Testa os casos base: 1, 2 e 3 são primos
        ArrayList<BigInteger> baseCases = new ArrayList<>(Arrays.asList(BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3L)));
        if (baseCases.contains(n)) return true;

        // Testa o caso os números pares
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        // Calcula m tal que n - 1 = 2^k * m; onde k > 0
        BigInteger m, k;
        m = n.subtract(BigInteger.ONE);
        k = BigInteger.ZERO;
        while (m.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            k = k.add(BigInteger.ONE);
            m = m.divide(BigInteger.TWO);
        }

        // Realiza o teste t vezes
        for (int i = 0; i < t; i++) {
            if (!millerTest(n, m)) return false;
        }
        return true;
    }

    protected static boolean millerTest(BigInteger n, BigInteger d) {
        BigInteger n1 = n.subtract(BigInteger.ONE);  // n - 1

        BigInteger a = getRandomBigInteger(BigInteger.TWO, n);
        BigInteger x = a.modPow(d, n);

        if (x.equals(BigInteger.ONE) || x.equals(n1)) return true;

        while (!d.equals(n1)) {
            x = x.modPow(BigInteger.TWO, n);
            d = d.multiply(BigInteger.TWO);

            if (x.equals(BigInteger.ONE)) return false;
            if (x.equals(n1)) return true;
        }
        return false;
    }

    protected static BigInteger getRandomBigInteger(BigInteger min, BigInteger max) {
        // Cria um gerador de números aleatórios
        SecureRandom secureRandom = new SecureRandom();

        // Gera um número aleatório
        BigInteger randomBigInteger = new BigInteger(max.bitLength(), secureRandom);

        // Garante que o número gerado está dentro do intervalo [2, n-1]
        while (randomBigInteger.compareTo(min) < 0 || randomBigInteger.compareTo(max) >= 0) {
            randomBigInteger = new BigInteger(max.bitLength(), secureRandom);
        }
        return randomBigInteger;
    }

    public static void main(String[] args) {
        Scanner io = new Scanner(System.in);

        System.out.println("Informe o número que deseja testar:");
        BigInteger n = io.nextBigInteger();

        System.out.println("\nInforme a quantidade de iterações de teste a serem realizadas:");
        int t = io.nextInt();

        System.out.println("\n----------------------------------------------------------------\n");

        boolean isPrime = isPrime(n, t);
        System.out.println(isPrime ? n + " é provavelmente primo." : n + " é composto.");
    }
}
