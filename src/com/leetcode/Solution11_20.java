package com.leetcode;

import java.util.*;

/**
 * 第0-100题
 */
public class Solution11_20 {

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
    public List<List<Integer>> fourSumYgx(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }
        //先排序
        Arrays.sort(nums);
        if (nums[0] > 0 || nums[nums.length - 1] < 0) {
            return result;
        }
        //只能少一层循环
        for (int i = 0; i < nums.length; i++) {
            while (i > 0 && i < nums.length && nums[i] == nums[i - 1]) {
                i++;
            }
            //求三数之和
            for (int j = i + 1; j < nums.length; j++) {
                while (j > i + 1 && j < nums.length && nums[j] == nums[j - 1]) {
                    j++;
                }
                int left = j + 1, right = nums.length - 1;
                while (left < right) {
                    while (left > j + 1 && nums[left] == nums[left - 1] && left < right) {
                        left++;
                        continue;
                    }
                    if (left < right) {
                        int sum = nums[i] + nums[j] + nums[left] + nums[right];
                        if (sum < target) {
                            left++;
                        } else if (sum > target) {
                            right--;
                        } else {
                            result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                            left++;
                            right--;
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> quadruplets = new ArrayList<List<Integer>>();
        if (nums == null || nums.length < 4) {
            return quadruplets;
        }
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            if (nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }
            for (int j = i + 1; j < length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                if (nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }
                int left = j + 1, right = length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        quadruplets.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return quadruplets;
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
        Solution11_20 solution = new Solution11_20();
//        int[] nums = {1, 0, -1, 0, -2, 2};//[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]
//        int[] nums = {0,0,0,0}; //0 //{0,0,0,0}
//        int[] nums = {-3, -1, 0, 2, 4, 5}; //0 //[[-3,-1,0,4]]
//        int[] nums = {-2, -1, -1, 1, 1, 2, 2}; //0 //[[-2,-1,1,2],[-1,-1,1,1]]
//        int[] nums = {-3, -1, 0, 2, 4, 5}; //2 //[[-3,-1,2,4]]
//        int[] nums = {-1, 0, 1, 2, -1, -4}; //-1 //[[-4,0,1,2],[-1,-1,0,1]]
//        int[] nums = {-1, -5, -5, -3, 2, 5, 0, 4}; //-7 //[[-5,-5,-1,4],[-5,-3,-1,2],[-5,-3,-1,2]]
//        int[] nums = {-4, 0, -4, 2, 2, 2, -2, -2};//8
        int[] nums = {-1, 2, 2, -5, 0, -1, 4};//3//[[-5,2,2,4],[-1,0,2,2]]
        var result = solution.fourSum(nums, 3);
        System.out.println(result);
    }
}
