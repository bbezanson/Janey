package com.janey.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.janey.handlers.create.CreateCommentHandler;
import com.janey.handlers.create.CreateConfigHandler;
import com.janey.handlers.create.CreateIssueHandler;
import com.janey.handlers.create.CreateProductHandler;
import com.janey.handlers.create.CreateUserHandler;
import com.janey.handlers.create.CreateVersionHandler;
import com.janey.handlers.delete.DeleteCommentHandler;
import com.janey.handlers.delete.DeleteIssueHandler;
import com.janey.handlers.delete.DeleteProductHandler;
import com.janey.handlers.delete.DeleteVersionHandler;
import com.janey.handlers.get.GetAllCommentsHandler;
import com.janey.handlers.get.GetAllIssuesHandler;
import com.janey.handlers.get.GetAllProductsHandler;
import com.janey.handlers.get.GetAllUsersHandler;
import com.janey.handlers.get.GetAllVersionsHandler;
import com.janey.handlers.get.GetConfigHandler;
import com.janey.handlers.get.GetIssueHandler;
import com.janey.handlers.get.GetMatchingIssuesHandler;
import com.janey.handlers.get.GetProductHandler;
import com.janey.handlers.update.UpdateCommentHandler;
import com.janey.handlers.update.UpdateIssueHandler;
import com.janey.handlers.update.UpdateProductHandler;
import com.janey.handlers.update.UpdateVersionHandler;

public class HandlerManager {
	final private static Logger log = Logger.getLogger(HandlerManager.class);
	
	private List<BaseHandler> handlers;
	
	public HandlerManager() {
		this.handlers = new ArrayList<BaseHandler>();
		this.handlers.add(new CreateVersionHandler());
		this.handlers.add(new DeleteVersionHandler());
		this.handlers.add(new UpdateVersionHandler());
		this.handlers.add(new GetAllVersionsHandler());
		
		this.handlers.add(new CreateIssueHandler());
		this.handlers.add(new DeleteIssueHandler());
		this.handlers.add(new UpdateIssueHandler());
		this.handlers.add(new GetIssueHandler());
		this.handlers.add(new GetAllIssuesHandler());
		this.handlers.add(new GetMatchingIssuesHandler());
		
		this.handlers.add(new CreateCommentHandler());
		this.handlers.add(new DeleteCommentHandler());
		this.handlers.add(new UpdateCommentHandler());
		this.handlers.add(new GetAllCommentsHandler());
		
		this.handlers.add(new CreateProductHandler());
		this.handlers.add(new DeleteProductHandler());
		this.handlers.add(new UpdateProductHandler());
		this.handlers.add(new GetProductHandler());
		this.handlers.add(new GetAllProductsHandler());
		
		this.handlers.add(new CreateConfigHandler());
		this.handlers.add(new GetConfigHandler());
		
		this.handlers.add(new CreateUserHandler());
		this.handlers.add(new GetAllUsersHandler());
	}
		
	
	
	public BaseHandler findHandler(String action) {
		BaseHandler handler = null;
		log.debug("searching for handler for " + action + " out of " + this.handlers.size() + " handlers");
		for ( BaseHandler bh : this.handlers ) {
	//		log.debug("handler:" + bh.getAction());
			if ( bh.getAction().equals(action) ) {
	//			log.debug("match!!");
				handler = bh;
				break;
			}
		}
		if ( handler != null ) {
	//		log.debug("returning handler " + handler.getAction());
		}
		return handler;
	}
}
