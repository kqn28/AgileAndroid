package edu.umd.cs.agileandroid;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import edu.umd.cs.agileandroid.service.*;
import edu.umd.cs.agileandroid.model.*;

public class BacklogActivity extends SingleFragmentActivity {

    private Button mainActivityButton, aboutUMDButton, createStoryButton;
    private TextView backlog;
    private static final int REQUEST_CODE_CREATE_STORY = 2;
    private int resultCode;
    private StoryService stoser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return BacklogFragment.newInstance();
    }


}
