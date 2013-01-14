package il.co.thepoosh.greendao.chat.sample.db;

import il.co.thepoosh.greendao.chat.sample.model.ConversationDao;
import il.co.thepoosh.greendao.chat.sample.model.DaoMaster;
import il.co.thepoosh.greendao.chat.sample.model.DaoSession;
import il.co.thepoosh.greendao.chat.sample.model.MessageDao;
import il.co.thepoosh.greendao.chat.sample.model.ConversationDao.Properties;
import il.co.thepoosh.greendao.chat.sample.model.DaoMaster.DevOpenHelper;

import java.util.Date;
import java.util.List;

import me.glide.greendao.chattest.db.Conversation;
import me.glide.greendao.chattest.db.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "GreenDaoTestDB";

    private static Context mContext;

    private static SQLiteDatabase database;
    private static DaoMaster master;
    private static DaoSession session;
    private static DevOpenHelper helper;

    private static ConversationDao mConversationDao;
    private static MessageDao mMessageDao;

    public static void init(Context context) {
	mContext = context;

	helper = new DevOpenHelper(mContext, DB_NAME, null);
	database = helper.getWritableDatabase();
	master = new DaoMaster(database);
	session = master.newSession();

	mConversationDao = session.getConversationDao();
	mMessageDao = session.getMessageDao();
	Log.i(TAG, "Database was created and a session is now active");
    }

    public static ConversationDao getConversationDao() {
	return mConversationDao;
    }

    public static MessageDao getMessageDao() {
	return mMessageDao;
    }

    public static void parseMessageList(final JSONArray messageList, final Conversation conv) {
	session.runInTx(new Runnable() {

	    @Override
	    public void run() {
		for (int i = 0, numberOfMessages = messageList.length(); i < numberOfMessages; i++) {
		    try {
			JSONObject singleMessage = messageList.getJSONObject(i);
			parseSingleMessage(singleMessage, conv);
		    } catch (JSONException e) {
			Log.e(TAG,
				"there was an error parsing the message list");
			e.printStackTrace();
		    }
		}
	    }
	});
    }

    public static void parseSingleMessage(JSONObject singleMessage, Conversation conv)
	    throws JSONException {
	
	Message msg = new Message();
	msg.setDataFromJson(singleMessage);
	if (conv != null) {
	    if (conv.getId() == null) {
		throw new RuntimeException("Conversation not persisted: "
			+ conv);
	    }
	    msg.setConversation(conv);
	    mMessageDao.insert(msg);
	    conv.resetMessageList();
	    Log.d(TAG, "message insreted!");
	}
    }

   
}
