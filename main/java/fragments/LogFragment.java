package fragments;

import android.Manifest;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kct.contacts.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.ContactAdapter;
import adapters.HistoryAdapter;
import data.Contact;
import data.DBHelper;
import data.History;

/**
 * Created by Rinik on 13/10/17.
 */

public class LogFragment extends Fragment {
    RecyclerView recyclerView;
    List<History> historyList;
    HistoryAdapter historyAdapter;
    DBHelper db;
    public LogFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_log, container, false);
        // Inflate the layout for this fragment
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        db=new DBHelper(getActivity());
        historyList=new ArrayList<>();
        load();
        this.getActivity()
                .getContentResolver()
                .registerContentObserver(
                        android.provider.CallLog.Calls.CONTENT_URI, true,
                        new MyContentObserver(new Handler()));
        return view;
    }

    private void load() {
        addIt();
        Log.d("GFCVH","GCVHBJNK");
        historyAdapter=new HistoryAdapter(historyList,getActivity(),getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);
    }

    private void addIt() {
        historyList.clear();
        Log.d("ADDE","MAE");
        Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        Log.d("CONTACT",cursor.getCount()+"");
        while (cursor.moveToNext()) {
            Log.d("CAME","--->");
            History history=new History();
            String phNumber = cursor.getString(number);
            history.setPhone(phNumber);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date dateS = new Date(Long.valueOf(callDate));
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            String dateFormatted = formatter.format(dateS);
            history.setTime(dateFormatted);
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    history.setType(0);
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    history.setType(1);
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    history.setType(2);
                    break;
            }
            Contact contact=db.isPresent(phNumber);
            if(contact!=null){
                history.setHaving(contact.isHavingPhoto);
                history.setPath(contact.path);
                history.setName(contact.name);
                history.setSaved(true);
            }else{
                history.setSaved(false);
            }
            historyList.add(history);
        }
        cursor.close();
    }
    class MyContentObserver extends ContentObserver {
        public MyContentObserver(Handler h) {
            super(h);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            Log.d("LOGGING", "MyContentObserver.onChange(" + selfChange + ")");
            super.onChange(selfChange);
            addIt();
            Log.d("COUNT",historyList.size()+"");
            historyAdapter.refresh(historyList);
            // here you ca ll the method to fill the list
        }
    }
}
