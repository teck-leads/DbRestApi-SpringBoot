package com.fresco.dbrestapi.model;

public class UserPost {
	private String user;
	private int postId;
	private String subuser;
	private String searchText;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getSubuser() {
		return subuser;
	}
	public void setSubuser(String subuser) {
		this.subuser = subuser;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	@Override
	public String toString() {
		return "UserPost [user=" + user + ", postId=" + postId + ", subuser=" + subuser + ", searchText=" + searchText
				+ "]";
	}
	
	
	
	

}
