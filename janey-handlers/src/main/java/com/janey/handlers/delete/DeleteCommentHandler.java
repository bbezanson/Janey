package com.janey.handlers.delete;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.objects.Comment;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class DeleteCommentHandler extends BaseHandler implements Handler {
	public DeleteCommentHandler() {
		super(Actions.DELETE_COMMENT);
	}
	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		Comment comment = new Comment(json);
		daoManager.getCommentManager().delete(comment);
	}

}
