package com.letcode.practise;

import java.util.*;

/**
 * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 输出：[3, 14.5, 11]
 * 解释：
 * 第 0 层的平均值是 3 ,  第1层是 14.5 , 第2层是 11 。因此返回 [3, 14.5, 11] 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/average-of-levels-in-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class SolutionTest4{

    private int deep=0;

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> resultList=new LinkedList<Double>();
        Map<Integer,AverageInfo> map=new TreeMap<Integer, AverageInfo>();
        if (root != null) {
            updateInfo(map,root);
        }
        for(Map.Entry<Integer,AverageInfo> entry:map.entrySet()){
            int count=entry.getValue().getCount();
            Long sum=entry.getValue().getSum();

            resultList.add(sum.doubleValue()/count);
        }
        return resultList;
    }

    public void updateInfo(Map<Integer,AverageInfo> map,TreeNode node){
        AverageInfo averageInfo=null;
        if((averageInfo=map.get(deep))==null){
            averageInfo=new AverageInfo();
            averageInfo.setCount(1);
            averageInfo.setSum(Long.valueOf(node.val));
            map.put(deep,averageInfo);
        }else{
            averageInfo.setCount(averageInfo.getCount()+1);
            averageInfo.setSum(averageInfo.getSum()+node.val);
        }
        if(node.left!=null){
            deep++;
            updateInfo(map,node.left);
            deep--;
        }
        if(node.right!=null){
            deep++;
            updateInfo(map,node.right);
            deep--;
        }
    }

    static class AverageInfo{
        Long sum;
        int count;


        public Long getSum() {
            return sum;
        }

        public void setSum(Long sum) {
            this.sum = sum;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }

    }

    public static void main(String[] args){
        TreeNode treeNode=new TreeNode(15);

        SolutionTest4 solutionTest4=new SolutionTest4();
    }

    public List<Double> averageOfLevelsGood(TreeNode root) {
        List<Double> res = new ArrayList();
        if (root == null) return res;
        Queue<TreeNode> list = new LinkedList();
        list.add(root);
        while (list.size() != 0){
            int len = list.size();
            double sum = 0;
            for (int i = 0; i < len; i++){
                TreeNode node = list.poll();
                sum += node.val;
                if (node.left != null) list.add(node.left);
                if (node.right != null) list.add(node.right);
            }
            res.add(sum/len);
        }
        return res;
    }
}
