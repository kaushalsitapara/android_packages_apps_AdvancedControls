package com.magicxavi.settings.device;

import android.os.SystemProperties;

import android.util.Log; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.NullPointerException;
import java.lang.SecurityException;

public class FileUtils {

    private static final String TAG = "FileUtils";

    static boolean fileWritable(String filename) {
    
        Log.e(TAG, "fileWritable ");
        return fileExists(filename) && new File(filename).canWrite();
    }

    
    private FileUtils() {
        // This class is not supposed to be instantiated
    }

    /**
     * Reads the first line of text from the given file.
     * Reference {@link BufferedReader#readLine()} for clarification on what a line is
     *
     * @return the read line contents, or null on failure
     */
    public static String readOneLine(String fileName) {
        String line = null;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileName), 512);
            line = reader.readLine();
        } catch (FileNotFoundException e) {
            Log.w(TAG, "No such file " + fileName + " for reading", e);
        } catch (IOException e) {
            Log.e(TAG, "Could not read from file " + fileName, e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException 1", e);
                // Ignored, not much we can do anyway
            }
        }

        return line;
    }

    public static boolean isFileReadable(String fileName) {
        final File file = new File(fileName);
        return file.exists() && file.canRead();
    }
    
    private static boolean fileExists(String filename) {
        Log.e(TAG, "fileExists called");
        if (filename == null) {
        Log.e(TAG, "fileExists");
            return false;
        }
        Log.e(TAG, "fileExists real");
        return new File(filename).exists();
    }

    public static void setValue(String path, Boolean value) {
    Log.e(TAG, "setValue Boolean");
        if (fileWritable(path)) {
            if (path == null) {
                Log.e(TAG, "setValue Boolean path null");
                return;
            }
            try {
                Log.e(TAG, "setValue Boolean writing");
                FileOutputStream fos = new FileOutputStream(new File(path));
                fos.write((value ? "1" : "0").getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                Log.e(TAG, "error boolean",e);
                e.printStackTrace();
            }
        }
    }

    public static void setValue(String path, int value) {
        if (fileWritable(path)) {
            if (path == null) {
                Log.e(TAG, "setValue Int path null");
                return;
                
            }
            try {
            Log.e(TAG, "setValue Int writing");
                FileOutputStream fos = new FileOutputStream(new File(path));
                fos.write(Integer.toString(value).getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException Int", e);
                e.printStackTrace();
                
            }
        }
    }

    static void setValue(String path, double value) {
    Log.e(TAG, "setValue double writing");
        if (fileWritable(path)) {
            if (path == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(new File(path));
                fos.write(Long.toString(Math.round(value)).getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setValue(String path, String value) {
     Log.e(TAG, "setValue String writing");
        if (fileWritable(path)) {
            if (path == null) {
                Log.e(TAG, "setValue Int path null");
                return;
            }
            try {
                Log.e(TAG, "setValue String writing");
                FileOutputStream fos = new FileOutputStream(new File(path));
                fos.write(value.getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException String", e);
                e.printStackTrace();
            }
        }
    }

    static String getValue(String filename) {
        if (filename == null) {
            return null;
        }
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename), 1024)) {
            line = br.readLine();
        } catch (IOException e) {
            return null;
        }
        // ignore
        return line;
    }

    static void setStringProp(String prop, String value) {
        SystemProperties.set(prop, value);
    }

    static String getStringProp(String prop, String defaultValue) {
        return SystemProperties.get(prop, defaultValue);
    }

    static void setintProp(String prop, int value) {
        SystemProperties.set(prop, String.valueOf(value));
    }

    static int getintProp(String prop, int defaultValue) {
        return SystemProperties.getInt(prop, defaultValue);
    }

    static boolean getFileValueAsBoolean(String filename, boolean defValue) {
        String fileValue = readLine(filename);
        if (fileValue != null) {
            return !fileValue.equals("0");
        }
        return defValue;
    }

    static String readLine(String filename) {
        if (filename == null) {
            return null;
        }
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(filename), 1024);
            line = br.readLine();
        } catch (IOException e) {
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return line;
    }
}
