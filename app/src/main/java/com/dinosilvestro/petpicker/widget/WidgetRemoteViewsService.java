package com.dinosilvestro.petpicker.widget;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dinosilvestro.petpicker.R;
import com.dinosilvestro.petpicker.model.PetContract;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                data = getContentResolver().query(
                        PetContract.PetEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_list_item);

                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(getApplicationContext())
                            .load(data.getString(data.getColumnIndex("PET_MEDIA")))
                            .resize(400, 400)
                            .centerCrop()
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                remoteViews.setImageViewBitmap(R.id.widget_pet_media, bitmap);

                remoteViews.setTextViewText(R.id.widget_pet_name, data.getString(data.getColumnIndex
                        ("PET_NAME")));

                remoteViews.setTextViewText(R.id.widget_pet_status, data.getString(data.getColumnIndex(
                        "PET_STATUS")));

                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data != null && data.moveToPosition(position)) {
                    final int QUOTES_ID_COL = 0;
                    return data.getLong(QUOTES_ID_COL);
                }
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}