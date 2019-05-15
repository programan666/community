package com.programan.cm.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSizeUtil {

    public static float getSize(float size, File[] fileArr) {
        if (null == fileArr || fileArr.length <= 0)//部分文件夹 无权限访问，返回null
        {
            return 0;
        }
        for (File file : fileArr)
        {
            if (file.isFile())
            {
                System.out.println(file.getName() + " : " + file.length());
                size += file.length();
            }
            if (file.isDirectory())
            {
                getSize(size, file.listFiles());
            }
        }
        return size;
    }

    public static float getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        float total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }

    public static List<String> readfile(String filepath) throws FileNotFoundException, IOException {
        List<String> filePath = new ArrayList<>();
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        filePath.add(readfile.getPath().replace("\\", "/"));
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return filePath;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<String> fileList = FileSizeUtil.readfile("/Users/apple/Downloads/nginx");
        System.out.println();
    }

}
