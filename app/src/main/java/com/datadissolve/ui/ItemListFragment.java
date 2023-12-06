package com.datadissolve.ui;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.datadissolve.R;
import com.datadissolve.db.Item;
import com.datadissolve.db.ItemDataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * @noinspection ALL
 */
public class ItemListFragment extends Fragment {
    private List<String> selectionListFromDB;
    private LinearLayout buttonInit;
    private LinearLayout buttonPerformDD;
    private final List<String> dataDissolveList = Arrays.asList("Default", "Gutmann", "DoD", "Schneier", "Custom");
    private ItemListAdapter adapter;
    private ItemDataSource dataSource;
    private TextView ddDescription;
    private String selectedItem;

    public ItemListFragment() {}

    private List<String> fetchItemsFromDatabase(ItemDataSource dataSource) {
        dataSource.open();
        List<String> items = dataSource.getListOfItems();
        Log.d("ItemListFragment", "fetchItemsFromDatabase: " + items.toString());
        dataSource.close();
        return items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        dataSource = new ItemDataSource(requireActivity());
        ddDescription = view.findViewById(R.id.data_dissolve_description_tv);
        selectionListFromDB = fetchItemsFromDatabase(dataSource);
//        if(selectionListFromDB.size() > 0) {
//            selectedItem = selectionListFromDB.get(0);
//        }
        buttonInit = view.findViewById(R.id.button_init);
        buttonPerformDD = view.findViewById(R.id.perform_data_dissolve_ll);
        adapter = new ItemListAdapter(selectionListFromDB);
        setupRecyclerView(view, adapter);
        setupButtonInit(dataSource);
        setupButtonPerformDD();
        return view;
    }

    private void setupButtonPerformDD() {
        buttonPerformDD.setOnClickListener(v -> {
            if (selectedItem.equals("Custom")) {
                Intent customIntent = new Intent(getActivity(), CustomDataSanitizationActivity.class);
                customIntent.putExtra("selectedDataDissolveMethod", selectedItem);
                startActivity(customIntent);
            } else {
                Intent intent = new Intent(getActivity(), DataDissolveActivity.class);
                intent.putExtra("selectedDataDissolveMethod", selectedItem);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView(View view, ItemListAdapter adapter) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void setupButtonInit(ItemDataSource dataSource) {
        buttonInit.setOnClickListener(v -> populateDatabase(dataSource));
    }

    private void populateDatabase(ItemDataSource dataSource) {
        dataSource.open();
        Log.d("ItemListFragment", "populateDatabase (pre): " + dataSource.getListOfItems().toString());
        List<String> fullList = dataDissolveList;
        List<String> currentListFromDB = dataSource.getListOfItems();

        for(String item : fullList) {
            if(!currentListFromDB.contains(item)) {
                Item item1 = new Item(item);
                dataSource.insertItem(item1);
            }
            else {dataSource.updateItem(new Item(item));}
        }
        Log.d("ItemListFragment", "populateDatabase (post): " + dataSource.getListOfItems().toString());
        dataSource.close();
        refreshList();
    }

    private void refreshList() {
        selectionListFromDB = fetchItemsFromDatabase(dataSource);
        adapter.notifyDataSetChanged();
        if(getActivity() instanceof DataSanitizationSelectionActivity) {
            ((DataSanitizationSelectionActivity) getActivity()).loadFragment();
        }
    }

    private class ItemListAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        private final ArrayList<String> items;
        public ItemListAdapter(List<String> itemList) {
            this.items = new ArrayList<>(itemList);
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
        private final TextView selectionNameTV;
        private final ImageView deleteButton;
        private String currentSelection;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            selectionNameTV = itemView.findViewById(R.id.selectionName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            selectionNameTV.setOnClickListener(v -> updateDDDescription(currentSelection));
            deleteButton.setOnClickListener(v -> deleteItemFromDB(currentSelection));
        }

        public void bindItem(String item) {
            currentSelection = item;
            selectionNameTV.setText(item);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), getString(R.string.toast_clicked_on) + currentSelection, Toast.LENGTH_SHORT).show();

            if (currentSelection.equals("Custom")) {
                Intent customIntent = new Intent(getActivity(), CustomDataSanitizationActivity.class);
                customIntent.putExtra("selectedDataDissolveMethod", currentSelection);
                startActivityForResult(customIntent, 1);
            } else {
                Intent intent = new Intent(getActivity(), DataDissolveActivity.class);
                intent.putExtra("selectedDataDissolveMethod", currentSelection);
                startActivity(intent);
            }
        }
    }

    private void updateDDDescription(String currentSelection) {
        switch (currentSelection) {
            case "Default":
                ddDescription.setText(getString(R.string.description_default));
                buttonPerformDD.setVisibility(View.VISIBLE);
                break;
            case "Gutmann":
                ddDescription.setText(getString(R.string.description_gutmann));
                buttonPerformDD.setVisibility(View.VISIBLE);
                break;
            case "DoD":
                ddDescription.setText(getString(R.string.description_dod));
                buttonPerformDD.setVisibility(View.VISIBLE);
                break;
            case "Schneier":
                ddDescription.setText(getString(R.string.description_schneier));
                buttonPerformDD.setVisibility(View.VISIBLE);
                break;
            case "Custom":
                ddDescription.setText(getString(R.string.description_custom));
                buttonPerformDD.setVisibility(View.VISIBLE);
                break;
            default:
                ddDescription.setText(getString(R.string.description_default));
                buttonPerformDD.setVisibility(View.INVISIBLE);
                break;
        }
        selectedItem = currentSelection;
    }

    private void launchDataDissolveActivity(String currentSelection) {
        Intent intent = new Intent(getActivity(), DataDissolveActivity.class);
        intent.putExtra("selectedDataDissolveMethod", currentSelection);
        startActivity(intent);
    }

    private void deleteItemFromDB(String itemText) {
        try {
            dataSource.open();
            Log.d("ItemListFragment", "deleteItemFromDatabase (pre): " + dataSource.getListOfItems().toString());
            dataSource.deleteItem(itemText);
            Log.d("ItemListFragment", "deleteItemFromDatabase (post): " + dataSource.getListOfItems().toString());
            dataSource.close();
            refreshList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}