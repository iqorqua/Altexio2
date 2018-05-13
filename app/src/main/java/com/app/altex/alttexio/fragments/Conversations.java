package com.app.altex.alttexio.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.altex.alttexio.MainActivity;
import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.UserListItem;
import com.app.altex.alttexio.firebaseandroid.adapter.ChatFirebaseAdapter;
import com.app.altex.alttexio.firebaseandroid.adapter.ClickListenerChatFirebase;
import com.app.altex.alttexio.firebaseandroid.model.ChatModel;
import com.app.altex.alttexio.firebaseandroid.model.UserModel;
import com.app.altex.alttexio.firebaseandroid.util.Util;
import com.app.altex.alttexio.firebaseandroid.view.LoginActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static com.app.altex.alttexio.fragments.UsersDialog.mFirebaseDatabaseReference;
import static com.app.altex.alttexio.fragments.UsersDialog.mFirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Conversations extends Fragment implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ClickListenerChatFirebase {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    private static final int IMAGE_CAMERA_REQUEST = 2;
    private static final int PLACE_PICKER_REQUEST = 3;

    static final String TAG = MainActivity.class.getSimpleName();
    static String CHAT_REFERENCE = "chatmodel2";
    static UserListItem speaking_to = null;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    public static GoogleApiClient mGoogleApiClient;

    //CLass Model
    private UserModel userModel;

    //Views UI
    private RecyclerView rvListMessage;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView btSendMessage,btEmoji;
    private EmojiconEditText edMessage;
    private View contentRoot;
    private EmojIconActions emojIcon;
    private View _fregment;

    //File
    private File filePathImageCamera;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public Conversations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.activity_main_message, container, false);
        // Inflate the layout for this fragment
        if (speaking_to != null){
            ((TextView)result.findViewById(R.id.dialog_txt_name)).setText(speaking_to.name);
            // ((TextView)view.findViewById(R.id.user_txt_mail)).setText(user.mail);
            Picasso.get().load(speaking_to.imgUrl).centerInside().fit().into((ImageView)result.findViewById(R.id.dialog_img_avatar));
        }
        if (!Util.verificaConexao(result.getContext())) {
            Util.initToast(result.getContext(), "Lost internet connection...");
            ;
        } else {
            contentRoot = result.findViewById(R.id.contentRoot);
            edMessage = (EmojiconEditText)result.findViewById(R.id.editTextMessage);
            btSendMessage = (ImageView)result.findViewById(R.id.buttonMessage);
            btSendMessage.setOnClickListener(this);
            btEmoji = (ImageView)result.findViewById(R.id.buttonEmoji);
            emojIcon = new EmojIconActions(result.getContext(),contentRoot,edMessage,btEmoji);
            emojIcon.ShowEmojIcon();
            rvListMessage = (RecyclerView)result.findViewById(R.id.messageRecyclerView);
            mLinearLayoutManager = new LinearLayoutManager(result.getContext());
            mLinearLayoutManager.setStackFromEnd(true);

            if (mFirebaseUser == null){
                startActivity(new Intent(result.getContext(), LoginActivity.class));
                //finish();
            }else{
                userModel = new UserModel(mFirebaseUser.getDisplayName(), mFirebaseUser.getPhotoUrl().toString(), mFirebaseUser.getUid() );
                mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

                final ChatFirebaseAdapter firebaseAdapter = new ChatFirebaseAdapter(mFirebaseDatabaseReference.child(CHAT_REFERENCE),userModel.getName(),this);
                firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        super.onItemRangeInserted(positionStart, itemCount);
                        int friendlyMessageCount = firebaseAdapter.getItemCount();
                        int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastVisiblePosition == -1 ||
                                (positionStart >= (friendlyMessageCount - 1) &&
                                        lastVisiblePosition == (positionStart - 1))) {
                            rvListMessage.scrollToPosition(positionStart);
                        }
                    }
                });
                rvListMessage.setLayoutManager(mLinearLayoutManager);
                rvListMessage.setAdapter(firebaseAdapter);
            }
            mGoogleApiClient = new GoogleApiClient.Builder(result.getContext())
                    .enableAutoManage(this.getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonMessage:
                sendMessageFirebase();
                break;
        }
    }

    private void sendMessageFirebase(){
        ChatModel model = new ChatModel(userModel,edMessage.getText().toString(), Calendar.getInstance().getTime().getTime()+"",null);
        mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(model);
        edMessage.setText(null);
    }

    @Override
    public void clickImageChat(View view, int position, String nameUser, String urlPhotoUser, String urlPhotoClick) {

    }

    @Override
    public void clickImageMapChat(View view, int position, String latitude, String longitude) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Util.initToast(this.getActivity(),"Google Play Services error.");
    }
}
