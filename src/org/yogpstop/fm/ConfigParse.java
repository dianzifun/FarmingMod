package org.yogpstop.fm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ConfigParse {
    public static void parse(File f) {
        File pf = new File(f, "FarmingItemBlock");
        if (!pf.exists())
            pf.mkdir();
        ArrayList<File> files = parseFolder(pf);
        for (File file : files) {
            parseFile(file);
        }
    }

    private static ArrayList<File> getFolders(File f) {
        ArrayList<File> dirs = new ArrayList<File>();
        File[] l = f.listFiles();
        for (File c : l) {
            if (c.isDirectory()) {
                dirs.add(c);
            }
        }
        return dirs;
    }

    private static ArrayList<File> getFiles(File f) {
        ArrayList<File> files = new ArrayList<File>();
        File[] l = f.listFiles();
        for (File c : l) {
            if (c.isFile() && getSuffix(c.getName()).equals("cfg")) {
                files.add(c);
            }
        }
        return files;
    }

    private static String getSuffix(String fileName) {
        if (fileName == null)
            return null;
        int point = fileName.lastIndexOf(".");
        if (point != -1) { return fileName.substring(point + 1); }
        return fileName;
    }

    private static ArrayList<File> parseFolder(File f) {
        ArrayList<File> d = getFolders(f);
        ArrayList<File> out = new ArrayList<File>();
        if (!d.isEmpty()) {
            for (File e : d) {
                out.addAll(parseFolder(e));
            }
        }
        out.addAll(getFiles(f));
        return out;
    }

    private static void parseFile(File f) {
        HashMap<String, String> data = new HashMap<String, String>();
        try {
            BufferedReader bufrd = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line;
            while ((line = bufrd.readLine()) != null) {
                String[] namedata = line.split(":");
                if (namedata.length != 2)
                    continue;
                data.put(namedata[0].trim().toLowerCase(), namedata[1].trim());
            }
            bufrd.close();
        } catch (Exception e) {}
        if (data.containsKey("plant")) {
            // TODO
        } else if (data.containsKey("recipe")) {
            // TODO
        } else {
            DB.addItem(new ItemMeta(data));
        }

    }
}
