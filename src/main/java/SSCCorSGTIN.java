import java.io.*;

public class SSCCorSGTIN {

    public static String whatSSCCorSGTIN(String path) {
        String s="null";

    if (path.equals("Empty")) {
        return path;
    }
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String[] a={"2"};
            String line = reader.readLine();
            while (line != null) {
                a=line.split("<sgtin>");
                if (a.length>1)
                    break;
                a=line.split("<sscc>");
                if (a.length>1)
                    break;
                line = reader.readLine();
            }
            if (a[1].contains("</sgtin>"))
                s = a[1].replaceAll("</sgtin>", "");
            else
                s = a[1].replaceAll("</sscc>", "");

            System.out.println(s);

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
