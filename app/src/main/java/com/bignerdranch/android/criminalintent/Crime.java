package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Kamal on 21-08-2018.
 */

public class Crime {
    private UUID mCrimeId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mCrimeId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mCrimeId;
    }
    
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
