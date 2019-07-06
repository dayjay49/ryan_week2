package com.example.androidauthpostgresqlnodejs;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Tab2Fragment extends Fragment {
    //    private static final int RESULT_OK = -1;
    public RecyclerViewAdapter adapter;
//    private SwipeController swipeController = null;
//    public Data data;

    //for recyclerView

    final int ADD_CONTACT = 3;
    final int ADD_CONTACT_PHOTO = 4;

    View view;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //for ArrayList
    TextView personName, personNumber, tvData;
    CircleImageView photo;
    String name, number;
    Context AppContext;
    List<String> nameList = new ArrayList<>();
    List<String> numberList = new ArrayList<>();

    public ArrayList<Data> dataList = new ArrayList<>();

    public Tab2Fragment() {

    }


//    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab2, container, false);
        FloatingActionButton add_fab = (FloatingActionButton) view.findViewById(R.id.fab);
        FloatingActionButton get_fab = (FloatingActionButton) view.findViewById(R.id.get);

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
        final ArrayList<Data> dataList = JSONtoArray(getContactList1());
        for (int i = 0; i < dataList.size(); i++) {
            adapter.addItem(dataList.get(i));
        }

        get_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://143.248.39.45:3000/users");
            }
        });


        //////////////////////////////////////////////////////////////////////////////////////
        //when click delete button after longclick, delete item and update adapter
        //////////////////////////////////////////////////////////////////////////////////////
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, int request_code) {
//                removeContact(v, position);
//                dataList.remove(position);
//                adapter.notifyDataSetChanged();
//                onResume();
                adapter.deleteItem(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////
        //Pressing floatingbutton that moves to new activity AddContact
        //////////////////////////////////////////////////////////////////////////////////////////
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddContact.class);
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
                    String str_photo = intent.getStringExtra("str_photo");
                    if (str_photo != null) {
                        byte[] decodedByteArray = Base64.decode(str_photo, Base64.NO_WRAP);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
                        Data data = new Data();
                        data.setUser_Name(str_name);
                        data.setUser_phNumber(str_number);
                        data.setPhoto(decodedBitmap);
                        dataList.add(data);
                        adapter.addItem(data);
                        adapter.notifyDataSetChanged();

                    } else {

                    }

                }
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    //Get Contacts from phone in JSONObjects
    //////////////////////////////////////////////////////////////////////////////
    public JSONArray getContactList1() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts._ID,
//                ContactsContract.Contacts.PHOTO
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
        JSONArray jArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject sObject = new JSONObject();
                    sObject.put("number", cursor.getString(0));
                    sObject.put("name", cursor.getString(1));
                    sObject.put("photo_id", cursor.getLong(2));
                    sObject.put("person_id", cursor.getLong(3));
                    jArray.put(sObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return jArray;
    }

    /////////////////////////////////////////////////////////////
    // Changes JSONArray to ArrayList.
    // Needed in using datalist to get data with components from JSONArray
    ////////////////////////////////////////////////////////////////
    public ArrayList<Data> JSONtoArray(JSONArray contactlist) {
        ArrayList<Data> changedList = new ArrayList<Data>();
        for (int i = 0; i < contactlist.length(); i++) {
            try {
                Data data = new Data();

                String user_Name = contactlist.getJSONObject(i).getString("name");
                data.setUser_Name(user_Name);

                String user_PhoneNumber = contactlist.getJSONObject(i).getString("number");
                data.setUser_phNumber(user_PhoneNumber);

                Long user_PersonId = contactlist.getJSONObject(i).getLong("person_id");
                data.setPerson_id(user_PersonId);

                Long user_PhotoId = contactlist.getJSONObject(i).getLong("photo_id");
                data.setPerson_id(user_PhotoId);

                Bitmap photo = loadContactPhoto(getActivity().getContentResolver(), user_PersonId, user_PhotoId);
                if (photo == null) {
                    Drawable drawable = getResources().getDrawable(R.drawable.ryan_contact_photo);
                    photo = ((BitmapDrawable)drawable).getBitmap();
                }
                data.setPhoto(photo);

                changedList.add(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return changedList;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //Needed to load photo
    //////////////////////////////////////////////////////////////////////////////////////////
    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input != null)
            return resizingBitmap(BitmapFactory.decodeStream(input));
        else
            Log.d("PHOTO", "first try failed to load photo");

        byte[] photoBytes = null;
        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
        try {
            if (c.moveToFirst())
                photoBytes = c.getBlob(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        if (photoBytes != null)
            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
        else
            Log.d("PHOTO", "second try also failed");
        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //Resize photos to avoid crashing when putting into the contact
    //////////////////////////////////////////////////////////////////////////////////////////////
    public Bitmap resizingBitmap(Bitmap oBitmap) {
        if (oBitmap == null)
            return null;
        float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = 120;
        Bitmap rBitmap = null;
        if (width > resizing_size) {
            float mWidth = (float) (width / 100);
            float fScale = (float) (resizing_size / mWidth);
            width *= (fScale / 100);
            height *= (fScale / 100);
        } else if (height > resizing_size) {
            float mHeight = (float) (height / 100);
            float fScale = (float) (resizing_size / mHeight);
            width *= (fScale / 100);
            height *= (fScale / 100);
        }

        Log.d("rBitmap : " + width + ", " + height, "photo size");
        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int) height, true);
        return rBitmap;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //Change added contact components into JSONObject
    //New contacts will be accumulated into Data type by onActivityResult....
    //It will change data into JSONObject
    //JSONObject will be used to be posted on server
    ////////////////////////////////////////////////////////////////////////////////////////////
    public JSONObject contactToJSON(Data data) {
        JSONObject newContact = new JSONObject();
        try {
            newContact.put("name", data.getUser_Name());
            newContact.put("number", data.getUser_phNumber());
            newContact.put("photo_uri", data.getPhoto());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newContact;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //Get all contacts from server.
    /////////////////////////////////////////////////////////////////////////////////////////
    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("user_id", "androidTest");
//                jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();

                    ////////////////////////////////////////////////
//                    con.setRequestMethod("POST");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
//                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    /////////////////////////////////////////////////////////////
                    con.connect();//연결 수행

                    /////////////////////////////////////////////////////////////
//                    OutputStream outStream = con.getOutputStream();
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
                    ///////////////////////////////////////////////////////////////////////

                    //출처: https://gakari.tistory.com/entry/안드로이드-Nodejs서버로-POST방식으로-데이터를-보내기?category=414830 [가카리의 공부방]

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //Change buffer to JSONArray, JSONArray to ArrayList
                    JSONArray contactJSONArray = new JSONArray(buffer.toString());
                    ArrayList<Data> newContactList = JSONtoArray(contactJSONArray);

                    //Add newContactList to adapter and update
                    for (int i = 0; i < newContactList.size(); i++) {
                        adapter.addItem(dataList.get(i));
                    }
                    adapter.notifyDataSetChanged();

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
//                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//        }

    }


}