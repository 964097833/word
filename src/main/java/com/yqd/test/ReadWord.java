package com.yqd.test;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadWord {
    public static final String SOURCE_DIR = "D:\\2017-2019年 CET4 试卷真题 + MP3听力文件";
    public static final String OBJECTIVE_DIR = "D:\\English\\";
    public static void main(String[] args) throws Exception {
        // 1 把所有试卷内容复制成txt放到D:\English下
        File file = new File(SOURCE_DIR);

        searchDirFile(file);

        System.out.println("拷贝完成");
    }

    /**
     * 遍历文件夹下所有.docx文件
     * 文档中有以~$开头的隐藏文件，需设置判断条件
     * @param file
     */
    private static void searchDirFile(File file) {
        if (file.isFile()) {
            String name = file.getName();
            if (name.contains("试卷") && !name.startsWith("~$") && name.endsWith(".docx")) {
                DocxToTxt(file);
            }
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File file1 : files) {
                    if (file1.isFile()) {
                        String name = file1.getName();
                        if (name.contains("试卷") && !name.startsWith("~$") && name.endsWith(".docx")) {
                            DocxToTxt(file1);
                        }
                    } else searchDirFile(file1);
                }
            }
        }
    }

    /**
     * 将docx文档中的内容复制到txt文件中
     * @throws IOException
     */
    private static void DocxToTxt(File file) {
        try (
                InputStream is = new FileInputStream(file);
                XWPFDocument xd = new XWPFDocument(is);
                XWPFWordExtractor xwe = new XWPFWordExtractor(xd);
                /**
                 * split中以“.”即：split(".")时，为分割的时候必须是转义字符，必须写为：split("\\.")
                 String name = file.getName();
                 System.out.println(name);
                 String[] split = name.split("\\.");
                 System.out.println(split[0]);
                 */
                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(OBJECTIVE_DIR+"单词汇总.txt",true));
                ){
//            bw.write(xwe.getText());
            // 1.定义爬取规则
            String regex = "([a-zA-Z]{2,})";
            // 2.编译正则表达式成为一个匹配规则对象
            Pattern pattern = Pattern.compile(regex);
            // 3.通过匹配规则对象得到一个匹配数据内容的匹配器对象
            Matcher matcher = pattern.matcher(xwe.getText());
            // 4.通过匹配器去内容中爬取出信息
            while(matcher.find()){
                bw.write((matcher.group()+" ").toLowerCase());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
