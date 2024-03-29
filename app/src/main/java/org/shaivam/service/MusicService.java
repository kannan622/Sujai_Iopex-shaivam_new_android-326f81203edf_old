package org.shaivam.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.shaivam.playback.MediaPlayerHolder;
import org.shaivam.playback.MusicNotificationManager;

public class MusicService extends Service {
    private final IBinder mIBinder = new LocalBinder();

    private MediaPlayerHolder mMediaPlayerHolder;

    private MusicNotificationManager mMusicNotificationManager;

    private boolean sRestoredFromPause = false;

    public final boolean isRestoredFromPause() {
        return sRestoredFromPause;
    }

    public void setRestoredFromPause(boolean restore) {
        sRestoredFromPause = restore;
    }

    public final MediaPlayerHolder getMediaPlayerHolder() {
        return mMediaPlayerHolder;
    }

    public MusicNotificationManager getMusicNotificationManager(Context context1) {
        return mMusicNotificationManager;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mMediaPlayerHolder.registerNotificationActionsReceiver(false);
        mMusicNotificationManager = null;
        mMediaPlayerHolder.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (mMediaPlayerHolder == null) {
            mMediaPlayerHolder = new MediaPlayerHolder(this);
            mMusicNotificationManager = new MusicNotificationManager(this);
            mMediaPlayerHolder.registerNotificationActionsReceiver(true);
        }
        return mIBinder;
    }

    public class LocalBinder extends Binder {
        public MusicService getInstance(Context context1) {
            return MusicService.this;
        }
    }
}