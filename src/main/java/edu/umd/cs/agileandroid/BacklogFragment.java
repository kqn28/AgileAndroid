package edu.umd.cs.agileandroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.List;

import edu.umd.cs.agileandroid.service.*;
import edu.umd.cs.agileandroid.model.*;

import static android.app.Activity.RESULT_OK;

public class BacklogFragment extends Fragment {


    private final int REQUEST_CODE_CREATE_STORY = 2;
    private int resultCode;
    private StoryService stoser2, stoser;
    private View view;
    private RecyclerView recycle;
    private StoryAdapter storyA;
    private final String TAG = getClass().getSimpleName();

    private class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Story aStory;
        private TextView summary, criteria, priority, points;

        public StoryHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            summary = (TextView)v.findViewById(R.id.list_item_story_summary);
            criteria = (TextView)v.findViewById(R.id.list_item_story_criteria);
            priority = (TextView)v.findViewById(R.id.list_item_story_priority);
            points = (TextView)v.findViewById(R.id.list_item_story_points);
        }

        public void bindStory(Story s) {
            aStory = s;
            summary.setText(s.getSummary());
            criteria.setText(s.getAcceptanceCriteria());
            points.setText(s.getStoryPoints() + "");
            if (s.getPriority() == Story.Priority.CURRENT) {
                priority.setText("CURRENT");
            } else if (s.getPriority() == Story.Priority.NEXT) {
                priority.setText("NEXT");
            } else {
                priority.setText("LATER");
            }
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, aStory.toString() + "2");
            Intent intent = StoryActivity.newIntent(getActivity().getApplicationContext(),
                    aStory.getId());
            startActivityForResult(intent, REQUEST_CODE_CREATE_STORY);
            onActivityResult(REQUEST_CODE_CREATE_STORY, resultCode, intent);
        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> {

        private List<Story> storyList;
        private LayoutInflater inflater1;

        public StoryAdapter(List<Story> list) {
            storyList = list;
        }

        public void setStories(List<Story> list) {
            storyList = list;
        }

        @Override
        public StoryHolder onCreateViewHolder(ViewGroup container, int num) {
            inflater1 = LayoutInflater.from(getActivity());
            View view1 = inflater1.inflate(R.layout.list_item_story,container, false);
            return new StoryHolder(view1);
        }

        @Override
        public void onBindViewHolder(StoryHolder s, int num) {
            Story aStory = storyList.get(num);
            s.bindStory(aStory);
        }

        @Override
        public int getItemCount() {
            return storyList.size();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stoser = DependencyFactory.getStoryService(getActivity().getApplicationContext());
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuinflate) {
        super.onCreateOptionsMenu(menu, menuinflate);
        menuinflate.inflate(R.menu.fragment_backlog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem) {
        switch(menuitem.getItemId()) {
            case R.id.menu_item_active_sprint:
                Intent intent2 = new Intent(getActivity().getApplicationContext(), SprintActivity.class);
                startActivity(intent2);
                return true;
            case R.id.menu_item_create_story:
                Intent intent3 = new Intent(getActivity().getApplicationContext(), StoryActivity.class);
                startActivityForResult(intent3, REQUEST_CODE_CREATE_STORY);
                onActivityResult(REQUEST_CODE_CREATE_STORY, resultCode, intent3);
                return true;
            default:
                return super.onOptionsItemSelected(menuitem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_backlog, container, false);
        recycle = (RecyclerView)view.findViewById(R.id.story_recycler_view);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_STORY) {
                Story aStory = (Story)data.getSerializableExtra(StoryFragment.EXTRA_STORY_CREATED);
                stoser.addStoryToBacklog(aStory);
                updateUI();
            }
        }
    }

    public static BacklogFragment newInstance() {
        return new BacklogFragment();
    }

    private void updateUI() {
        List<Story> storyL = stoser.getAllStories();
        if (storyA == null) {
            storyA = new StoryAdapter(storyL);
            recycle.setAdapter(storyA);
        } else {
            storyA.setStories(storyL);
            storyA.notifyDataSetChanged();
        }
    }

}

