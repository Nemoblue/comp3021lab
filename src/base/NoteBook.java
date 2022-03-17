package base;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NoteBook implements java.io.Serializable {
    private ArrayList<Folder> folders;
    private static final long serialVersionUID = 1L;

    public NoteBook() {
        folders = new ArrayList<Folder>();
    }

    public NoteBook(String file) {
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);

            NoteBook n = (NoteBook) in.readObject();
            this.folders = n.getFolders();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createTextNote(String folderName, String title) {
        TextNote note = new TextNote(title);
        return insertNote(folderName, note);
    }

    public boolean createTextNote(String forlderName, String title, String content) {
        TextNote note = new TextNote(title, content);
        return insertNote(forlderName, note);
    }

    public boolean createImageNote(String folderName, String title) {
        ImageNote note = new ImageNote(title);
        return insertNote(folderName, note);
    }

    public ArrayList<Folder> getFolders() {
        return this.folders;
    }

    public boolean insertNote(String folderName, Note note) {
        Folder f = null;
        for (Folder f1: folders) {
            if (f1.getName().equals(folderName)) {
                f = f1;
                break;
            }
        }

        if (f == null) {
            f = new Folder(folderName);
            folders.add(f);
        }

        for (Note n: f.getNotes()) {
            if (n.equals(note)) {
                System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed.");
                return false;
            }
        }

        f.addNote(note);
        return true;
    }

    public void sortFolders() {
        for (Folder folder: this.getFolders())
            folder.sortNotes();

        folders.sort(Folder::compareTo);
    }

    public List<Note> searchNotes(String keywords) {
        List<Note> foundNotes = new LinkedList<Note>();

        for(Folder folder: folders) {
            foundNotes.addAll(folder.searchNotes(keywords));
        }

        return foundNotes;
    }

    public boolean save(String file) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        try {
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);

            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
