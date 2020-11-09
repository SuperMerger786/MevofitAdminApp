package com.app.newuidashboardadmin;

public interface IResponseUpdater {
	void onServerResponseSuccess(String reqTag, String response) throws Exception;

	void onServerResponseError(String reqTag, String errorMessage);

}


