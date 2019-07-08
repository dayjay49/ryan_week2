package com.example.androidauthpostgresqlnodejs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidauthpostgresqlnodejs.Retrofit.RetroCallback;
import com.example.androidauthpostgresqlnodejs.Retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Tab2Fragment extends Fragment {
    public RecyclerViewAdapter adapter;

    //for recyclerView

    final int ADD_CONTACT = 3;
    final int ADD_CONTACT_PHOTO = 4;
    String user_Email = "";

    View view;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //for ArrayList
    TextView personName, personNumber, tvData;
    CircleImageView photo;
    //RetrofitClient retrofitClient;

    public ArrayList<Contact_Data> contactDataList = new ArrayList<>();

    public Tab2Fragment() {
    }

//    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /////////////Get login-ed user email////////////////////
        user_Email = MainActivity.login_user.getEmail();

        view = inflater.inflate(R.layout.fragment_tab2, container, false);
        FloatingActionButton add_fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        FloatingActionButton get_fab = (FloatingActionButton) view.findViewById(R.id.get);

        personName = (TextView) view.findViewById(R.id.name);
        personNumber = (TextView) view.findViewById(R.id.number);
        photo = (CircleImageView) view.findViewById(R.id.imageView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        /////////////////////////////////////////////////////////////////////////////
        //Get contacts from JSONArray and add it into adpater.
        ///////////////////////////////////////////////////////////////////////////
//        final List<Contact_Data> contactDataList = MainActivity.contactList;
        for (int i = 0; i < MainActivity.contactList.size(); i++) {
            adapter.addItem(MainActivity.contactList.get(i));
        }

        //////////////////////////////////////////////////////////////////////////////////////
        //when click delete button after longclick, delete item and update adapter
        //////////////////////////////////////////////////////////////////////////////////////
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, int request_code) {

                final String number_to_delete = MainActivity.contactList.get(position).getPhone_number();
                adapter.deleteItem(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
//                contactDataList.remove(position);
                //3151875431

                //Initialize Service
                final RetrofitClient retrofitClient;
                retrofitClient = RetrofitClient.getInstance(getContext()).createBaseApi();

                retrofitClient.deleteContact(user_Email, number_to_delete, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        Toast.makeText(getActivity(), "Contact removed from user", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code) {
                        Toast.makeText(getActivity(), "Code: " + code, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////
        //Pressing floatingbutton that moves to new activity AddContact
        //////////////////////////////////////////////////////////////////////////////////////////
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddContact.class);
                intent.putExtra("current_user_email", user_Email);
                startActivityForResult(intent, ADD_CONTACT);
            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }
    /////////////////////////////////////////////////////////////////
    //After done with getting new contact components(name, number, photo),
    //Puts it in the adapter and view and show
    /////////////////////////////////////////////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case ADD_CONTACT:
                if (resultCode == RESULT_OK) {
                    String str_name = intent.getStringExtra("str_name");
                    String str_number = intent.getStringExtra("str_number");
                    //String str_photo = intent.getStringExtra("str_photo");
//                    if (str_photo != null) {
//                        byte[] decodedByteArray = Base64.decode(str_photo, Base64.NO_WRAP);
//                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
//                        Contact_Data contactData = new Contact_Data();
//                        contactData.setContact_name(str_name);
//                        contactData.setPhone_number(str_number);
//                        //contactData.setPhoto(decodedBitmap);
//                        contactDataList.add(contactData);
//                        adapter.addItem(contactData);
//                        adapter.notifyDataSetChanged();
//                    } else {
//
//                    }
                    Contact_Data contactData = new Contact_Data();
                    contactData.setContact_name(str_name);
                    contactData.setPhone_number(str_number);
                    MainActivity.contactList.add(contactData);
                    adapter.addItem(contactData);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /////////////////////////////////////////////////////////////
    // Changes JSONArray to ArrayList.
    // Needed in using datalist to get data with components from JSONArray
    ////////////////////////////////////////////////////////////////
//    public ArrayList<Contact_Data> JSONtoArray(JSONArray contactList) {
//        ArrayList<Contact_Data> changedList = new ArrayList<Contact_Data>();
//        for (int i = 0; i < contactList.length(); i++) {
//            try {
//                Contact_Data contactData = new Contact_Data();
//
//                String user_Name = contactList.getJSONObject(i).getString("name");
//                contactData.setContact_name(user_Name);
//
//                String user_PhoneNumber = contactList.getJSONObject(i).getString("number");
//                contactData.setPhone_number(user_PhoneNumber);
//
//                /*Long user_PersonId = contactlist.getJSONObject(i).getLong("person_id");
//                contactData.setPerson_id(user_PersonId);
//
//                Long user_PhotoId = contactlist.getJSONObject(i).getLong("photo_id");
//                contactData.setPerson_id(user_PhotoId);
//
//                Bitmap photo = loadContactPhoto(getActivity().getContentResolver(), user_PersonId, user_PhotoId);
//                if (photo == null) {
//                    Drawable drawable = getResources().getDrawable(R.drawable.ryan_contact_photo);
//                    photo = ((BitmapDrawable)drawable).getBitmap();
//                }
//                contactData.setPhoto(photo);*/
//
//                changedList.add(contactData);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return changedList;
//
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //Needed to load photo
    //////////////////////////////////////////////////////////////////////////////////////////
//    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
//        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
//        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
//        if (input != null)
//            return resizingBitmap(BitmapFactory.decodeStream(input));
//        else
//            Log.d("PHOTO", "first try failed to load photo");
//
//        byte[] photoBytes = null;
//        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
//        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
//        try {
//            if (c.moveToFirst())
//                photoBytes = c.getBlob(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            c.close();
//        }
//
//        if (photoBytes != null)
//            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
//        else
//            Log.d("PHOTO", "second try also failed");
//        return null;
//    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Resize photos to avoid crashing when putting into the contact
    //////////////////////////////////////////////////////////////////////////////////////////////
//    public Bitmap resizingBitmap(Bitmap oBitmap) {
//        if (oBitmap == null)
//            return null;
//        float width = oBitmap.getWidth();
//        float height = oBitmap.getHeight();
//        float resizing_size = 120;
//        Bitmap rBitmap = null;
//        if (width > resizing_size) {
//            float mWidth = (float) (width / 100);
//            float fScale = (float) (resizing_size / mWidth);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//        } else if (height > resizing_size) {
//            float mHeight = (float) (height / 100);
//            float fScale = (float) (resizing_size / mHeight);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//        }
//
//        Log.d("rBitmap : " + width + ", " + height, "photo size");
//        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int) height, true);
//        return rBitmap;
//
//    }
}