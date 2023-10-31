package prng;

import java.math.BigInteger;

public class BlumBlumShub {
    private int nbits;
    private BigInteger seed;
    private BigInteger p;
    private BigInteger q;
    private BigInteger M;

    public BlumBlumShub(int nbits, BigInteger seed, BigInteger p, BigInteger q) {
        this.nbits = nbits;
        this.seed = seed;
        this.p = p;
        this.q = q;
        this.M = p.multiply(q);
    }

    public BigInteger generate() {
        StringBuilder bitOutput = new StringBuilder();
        BigInteger previousX = seed;
        for (int i = 0; i < nbits; i++) {
            BigInteger x = getNextX(previousX);
            int leastSignificantBit = getLeastSignificantBit(x);
            bitOutput.append(leastSignificantBit);
            previousX = x;
        }
        BigInteger decimalOutput = new BigInteger(bitOutput.toString(), 2);
        return decimalOutput;
    }

    private BigInteger getNextX(BigInteger Xn) {
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
