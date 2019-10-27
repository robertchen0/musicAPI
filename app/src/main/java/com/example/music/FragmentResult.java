package com.example.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentResult extends Fragment {

    private static MusicInfoPojo dataSet;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    public final static String KEY_DATA = "keydata";

    public FragmentResult(){

    }
    public static FragmentResult newInstance(MusicInfoPojo data){
        dataSet = data;
        FragmentResult instance = new FragmentResult();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        instance.setArguments(bundle);
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_display_results,
                container,
                false);
        recyclerView = view.findViewById(R.id.rv_list_results);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            dataSet = savedInstanceState.getParcelable(KEY_DATA);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CustomAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(),
                DividerItemDecoration.HORIZONTAL
        ));
        adapter.setDataSet(dataSet, (ActivityListener) getActivity());
        recyclerView.setAdapter(adapter);
    }

}
