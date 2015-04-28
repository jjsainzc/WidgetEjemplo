package com.example.widgetejemplo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		for (int i = 0; i < appWidgetIds.length; i++) {
			// ID del widget actual

			// Obtenemos la hora actual
			Date hora = new Date();

			int widgetId = appWidgetIds[i];

			// Actualizamos el widget actual
			RemoteViews controles = new RemoteViews(context.getPackageName(),
					R.layout.activity_widget);

			Intent intent2 = new Intent(context, Principal.class);
			intent2.putExtra("ID", widgetId);
			
			PendingIntent pendingIntent2 = PendingIntent.getActivity(context,
					widgetId, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

			
			
			controles.setOnClickPendingIntent(R.id.frameLayout, pendingIntent2);

			// Actualizamos la hora en el control del widget
			controles.setTextViewText(R.id.resultado1, hora.toString());

			// Notificamos al manager de la actualizacion del widget actual
			appWidgetManager.updateAppWidget(widgetId, controles);
		}

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		RemoteViews controles = new RemoteViews(context.getPackageName(),
				R.layout.activity_widget);
		
		AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

		int[] ids = widgetManager.getAppWidgetIds(new ComponentName(context,
				Widget.class));

		Log.i("INTENT", intent.getAction());
		
		try {
			
			CharSequence intentData = intent.getCharSequenceExtra("message");
			controles.setTextViewText(R.id.resultado1, intentData.toString());
			widgetManager.updateAppWidget(ids, controles);
			
		} catch (NullPointerException e) {
			onUpdate(context, widgetManager, ids);
		}

		super.onReceive(context, intent);
	}

}
