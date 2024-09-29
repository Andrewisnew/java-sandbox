package edu.andrewisnew.java.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SegmentTree {


}

class SegmentTreeNode {
    Map<Integer, Integer> valueToCounter = new HashMap<>();
    SegmentTreeNode parent;
    SegmentTreeNode left;
    SegmentTreeNode right;
    int leafsCount;

    boolean isLeftChild() {
        return parent != null && this == parent.left;
    }
}

class RangeFreqQuery {
    Map<Integer, Map<Integer, Integer>> valueToIndexToCount = new HashMap<>();
    int[] arr;
    List<SegmentTreeNode> leafs = new ArrayList<>();

    public RangeFreqQuery(int[] arr) {
        buildTree(arr, 0, arr.length - 1, null);
    }

    private SegmentTreeNode buildTree(int[] arr, int left, int right, SegmentTreeNode parent) {
        SegmentTreeNode segmentTreeNode = new SegmentTreeNode();
        segmentTreeNode.parent = parent;
        if (left == right) {
            segmentTreeNode.valueToCounter.put(arr[left], 1);
            leafs.add(segmentTreeNode);
            segmentTreeNode.leafsCount = 1;
            return segmentTreeNode;
        }
        int mid = (right + left) / 2;
        SegmentTreeNode leftNode = buildTree(arr, left, mid, segmentTreeNode);
        SegmentTreeNode rightNode = buildTree(arr, mid + 1, right, segmentTreeNode);
        segmentTreeNode.leafsCount = leftNode.leafsCount + rightNode.leafsCount;

        segmentTreeNode.left = leftNode;
        segmentTreeNode.right = rightNode;
        Map<Integer, Integer> valueToCounter = Stream.concat(leftNode.valueToCounter.entrySet().stream(), rightNode.valueToCounter.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
        segmentTreeNode.valueToCounter = valueToCounter;
        return segmentTreeNode;
    }

    public int query(int left, int right, int value) {
        int count = 0;
        while (left <= right) {
            SegmentTreeNode segmentTreeNode = leafs.get(left);
            if (left == right) {
                count += segmentTreeNode.valueToCounter.getOrDefault(value, 0);
                break;
            }
            while (segmentTreeNode.isLeftChild() && segmentTreeNode.parent != null && left + segmentTreeNode.parent.leafsCount < right) {
                segmentTreeNode = segmentTreeNode.parent;
            }
            count += segmentTreeNode.valueToCounter.getOrDefault(value, 0);
            left += segmentTreeNode.leafsCount;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(1 >> 2);
        RangeFreqQuery rangeFreqQuery = new RangeFreqQuery(new int[] {4,2,9,7,5, 5,1, 9, 8,6});
        rangeFreqQuery.query(0, 7, 8);
    }
}
