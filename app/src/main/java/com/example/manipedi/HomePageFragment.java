package com.example.manipedi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manipedi.databinding.FragmentHomePageBinding;
import com.example.manipedi.model.DBImplementation;
import com.example.manipedi.model.Post;

import java.util.LinkedList;
import java.util.List;

public class HomePageFragment extends Fragment {
    FragmentHomePageBinding binding;
    List<Post> data = new LinkedList<>();
    PostsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.PostsList.setHasFixedSize(true);
        binding.PostsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostsListAdapter(getLayoutInflater(),data);
        binding.PostsList.setAdapter(adapter);

        Post p = new Post("aaa", "avital", "space", 2, "save me", "");
//        DBImplementation.instance().addPost(p, () -> {});

//        DBImplementation.instance().getAllPosts((pList)->{data = pList;});

//        adapter.setOnItemClickListener(new PostsListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int pos) {
//                Log.d("TAG", "Row was clicked " + pos);
//                Post st = data.get(pos);
//                HomePageFragmentDirections.ActionStudentsListFragmentToBlueFragment action = HomePageFragmentDirections.actionStudentsListFragmentToBlueFragment(st.name);
//                Navigation.findNavController(view).navigate(action);
//            }
//        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData(){
//        binding.progressBar.setVisibility(View.VISIBLE);
//        DBImplementation.instance().getAllPosts((pList)->{
//            data = pList;
//            adapter.setData(data);
//            binding.progressBar.setVisibility(View.GONE);
//        });
    }
}

    //    public static HomePageFragment newInstance(String param1, String param2) {
//        HomePageFragment fragment = new HomePageFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

