package prng;

import java.math.BigInteger;

public class LinearCongruential {
    private int nbits;
    private BigInteger a;
    private BigInteger c;
    private BigInteger m;
    private BigInteger seed;

    /**
     * Construtor do algoritmo gerador de números pseudo-aleatórios Linear Congruential.
     * @param nbits tamanho do número a ser gerado.
     * @param seed semente inicial.
     * @param a multiplicador a.
     * @param c incremento c.
     */
    public LinearCongruential(int nbits, BigInteger seed, BigInteger a, BigInteger c) {
        this.nbits = nbits;
        this.a = a;
        this.c = c;
        this.m = BigInteger.ONE.shiftLeft(nbits); // Módulo = 2^nbits, a fim de gerar os números com o tamanho desejado
        this.seed = seed;
    }

    public BigInteger generate() {
        // Gera o próximo valor utilizando a fórmula do LCG: Xi = (Xi−1 * a + c) mod m
        do {
            seed = seed.multiply(a).add(c).mod(m);
        } while (seed.bitLength() != nbits);  // Garante que o valor possuirá o tamanho desejado

        return seed;
    }

    public static void main(String[] args) {
        BigInteger seed = BigInteger.valueOf(System.nanoTime());
        BigInteger a = new BigInteger("1664525");
        BigInteger c = new BigInteger("1013904223");

        LinearCongruential lcg = new LinearCongruential(56, seed, a, c);
        BigInteger number = lcg.generate();
        System.out.println(number);
    }
}
