package edu.andrewisnew.java.topics.concurrency.lessons.lesson01;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class FirstSecondThird {
    private final Object secondStarter = new Object();
    private final Object thirdStarter = new Object();
    private volatile boolean firstFinished;
    private volatile boolean secondFinished;

    private void first(Runnable first) {
        synchronized (secondStarter) {
            first.run();
            secondStarter.notifyAll();
            firstFinished = true;
        }
    }

    private void second(Runnable second) {
        try {
            synchronized (secondStarter) {
                if (!firstFinished) {
                    secondStarter.wait();
                }
                second.run();
                synchronized (thirdStarter) {
                    thirdStarter.notifyAll();
                    secondFinished = true;
                }
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unexpected interrupt", e);
        }
    }

    private void third(Runnable third) {
        try {
            synchronized (thirdStarter) {
                if (!secondFinished) {
                    thirdStarter.wait();
                }
                third.run();
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unexpected interrupt", e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            FirstSecondThird firstSecondThird = new FirstSecondThird();

            Thread first = new Thread(() -> firstSecondThird.first(() -> System.out.print("first")));
            Thread second = new Thread(() -> firstSecondThird.second(() -> System.out.print("second")));
            Thread third = new Thread(() -> firstSecondThird.third(() -> System.out.print("third")));
            List<Thread> threads = Arrays.asList(first, second, third);
            Collections.shuffle(threads);
            for (Thread thread : threads) {
                thread.start();
            }
            first.join();
            second.join();
            third.join();
            System.out.println();
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}


class FreqStack {
    private final List<List<Integer>> vals = new ArrayList<>();
    private final Map<Integer, Integer> valToFrequency = new HashMap<>();

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        a = Arrays.copyOf(a, 12);
        System.out.println(Arrays.toString(a));
        System.out.println(splitString("64424509442147483647"));
    }

    public static boolean splitString(String s) {
        int startIndex = getFirstNonZeroIndex(s, 0, s.length());
        if (startIndex == -1) {
            return false; //all zeros
        }
        for (int firstNumSize = (s.length() - startIndex + 1) / 2; firstNumSize > 0; firstNumSize--) {
            long currentNum;
            int currentNumSize;
            int currentNumIndex;
            while (true) {
                if (firstNumSize == 0) {
                    return false;
                }
                int secondNumStartIndex = getFirstNonZeroIndex(s, startIndex + firstNumSize, s.length());
                if (secondNumStartIndex == -1) {
                    if (firstNumSize == 1 && s.charAt(startIndex) == '1') {
                        return startIndex != s.length() - 1;
                    } else {
                        firstNumSize--;
                        continue;
                    }
                }
                int secondNumSize = Math.min(firstNumSize, s.length() - secondNumStartIndex);
                long secondNum = Long.parseLong(s.substring(secondNumStartIndex, secondNumStartIndex + secondNumSize));
                long firstNum = Long.parseLong(s.substring(startIndex, startIndex + firstNumSize));
                if (firstNum - secondNum == 1) {
                    currentNum = secondNum;
                    currentNumIndex = secondNumStartIndex;
                    currentNumSize = secondNumSize;
                    break;
                } else if (secondNumSize > 1 && firstNum - (secondNum = Long.parseLong(s.substring(secondNumStartIndex, secondNumStartIndex + secondNumSize - 1))) == 1) {
                    currentNum = secondNum;
                    currentNumIndex = secondNumStartIndex;
                    currentNumSize = secondNumSize - 1;
                    break;
                } else {
                    firstNumSize--;
                    if (firstNumSize == 0) {
                        return false;
                    }
                }
            }

            while (true) {
                if (currentNumIndex + currentNumSize == s.length()) {
                    return true;
                }
                int secondNumStartIndex = getFirstNonZeroIndex(s, currentNumIndex + currentNumSize, s.length());
                if (secondNumStartIndex == -1) {
                    if (currentNum == 1) {
                        return true;
                    } else {
                        break;
                    }
                }
                int secondNumSize = Math.min(firstNumSize, s.length() - secondNumStartIndex);
                long secondNum = Long.parseLong(s.substring(secondNumStartIndex, secondNumStartIndex + secondNumSize));
                if (currentNum - secondNum == 1) {
                    currentNum = secondNum;
                    currentNumIndex = secondNumStartIndex;
                    currentNumSize = secondNumSize;
                } else if (secondNumSize > 1 && currentNum - (secondNum = Long.parseLong(s.substring(secondNumStartIndex, secondNumStartIndex + secondNumSize - 1))) == 1) {
                    currentNum = secondNum;
                    currentNumIndex = secondNumStartIndex;
                    currentNumSize = secondNumSize - 1;
                } else {
                    break;
                }
            }
        }
        return false;
    }



    private static int getFirstNonZeroIndex(String s, int fromInclusive, int toExclusive) {
        for (int i = fromInclusive; i < toExclusive; i++) {
            if (s.charAt(i) != '0') {
                return i;
            }
        }
        return -1;
    }


    public FreqStack() {

    }

    public void push(int val) {
        Integer newFreq = valToFrequency.merge(val, 1, Integer::sum);
        if (vals.size() < newFreq) {
            vals.add(new ArrayList<>());
        }
        vals.get(newFreq - 1).add(val);
    }

    public int pop() {
        List<Integer> valsWithMaxFrequency = vals.get(vals.size() - 1);
        Integer valToPop = valsWithMaxFrequency.remove(valsWithMaxFrequency.size() - 1);
        if (valsWithMaxFrequency.isEmpty()) {
            vals.remove(vals.size() - 1);
        }
        valToFrequency.compute(valToPop, ($, freq) -> freq - 1);
        return valToPop;
    }
}
