package hu.mobilalkfejl.minesweeper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class StartGameLoader extends AsyncTaskLoader<Map> {

    StartGameLoader(Context context){
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Map loadInBackground() {
        return new Map();
    }
}
