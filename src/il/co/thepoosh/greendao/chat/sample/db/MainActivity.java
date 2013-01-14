package il.co.thepoosh.greendao.chat.sample.db;

import il.co.thepoosh.greendao.chat.sample.R;

import java.util.Date;

import me.glide.greendao.chattest.db.Conversation;
import me.glide.greendao.chattest.db.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    private JSONArray data;

    private static int messageCounter = 6;
    private Conversation conv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	DatabaseHelper.init(getApplicationContext());
	initUI();
	try {
	    conv = new Conversation((long) 16);
	    conv.setDateCreated(new Date());
	    DatabaseHelper.getConversationDao().insert(conv);
	    setData();
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    private void initUI() {
	Button single = (Button) findViewById(R.id.insert_single_message);
	Button bulk = (Button) findViewById(R.id.insert_bulk_messages);

	single.setOnClickListener(this);
	bulk.setOnClickListener(this);
    }

    private void setData() throws JSONException {
	data = new JSONArray();
	JSONObject obj = new JSONObject();
	for (int i = 0; i < 6; i++) {
	    Date date = new Date();
	    obj.put("dateCreated", date.getTime());
	    obj.put("content", date.toString());
	    obj.put("parent", conv.getId());
	    data.put(obj);
	}
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.insert_single_message:
	    Message msg = createMessage();
	    msg.setConversation(conv);
	    DatabaseHelper.getMessageDao().insert(msg);
	    conv.getMessageList().add(msg);
	    break;
	case R.id.insert_bulk_messages:
	    DatabaseHelper.parseMessageList(data, conv);
	}
	Log.d("MainActivity", "the conversation has now "
		+ conv.getMessageList().size() + " messages");
    }

    private Message createMessage() {
	Message msg = new Message();
	Date now = new Date();
	msg.setDateCreated(now);
	msg.setContent(now.toString());
	return msg;
    }

}
