package prng;

import java.math.BigInteger;

public class LinearCongruential {
    private int nbits;
    private BigInteger a;
    private BigInteger c;
    private BigInteger m;
    private BigInteger seed;

    public LinearCongruential(int nbits, BigInteger seed, BigInteger a, BigInteger c) {
        this.nbits = nbits;
        this.a = a;
        this.c = c;
        this.m = BigInteger.ONE.shiftLeft(nbits);
        this.seed = seed;
    }

    public BigInteger generate() {
        do {
            seed = seed.multiply(a).add(c).mod(m);
        } while (seed.bitLength() != nbits);

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
