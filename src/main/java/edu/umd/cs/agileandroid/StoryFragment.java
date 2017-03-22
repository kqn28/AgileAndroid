package edu.umd.cs.agileandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.umd.cs.agileandroid.model.Story;
import edu.umd.cs.agileandroid.service.StoryService;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static edu.umd.cs.agileandroid.model.Story.Priority.CURRENT;
import static edu.umd.cs.agileandroid.model.Story.Priority.LATER;
import static edu.umd.cs.agileandroid.model.Story.Priority.NEXT;
import static edu.umd.cs.agileandroid.model.Story.Status.DONE;
import static edu.umd.cs.agileandroid.model.Story.Status.IN_PROGRESS;
import static edu.umd.cs.agileandroid.model.Story.Status.TODO;

public class StoryFragment extends Fragment {
    private Button cancelButton, saveButton;
    static final String EXTRA_STORY = "EXTRA_STORY";
    static final String EXTRA_ID = "EXTRA_ID";
    static final String EXTRA_STORY_CREATED = "EXTRA_STORY_CREATED";
    private EditText summary, criteria, points;
    private int priority;
    private static Story aStory;
    private View view;
    private RadioGroup radio;
    private Spinner spinner;
    private final String TAG = getClass().getSimpleName();
    private StoryService stoser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle aBundle = getArguments();
        String storyId = aBundle.getString(EXTRA_STORY);
        stoser = DependencyFactory.getStoryService(getActivity().getApplicationContext());
        aStory = stoser.getStoryById(storyId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_story, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        summary = (EditText) view.findViewById(R.id.summary);
        criteria = (EditText) view.findViewById(R.id.criteria);
        points = (EditText) view.findViewById(R.id.points);
        radio = (RadioGroup) view.findViewById(R.id.radio_group);
        cancelButton = (Button) view.findViewById(R.id.cancel_story_button);
        if (aStory != null) {
            summary.setText(aStory.getSummary());
            criteria.setText(aStory.getAcceptanceCriteria());
            points.setText(aStory.getStoryPoints() + "");
            if (aStory.getPriority() == CURRENT)
                radio.check(R.id.radio_current);
            else if (aStory.getPriority() == NEXT)
                radio.check(R.id.radio_next);
            else if (aStory.getPriority() == LATER)
                radio.check(R.id.radio_later);
            if (aStory.getStatus() == TODO)
                spinner.setSelection(0);
            else if (aStory.getStatus() == IN_PROGRESS)
                spinner.setSelection(1);
            else if (aStory.getStatus() == DONE)
                spinner.setSelection(2);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1) {
                Intent intent1 = getActivity().getIntent();
                getActivity().setResult(RESULT_CANCELED, intent1);
                getActivity().finish();
            }
        });
        saveButton = (Button) view.findViewById(R.id.save_story_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1) {
                Intent intent2 = getActivity().getIntent();
                String summary_string = summary.getText().toString();
                String criteria_string = criteria.getText().toString();
                int points_string = Integer.parseInt(points.getText().toString());
                priority = radio.getCheckedRadioButtonId();
                if (aStory == null) {
                    aStory = new Story();
                }
                aStory.setSummary(summary_string);
                aStory.setAcceptanceCriteria(criteria_string);
                aStory.setStoryPoints((double) points_string);
                if (priority == R.id.radio_current)
                    aStory.setPriorityCurrent();
                else if (priority == R.id.radio_next)
                    aStory.setPriorityNext();
                else if (priority == R.id.radio_later)
                    aStory.setPriorityLater();
                aStory.setStatus(spinner.getSelectedItemPosition());
                Log.d(TAG, aStory.getId());
                intent2.putExtra(EXTRA_STORY_CREATED, aStory);
                getActivity().setResult(RESULT_OK, intent2);
                getActivity().finish();
            }
        });
        return view;
    }

    public static StoryFragment newInstance(String storyId) {
        Bundle aBundle = new Bundle();
        aBundle.putString(EXTRA_STORY, storyId);
        StoryFragment returnFrag = new StoryFragment();
        returnFrag.setArguments(aBundle);
        return returnFrag;
    }

    public static Story getStoryCreated() {
        return aStory;
    }

}
