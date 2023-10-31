package primality.testing;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class SolovayStrassen {
    /**
     * Testa se um número é composto ou provavelmente primo, com base no algoritmo de Solovay-Strassen.
     * @param n número a ser testado.
     * @param t quantidade de rodadas de teste a serem realizadas. Quanto maior t, menor a probabilidade de erro.
     * @return false se n for composto e true se n for provavelmente primo.
     */
    public static boolean isPrime(BigInteger n, int t) {
        if (n.compareTo(BigInteger.TWO) < 0) return false;

        for (int i = 0; i < t; i++) {
            BigInteger a = getRandomBigInteger(BigInteger.TWO, n);

            int jacobianSymbol = jacobi(a, n);
            BigInteger y = a.modPow(n.subtract(BigInteger.ONE).divide(BigInteger.TWO), n);
            if (jacobianSymbol == 0 || !BigInteger.valueOf(jacobianSymbol).subtract(y).mod(n).equals(BigInteger.ZERO)) {
                return false; // n é composto
            }
        }
        return true; // n é provavelmente primo
    }

    private static int jacobi(BigInteger a, BigInteger n) {
        a = a.mod(n);
        int jacobi = 1;
        BigInteger mod8;
        while (!a.equals(BigInteger.ZERO)) {
            while (a.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                a = a.divide(BigInteger.TWO);
                mod8 = n.mod(BigInteger.valueOf(8));
                if (mod8.equals(BigInteger.valueOf(3)) || mod8.equals(BigInteger.valueOf(5))) {
                    jacobi = -jacobi;
                }
            }
            mod8 = n;
            n = a;
            a = mod8;
            if (a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && n.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                jacobi = -jacobi;
            }
            a = a.mod(n);
        }
        return (n.equals(BigInteger.ONE) ? jacobi : 0);
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
