package base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Folder implements Comparable<Folder>, java.io.Serializable{
    private ArrayList<Note> notes;
    private String name;

    public Folder(String name) {
        this.name = name;
        notes = new ArrayList<Note>();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Note> getNotes() {
        return this.notes;
    }

    @Override
    public String toString() {
        int nText = 0;
        int nImage = 0;

        for (Note note: notes) {
            if (note instanceof TextNote)
                nText += 1;
            if (note instanceof ImageNote)
                nImage += 1;
        }

        return name + ":" + nText + ":" + nImage;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Folder))
			return false;
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    @Override
    public int compareTo(Folder o) {
        return this.getName().compareTo(o.getName());
    }

    public void sortNotes() {
        notes.sort(Note::compareTo);
    }

    public List<Note> searchNotes(String keywords) {
        String[] tokens = keywords.toLowerCase().split(" ");
        ArrayList<ArrayList<String>> pattens = new ArrayList<ArrayList<String>>();
        List<Note> foundNotes = new LinkedList<Note>();

        for(int i = 0; i < tokens.length; i++) {
            if(!tokens[i].equals("or")) {
                ArrayList<String> patten = new ArrayList<String>();
                patten.add(tokens[i]);
                pattens.add(patten);
            }
            else {  // tokens[i] equals "or"
                assert pattens.size() > 0 && i < tokens.length - 1;
                pattens.get(pattens.size() - 1).add(tokens[i + 1]);
                i = i + 1;
            }
        }

//        for(ArrayList<String> patten: pattens){
//            for(String keyword: patten){
//                System.out.print(keyword + " ");
//            }
//            System.out.println();
//        }

        for(Note note: notes) {
            boolean hasKey = true;
            for(ArrayList<String> patten: pattens){
                boolean temp = false;
                for(String keyword: patten){
                    if (note instanceof TextNote){
                        if (note.getTitle().toLowerCase().contains(keyword) || ((TextNote) note).getContent().toLowerCase().contains(keyword))
                            temp = true;
                    }
                    else {
                        if (note.getTitle().toLowerCase().contains(keyword))
                            temp = true;
                    }
                }

                hasKey = temp;
                if (!hasKey)
                    break;
            }

            if(hasKey)
                foundNotes.add(note);
        }

        return foundNotes;
    }
}
