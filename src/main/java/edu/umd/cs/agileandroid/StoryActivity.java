package edu.umd.cs.agileandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import edu.umd.cs.agileandroid.model.Story;
import android.support.v4.app.Fragment;
import android.util.Log;

import static edu.umd.cs.agileandroid.StoryFragment.EXTRA_ID;

public class StoryActivity extends SingleFragmentActivity {

    static final String EXTRA_STORY_CREATED = "EXTRA_STORY_CREATED";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        String storyId = intent.getStringExtra(EXTRA_ID);
        return StoryFragment.newInstance(storyId);
    }

    protected Story getStoryCreated() {
        return StoryFragment.getStoryCreated();
    }

    public static Intent newIntent(Context c, String storyId) {
        Intent intent = new Intent(c, StoryActivity.class);
        intent.putExtra(EXTRA_ID, storyId);
        return intent;
    }

}

