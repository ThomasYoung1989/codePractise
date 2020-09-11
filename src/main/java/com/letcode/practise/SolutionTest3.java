package com.letcode.practise;

import java.util.*;

/**
 * 给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用
 *输入:
 * words = ["oath","pea","eat","rain"] and board =
 * [
 *   ['o','a','a','n'],
 *   ['h','t','a','e'],
 *   ['i','h','k','r'],
 *   ['i','f','l','v']
 * ]
 *
 * 输出: ["eat","oath"]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-search-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *
 * 传统写法 性能较差 初学者的第一反应
 */
public class SolutionTest3 {

    Set<String> resultSet=new HashSet<String>();

    public List<String> findWords(char[][] board, String[] words) {
        for(String word:words) {
            if(word==null||word.length()==0){
                //空字符串不处理
                continue;
            }
            char targetChar=word.charAt(0);
            List<int[]> indexList=findChar(board,targetChar);
            if(indexList.size()==0){
                //首字母都找不到，不处理
                continue;
            }
            //从第二个字符开始找邻居
            int charIndex=1;
            for(int[] indexArray:indexList){
                //将自己的坐标放进历史坐标中
                LinkedList<int[]> historyIndex=new LinkedList<int[]>();
                historyIndex.add(indexArray);
                findNeighbor(board,indexArray[0],indexArray[1],word,charIndex,historyIndex);
            }
        }
        return new ArrayList<String>(resultSet);
    }

    /**
     *
     * @param
     * @param i
     * @param j
     * @param historyIndex 历史已走过的路径，需要排除
     * @return
     */
    void findNeighbor(char[][] board, int i, int j, String word, int charIndex, LinkedList<int[]> historyIndex){
        //direction 方向 1：上 2：下 3：左 4：右
        if(charIndex>word.length()-1){
            resultSet.add(word);
            return;
        }
        for(int direction=1;direction<=4;direction++){
            char target=word.charAt(charIndex);
            int iIdx=0,jIdx=0;
            if(direction==1){
                iIdx=i-1;
                jIdx=j;
            }
            if(direction==2){
                iIdx=i+1;
                jIdx=j;
            }
            if(direction==3){
                iIdx=i;
                jIdx=j-1;
            }
            if(direction==4){
                iIdx=i;
                jIdx=j+1;
            }
            if(!checkRange(iIdx,jIdx,board)){
                continue;
            }
            if(board[iIdx][jIdx]==target){
                int[] indexArray=new int[]{iIdx,jIdx};
                if(!contains(historyIndex,indexArray)){
                    historyIndex.add(new int[]{iIdx,jIdx});
                    findNeighbor(board,iIdx,jIdx,word,charIndex+1,historyIndex);
                    historyIndex.removeLast();
                }
            }
        }
    }

    boolean contains(List<int[]> historyIndex,int[] indexArray){
        for(int[] index:historyIndex){
            if(index[0]==indexArray[0]&&index[1]==indexArray[1]){
                return true;
            }
        }
        return false;
    }

    boolean checkRange(int iIdx,int jIdx,char[][] board){
        if(iIdx>=0&&iIdx<board.length){
            if(jIdx>=0&&jIdx<board[0].length){
                return true;
            }
        }
        return false;
    }

    List<int[]> findChar(char[][] board,char targetChar){
        List<int[]> indexList=new ArrayList<int[]>();
        for(int i=0;i<board.length;i++ ){
            char[] charLevel1=board[i];
            for(int j=0;j<charLevel1.length;j++){
                char charStr=charLevel1[j];
                if(targetChar==charStr){
                    int[] indexArray=new int[]{i,j};
                    indexList.add(indexArray);
                }
            }
        }
        return indexList;
    }

    public static void main(String[] args){
        char[][] source=new char[][]{
                {'b','a','a','b','a','b'},
                {'a','b','a','a','a','a'},
                {'a','b','a','a','a','b'},
                {'a','b','a','b','b','a'},
                {'a','a','b','b','a','b'},
                {'a','a','b','b','b','a'},
                {'a','a','b','a','a','b'}
        };
        String[] strings=new String[]{"aabbbbabbaababaaaabababbaaba","abaabbbaaaaababbbaaaaabbbaab","ababaababaaabbabbaabbaabbaba"};

        SolutionTest3 solutionTest3=new SolutionTest3();
        solutionTest3.findWords(source,strings);
    }
}
