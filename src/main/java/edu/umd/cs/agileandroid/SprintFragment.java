package edu.umd.cs.agileandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.umd.cs.agileandroid.model.*;
import edu.umd.cs.agileandroid.service.StoryService;

import static edu.umd.cs.agileandroid.model.Story.Status.IN_PROGRESS;
import static edu.umd.cs.agileandroid.model.Story.Status.TODO;

public class SprintFragment extends Fragment {

    private View view;
    private StoryService stoser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_sprint, container, false);
        stoser = DependencyFactory.getStoryService(getActivity().getApplicationContext());
        List<Story> storyList = stoser.getCurrentSprintStories();
        LinearLayout todoCol = (LinearLayout) view.findViewById(R.id.todo_column);
        LinearLayout inprogressCol = (LinearLayout) view.findViewById(R.id.inprogress_column);
        LinearLayout doneCol = (LinearLayout) view.findViewById(R.id.done_column);
        for(int i = 0; i < storyList.size(); i++) {
            TextView tempView = new TextView(getContext());
            tempView.setText(storyList.get(i).getSummary());
            final String tempString = storyList.get(i).toString();
            tempView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(getActivity().getApplicationContext(), tempString,
                            Toast.LENGTH_SHORT).show();
                }
            });
            if (storyList.get(i).getStatus() == TODO) {
                todoCol.addView(tempView);
            } else if (storyList.get(i).getStatus() == IN_PROGRESS) {
                inprogressCol.addView(tempView);
            } else {
                doneCol.addView(tempView);
            }
        }
        return view;
    }

    public static SprintFragment newInstance() {
        return new SprintFragment();
    }
}
