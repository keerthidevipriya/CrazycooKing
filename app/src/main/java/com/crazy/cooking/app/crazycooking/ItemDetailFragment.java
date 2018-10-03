package com.crazy.cooking.app.crazycooking;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.crazy.cooking.app.crazycooking.dummy.DummyContent;
import com.crazy.cooking.app.crazycooking.model.MyUserIngredient;
import com.crazy.cooking.app.crazycooking.model.MyUserSteps;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    public static boolean check, readyplay;
    public long cpos;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    ArrayList<MyUserIngredient> ingredientArrayListfragment = null;
    MyUserSteps stepsArrayListfragment = null;
    SimpleExoPlayerView recipieVideoView;
    SimpleExoPlayer reciepieVideoPlayer;
    // MyUserSteps myUserSteps;
    RelativeLayout relativeLayout;
    ImageView thumbimgView;
    String videourl = "";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        onCreate(savedInstanceState);
        relativeLayout = rootView.findViewById(R.id.id_second_relative);
        // Show the dummy content as text in a TextView.
       /* if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }*/

        recipieVideoView = (SimpleExoPlayerView) rootView.findViewById(R.id.reciepie_video);
        thumbimgView = rootView.findViewById(R.id.id_thumb);
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (getArguments().getParcelableArrayList("keerthi") != null) {
            ingredientArrayListfragment = getArguments().getParcelableArrayList("keerthi");
            if (appBarLayout != null) {
                appBarLayout.setTitle("Ingredients");
                check = true;
            }
            for (int i = 0; i < ingredientArrayListfragment.size(); i++) {
                TextView tv = rootView.findViewById(R.id.item_detail);
                tv.append((i + 1) + "." + ingredientArrayListfragment.get(i).getIngredient() + "\t");
                tv.append("Q: " + ingredientArrayListfragment.get(i).getQuantity() + "\t");
                tv.append("M: " + ingredientArrayListfragment.get(i).getMeasure() + "\n\n");
            }
        }

        if (getArguments().getParcelable("keerthisteps") != null) {
            stepsArrayListfragment = getArguments().getParcelable("keerthisteps");

            if (appBarLayout != null) {
                appBarLayout.setTitle(stepsArrayListfragment.getShortDescription());
                check = true;
            }

            TextView textView = rootView.findViewById(R.id.item_detail);
            textView.append(stepsArrayListfragment.getDescription());

            if (!stepsArrayListfragment.getVideoURL().isEmpty()) {
                videourl = stepsArrayListfragment.getVideoURL();
                recipieVideoView.setVisibility(View.VISIBLE);
                playVideoSetting();
            } else if (!stepsArrayListfragment.getThumbnailURL().isEmpty()) {
                videourl = stepsArrayListfragment.getThumbnailURL();
                Toast.makeText(getActivity(), videourl, Toast.LENGTH_SHORT).show();
                recipieVideoView.setVisibility(View.VISIBLE);
                playVideoSetting();
                if (stepsArrayListfragment.getThumbnailURL().isEmpty()) {
                    Glide.with(this).load(videourl).into(thumbimgView);
                }
            } else {
                Toast.makeText(activity, "No video Available", Toast.LENGTH_SHORT).show();
                recipieVideoView.setVisibility(View.GONE);
            }
        }
            /*if (appBarLayout!=null){
                appBarLayout.setTitle(stepsArrayListfragment.getShortDescription());
                check = true;
            }*/
           /*for (int i=0;i<stepsArrayListfragment.size(); i++){
               TextView textView = rootView.findViewById(R.id.item_detail);
               textView.setText(stepsArrayListfragment.get(i).getShortDescription()+"\n");
           }*/


        if (savedInstanceState != null) {
            cpos = savedInstanceState.getLong("positionrunning");
            reciepieVideoPlayer.seekTo(cpos);
            readyplay = savedInstanceState.getBoolean("playfix");
            reciepieVideoPlayer.setPlayWhenReady(readyplay);
        }
        return rootView;
    }

    public void playVideoSetting() {
        if (reciepieVideoPlayer == null && videourl != null) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new
                    AdaptiveTrackSelection.Factory(bandwidthMeter));

            reciepieVideoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            Uri videoUri = Uri.parse(videourl);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ReciepieVideo");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

            recipieVideoView.setPlayer(reciepieVideoPlayer);
            reciepieVideoPlayer.prepare(mediaSource);
            reciepieVideoPlayer.setPlayWhenReady(false);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (reciepieVideoPlayer != null) {
            outState.putLong("positionrunning", reciepieVideoPlayer.getCurrentPosition());
            outState.putBoolean("playfix", reciepieVideoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (reciepieVideoPlayer != null) {
            reciepieVideoPlayer.stop();
            reciepieVideoPlayer.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reciepieVideoPlayer != null) {
            reciepieVideoPlayer.stop();
            reciepieVideoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (reciepieVideoPlayer != null) {
            reciepieVideoPlayer.stop();
            reciepieVideoPlayer.release();
        }
    }
}
