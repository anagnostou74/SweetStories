package gr.mobap.sweetstories.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import gr.mobap.sweetstories.R;

import static gr.mobap.sweetstories.ui.activities.DetailsActivity.title;
import static gr.mobap.sweetstories.utilities.Constants.KEY_CURRENT_WINDOW;
import static gr.mobap.sweetstories.utilities.Constants.KEY_PLAYBACK;
import static gr.mobap.sweetstories.utilities.Constants.KEY_PLAY_WHEN_READY;


public class StepDetailsFragment extends Fragment {

    @BindView(R.id.step_video_player)
    PlayerView mVideoPlayer;
    @BindView(R.id.step_video)
    TextView mStepVideo;
    @BindView(R.id.step_description)
    TextView mStepDescription;

    private SimpleExoPlayer mPlayer;
    private String description, video;
    private int mCurrentWindow;
    private long mPlaybackPosition;
    private boolean mPlayWhenReady;

    public StepDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }

        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(KEY_CURRENT_WINDOW);
            mCurrentWindow = savedInstanceState.getInt(KEY_CURRENT_WINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            initializePlayer();
        }

        if (description != null) {
            mStepDescription.setText(description);
        }
        if (video != null) {
            if (TextUtils.isEmpty(video) || video.isEmpty()) {
                mStepVideo.setText(getResources().getText(R.string.no_video));
                mStepVideo.setVisibility(View.VISIBLE);
                mVideoPlayer.setVisibility(View.INVISIBLE);
            } else {
                mVideoPlayer.setVisibility(View.VISIBLE);
                mStepVideo.setVisibility(View.GONE);
                mPlayWhenReady = true;
                initializePlayer();
            }
        } else {
            mStepVideo.setText(getResources().getText(R.string.no_video));
            mStepVideo.setVisibility(View.VISIBLE);
            mVideoPlayer.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(KEY_CURRENT_WINDOW);
            mCurrentWindow = savedInstanceState.getInt(KEY_CURRENT_WINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            initializePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_PLAYBACK, mPlaybackPosition);
        outState.putInt(KEY_CURRENT_WINDOW, mCurrentWindow);
        outState.putBoolean(KEY_PLAY_WHEN_READY, mPlayWhenReady);
    }

    private void initializePlayer() {
        if (mPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();

            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            mVideoPlayer.setPlayer(mPlayer);
        }

        String user = Util.getUserAgent(getActivity(), String.valueOf(getResources().getText(R.string.app_name)));

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this.getContext(), user);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(video));

        mPlayer.prepare(mediaSource, true, false);
        mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        mPlayer.setPlayWhenReady(mPlayWhenReady);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            initializePlayer();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
