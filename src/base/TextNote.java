package base;

import java.io.*;

public class TextNote extends Note implements java.io.Serializable {
    private String content;

    public TextNote(String title) {
        super(title);
    }

    public TextNote(String title, String content) { super(title); this.content = content;}

    public TextNote(File f) {
        super(f.getName());
        this.content = getTextFromFile(f.getAbsolutePath());
    }

    private String getTextFromFile(String absolutePath) {
        String result = "";

        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream(absolutePath);
            in = new ObjectInputStream(fis);

            result = (String) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getContent() { return content; }

    public void exportTextToFile(String pathFolder) {
        if (pathFolder == "") {
            pathFolder = ".";
        }

        File file = new File(pathFolder
                + File.separator
                + this.getTitle().replaceAll(" ", "_")
                + ".txt");

        if (file.exists()) {
            System.out.println("File exists");
            return;
        }

        try {
            PrintWriter output = new PrintWriter(file);
            output.print(this.getContent());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
