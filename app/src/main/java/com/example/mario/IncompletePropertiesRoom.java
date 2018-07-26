package com.example.mario;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = IncompleteProperty.class, version = 1)
public abstract class IncompletePropertiesRoom extends RoomDatabase {

    public abstract PropertyDAO getPropertyDAO();

    private static IncompletePropertiesRoom INSTANCE;

    static IncompletePropertiesRoom getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (IncompletePropertiesRoom.class) {
                if(INSTANCE == null) INSTANCE = Room.databaseBuilder(context, IncompletePropertiesRoom.class, "properties").addCallback(sRoomDatabaseCallback).build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
        new RoomDatabase.Callback(){

            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new PopulateDbAsync(INSTANCE).execute();
            }
        };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PropertyDAO mDao;

        PopulateDbAsync(IncompletePropertiesRoom db) {
            mDao = db.getPropertyDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            IncompleteProperty word = new IncompleteProperty();
            word.setName("Test 1");
            mDao.insert(word);
            IncompleteProperty word2 = new IncompleteProperty();
            word2.setName("Test 2");
            mDao.insert(word2);
            return null;
        }
    }
}
