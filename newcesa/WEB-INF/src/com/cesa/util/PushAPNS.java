/*============================================================================
 * @BusinessType : Common
 * @File : PushAPNS.java
 * @FileName : URL 의 내용을 가져오는 클래스 
 *
 * Note:
 *
 * Change history
 * @LastModifyDate : 20050220
 * @LastModifier   : 
 * @LastVersion    : 1.0
 *   2005-01-03 최초생성
 ============================================================================*/
package com.cesa.util;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;
import org.apache.log4j.Logger;
import com.cesa.common.SiteContext;

public class PushAPNS {

	Logger log = Logger.getLogger(PushAPNS.class);

	public PushAPNS(){}
	
	
	// app별로 인증서가 다르므로, full_version인 경우 다른 인증서를 사용
	public void send(String runMode, String deviceToken, String alertMessage, String QUEST_ID, int badgeCount, String full_version) {

		PushNotificationManager pushManager = null;
		SiteContext sc = SiteContext.getInstance();
		
		try {
			PayLoad payLoad = new PayLoad();
			payLoad.addAlert(alertMessage);
			payLoad.addBadge(badgeCount);
			payLoad.addSound("default");

			// QUEST_ID 가 존재하는 경우 customData 추가
			if(QUEST_ID!=null && QUEST_ID.length()>0) {
				payLoad.addCustomDictionary("QUEST_ID", QUEST_ID);
			}

			
			//log.debug("deviceToken : "+deviceToken);
			pushManager = PushNotificationManager.getInstance();
			pushManager.addDevice("iPhone", deviceToken);

			String host = null;
			String certificatePath = null;

			int port = 2195;
			String certificatePassword = "dntechdams";

			// 주홍이 폰만 Test로 보냄 (ADHOC 배포는 Real로 인식됨)
			if(deviceToken.equals("985e4c5732702e53f68193cee180f5e9e6ecf191f7904a8b09f820779d040b14"))
				runMode = "test";
			else if(deviceToken.equals("6468f624a8cb0f0b55e37768374e85294dc6b8aedcf38b2021a3c6d6c1a5"))
				runMode = "test";
			else
				runMode = "real";



			log.debug("--------------runMode : "+runMode);

			if (runMode.equals("test")) {
				host = "gateway.sandbox.push.apple.com";

				if(full_version.equals("Y"))		// full_version
					certificatePath = sc.get("web.root.path") + "WEB-INF/keystore/Server_Push_Develop_Pro.p12";
				else
					certificatePath = sc.get("web.root.path") + "WEB-INF/keystore/Server_Push_Develop.p12";
			}
			else if (runMode.equals("real")) {
				host = "gateway.push.apple.com";
				
				if(full_version.equals("Y"))		// full_version
					certificatePath = sc.get("web.root.path") + "WEB-INF/keystore/Server_Push_Release_Pro.p12";
				else
					certificatePath = sc.get("web.root.path") + "WEB-INF/keystore/Server_Push_Release.p12";
			}
			
			pushManager.initializeConnection(host, port, certificatePath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);

			Device client = pushManager.getDevice("iPhone");
			pushManager.sendNotification(client, payLoad);
			pushManager.stopConnection();

		}
		catch (Exception ex) {
			log.fatal(ex, ex);
			//ex.printStackTrace(); 
		}
		finally {
			try{
				pushManager.removeDevice("iPhone");
			}
			catch (Exception e2) {
				log.fatal(e2, e2);
			}
		}
	}
}
