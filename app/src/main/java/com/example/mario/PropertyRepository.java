package com.example.mario;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PropertyRepository {

    private PropertyDAO mpropertyDAO;
    private LiveData<List<IncompleteProperty>> mProps;

    PropertyRepository(Application application) {
        IncompletePropertiesRoom db = IncompletePropertiesRoom.getDatabase(application);
        mpropertyDAO = db.getPropertyDAO();
        mProps = mpropertyDAO.getAllIncompleteProperties();
    }

    LiveData<List<IncompleteProperty>> getAllProperties() {
        return mProps;
    }


    public void insert (IncompleteProperty p) { new insertAsyncTask(mpropertyDAO).execute(p); }
    public void delete (IncompleteProperty p) { new deleteAsyncTask(mpropertyDAO).execute(p); }

    private static class insertAsyncTask extends AsyncTask<IncompleteProperty, Void, Void> {

        private PropertyDAO mAsyncTaskDao;

        insertAsyncTask(PropertyDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final IncompleteProperty... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<IncompleteProperty, Void, Void> {

        private PropertyDAO mAsyncTaskDao;

        deleteAsyncTask(PropertyDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final IncompleteProperty... params) {
            mAsyncTaskDao.delete(params[0]);
            System.out.println("deleting from thread");
            return null;
        }
    }
}
