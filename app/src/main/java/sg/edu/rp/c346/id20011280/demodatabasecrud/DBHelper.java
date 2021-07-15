package sg.edu.rp.c346.id20011280.demodatabasecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simplenotes.db";// final means cannot change
    private static final int DATABASE_VERSION = 1; // change in version using upgrade method
    private static final String TABLE_NOTE = "note"; // name
    private static final String COLUMN_ID = "_id"; // id
    private static final String COLUMN_NOTE_CONTENT = "note_content";// string content

    public DBHelper(Context context) { // pass the context
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createNoteTableSql = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE_CONTENT + " TEXT ) ";
        db.execSQL(createNoteTableSql);

        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOTE_CONTENT, "Data number " + i);
            db.insert(TABLE_NOTE, null, values);
        }
        Log.i("info", "dummy records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  // add the table
        db.execSQL("ALTER TABLE " + TABLE_NOTE + " ADD COLUMN module_name TEXT");
        onCreate(db);
    }

    public long insertNote(String noteContent) {
        SQLiteDatabase db = this.getWritableDatabase(); // retrieve database
        ContentValues values = new ContentValues(); // hold all the content
        values.put(COLUMN_NOTE_CONTENT, noteContent); // put into the column, psssed content from user
        long result = db.insert(TABLE_NOTE, null, values); // null because of auto increment
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result; // how many insertp
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();
        String selectQuery = "SELECT " + COLUMN_ID + "," // retrieve querry
                + COLUMN_NOTE_CONTENT + " FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // point to the rows
        if (cursor.moveToFirst()) { // if got data in first row
            do {
                int id = cursor.getInt(0);
                String noteContent = cursor.getString(1);
                Note note = new Note(id, noteContent);
                notes.add(note); // add to arraylsit
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public int updateNote(Note data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_CONTENT, data.getNoteContent());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_NOTE, values, condition, args);
        db.close();
        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NOTE, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Note> getAllNotes(String keyword) {
        ArrayList<Note> notes = new ArrayList<Note>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NOTE_CONTENT};
        String condition = COLUMN_NOTE_CONTENT + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_NOTE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String noteContent = cursor.getString(1);
                Note note = new Note(id, noteContent);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }





}
