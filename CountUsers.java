import java.io.*;
import java.util.*;
public class CountUsers {
    public static void main(String[] args) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.dat"))) {
            Object obj = in.readObject();
            if (obj instanceof List) {
                List<?> list = (List<?>) obj;
                System.out.println(list.size());
                for (int i = 0; i < Math.min(5, list.size()); i++) {
                    Object u = list.get(i);
                    try {
                        Class<?> cls = u.getClass();
                        Object id = cls.getMethod("getUserId").invoke(u);
                        Object name = cls.getMethod("getName").invoke(u);
                        Object role = cls.getMethod("getRole").invoke(u);
                        System.out.println(cls.getSimpleName() + " " + id + " " + name + " " + role);
                    } catch (Exception e) {
                        System.out.println("entry " + i + " type=" + u.getClass().getName());
                    }
                }
            } else {
                System.out.println("not-list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
