package com.letcode.practise;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {


    LinkedHashMap<Integer,Integer> cache;

    int capacity;

    public LRUCache(int capacity) {
        this.capacity=capacity;
        this.cache=new LinkedHashMap(capacity);
    }

    public int get(int key) {
        if(cache.containsKey(key)){
           int value=cache.get(key);
           cache.remove(key);
           cache.put(key,value);
           return value;
        }else{
            return -1;
        }
    }

    public void put(int key, int value) {
        if(cache.containsKey(key)){
            cache.remove(key);
            cache.put(key,value);
        }else{
            if(cache.size()==capacity){
                if(capacity!=0){
                    Map.Entry entry=cache.entrySet().iterator().next();
                    cache.remove(entry.getKey());
                }
            }
            cache.put(key,value);
        }
    }

    public static void  main(String[] args){
        LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );

        cache.put(2,1);
        cache.put(1,1);
        cache.put(2,3);
        cache.put(4,1);
        cache.get(1);
        cache.get(2);

    }
}
