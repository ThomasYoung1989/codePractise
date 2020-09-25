package com.letcode.practise;

import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class SolutionTest7 {

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode resultNode=null;
        ListNode currentNode=null;
        boolean flag=true;
        while(flag){
            int minIndex=-1;
            if(lists.length==1){
                return lists[0];
            }
            for(int i=1;i<lists.length;i++){
                int pre;
                if(minIndex!=-1)
                    pre=minIndex;
                else
                    pre=i-1;
                ListNode node=lists[pre];
                ListNode node2=lists[i];
                if(node==null&&node2==null)
                    continue;
                if(node==null&&node2!=null)
                    minIndex=i;
                if(node!=null&&node2==null)
                    minIndex=pre;
                if(node!=null&&node2!=null){
                    if(node.val>=node2.val){
                        minIndex=i;
                    }else{
                        minIndex=pre;
                    }
                }
            }
            if(minIndex==-1)
            {
                //都是空节点，可以返回结果了
                break;
            }
            if(resultNode==null) {
                resultNode = new ListNode(lists[minIndex].val);
                currentNode = resultNode;
            }
            else{
                ListNode node=new ListNode(lists[minIndex].val);
                currentNode.next=node;
                currentNode=node;
            }
            lists[minIndex]=lists[minIndex].next;
        }
        return resultNode;
    }

    HashSet<Integer> set=new HashSet<Integer>();
    Integer temp=null;
    Integer count=1;
    Integer maxCount=1;
    public int[] findMode(TreeNode root) {
        findData(root);
        int[] ints=new int[set.size()];
        Iterator<Integer> integerIterator=  set.iterator();
        int i=0;
        while (integerIterator.hasNext()){
            ints[i++]=integerIterator.next();
        }
        return ints;
    }

    void findData(TreeNode root){
        if(root==null)
            return;
        findData(root.left);
        boolean flag=false;
        if(temp!=null&&temp==root.val){
            count++;
            flag=true;
        }
        temp=root.val;
        if(!flag)
            count=1;
        if (count == maxCount) {
            set.add(temp);
        }else if(count>maxCount){
            set.clear();
            set.add(temp);
            maxCount=count;
        }


        findData(root.right);
    }

    static public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        //后序遍历的最后一个节点肯定是根节点
        if(inorder.length==0){
            return null;
        }
        TreeNode root=new TreeNode(postorder[postorder.length-1]);
        int rootIndex=-1;
        for(int i=0;i<inorder.length;i++){
            if(inorder[i]==root.val){
                rootIndex=i;
                break;
            }
        }
        int[] leftInorder=new int[rootIndex];
        int[] rightInorder=new int[inorder.length-rootIndex-1];
        for(int i=0;i<inorder.length;i++){
            //遍历左子树
            if(i<rootIndex){
                leftInorder[i]=inorder[i];
            }
            if(i>rootIndex){
                rightInorder[i-rootIndex-1]=inorder[i];
            }
        }
        int[] leftPostnorder=new int[rootIndex];
        int[] rightPostorder=new int[inorder.length-rootIndex-1];
        for(int i=0;i<postorder.length-1;i++){
            if(i<rootIndex){
                leftPostnorder[i]=postorder[i];
            }else{
                rightPostorder[i-rootIndex]=postorder[i];
            }
        }

        TreeNode leftNode=buildTree(leftInorder,leftPostnorder);
        TreeNode rightNode=buildTree(rightInorder,rightPostorder);
        root.left=leftNode;
        root.right=rightNode;
        return root;
    }


    public static void main(String[] args) {
//        ListNode listNode=new ListNode(1);
//        ListNode listNode2=new ListNode(2);
//        listNode.next=listNode2;
//
//        ListNode listNode3=new ListNode(4);
//        ListNode listNode4=new ListNode(5);
//        listNode3.next=listNode4;
//
//        ListNode[] lists=new ListNode[]{listNode,listNode3};
//        SolutionTest7 solutionTest7=new SolutionTest7();
//        solutionTest7.mergeKLists(lists);

//        TreeNode treeNode=new TreeNode(1000);
//        SolutionTest7 solutionTest7=new SolutionTest7();
//        solutionTest7.findMode(treeNode);
        SolutionTest7 solutionTest7=new SolutionTest7();
        solutionTest7.buildTree(new int[]{},new int[]{});


    }

}
