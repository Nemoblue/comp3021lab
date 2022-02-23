package base;

import java.util.Date;
import java.util.Objects;

public class Note {
    private Date date;
    private String title;

    public Note(String title) {
        this.title = title;
        date = new Date(System.currentTimeMillis());
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return getTitle().equals(note.getTitle());
    }
}
