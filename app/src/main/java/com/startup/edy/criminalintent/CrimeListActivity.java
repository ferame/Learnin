package com.startup.edy.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Justin on 6/24/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
