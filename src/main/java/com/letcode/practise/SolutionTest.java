package com.letcode.practise;

import java.util.*;

public class SolutionTest {

    List<List<Integer>> list=new ArrayList();
    List<Integer> path=new ArrayList();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(candidates,target,0);
        return list;
    }
    private void dfs(int[] candidates, int target,int index){
        if(target==0){
            list.add(new ArrayList(path));
            return;
        }
        for(int i=index;i<candidates.length;i++){
            if(candidates[i]<=target){
                if(i>index&&candidates[i]==candidates[i-1]){
                    continue;
                }
                path.add(candidates[i]);
                dfs(candidates,target-candidates[i],i+1);
                path.remove(path.size()-1);
            }
        }
    }



    public static void main(String[] args){
        int[] ca=new int[]{10,1,2,7,6,1,5};
        int target=8;
        SolutionTest solutionTest=new SolutionTest();
        solutionTest.combinationSum2(ca,target);

    }
}
