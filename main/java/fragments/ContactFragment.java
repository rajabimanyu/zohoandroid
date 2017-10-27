package fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kct.contacts.R;

import java.util.ArrayList;
import java.util.List;

import adapters.ContactAdapter;
import data.Contact;
import data.DBHelper;

/**
 * Created by Rinik on 13/10/17.
 */

public class ContactFragment extends Fragment {
    RecyclerView recyclerView;
    List<Contact> contactList;
    ContactAdapter contactAdapter;
    DBHelper db;
    public ContactFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact, container, false);
        // Inflate the layout for this fragment
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        db=new DBHelper(getActivity());
        contactList=db.getAllContacts();
        Log.d("COUNT",contactList.size()+"");
        contactAdapter=new ContactAdapter(contactList,getActivity(),getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        contactList=db.getAllContacts();
        contactAdapter.refresh(contactList);
    }

    public void refresh() {
        contactList=db.getAllContacts();
        contactAdapter.refresh(contactList);
    }
}
