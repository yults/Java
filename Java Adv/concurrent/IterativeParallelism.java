package info.kgeorgiy.ja.yulcova.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.*;
import java.util.stream.Stream;

public class IterativeParallelism implements ScalarIP {

    private <T, R> R runMultiThread(int nOfThreads, List<T> vals, Function<List<T>, R> makeOp, Function<List<R>, R> joinRes) throws InterruptedException {
            if (nOfThreads <= 0){
                throw new InterruptedException("Invalid num of threads");
            }
            nOfThreads = Math.min(nOfThreads, vals.size());
            List<Thread> listThreads = new ArrayList<>();
            List<R> res = new ArrayList<>(Collections.nCopies(nOfThreads, null));
            int sizeValsThread = vals.size() / nOfThreads;
            int leftovers = vals.size() % nOfThreads;
            int right = 0;
            for (int i = 0; i < nOfThreads; i++) {
                final int left = right;
                right += sizeValsThread;
                if (leftovers > 0) {
                    right++;
                    leftovers--;
                }
                final int finalI = i;
                final int finalRight = right;
                Thread thread = new Thread(() -> res.set(finalI, makeOp.apply(vals.subList(left, finalRight))));
                listThreads.add(thread);
                thread.start();
            }
            for (Thread thread : listThreads) {
                // :NOTE: you want all child threads to die when the function ends
				try {
					thread.join();
				} catch (InterruptedException e) {
					System.out.println("Can't join");
				}
            }
            return joinRes.apply(res);
        }

    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return runMultiThread(threads, values,
                list -> list.stream().max(comparator).orElse(null),
                list -> list.stream().max(comparator).orElse(null));
    }


    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(threads, values, comparator.reversed());
    }

    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return runMultiThread(threads, values, list -> list.stream().allMatch(predicate), bools -> !bools.contains(false));
    }

    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }
}
