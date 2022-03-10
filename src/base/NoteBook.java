package base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NoteBook {
    private ArrayList<Folder> folders;

    public NoteBook() {
        folders = new ArrayList<Folder>();
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
}
