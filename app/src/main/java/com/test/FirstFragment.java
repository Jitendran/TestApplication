package com.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.test.adapter.BirdListAdapter;
import com.test.databinding.FragmentFirstBinding;
import com.test.model.firstfragmentbird.BirdItem;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements BirdListAdapter.ClickListener {
    private FragmentFirstBinding fragmentFirstBinding;
    private List<BirdItem> movieList;
    private BirdListAdapter birdListAdapter;
    private BirdItem selectedBirdItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentFirstBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        setToolBar();
        Subscriber.init();
        MainActivity.RX.onNext("Overview Fragment created");
        movieList = new ArrayList<>();
        birdListAdapter = new BirdListAdapter(movieList, this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        fragmentFirstBinding.rvBird.setLayoutManager(mLayoutManager);
        fragmentFirstBinding.rvBird.setItemAnimator(new DefaultItemAnimator());
        fragmentFirstBinding.rvBird.setAdapter(birdListAdapter);
        prepareBirdData();

        return fragmentFirstBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentFirstBinding.faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("detail", selectedBirdItem);
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_detailFragment, bundle);
                MainActivity.RX.onNext("Info Button clicked");
            }
        });
    }

    /**
     * Prepare Bird List to set in adapter
     */
    private void prepareBirdData() {
        BirdItem birdItem = new BirdItem("Crow",
                "https://upload.wikimedia.org/wikipedia/commons/0/08/Fish_crow_in_Red_Hook_%2842712%29.jpg",
                "https://en.wikipedia.org/wiki/Crow");
        movieList.add(birdItem);
        birdItem = new BirdItem("Dove",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Paloma_brav%C3%ADa_%28Columba_livia%29%2C_Palacio_de_Nymphenburg%2C_M%C3%BAnich%2C_Alemania01.JPG/800px-Paloma_brav%C3%ADa_%28Columba_livia%29%2C_Palacio_de_Nymphenburg%2C_M%C3%BAnich%2C_Alemania01.JPG",
                "https://en.wikipedia.org/wiki/Columbidae");
        movieList.add(birdItem);
        birdItem = new BirdItem("Parrot",
                "https://upload.wikimedia.org/wikipedia/commons/4/44/Parrot_India_2.jpg",
                "https://en.wikipedia.org/wiki/Parrot");
        movieList.add(birdItem);
        birdItem = new BirdItem("Sparrow",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/House_Sparrow_mar08.jpg/1280px-House_Sparrow_mar08.jpg",
                "https://en.wikipedia.org/wiki/Sparrow");
        movieList.add(birdItem);
        birdListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BirdItem birdItem) {
        selectedBirdItem = birdItem;
        if (birdItem != null) {
            fragmentFirstBinding.faButton.setVisibility(View.VISIBLE);
        } else {
            fragmentFirstBinding.faButton.setVisibility(View.GONE);
        }
    }

    /**
     * To set the tool bar tile and hide back left arrow
     */
    private void setToolBar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("TestApp - Overview");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        birdListAdapter = null;
        fragmentFirstBinding = null;
    }
}