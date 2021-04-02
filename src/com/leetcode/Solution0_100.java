package com.leetcode;

import java.util.*;

/**
 * 第0-100题
 */
public class Solution0_100 {

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

    /**
     * 11. 盛最多水的容器
     * https://leetcode-cn.com/problems/container-with-most-water/
     *
     * @param height
     * @return maxArea
     * [1, 8, 6, 2, 5, 4, 8, 3, 7]
     * 49
     */
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, max = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                max = Math.max(height[left] * (right - left), max);
                left++;
            } else {
                max = Math.max(height[right] * (right - left), max);
                right--;
            }
        }
        return max;
    }


    static Map<Integer, String> numMap = new HashMap<>();

    static {
        numMap.put(1, "I");
        numMap.put(4, "IV");
        numMap.put(5, "V");
        numMap.put(9, "IX");
        numMap.put(10, "X");
        numMap.put(40, "XL");
        numMap.put(50, "L");
        numMap.put(90, "XC");
        numMap.put(100, "C");
        numMap.put(400, "CD");
        numMap.put(500, "D");
        numMap.put(900, "CM");
        numMap.put(1000, "M");
    }

    /**
     * 12. 整数转罗马数字
     * https://leetcode-cn.com/problems/integer-to-roman/
     * 自己方案
     *
     * @param num
     * @return
     */
    public String intToRoman2(int num) {
        //k当前最后一位的原大小
        int k = 1, temp = num, mod = 0;
        String result = "";
        while (temp > 0) {
            mod = temp % 10;
            int c = mod > 5 ? mod - 5 : mod;
            //从map中获取数字对应的字符串
            String s = numMap.get(mod * k);
            if (s == null) {
                //mod为2，3或者6，7，8
                for (int i = 1; i < c + 1; i++) {
                    result = numMap.get(k) + result;
                }
                if (mod > 5) {
                    result = numMap.get(5 * k) + result;
                }
            } else {
                result = s + result;
            }
            //下一位
            temp /= 10;
            k *= 10;
        }
        return result;
    }

    static int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    static String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    /**
     * 12. 整数转罗马数字
     * https://leetcode-cn.com/problems/integer-to-roman/
     * 参考方案
     *
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 13. 罗马数字转整数
     * https://leetcode-cn.com/problems/roman-to-integer/
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     *
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int result = 0, index = 0;
        for (int i = 0; i < symbols.length; i++) {
            while (s.startsWith(symbols[i], index)) {
                result += values[i];
                index += symbols[i].length();
            }
        }
        return result;
    }

    /**
     * 14. 最长公共前缀
     * https://leetcode-cn.com/problems/longest-common-prefix/
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String result = strs[0];
        //先比较第一个和第二个字符，取出这两个字符的公共字符
        //然后用公共字符往后匹配
        for (int i = 1; i < strs.length; i++) {
            result = stringPrefix(result, strs[i]);
            if (result.isEmpty()) {
                return "";
            }
        }
        return result;
    }

    private String stringPrefix(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            //超过s2长度或者字符不匹配，那么返回
            if (i >= s2.length() || s1.charAt(i) != s2.charAt(i)) {
                break;
            }
            sb.append(s1.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 15. 三数之和
     * https://leetcode-cn.com/problems/3sum/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        //排序
        Arrays.sort(nums);
        if (nums[0] > 0 || nums[nums.length - 1] < 0) {
            return result;
        }
        //双指针j,k，外部两次循环
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int value = 0 - nums[i] - nums[j], k = nums.length - 1;
                while (j < k && nums[k] > value) {
                    k--;
                }
                if (j == k) {
                    break;
                }
                if (nums[k] == value) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                }
            }
        }
        return result;
    }

    /**
     * 15. 三数之和
     * https://leetcode-cn.com/problems/3sum/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;
        if (nums == null || length < 3) {
            return result;
        }
        //排序
        Arrays.sort(nums);
        if (nums[0] > 0 || nums[length - 1] < 0) {
            return result;
        }
        //双指针left,right
        for (int i = 0; i < length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                //跳过已经处理的数字
                continue;
            }
            int left = i + 1, right = length - 1;
            //确保right在右侧，不会重复处理数据
            while (left < right) {
                if (left > i + 1 && nums[left] == nums[left - 1]) {
                    //跳过已经处理的数字
                    left++;
                    continue;
                }
                if (nums[i] + nums[left] > 0) {
                    //nums[right]必然大于0，所以退出循环
                    break;
                }
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    //和大了，那么right往右移，变小
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    //符合要求，那么同时左右移，防止重复
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                }
            }
        }
        return result;
    }

    /**
     * 16. 最接近的三数之和
     * https://leetcode-cn.com/problems/3sum-closest/
     * 输入：nums = [-1,2,1,-4], target = 1
     * 输出：2
     * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int length = nums.length, result = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < length; i++) {
            int left = i + 1, right = length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    return target;
                }
                if (Math.abs(target - result) > Math.abs(target - sum)) {
                    result = sum;
                }
            }
        }
        return result;
    }


    Map<Character, String> map = new HashMap<>();

    {
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
    }

    /**
     * 17. 电话号码的字母组合
     * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations2(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return result;
        }

        //取出第一个字符串的所有字符
        String s = map.get(digits.charAt(0));
        for (int i = 0; i < s.length(); i++) {
            result.add(s.charAt(i) + "");
        }
        //两两操作
        for (int i = 1; i < digits.length(); i++) {
            result = strCombination2(result, map.get(digits.charAt(i)));
        }
        return result;
    }

    private List<String> strCombination2(List<String> strs, String str) {
        List<String> ss = new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            for (int j = 0; j < str.length(); j++) {
                ss.add(strs.get(i) + str.charAt(j));
            }
        }
        return ss;
    }

    String[] phoneLetters = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    List<String> result = new ArrayList<>();
    StringBuilder sb = new StringBuilder();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return result;
        }
        backtrack(digits, 0);
        return result;
    }

    private void backtrack(String digits, int depth) {
        if (depth == digits.length()) {
            result.add(sb.toString());
        } else {
            char c = digits.charAt(depth);
            String letters = phoneLetters[c - '2'];
            int count = letters.length();
            for (int i = 0; i < count; i++) {
                sb.append(letters.charAt(i));
                backtrack(digits, depth + 1);
                sb.deleteCharAt(depth);
            }
        }
    }


    /**
     * 18. 四数之和
     * https://leetcode-cn.com/problems/4sum/
     * 答案中不可以包含重复的四元组。
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }
        //先排序
        Arrays.sort(nums);
        //三层循环，双指针
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int k = j + 1, l = nums.length - 1, value = target - nums[i] - nums[j] - nums[k];
                if (k > j + 1 && nums[k] == nums[k - 1]) {
                    //调过重复数字
                    k++;
                    continue;
                }
                while (l > k) {
                    if (value > nums[l]) {
                        l--;
                    } else if (value < nums[l]) {
                        k++;
                    } else {
                        result.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                    }
                }
            }
        }
        return result;
    }

    /**
     * 19. 删除链表的倒数第N个节点
     * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int length = 0;
        ListNode temp = head;
        while (temp.next != null) {
            length++;
            temp = temp.next;
        }
        //需要判断删除的节点是否是尾结点
        //
        temp = head;
        for (int i = 0; i < length - n; i++) {
            temp = temp.next;
        }

        return null;
    }

    public static void main(String[] args) {
        Solution0_100 solution = new Solution0_100();
        int[] nums = {1, 0, -1, 0, -2, 2};
        var result = solution.fourSum(nums, 0);
        System.out.println(result);
    }
}
