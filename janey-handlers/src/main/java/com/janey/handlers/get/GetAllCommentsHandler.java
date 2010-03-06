package com.janey.handlers.get;

import java.sql.SQLException;
import java.util.List;

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

public class GetAllCommentsHandler extends BaseHandler implements Handler {

	public GetAllCommentsHandler() {
		super(Actions.GET_ALL_COMMENTS);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		long issueId = json.getLong("issue_id");
		List<Comment> comments = daoManager.getCommentManager().getAll(issueId);
		
		if ( comments != null ) {
			out.object();
			out.array();
			for ( Comment comment : comments ) {
				comment.toJson(out);
			}
			out.endArray();
			out.endObject();
		}
	}

}
