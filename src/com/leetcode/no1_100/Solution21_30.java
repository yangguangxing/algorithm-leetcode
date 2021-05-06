package com.leetcode.no1_100;

import com.leetcode.ListNode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        ListNode dummy = new ListNode(0), temp = dummy;
        ListNode temp1 = l1, temp2 = l2;
        while (temp1 != null || temp2 != null) {
            if (temp1 == null) {
                // 如果temp1为null，temp2直接排序
                temp.next = new ListNode(temp2.val);
                temp2 = temp2.next;
            } else if (temp2 == null) {
                // 如果temp2为null，temp1直接排序
                temp.next = new ListNode(temp1.val);
                temp1 = temp1.next;
            } else if (temp1.val < temp2.val) {
                // 两个都不为null
                temp.next = new ListNode(temp1.val);
                temp1 = temp1.next;
            } else {
                temp.next = new ListNode(temp2.val);
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        return dummy.next;
    }

    /**
     * 22. 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"] 输入：n = 1 输出：["()"]
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new LinkedList<>();
        parenthesis(0, 0, n, result, "");
        return result;
    }

    private void parenthesis(int left, int right, int count, List<String> result, String str) {
        if (left > count || right > count || right > left) {
            return;
        }
        if (left == count && right == count) {
            result.add(str);
        }
        parenthesis(left + 1, right, count, result, str + "(");
        parenthesis(left, right + 1, count, result, str + ")");
    }

    /**
     * 23. 合并K个升序链表
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null) {
            return null;
        }
        ListNode result = null;
        int length = lists.length;
        return result;
    }

    public static void main(String[] args) {
        Solution21_30 solution = new Solution21_30();
        var result = solution.mergeKLists(null);
        System.out.println(result);
    }
}
