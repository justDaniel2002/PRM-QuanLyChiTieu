package com.example.personal_manager_expenses.ui.report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReportViewModel {
    private final MutableLiveData<String> mText;

    public ReportViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
