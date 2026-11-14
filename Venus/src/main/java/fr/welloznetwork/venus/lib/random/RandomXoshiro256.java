package fr.welloznetwork.venus.lib.random;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RandomXoshiro256 extends Random
{
    private static final long serialVersionUID = -2837799889588687855L;
    private static final AtomicLong uniq = new AtomicLong(System.nanoTime());
    
    private static final long nextUniq()
    {
        return splitmix64_2(uniq.addAndGet(SPLITMIX1_MAGIC));
    }
    
    public RandomXoshiro256()
    {
        this(System.nanoTime() ^ nextUniq());
    }
    
    public RandomXoshiro256(long seed)
    {
        super(seed);
    }
    
    public RandomXoshiro256(long s1, long s2, long s3, long s4)
    {
        this.setState(s1, s2, s3, s4);
    }
    
    private static final long SPLITMIX1_MAGIC = 0x9E3779B97F4A7C15L;
    
    private static long splitmix64_1(long x)
    {
        return (x + SPLITMIX1_MAGIC);
    }
    
    private static long splitmix64_2(long z)
    {
        z = (z ^ (z >> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >> 27)) * 0x94D049BB133111EBL;
        
        return z ^ (z >> 31);
    }
    
    public void setSeed(long seed)
    {
        super.setSeed(seed);
        
        long sms = splitmix64_1(seed);
        
        this.s0 = splitmix64_2(sms);
        sms = splitmix64_1(sms);
        this.s1 = splitmix64_2(sms);
        sms = splitmix64_1(sms);
        this.s2 = splitmix64_2(sms);
        sms = splitmix64_1(sms);
        this.s3 = splitmix64_2(sms);
    }
    
    public void setState(long s0, long s1, long s2, long s4)
    {
        if (s0 == 0 && s1 == 0 && s2 == 0 && s4 == 0)
        {
            throw new IllegalArgumentException("xoshiro256** state cannot be all zeroes");
        }
        
        this.s0 = s0;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s4;
    }
    
    protected int next(int bits)
    {
        return (int) (this.nextLong() & ((1L << bits) - 1));
    }
    
    public int nextInt()
    {
        return (int) this.nextLong();
    }
    
    public int nextInt(int bound)
    {
        return (int) this.nextLong(bound);
    }
    
    public long nextLong(long bound)
    {
        if (bound <= 0)
        {
            throw new IllegalArgumentException("bound must be positive");
        }
        
        return (this.nextLong() & Long.MAX_VALUE) % bound;
    }
    
    public double nextDouble()
    {
        return (this.nextLong() >>> 11) * 0x1.0P-53;
    }
    
    public float nextFloat()
    {
        return (this.nextLong() >>> 40) * 0x1.0P-24F;
    }
    
    public boolean nextBoolean()
    {
        return (this.nextLong() & 1) != 0;
    }
    
    public void nextBytes(byte[] buf)
    {
        this.nextBytes(buf, 0, buf.length);
    }
    
    public void nextBytes(byte[] buf, int ofs, int len)
    {
        if (ofs < 0)
        {
            throw new ArrayIndexOutOfBoundsException("Offset " + ofs + " is negative");
        }
        
        if (ofs >= buf.length)
        {
            throw new ArrayIndexOutOfBoundsException("Offset " + ofs + " is greater than buffer length");
        }
        
        if (ofs + len > buf.length)
        {
            throw new ArrayIndexOutOfBoundsException("Length " + len + " with offset " + ofs + " is past end of buffer");
        }
        
        int j = 8;
        long l = 0;
        
        for (int i = ofs; i < ofs + len; i++)
        {
            if (j >= 8)
            {
                l = this.nextLong();
                j = 0;
            }
            
            buf[i] = (byte) (l&0xFF);
            l = l >>> 8L;
            j++;
        }
    }
    
    private static long rotl(long x, int k)
    {
        return (x << k) | (x >>> (64 - k));
    }
    
    private long s0, s1, s2, s3;
    
    public long nextLong()
    {
        long result_starstar = rotl(this.s1 * 5, 7) * 9;
        
        long t = this.s1 << 17;
        
        this.s2 ^= this.s0;
        this.s3 ^= this.s1;
        this.s1 ^= this.s2;
        this.s0 ^= this.s3;
        
        this.s2 ^= t;
        
        this.s3 = rotl(this.s3, 45);
        
        return result_starstar;
    }
}