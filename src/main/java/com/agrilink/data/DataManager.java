package com.agrilink.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    
    // Generic method to save any list of objects to a file
    public static <T> void saveList(List<T> list, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error saving data to " + filename + ": " + e.getMessage());
        }
    }

    // Generic method to load any list of objects from a file
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadList(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist yet
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error loading data from " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}