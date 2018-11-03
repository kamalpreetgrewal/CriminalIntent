package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    /**
     * Get the data from CrimeLab and pass it onto adapter and associate it to recyclerview.
     */
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mCrimeAdapter = new CrimeAdapter(crimes);
        mRecyclerView.setAdapter(mCrimeAdapter);
    }

    /**
     * This class creates a view holder for each of the crime items in the list. Get each of the
     * views in the item and assign them values in the bind method which is called in the adapter.
     * Actions like touch on the list items are also handled here.
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Button mContactPoliceButton;
        private ImageView mCrimeSolvedImageView;
        private Crime mCrime;
        private boolean mSolvedImageExists = false;

        public CrimeHolder(View view) {
            super(view);
            mTitleTextView = (TextView) view.findViewById(R.id.crime_title);
            mDateTextView = (TextView) view.findViewById(R.id.crime_date);
            mContactPoliceButton = (Button) view.findViewById(R.id.requires_police_button);
            /**
             * Depending on which item type is displayed, the image icon will be visible or invisible.
             * First it has to be checked if the imageview exists or not. If it exists, it is defined
             * otherwise a crash occurs which is being dealt with the if statement here.
             */
            if (view.findViewById(R.id.image_crime_solved) != null) {
                mSolvedImageExists = true;
                mCrimeSolvedImageView = (ImageView) view.findViewById(R.id.image_crime_solved);
            }
            view.setOnClickListener(this);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            if (crime.isSolved() && mSolvedImageExists) {
                mCrimeSolvedImageView.setVisibility(View.VISIBLE);
            } else if (!crime.isSolved() && mSolvedImageExists){
                mCrimeSolvedImageView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Crime " + mCrime.getId().toString() + " is clicked",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This class creates a connection between the view holder for the crime item and the data.
     * Create a new view and bind data into it after getting position of the item in recyclerview.
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
            Crime crime = mCrimes.get(position);
            if (crime.getRequiresPolice()) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime,
                            parent, false);
                    break;
                case 1:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_serious_crime,
                            parent, false);
                    break;
            }
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
