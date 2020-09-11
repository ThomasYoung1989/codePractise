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

    public List<String> findWordsOld(char[][] board, String[] words) {
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

    public List<String> findWords(char[][] board, String[] words) {
        //构建字典树
        WordTrie myTrie = new WordTrie();
        TrieNode root = myTrie.root;
        //插入数据
        for (String word : words){
            myTrie.insert(word);
        }

        //构建结果集容器
        List<String> result = new LinkedList();
        //矩阵行数
        int m = board.length;
        //矩阵列数
        int n = board[0].length;
        //存储该节点是否访问
        boolean[][] visited = new boolean[n][m];
        //遍历整个二维数组
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                find(board, visited, i, j, m, n, result, root);
            }
        }
        return result;
    }

    private void find(char[][] board, boolean[][] visited, int i, int j, int m, int n, List<String> result, TrieNode cur) {
        //边界判断以及是否已经访问判断
        if (i < 0 || i >= m || j < 0 || j >= n || visited[j][i])
            return;
        //获取子节点状态，判断其是否有子节点
        cur = cur.child[board[i][j] - 'a'];
        if (cur == null) {
            return;
        }
        //修改节点状态，防止重复访问
        visited[j][i] = true;
        //找到单词加入
        if (cur.isEnd) {
            result.add(cur.val);
            //找到单词后，修改字典树内叶子节点状态为false，防止出现重复单词
            cur.isEnd = false;
        }
        find(board, visited, i + 1, j, m, n, result, cur);
        find(board, visited, i - 1, j, m, n, result, cur);
        find(board, visited, i, j + 1, m, n, result, cur);
        find(board, visited, i, j - 1, m, n, result, cur);
        //最后修改节点状态为未访问状态
        visited[j][i] = false;
    }


    /**
     * 字典树
     */
    class WordTrie {
        //创建根节点
        TrieNode root = new TrieNode();

        void insert(String s) {
            TrieNode cur = root;
            for (char c : s.toCharArray()) {
                //判断是否存在该字符的节点，不存在则创建
                if (cur.child[c - 'a'] == null) {
                    cur.child[c - 'a'] = new TrieNode();
                    cur = cur.child[c - 'a'];
                } else
                    cur = cur.child[c - 'a'];
            }
            //遍历结束后，修改叶子节点的状态，并存储字符串
            cur.isEnd = true;
            cur.val = s;
        }
    }

    /**
     * 字典树节点
     */
    class TrieNode {
        /**
         * 存储最后节点的字符串
         */
        String val;
        /**
         * 根据字符排序，[a,b,c,……,z]
         */
        TrieNode[] child = new TrieNode[26];
        /**
         * 是否是最后叶子节点
         */
        boolean isEnd = false;
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
