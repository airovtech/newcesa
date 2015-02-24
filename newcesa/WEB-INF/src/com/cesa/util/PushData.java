package com.cesa.util;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;
import org.apache.log4j.Logger;
import com.cesa.common.SiteContext;

public class PushData {

	private String device_token = null;
	private String push_msg		= null;
	private String quest_id		= null;
	private int	badge_cnt	= 0;

	public PushData(String device_token, String push_msg, String quest_id, int badge_cnt)
	{
		this.device_token = device_token;
		this.push_msg = push_msg;
		this.quest_id = quest_id;
		this.badge_cnt = badge_cnt;
	}

}
