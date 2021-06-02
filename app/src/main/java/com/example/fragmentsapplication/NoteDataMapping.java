package com.example.fragmentsapplication;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static  class Fields {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String DATE_CREATED = "dateCreated";
        public static final String IS_FAVORITE = "isFavorite";


    }

    public static Note toNoteData(String id, Map<String, Object> doc) {
        String name = (String) doc.get(Fields.NAME);
        String description = (String) doc.get(Fields.DESCRIPTION);
        String dateCreated = (String) doc.get(Fields.DATE_CREATED);
        boolean isFavorite;
        if (doc.get(Fields.IS_FAVORITE) == null) {
            isFavorite = false;
        } else {
            isFavorite = (boolean) doc.get(Fields.IS_FAVORITE);
        }

        Note note = new Note(
                name,
                description,
                dateCreated,
                isFavorite);

        note.setId(id);

        return note;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> doc = new HashMap<>();

        doc.put(Fields.NAME, note.getName());
        doc.put(Fields.DESCRIPTION, note.getDescription());
        doc.put(Fields.DATE_CREATED, note.getDateCreated());
        doc.put(Fields.IS_FAVORITE, note.isFavorite());

        return doc;
    }
}
