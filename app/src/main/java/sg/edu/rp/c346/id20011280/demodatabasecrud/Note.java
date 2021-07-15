package sg.edu.rp.c346.id20011280.demodatabasecrud;

import java.io.Serializable;

public class Note implements Serializable {

    private 	int id;
    private 	String noteContent; // stays in this class

    public Note( int id, String noteContent  ) {
        this.id = id;
        this.noteContent = noteContent;
    }

    public int getId() {  return id;  }

    public String getNoteContent() { return noteContent; }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent; // setter method
    }

    @Override
    public String toString()
    {
        return "ID:" + id + ", " + noteContent;

    } // call on this format for items in the listview



}
