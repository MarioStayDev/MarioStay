package com.example.mario;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class PropertyViewModel extends AndroidViewModel {

    private PropertyRepository repository;
    private LiveData<List<IncompleteProperty>> mAllWords;

    public PropertyViewModel (Application application) {
        super(application);
        repository = new PropertyRepository(application);
        mAllWords = repository.getAllProperties();
    }

    LiveData<List<IncompleteProperty>> getAllWords() { return mAllWords; }

    public void insert(IncompleteProperty word) { repository.insert(word); }


}
