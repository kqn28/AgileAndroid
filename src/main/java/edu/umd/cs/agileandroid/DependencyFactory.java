package edu.umd.cs.agileandroid;


import android.content.Context;

import edu.umd.cs.agileandroid.service.StoryService;
import edu.umd.cs.agileandroid.service.impl.InMemoryStoryService;

public class DependencyFactory {
    private static StoryService storyService;

    public static StoryService getStoryService(Context context) {
        if (storyService == null) {
            storyService = new InMemoryStoryService(context);
        }
        return storyService;
    }
}
