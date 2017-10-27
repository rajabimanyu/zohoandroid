package adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kct.contacts.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import data.Contact;

/**
 * Created by Rinik on 13/10/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private List<Contact> contactList;
    private Context context;
    private Activity activity;
    public ContactAdapter(List<Contact> contactList, Context context, Activity activity) {
        this.contactList = contactList;
        this.context = context;
        this.activity=activity;
    }

    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.MyViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.name.setText(contact.name);
        holder.number.setText(contact.phone);
        if (contact.isHavingPhoto) {
            Log.d("FILE",""+position);
            holder.profile.setImageURI(contact.path);
        }
    }



    public void refresh(List<Contact> contactList) {
        this.contactList.clear();
        this.contactList.addAll(contactList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;
        public SimpleDraweeView profile;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            number = (TextView) view.findViewById(R.id.number);
            profile = (SimpleDraweeView) view.findViewById(R.id.profile);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + contactList.get(position).phone));
                        context.startActivity(callIntent);

                }
            });
        }

    }
}
