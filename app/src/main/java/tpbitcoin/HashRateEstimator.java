package tpbitcoin;

import org.bitcoinj.core.Sha256Hash;
import org.checkerframework.checker.units.qual.A;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HashRateEstimator {
    private final int duration; //duration of each experience, in milliseconds
    private final int numberOfTries; // number of experience

    /** Create a new object for estimating the number of SHA256 hashs the host can perform per second
    ** @param duration: (milli-seconds) duration of each experiment
     * @param numberOfTries : number of experiments to run
     */
    public HashRateEstimator(int duration, int numberOfTries) {
        this.duration = duration;
        this.numberOfTries = numberOfTries;
    }

    /**
     * @return : return the hashrate (hash/second)
     */
    public double estimate(){
        byte[] bytes = "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzzaabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz".getBytes();
        MessageDigest md = Sha256Hash.newDigest();
        int i = 0;
        long start = System.currentTimeMillis();
        while (true){
            if (System.currentTimeMillis() - start > duration){
                break;
            }
            else if (i >= numberOfTries){
                break;
            }
            else {
                md.update(bytes);
                //System.out.println(md.digest());
                i = i+1;
            }

        }
        long end = System.currentTimeMillis();
        long temps = end-start;
        double hashrate = 1000*numberOfTries/temps;
        return hashrate;
    }

}
