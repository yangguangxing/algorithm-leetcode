package com.leetcode.no1_100;

import com.leetcode.ListNode;

public class Solution21_30 {

    /**
     * 21. 合并两个有序链表 https://leetcode-cn.com/problems/merge-two-sorted-lists/
     * 
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 输入：l1 = [1,2,4], l2 = [1,3,4],输出：[1,1,2,3,4,4]
        ListNode dummy = new ListNode(0);
        
        return dummy.next;
    }

    public static void main(String[] args) {
        Solution21_30 solution = new Solution21_30();
        ListNode l1 = ListNode.init(1, 2, 4);
        ListNode l2 = ListNode.init(1, 3, 4);
        var result = solution.mergeTwoLists(l1, l2);
        System.out.println(result);
    }
}
