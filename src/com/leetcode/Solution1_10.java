package com.leetcode;

import java.util.*;

/**
 * 第0-10题
 */
public class Solution1_10 {

    /**
     * 1. 两数之和
     * https://leetcode-cn.com/problems/two-sum/
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        //{数组值：索引}
        Map<Integer, Integer> valueMap = new HashMap<>();
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            //求出差值value，在map中查找value
            int value = target - nums[i];
            if (valueMap.containsKey(value)) {
                //存在的话，取出索引，返回数组
                result[0] = valueMap.get(value);
                result[1] = i;
            } else {
                //不存在的话，将数组值和索引放入map中
                valueMap.put(nums[i], i);
            }
        }
        return result;
    }

    /**
     * 2. 两数相加
     * https://leetcode-cn.com/problems/add-two-numbers/
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode t = dummy, t1 = l1, t2 = l2;
        int carry = 0;
        while (t1 != null || t2 != null) {
            //遍历，相加，求进位
            int val = (t1 == null ? 0 : t1.val) + (t2 == null ? 0 : t2.val) + carry;
            if (val >= 10) {
                val -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            t.next = new ListNode(val);
            t = t.next;
            if (t1 != null) {
                t1 = t1.next;
            }
            if (t2 != null) {
                t2 = t2.next;
            }
        }
        //有进位
        if (carry != 0) {
            t.next = new ListNode(carry);
        }
        return dummy.next;
    }

    /**
     * 3. 无重复字符的最长子串
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 解法1，未优化
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        //最大长度，双指针
        int max = 0, left = 0, right = 0;
        //存放字符和索引
        Map<Character, Integer> charMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            //查找字符对应的索引
            Integer index = charMap.get(c);
            if (index != null) {
                //字符存在的话，重复
                //将重复字符和之前的字符，全部移除
                for (int j = left; j <= index; j++) {
                    charMap.remove(s.charAt(j));
                }
                left = index + 1;
            }
            //将字符和索引缓存到map中
            charMap.put(c, i);
            right++;
            max = Math.max(max, right - left);
        }
        return max;
    }

    /**
     * 3. 无重复字符的最长子串
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 解法2，在解法1基础上进行了优化
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        //最大长度，双指针
        int max = 0, left = 0;
        //存放字符和索引
        Map<Character, Integer> charMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            //查找字符对应的索引
            Integer index = charMap.get(c);
            //left是无重复子串的开始，前面的字符已经被过滤掉
            if (index != null && index >= left) {
                //字符存在的话，从字符下一个索引，开始重新计算
                left = index + 1;
            }
            //将字符和索引缓存到map中
            charMap.put(c, i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
     * 时间复杂度 log(m+n)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length, k = length / 2;
        if (length % 2 == 0) {
            //偶数，中位数是第k,k+1个元素
            return (binarySearchK(nums1, nums2, k) + binarySearchK(nums1, nums2, k + 1)) / 2.0;
        } else {
            //奇数，中位数是第k+1个
            return binarySearchK(nums1, nums2, k + 1);
        }
    }

    //二分查找k大元素
    private int binarySearchK(int[] nums1, int[] nums2, int k) {
        //对比nums1[k/2-1]，nums2[k/2-1]，丢弃小的部分
        //(k/2-1)*2 = k-2,小于k
        int index1 = 0, index2 = 0;
        for (; ; ) {
            if (index1 >= nums1.length) {
                //超过了nums1的长度
                return nums2[index2 + k - 1];
            }
            if (index2 >= nums2.length) {
                //超过了num2的长度
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                //剩余最后一个，取两个元素的较小值
                return Math.min(nums1[index1], nums2[index2]);
            }
            //特殊情况，如果超过了数组长度，取数组最后一位
            int mid = k / 2;
            int newIndex1 = Math.min(index1 + mid, nums1.length) - 1;
            int newIndex2 = Math.min(index2 + mid, nums2.length) - 1;
            if (nums1[newIndex1] < nums2[newIndex2]) {
                //丢弃元素
                k -= (newIndex1 - index1 + 1);
                //调整索引位置
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

    /**
     * 5. 最长回文子串
     * https://leetcode-cn.com/problems/longest-palindromic-substring/
     * 解法1，未优化
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null) {
            return null;
        }
        if (s.isEmpty() || s.length() == 1) {
            return s;
        }
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            String odd = palindrome(s, i, true);
            String even = palindrome(s, i, false);
            String r = odd.length() > even.length() ? odd : even;
            if (r.length() > result.length()) {
                result = r;
            }
        }
        return result;
    }

    //取出回文串，
    private String palindrome(String s, int index, boolean odd) {
        //奇数回文串
        int left = index, right = index + 1;
        String str = "";
        if (odd) {
            //偶数回文串
            left = index - 1;
            str = s.charAt(index) + "";
        }
        //当前字符前后遍历，获取回文串
        while (left >= 0 && right < s.length()) {
            char cl = s.charAt(left);
            char cr = s.charAt(right);
            if (cl != cr) {
                //不是回文串，立即退出循环
                break;
            }
            str = cl + str + cr;
            left--;
            right++;
        }
        return str;
    }

    /**
     * 6. Z 字形变换
     * https://leetcode-cn.com/problems/zigzag-conversion/
     * 未优化
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert1(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        char[] chars = s.toCharArray();
        //行数固定，列数多出来一些
        char[][] rowChars = new char[numRows][s.length()];
        int index = 0;
        //先遍历列，保存数据
        //i行索引，j列索引
        for (int j = 0; j < s.length(); j++) {
            //每列第一个数字和列数的关系
            if (2 * j == index) {
                //整列都有数据
                for (int i = 0; i < rowChars.length; i++) {
                    if (index >= s.length()) {
                        break;
                    }
                    rowChars[i][j] = chars[index++];
                }
            } else {
                //右上数据
                for (int i = numRows - 2; i > 0; i--) {
                    if (index >= s.length()) {
                        break;
                    }
                    rowChars[i][j++] = chars[index++];
                }
                j--;
            }
        }

        //从左往右，从上往下读取，保存到String中
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowChars.length; i++) {
            for (int j = 0; j < rowChars[i].length; j++) {
                if (rowChars[i][j] != 0) {
                    sb.append(rowChars[i][j]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 6. Z 字形变换
     * https://leetcode-cn.com/problems/zigzag-conversion/
     * 优化方案
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if (numRows < 2 || numRows >= s.length()) {
            return s;
        }
        //每行一个StringBuilder
        List<StringBuilder> rowSbs = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            rowSbs.add(new StringBuilder());
        }
        //当前行号
        int curRow = 0;
        //标记，行索引往下移动还是往上走
        boolean down = false;
        for (char c : s.toCharArray()) {
            //获取行对应的StringBuilder，添加字符
            rowSbs.get(curRow).append(c);
            if (curRow == 0 || curRow == (numRows - 1)) {
                //如果当前行是首行或者尾行，那么更换标记
                down = !down;
            }
            //行索引上移或者下移
            curRow += down ? 1 : -1;
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowSbs) {
            result.append(sb);
        }
        return result.toString();
    }

    /**
     * 7. 整数反转
     * https://leetcode-cn.com/problems/reverse-integer/
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        if (x == 0) {
            return 0;
        }
        long result = 0L;
        while (x != 0) {
            result = result * 10 + x % 10;
            x /= 10;
        }
        if (x < 0) {
            result = 0 - result;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) result;
    }

    /**
     * 8. 字符串转换整数 (atoi)
     * https://leetcode-cn.com/problems/string-to-integer-atoi/
     *
     * @param str
     * @return
     */
    public int myAtoi(String str) {
        //空字符串，返回0
        if (str == null || str.isEmpty()) {
            return 0;
        }
        //long类型会溢出，所以使用double
        double result = 0;
        //记录first
        Character first = null;
        for (char c : str.toCharArray()) {
            if (first == null) {
                //判断第一个字符
                if (c >= '0' && c <= '9') {
                    //第一个字符在0-9之间
                    first = c;
                    result = c - '0';
                } else if (c == '+' || c == '-') {
                    //第一个字符是+-号
                    first = c;
                } else if (c != ' ') {
                    //字符不为空
                    return 0;
                }
                //字符为空，继续遍历
            } else {
                if (c < '0' || c > '9') {
                    //第一个字符不为空，当前字符不在0-9之间，直接跳出循环
                    break;
                }
                result = result * 10 + c - '0';
            }
        }
        //首字符没有找到，直接返回0
        if (first == null) {
            return 0;
        }
        //首字符是符号，那么直接返回负数
        if (first == '-') {
            result = 0 - result;
        }
        if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) result;
    }


    /**
     * 9. 回文数
     * https://leetcode-cn.com/problems/palindrome-number/
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int val = 0, temp = x;
        while (temp != 0) {
            val = val * 10 + temp % 10;
            temp /= 10;

        }
        return val == x;
    }

    /**
     * 10. 正则表达式匹配
     * https://leetcode-cn.com/problems/regular-expression-matching/
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        if (p == null || p.isEmpty()) {
            return s == null || s.isEmpty();
        }
        if (p.charAt(0) == '*') {
            return false;
        }
        int m = s.length();
        int n = p.length();

        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    f[i][j] = f[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
            }
        }
        return f[m][n];
    }

    public boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    public static void main(String[] args) {

    }
}
