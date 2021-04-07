package com.leetcode;

public class ListNode {

    public int val;
    public ListNode next;


    public static ListNode init(int... vals) {
        ListNode node = new ListNode(-1);
        ListNode tmp = node;
        for (int i = 0; i < vals.length; i++) {
            tmp.next = new ListNode(vals[i]);
            tmp = tmp.next;
        }
        return node.next;
    }

    public ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ListNode node = this;
        while (node != null) {
            sb.append(node.val).append(",");
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
