package edu.umd.cs.agileandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class SprintActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return SprintFragment.newInstance();
    }

}
