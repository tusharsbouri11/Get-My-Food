package com.tsb.getmyfood.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.adapters.HomeCategoryAdapter;
import com.tsb.getmyfood.adapters.HomeNewOfferingAdapter;
import com.tsb.getmyfood.adapters.MostlyBoughtAdapter;
import com.tsb.getmyfood.models.HomeCategoryModel;
import com.tsb.getmyfood.models.ItemsModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mostlyView, homeCategoryView, homeNewOfferingView;
    private TextView mostlyViewAll, categoriesViewAll, newViewAll;
    private EditText searchBar;
    private FirebaseFirestore db;
    private List<ItemsModel> mostlyBoughtList, newOfferingList, searchList;
    private ArrayList<HomeCategoryModel> homeCategoryList;
    private MostlyBoughtAdapter mostlyBoughtAdapter;
    private HomeCategoryAdapter categoryAdapter;
    private HomeNewOfferingAdapter homeNewOfferingAdapter;
    private LinearLayout mostlyContainer, exploreContainer, newestContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        mostlyViewAll=root.findViewById(R.id.mostly_view_all);
        categoriesViewAll=root.findViewById(R.id.categories_view_all);
        newViewAll=root.findViewById(R.id.new_view_all);
        mostlyContainer=root.findViewById(R.id.mostly_container);
        exploreContainer=root.findViewById(R.id.explore_container);
        newestContainer=root.findViewById(R.id.newest_container);

        viewAllOnClicks(root);

        searchBar=root.findViewById(R.id.search_bar);
        searchOperation(root);

        mostlyView=root.findViewById(R.id.mostly_bought);
        mostlyView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        callmostlyBought(root);

        homeCategoryView=root.findViewById(R.id.home_category);
        callHomeCategory(root);

        homeNewOfferingView=root.findViewById(R.id.new_offering);
        callHomeNewOffering(root);

        return root;
    }

    private void searchOperation(View root) {

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                {
                    mostlyView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                    mostlyContainer.setVisibility(View.GONE);
                    exploreContainer.setVisibility(View.GONE);
                    newestContainer.setVisibility(View.GONE);
                    homeCategoryView.setVisibility(View.GONE);
                    homeNewOfferingView.setVisibility(View.GONE);
                    mostlyBoughtList.clear();
                    searchItems(charSequence.toString());
                }
                else
                {
                    mostlyView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                    mostlyContainer.setVisibility(View.VISIBLE);
                    mostlyBoughtList.clear();
                    callmostlyBought(root);
                    exploreContainer.setVisibility(View.VISIBLE);
                    newestContainer.setVisibility(View.VISIBLE);
                    homeCategoryView.setVisibility(View.VISIBLE);
                    homeNewOfferingView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void searchItems(String searchKey) {

        HashSet<String> seenItems=new HashSet<>();

        db.collection("All")
                .whereArrayContains("arr", searchKey)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemsModel itemsModel=document.toObject(ItemsModel.class);
                                if(seenItems.contains(itemsModel.getName()))
                                    continue;
                                mostlyBoughtList.add(itemsModel);
                            }
                            mostlyBoughtAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        db.collection("All")
                .whereEqualTo("name", searchKey)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemsModel itemsModel=document.toObject(ItemsModel.class);
                                if(seenItems.contains(itemsModel.getName()))
                                    continue;
                                mostlyBoughtList.add(itemsModel);
                            }
                            mostlyBoughtAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private NavController getNav()
    {
       return Navigation.findNavController(requireView());
    }

    private void viewAllOnClicks(@NonNull View root) {


        categoriesViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController=getNav();
                navController.navigate(R.id.action_homeFragment_to_categoryFragment);
            }
        });

        newViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController=getNav();
                navController.navigate(R.id.action_homeFragment_to_newRecipesFragment);
            }
        });

    }

    private void callHomeNewOffering(@NonNull View root) {

        newOfferingList = new ArrayList<>();

        db.collection("All")
                .whereArrayContains("arr", "New")
                .limit(3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                HomeNewOfferingModel homeNewOfferingModel=document.toObject(HomeNewOfferingModel.class);
//                                homeNewOfferingList.add(homeNewOfferingModel);
                                ItemsModel itemsModel=document.toObject(ItemsModel.class);
                                newOfferingList.add(itemsModel);
                                homeNewOfferingAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        homeNewOfferingView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeNewOfferingAdapter=new HomeNewOfferingAdapter(getActivity(), newOfferingList);
        homeNewOfferingView.setAdapter(homeNewOfferingAdapter);

    }

    private void callHomeCategory(@NonNull View root) {

        homeCategoryList=new ArrayList<>();

        db.collection("Category")
                .limit(3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document:task.getResult())
                            {
                                HomeCategoryModel homeCategoryModel=document.toObject(HomeCategoryModel.class);
                                homeCategoryList.add(homeCategoryModel);
//                                Toast.makeText(getContext(), document.getId(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), db.collection("HomeCategory").getPath(), Toast.LENGTH_SHORT).show();
                                categoryAdapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        homeCategoryView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryAdapter=new HomeCategoryAdapter(getActivity(), homeCategoryList);
        homeCategoryView.setAdapter(categoryAdapter);
    }

    private void callmostlyBought(@NonNull View root)
    {
        mostlyBoughtList = new ArrayList<>();

        db.collection("All")
                .whereArrayContains("arr", "Mostly")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document:task.getResult())
                            {
                                ItemsModel itemsModel=document.toObject(ItemsModel.class);
                                mostlyBoughtList.add(itemsModel);
                                mostlyBoughtAdapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mostlyBoughtAdapter=new MostlyBoughtAdapter(getActivity(), mostlyBoughtList);
        mostlyView.setAdapter(mostlyBoughtAdapter);
    }
}