package com.letcode.practise;

import java.util.ArrayList;
import java.util.List;

/**
 * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 */
public class SolutionTest2 {
    List<Integer> tempList=new ArrayList<Integer>();
    List<List<Integer>> resultList=new ArrayList<List<Integer>>();

    public List<List<Integer>> combinationSum3(int k, int n) {
        int[] intArray=new int[]{1,2,3,4,5,6,7,8,9};
        dfs(intArray,n,k,0);

        return resultList;
    }

    public void dfs(int[] intArray,int target,int count,int beginIndex){
        if(target==0&&tempList.size()==count){
            resultList.add(new ArrayList<Integer>(tempList));
            return;
        }
        if(tempList.size()==count){
            return;
        }
        for(int i=beginIndex;i<intArray.length;i++){
            if(intArray[i]<=target){
                tempList.add(intArray[i]);
                dfs(intArray,target-intArray[i],count,i+1);
                tempList.remove(tempList.size()-1);
            }
        }
    }

    public static void main(String[] args){
        SolutionTest2 solutionTest2=new SolutionTest2();
        solutionTest2.combinationSum3(3,15);
    }
}
