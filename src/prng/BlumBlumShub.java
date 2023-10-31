package prng;

import java.math.BigInteger;

public class BlumBlumShub {
    private int nbits;
    private BigInteger seed;
    private BigInteger p;
    private BigInteger q;
    private BigInteger M;

    /**
     * Construtor do algoritmo gerador de números pseudo-aleatórios Blum Blum Shub.
     * @param nbits tamanho do número a ser gerado.
     * @param seed semente inicial.
     * @param p um número primo p, congruente à 3 mod 4.
     * @param q um número primo q, congruente à 3 mod 4.
     */
    public BlumBlumShub(int nbits, BigInteger seed, BigInteger p, BigInteger q) {
        this.nbits = nbits;
        this.seed = seed;
        this.p = p;
        this.q = q;
        this.M = p.multiply(q);
    }

    public BigInteger generate() {
        // Para armazenar os bits calculados, que formarão o número pseudo-aleatório
        StringBuilder bitOutput = new StringBuilder();
        // x0 = seed
        BigInteger previousX = seed;

        // Calcula os nbits do número
        for (int i = 0; i < nbits; i++) {
            BigInteger x = getNextX(previousX);  // Obtém Xn+1
            int leastSignificantBit = getLeastSignificantBit(x);  // Obtém o bit menos significativo de Xn+1
            bitOutput.append(leastSignificantBit);  // Acrescenta o bit obtido ao número
            previousX = x;
        }
        BigInteger decimalOutput = new BigInteger(bitOutput.toString(), 2);
        return decimalOutput;
    }

    private BigInteger getNextX(BigInteger Xn) {
        // Xn+1 = Xn^2 (mod M)
        return (Xn.pow(2)).mod(M);
    }

    private int getLeastSignificantBit(BigInteger number) {
        return number.mod(BigInteger.TWO).intValue();
    }

    public static void main(String[] args) {
        BigInteger seed = BigInteger.valueOf(System.nanoTime());
        BigInteger p = BigInteger.valueOf(30000000091L);
        BigInteger q = BigInteger.valueOf(40000000003L);

        BlumBlumShub blumBlumShub = new BlumBlumShub(56, seed, p, q);
        BigInteger number = blumBlumShub.generate();
        System.out.println(number);
    }
}
