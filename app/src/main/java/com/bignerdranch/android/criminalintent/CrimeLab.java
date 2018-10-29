package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    // Singleton for storing crime list
    private static CrimeLab sCrimeLab;

    // Use the interface List, not ArrayList for declaration
    private List<Crime> mCrimes;

    /**
     * Private constructor that creates an instance if one does not exist, otherwise it returns the
     * instance that exists already.
     * @param context
     */
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            // Set every other crime to be solved.
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    /**
     * Get method that creates an instance if one does not exist, otherwise it returns the instance
     * that exists already.
     * @param context
     * @return
     */
    public static CrimeLab getInstance(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    /**
     * This method returns the list of crimes.
     * @return
     */
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * This method returns the crime from the list with the given ID if it exists in the list.
     * @param id
     * @return
     */
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}