package com.crazy.cooking.app.crazycooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy.cooking.app.crazycooking.model.MyUserIngredient;
import com.crazy.cooking.app.crazycooking.model.MyUserJson;
import com.crazy.cooking.app.crazycooking.model.MyUserSteps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    MyUserJson myUserJson;
    MyUserSteps myUserSteps;
    ArrayList<MyUserSteps> stepsdetails = new ArrayList<>();
    ArrayList<MyUserIngredient> ingredientdetails = new ArrayList<>();
    String rName;
    String ingredients;
    String steps;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);


        Intent intent = getIntent();
        if (intent.getParcelableExtra("ingredientsInfo") != null) {
            myUserJson = intent.getParcelableExtra("ingredientsInfo");
            rName = myUserJson.getName();
            ingredients = myUserJson.getIngredientsMain();
            steps = myUserJson.getStepsMain();
        }

        if (steps != null) {
            try {
                JSONArray jsonArray = new JSONArray(steps);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String shortDescription = jsonObject.optString("shortDescription");
                    String description = jsonObject.optString("description");
                    String videoURL = jsonObject.optString("videoURL");
                    String thumbnailURL = jsonObject.optString("thumbnailURL");
                    MyUserSteps myUserSteps = new MyUserSteps(id, shortDescription, description, videoURL, thumbnailURL);
                    stepsdetails.add(myUserSteps);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (ingredients != null) {
            try {
                JSONArray jsonArray = new JSONArray(ingredients);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    double quantity = jsonObject.getDouble("quantity");
                    String measure = jsonObject.getString("measure");
                    String ingredient = jsonObject.getString("ingredient");
                    MyUserIngredient myUserIngredient = new MyUserIngredient(quantity, measure, ingredient);
                    ingredientdetails.add(myUserIngredient);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(rName);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, stepsdetails, ingredientdetails, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<MyUserSteps> mValues;
        private final ArrayList<MyUserIngredient> mValuesIngredients;
        private final boolean mTwoPane;


        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<MyUserSteps> items,
                                      ArrayList<MyUserIngredient> itemIngredient,
                                      boolean twoPane) {
            mValues = items;
            mValuesIngredients = itemIngredient;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //holder.mIdView.setText(mValues.get(position).id);
            if (position == 0) {
                holder.mContentView.setText("Ingredients");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelableArrayList("keerthi", mValuesIngredients);
                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();

                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ItemDetailActivity.class);
                            intent.putExtra("keerthi", mValuesIngredients);
                            context.startActivity(intent);

                        }
                    }
                });
            } else {
                if (position != 0) {
                    holder.mContentView.setText(mValues.get(position - 1).getId() + " ." + mValues.get(position - 1).getShortDescription());
                    holder.itemView.setTag(mValues.get(position - 1));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUserSteps myUserSteps = (MyUserSteps) v.getTag();
                            if (mTwoPane) {
                                Bundle arguments = new Bundle();
                                arguments.putParcelable("keerthisteps", myUserSteps);
                                ItemDetailFragment fragment = new ItemDetailFragment();
                                fragment.setArguments(arguments);
                                mParentActivity.getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.item_detail_container, fragment)
                                        .commit();
                            } else {
                                Context context = v.getContext();
                                Intent intent = new Intent(context, ItemDetailActivity.class);
                                intent.putExtra("keerthisteps", myUserSteps);
                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    holder.mContentView.setText(mValues.get(position - 1).getShortDescription());
                }
            }
            //holder.itemView.setTag(mValues.get(position));
            //holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            //final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                //  mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
