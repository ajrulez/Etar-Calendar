package com.android.calendar.alerts;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.CalendarContract;
import android.widget.Toast;

import com.android.calendar.widget.CalendarAppWidgetProvider;
import com.android.calendar.widget.CalendarAppWidgetService;

@TargetApi(Build.VERSION_CODES.N)
public class ProviderChangedReceiverAsJob extends JobService {
    private static final int mJobId = 1005; //Job Id
    final Handler mHandler = new Handler(); //Just to display Toasts

    public static void schedule(Context oContext) {
        ComponentName oComponentName = new ComponentName(oContext, ProviderChangedReceiverAsJob.class);
        JobInfo.Builder oJobInfoBuilder = new JobInfo.Builder(mJobId, oComponentName);
        final Uri CALENDAR_URI = Uri.parse("content://" + CalendarContract.AUTHORITY + "/");
        oJobInfoBuilder.addTriggerContentUri(new JobInfo.TriggerContentUri(CalendarContract.CONTENT_URI, JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS));
        oJobInfoBuilder.addTriggerContentUri(new JobInfo.TriggerContentUri(CALENDAR_URI, 0));
        JobScheduler jobScheduler = (JobScheduler) oContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(oJobInfoBuilder.build());
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        //Toast.makeText(getApplicationContext(), "Update The widget!", Toast.LENGTH_SHORT).show();

        //TODO: Here should be the update of the widgets.


        schedule(this); //Reschedule to receive future changes
        return (false);
    }


    @Override
    synchronized public boolean onStopJob(JobParameters params) {
        return (false);
    }

}