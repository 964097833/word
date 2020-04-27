package com.yqd.test;

import java.io.*;
import java.util.*;

public class CountWord {
    private static final String OBJECTIVE_DIR = "D:\\English\\";

    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder allWord = new StringBuilder();
        try (   BufferedReader br =
                        new BufferedReader(new FileReader(OBJECTIVE_DIR + "单词汇总.txt"));) {
            String str;
            while ((str=br.readLine())!=null) {
                allWord.append(str);
            }
        } catch (Exception e) { e.printStackTrace(); }
        String[] split = allWord.toString().split(" ");
        Map<String,Integer> map = new HashMap<>();
        for (String s : split) {
            if (map.containsKey(s)) map.put(s,map.get(s)+1);
            else map.put(s,1);
        }

        try (PrintWriter pw = new PrintWriter(OBJECTIVE_DIR + "词频统计.txt")) {
            // 借助list实现hashmap以value排序
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> o2.getValue() - o1.getValue());
            for (Map.Entry<String, Integer> mapEntry : list) {
                pw.println(mapEntry.getKey() + "=" + mapEntry.getValue());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
