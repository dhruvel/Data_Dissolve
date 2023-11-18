package com.datadissolve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.db.ItemDataSource;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<String> selectionListFromDB;
    private ImageView buttonInit;

    public ItemListFragment() {
        // Required empty public constructor
//        ArrayList<String> selectionListFromDB = new ArrayList<>(Arrays.asList("Default", "Gutmann", "Dod", "Schneider"));
    }
    private List<String> fetchItemsFromDatabase() {
        ItemDataSource dataSource = new ItemDataSource(requireActivity());
        dataSource.open();
        List<String> items = dataSource.getListOfItems();
        dataSource.close();
        return items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        selectionListFromDB = fetchItemsFromDatabase();

        buttonInit = view.findViewById(R.id.button_init);

        setupButtonInit();


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ItemListAdapter(selectionListFromDB));

        return view;
    }

    private void setupButtonInit() {
        buttonInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDataSource dataSource = new ItemDataSource(requireActivity());
                dataSource.open();
                dataSource.deleteAllItems();
            }
        });
    }

    private class ItemListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        private ArrayList<String> items;
        public ItemListAdapter(List<String> itemList) {
            this.items = new ArrayList<>(itemList);
        }
        public void removeItem(int position) {
            items.remove(position);
            notifyItemRemoved(position);
        }
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new ItemViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bindItem(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView selectionNameTV;
        private ImageView deleteButton;

        private String itemText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            selectionNameTV = itemView.findViewById(R.id.selectionName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            selectionNameTV.setOnClickListener(this);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Remove the item from the list
                        ((ItemListAdapter) recyclerView.getAdapter()).removeItem(position);
                    }
                }
            });
        }

        public void bindItem(String item) {
            itemText = item;
            /* Bind the item data to the view here (e.g., set text to a TextView). */
            selectionNameTV.setText(item);
        }

        @Override
        public void onClick(View v) {
            /* Handle item click here */
            Toast.makeText(getActivity(), "Clicked on " + itemText, Toast.LENGTH_SHORT).show();

            /* Start a new activity here */


        }
    }
}