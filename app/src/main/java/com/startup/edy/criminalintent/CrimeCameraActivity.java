package com.startup.edy.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Justin on 7/17/2015.
 */
public class CrimeCameraActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}
