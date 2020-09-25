package com.letcode.practise;

import java.util.*;

/**
 * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 *
 *  
 *
 * 例如：
 *
 * 输入: 原始二叉搜索树:
 *               5
 *             /   \
 *            2     13
 *
 * 输出: 转换为累加树:
 *              18
 *             /   \
 *           20     13
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-bst-to-greater-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SolutionTest6 {

    int num=0;

    //后序遍历，相当于从大到小遍历一遍节点，并将上个累加值记录，遍历到新节点不断累加即可
    public TreeNode convertBST(TreeNode root) {
        if(root==null){
            return null;
        }
        convertBST(root.right);
        root.val=root.val+num;
        num=root.val;
        convertBST(root.left);
        return root;
    }

    static public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }

    static class Solution {
        int num=0;
        public int removeDuplicates(int[] nums) {
            if(nums==null){
                return 0;
            }
            for(int i=0;i<nums.length;i++){
                if(i+1<nums.length&&nums[i+1]!=Integer.MAX_VALUE){
                    while(nums[i]==nums[i+1]){
                        //数组左移一位
                        num++;
                        transfer(nums,i+1);
                    }
                }
            }
            return nums.length-num;
        }
        void transfer(int[] nums,int begin){
            for(int i=begin;i<nums.length;i++){
                if(i+1<nums.length)
                    nums[i]=nums[i+1];
                else nums[i]=Integer.MAX_VALUE;
            }
        }

//        public int maxProfit(int[] prices) {
//            int profit = 0;
//            for (int i = 1; i < prices.length; i++) {
//                int tmp = prices[i] - prices[i - 1];
//                if (tmp > 0) profit += tmp;
//            }
//            return profit;
//        }

        int maxProfit=0;
//        public int maxProfit(int[] prices) {
//            for(int i=0;i<prices.length;i++){
//                int min=prices[i];
//                int max=0;
//                int j=i+1;
//                int current;
//                while(j<prices.length){
//                    if(prices[j]>min&&prices[j]>max){
//                        max=prices[j];
//                    }
//                    j++;
//                }
//                current=max-min;
//                if(current>maxProfit)
//                    maxProfit=current;
//            }
//            return maxProfit;
//        }

        public int maxProfit(int[] prices) {
            int buy1 = Integer.MAX_VALUE;
            int buy2 = Integer.MAX_VALUE;
            int pro1 = 0;
            int pro2 = 0;
            for(int price : prices) {
                buy1 = Math.min(buy1, price);
                pro1 = Math.max(pro1, price - buy1);
                buy2 = Math.min(buy2, price - pro1);
                pro2 = Math.max(pro2, price - buy2);
            }
            return pro2;
        }

        public void moveZeroes(int[] nums) {
            int cur=0;
            for(int i=0;i<nums.length;i++){
                if(nums[i]!=0){
                    nums[cur]=nums[i];
                    if(cur!=i)
                        nums[i]=0;
                    cur++;
                }
            }
        }

        int[] result=new int[2];

        public int[] twoSum(int[] nums, int target) {
            return getResult(nums,0,target);
        }

        private int[] getResult(int[] nums, int begin, int target) {
            if(begin>=nums.length)
                return result;
            for(int i=begin+1;i<nums.length;i++){
                if(nums[begin]+nums[i]==target){
                    result[0]=begin;
                    result[1]=i;
                    return result;
                }
            }
            return getResult(nums,begin+1,target);
        }
    }

    public int firstUniqChar(String s) {
        Map<Character,Integer> map=new LinkedHashMap<Character, Integer>();

        int index=-1;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(!map.containsKey(c)){
                map.put(c,i);
            }else{
                map.put(c,-1);
            }
        }
        for(Map.Entry<Character,Integer> entry:map.entrySet()){
            if(entry.getValue()!=-1){
                return entry.getValue();
            }
        }
        return index;
    }


    public boolean isPalindrome(String s) {
        s=s.toLowerCase();
        int j=s.length()-1;
        boolean result=true;
        for(int i=0;i<s.length();i++){
            char a=s.charAt(i);
            if(!((48<=a&&a<=57)||(97<=a&&a<=122))){
                //如果不是字符或者是数字，则跳过
                continue;
            }
            char b=s.charAt(j);
            while(!((48<=b&&b<=57)||(97<=b&&b<=122))){
                //如果不是字符或者是数字，则跳过
                j--;
                b=s.charAt(j);
            }
            if(i>=j){
                break;
            }
            if(a!=b)
                return false;
            j--;
        }
        return result;
    }

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1!=null&&t2!=null){
            t1.val=t1.val+t2.val;
        }else if(t1==null&&t2!=null){
            return t2;
        }else if(t1==null&&t2==null){
            return null;
        }else{
            return t1;
        }

        TreeNode left=mergeTrees(t1.left,t2.left);
        if(left!=null)
            t1.left=left;

        TreeNode right=mergeTrees(t1.right,t2.right);
        if(right!=null)
            t1.right=right;
        return t1;
    }


    List<List<Integer>> list=new LinkedList<List<Integer>>();
    public boolean isSymmetric(TreeNode root) {
        int deep=1;
        if(root==null)
            return false;
       setData(root,deep);
       for(List<Integer> data:list){
           if(!checkBalance(data)){
               return false;
           }
       }
       return true;
    }

    boolean checkBalance(List<Integer> data){
        for(int i=0;i<data.size()/2;i++){
            if(data.get(i)!=data.get(data.size()-i-1)){
                return false;
            }
        }
        return true;
    }

    void setData(TreeNode root,int deep){
        if(root==null)
            return;
        if(list.size()!=deep){
            List<Integer> listData=new LinkedList<Integer>();
            list.add(listData);
        }
        list.get(deep-1).add(root.left!=null?root.left.val:null);
        list.get(deep-1).add(root.right!=null?root.right.val:null);
        setData(root.left,deep+1);
        setData(root.right,deep+1);
    }
    static public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int temp=0;
        return calc(l1,l2,temp);
    }

    ListNode calc(ListNode l1,ListNode l2,int up){
        if(l1==null&&l2==null&&up==0){
          return null;
        }
        int val=(l1!=null?l1.val:0)+(l2!=null?l2.val:0)+up;
        if(val-10>=0){
            up=1;
        }else{
            up=0;
        }
        ListNode node=new ListNode(up==1?val-10:val);
        node.next=calc(l1!=null?l1.next:null,l2!=null?l2.next:null,up);
        return node;
    }




    public static void main(String[] args){
        SolutionTest6 solution=new SolutionTest6();
        ListNode treeNode=new ListNode(0);
        ListNode treeNode1=new ListNode(1);
        ListNode treeNode2=new ListNode(0);
        ListNode treeNode3=new ListNode(5);
        ListNode treeNode4=new ListNode(6);
        ListNode treeNode5=new ListNode(4);
//        treeNode.next=treeNode1;

        //1223 44
        //3221+44
        //5623 3265

        System.out.print( solution.addTwoNumbers(treeNode,treeNode2));
    }
}
