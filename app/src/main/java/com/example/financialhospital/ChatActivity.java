package com.example.financialhospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.financialhospital.adapter.MessageAdapter;
import com.example.financialhospital.object.ChatObj;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ChatActivity extends AppCompatActivity {
    TextView tvsend;
    EditText etMessage;
    Query query;
    private FirebaseFirestore db;
    private ListenerRegistration mChatMessageEventListener;
    private RecyclerView conversations_rv;
    private LinearLayoutManager linearLayoutManager;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tvsend = findViewById(R.id.group_chat_send_btn);
        etMessage = findViewById(R.id.group_chat_message_box);
        db = FirebaseFirestore.getInstance();
        conversations_rv = findViewById(R.id.conversations_rv);
        linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        conversations_rv.setLayoutManager(linearLayoutManager);

        FirebaseFirestore.setLoggingEnabled(true);

        query = db.collection("Chat").orderBy("timeStamp", Query.Direction.ASCENDING);


        mChatMessageEventListener = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Chat", "onEvent: Listen failed.", e);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                List <ChatObj> chatObjList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        chatObjList.add(new ChatObj(doc.getString("textMessage"),doc.getString("timeStamp") ));
                    }
                     messageAdapter = new MessageAdapter(chatObjList,ChatActivity.this);
                    conversations_rv.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                    conversations_rv.setHasFixedSize(true);
                    conversations_rv.setAdapter(messageAdapter);
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            conversations_rv.scrollToPosition(messageAdapter.getItemCount() - 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 100);

            }
        });

        tvsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMessage.getText().toString().isEmpty()) {


                    DocumentReference messageRef = db.collection("Chat").document();
                    WriteBatch batch = db.batch();
                    Map<String, Object> chatMessageObj = new HashMap<>();
                    chatMessageObj.put("textMessage", "" + etMessage.getText().toString());
                    chatMessageObj.put("timeStamp", "" + System.currentTimeMillis());
                    chatMessageObj.put("id", messageRef.getId());

                    //Create a messagemap to upload to database
                    batch.set(messageRef, chatMessageObj);

                    batch.commit()

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Chats", "DocumentSnapshot successfully written!");
                                    etMessage.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Chats", "Error writing document", e);
                                }
                            });


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                conversations_rv.scrollToPosition(messageAdapter.getItemCount() - 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 100);
                }

            }
        });
        getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (conversationsAdapter != null)
//            conversationsAdapter.stopListening();
//        new LoadAllConversation(true).cancel(true);
        try {
            mChatMessageEventListener.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}