package me.glide.greendao.chattest.db;


import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;
// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

import il.co.thepoosh.greendao.chat.sample.model.ConversationDao;
import il.co.thepoosh.greendao.chat.sample.model.DaoSession;
import il.co.thepoosh.greendao.chat.sample.model.MessageDao;

// KEEP INCLUDES - put your custom includes here
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

// KEEP INCLUDES END
/**
 * Entity mapped to table MESSAGE.
 */
public class Message {

    private Long id;
    private java.util.Date dateCreated;
    private String content;
    private Long parent;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient MessageDao myDao;

    private Conversation conversation;
    private Long conversation__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Message() {
    }

    public Message(Long id) {
	this.id = id;
    }

    public Message(Long id, java.util.Date dateCreated, String content,
	    Long parent) {
	this.id = id;
	this.dateCreated = dateCreated;
	this.content = content;
	this.parent = parent;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
	this.daoSession = daoSession;
	myDao = daoSession != null ? daoSession.getMessageDao() : null;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public java.util.Date getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(java.util.Date dateCreated) {
	this.dateCreated = dateCreated;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public Long getParent() {
	return parent;
    }

    public void setParent(Long parent) {
	this.parent = parent;
    }

    /** To-one relationship, resolved on first access. */
    public Conversation getConversation() {
	if (conversation__resolvedKey == null
		|| !conversation__resolvedKey.equals(parent)) {
	    if (daoSession == null) {
		throw new DaoException("Entity is detached from DAO context");
	    }
	    ConversationDao targetDao = daoSession.getConversationDao();
	    conversation = targetDao.load(parent);
	    conversation__resolvedKey = parent;
	}
	return conversation;
    }

    public void setConversation(Conversation conversation) {
	this.conversation = conversation;
	parent = conversation == null ? null : conversation.getId();
	conversation__resolvedKey = parent;
    }

    /**
     * Convenient call for {@link AbstractDao#delete(Object)}. Entity must
     * attached to an entity context.
     */
    public void delete() {
	if (myDao == null) {
	    throw new DaoException("Entity is detached from DAO context");
	}
	myDao.delete(this);
    }

    /**
     * Convenient call for {@link AbstractDao#update(Object)}. Entity must
     * attached to an entity context.
     */
    public void update() {
	if (myDao == null) {
	    throw new DaoException("Entity is detached from DAO context");
	}
	myDao.update(this);
    }

    /**
     * Convenient call for {@link AbstractDao#refresh(Object)}. Entity must
     * attached to an entity context.
     */
    public void refresh() {
	if (myDao == null) {
	    throw new DaoException("Entity is detached from DAO context");
	}
	myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    public void setDataFromJson(JSONObject msg) throws JSONException {
	if (msg.has("dateCreated")) {
	    this.dateCreated = new Date(msg.getLong("dateCreated") * 1000);
	}
	if (msg.has("id")) {
	    this.id = msg.getLong("id");
	}
	if (msg.has("content")) {
	    this.content = msg.getString("content");
	}
    }
    // KEEP METHODS END

}
