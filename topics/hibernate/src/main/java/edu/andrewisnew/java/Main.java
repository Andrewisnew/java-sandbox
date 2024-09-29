package edu.andrewisnew.java;

import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        removeDuplicates(new int[] {0,0,1,1,1,2,2,3,3,4});
    }

    public static int removeDuplicates(int[] nums) {
        int k = 1;
        int size = nums.length;
        for (int i = 1; i < size;) {
            if (nums[i] != nums[i-1]) {
                k++;
                i++;
            } else {
                //i - first to update
                int j = i + 1;
                for(; j < size; j++) {
                    if (nums[j] != nums[j-1]) {
                        break;
                    }
                }
                size -= j - i;
                //j - last exclucive
                for (int m = i; m < size; m++) {
                    nums[m] = nums[j + m - i];
                }
                i = j + 1;
                k++;
            }
        }
        return k;
    }
}