package gr.mobap.sweetstories.widget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViewsService;

import gr.mobap.sweetstories.models.Recipes;
import gr.mobap.sweetstories.utilities.Constants;

@SuppressWarnings("ALL")
public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Recipes recipe = null;
        //get the Recipe
        if (intent.hasExtra("BUNDLE")) {
            Bundle b = intent.getBundleExtra("BUNDLE");
            recipe = b.getParcelable(Constants.RECIPE);

        }
        return new ListRemoteViewsFactory(this.getApplicationContext(), recipe);
    }
}

