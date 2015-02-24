package com.cesa.taglib;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.Logger;
import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


import com.cesa.common.*;

public class PageTag extends TagSupport {

	protected static final Logger log = Logger.getLogger(PageTag.class);

	String pageCommonUrl;
	int totCount;
	int currPage;
	int articlesPerPage;
	int pagesPerScreen;
	
	boolean showAlways = false;
    String serviceType = "admin";

	boolean ajax = false;
	String ajaxFunction;

	/**
	 * @return the pageCommonUrl
	 */
	public String getPageCommonUrl() {
		return pageCommonUrl;
	}

	/**
	 * @param pageCommonUrl the pageCommonUrl to set
	 */
	public void setPageCommonUrl(String pageCommonUrl) {
		this.pageCommonUrl = pageCommonUrl;
	}

	/**
	 * @return the totCount
	 */
	public int getTotCount() {
		return totCount;
	}

	/**
	 * @param totCount the totCount to set
	 */
	public void setTotCount(int totCount) {
		this.totCount = totCount;
	}

	/**
	 * @return the currPage
	 */
	public int getCurrPage() {
		return currPage;
	}

	/**
	 * @param currPage the currPage to set
	 */
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	/**
	 * @return the articlesPerPage
	 */
	public int getArticlesPerPage() {
		return articlesPerPage;
	}

	/**
	 * @param articlesPerPage the articlesPerPage to set
	 */
	public void setArticlesPerPage(int articlesPerPage) {
		this.articlesPerPage = articlesPerPage;
	}

	/**
	 * @return the pagesPerScreen
	 */
	public int getPagesPerScreen() {
		return pagesPerScreen;
	}

	/**
	 * @param pagesPerScreen the pagesPerScreen to set
	 */
	public void setPagesPerScreen(int pagesPerScreen) {
		this.pagesPerScreen = pagesPerScreen;
	}

	
	public boolean isShowAlways() {
        return showAlways;
    }
	public void setShowAlways(boolean showAlways) {
        this.showAlways = showAlways;
    }
	public String getServiceType() {
        return serviceType;
    }
	public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
	

	public boolean isAjax() {
        return ajax;
    }
	public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }

	public String getAjaxFunction() {
        return ajaxFunction;
    }
	public void setAjaxFunction(String ajaxFunction) {
        this.ajaxFunction = ajaxFunction;
    }


	public int doStartTag() throws JspException {

		int pages  = 0;
        int lpages = 0;
		int lpage  = 0;
		
		SiteContext sc = SiteContext.getInstance();
		
		//넘어오 값이 없을 때 sc 에서 값을 가져와서 넣는다.

		//System.out.println("stardust : "+this.articlesPerPage);

		if(this.articlesPerPage == 0){
			if(serviceType.equals("front")){
				setArticlesPerPage(sc.getInt("front.list.cnt"));
			}
			else{
				setArticlesPerPage(sc.getInt("admin.list.cnt"));
			}
		}
		
		//넘어오 값이 없을 때 sc 에서 값을 가져와서 넣는다.
		if(this.pagesPerScreen == 0){
			if(serviceType.equals("front")){
				setPagesPerScreen(sc.getInt("front.page.cnt"));
			}
			else{
				setPagesPerScreen(sc.getInt("admin.page.cnt"));
			}
		}
		
		StringBuffer results = new StringBuffer();

		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		JspWriter out = pageContext.getOut();

		pages  = (this.totCount-1)/this.articlesPerPage + 1;
		lpages = (pages-1)/this.pagesPerScreen + 1;
		lpage  = (this.currPage-1)/this.pagesPerScreen + 1;
		

		if(this.pageCommonUrl != null){
			if(this.pageCommonUrl.indexOf("?") == -1) {
				setPageCommonUrl(pageCommonUrl += "?");
			}
			else {
				setPageCommonUrl(pageCommonUrl += "&");
			}
		}
		else{ 
			//return;
		}
		
		
		if(serviceType.equals("front")){
			results.append(renderFrontPageNaviagtion(lpage, lpages, pages));
		}
		else{
			results.append(renderAdminPageNaviagtion(lpage, lpages, pages));
		}

		try{
			out.print(results.toString());
		}
		catch(Exception e){

		}
		return SKIP_BODY;
	}


	protected String renderFrontPageNaviagtion(int lpage, int lpages, int pages) {

        StringBuffer results = new StringBuffer();
		int tmpLpage = 0;
		int pageCnt = 0;
		String pageClass = "";

		// prev button
		if(lpage>=2){
			if(isAjax()){
				results.append("<a href='javascript:"+getAjaxFunction()+"("+(((lpage-2)*this.pagesPerScreen)+1)+")'><img src='/images/front/btnFirst.png' class='pointer' style='vertical-align:middle;' alt='firstPage' /></a>&nbsp;");
			}
			else{
				results.append("<a href='"+getPageCommonUrl()+"_currPage="+(((lpage-2)*this.pagesPerScreen)+1)+"'><img src='/images/front/btnFirst.png' class='pointer' style='vertical-align:middle;' alt='firstPage' /></a>&nbsp;");
			}
		}
		else{
			results.append("<img src='/images/front/btnFirst.png' class='pointer' style='vertical-align:middle;' alt='firstPage' />&nbsp;");
		}

		if(this.currPage > 1) {
            if((currPage%5)==0)
	            tmpLpage = lpages - 1;
            else
                tmpLpage = lpages;
			

			if(isAjax()){
				results.append("<a href='javascript:"+getAjaxFunction()+"("+(this.currPage-1)+")'><img src='/images/front/btnBack.png' class='pointer' style='vertical-align:middle;' alt='prevPage' /></a>&nbsp;");
			}
			else{
	            results.append("<a href='"+getPageCommonUrl()+"_currPage="+(this.currPage-1)+"' ><img src='/images/front/btnBack.png' class='pointer' style='vertical-align:middle;' alt='prevPage' /></a>&nbsp;");
			}
        }
		else{
			results.append("<img src='/images/front/btnBack.png' class='pointer' style='vertical-align:middle;' alt='prevPage' />&nbsp;");
		}

		
		for(int i=(lpage-1)*this.pagesPerScreen+1; i<=pages && i<=lpage*this.pagesPerScreen; i++){
			if(i==(lpage-1)*this.pagesPerScreen+1){
				pageClass = " class='sta'";
			}else{
				pageClass = "";
			}
			if(i==this.currPage){
				results.append("<font style='font-size:12px;font-weight:bold; color:#4d4d4d;'>"+i+"</font>&nbsp;");
			}
			else{
				if(isAjax()){
					results.append("<a href='javascript:"+getAjaxFunction()+"("+i+")' style='font-size:12px;color:#4d4d4d;'>"+i+"</a>&nbsp;");
				}
				else{
					results.append("<a href='"+getPageCommonUrl()+"_currPage="+i+"' style='font-size:12px;color:#4d4d4d;'>"+i+"</a>&nbsp;");
				}
			}
		}

		if(this.currPage<pages) {
	        if((this.currPage%5)==0)
	            tmpLpage = lpage + 1;
            else
                tmpLpage = lpage;
			
			if(isAjax()){
	            results.append("<a href='javascript:"+getAjaxFunction()+"("+(this.currPage+1)+")' ><img src='/images/front/btnForward.png' class='pointer' style='vertical-align:middle;' alt='nextPage' /></a>&nbsp;");
			}
			else{
				results.append("<a href='"+getPageCommonUrl()+"_currPage="+(this.currPage+1)+"' ><img src='/images/front/btnForward.png' class='pointer' style='vertical-align:middle;' alt='nextPage' /></a>&nbsp;");
			}
        }
		else{
			results.append("<img src='/images/front/btnForward.png' class='pointer' style='vertical-align:middle;' alt='nextPage' />&nbsp;");
		}

		// next button
		if(lpage<lpages){
			if(isAjax()){
				results.append("<a href='javascript:"+getAjaxFunction()+"("+((lpage*this.pagesPerScreen)+1)+")'><img src='/images/front/btnLast.png' class='pointer' style='vertical-align:middle;' alt='lastPage' /></a>");
			}
			else{
				results.append("<a href='"+getPageCommonUrl()+"_currPage="+((lpage*this.pagesPerScreen)+1)+"'><img src='/images/front/btnLast.png' class='pointer' style='vertical-align:middle;' alt='lastPage' /></a>");
			}
		}
		else{
			results.append("<img src='/images/front/btnLast.png' class='pointer' style='vertical-align:middle;' alt='lastPage' />");
		}

		return results.toString();

	}


	protected String renderAdminPageNaviagtion(int lpage, int lpages, int pages) {

        StringBuffer results = new StringBuffer();
		int tmpLpage = 0;
		int pageCnt = 0;
		String pageClass = "";


		// prev button
		if(lpage>=2){
			results.append("<a href='"+getPageCommonUrl()+"_currPage="+(((lpage-2)*this.pagesPerScreen)+1)+"'><img src='http://ui.dnt7.com/backoffice/images/btn/pre_dubble_button.png' alt='prePage' /></a>&nbsp;");
		}
		else{
			results.append("<img src='http://ui.dnt7.com/backoffice/images/btn/pre_dubble_button.png' alt='prePage' />&nbsp;");
		}


		if(this.currPage > 1) {
            if((currPage%5)==0)
	            tmpLpage = lpages - 1;
            else
                tmpLpage = lpages;

            results.append("<a href='"+getPageCommonUrl()+"_currPage="+(this.currPage-1)+"&amp;lpage="+tmpLpage+"' ><img src='http://ui.dnt7.com/backoffice/images/btn/pre_button.png' alt='prePage'/></a>&nbsp;");
        }
		else{
			results.append("<img src='http://ui.dnt7.com/backoffice/images/btn/pre_button.png' alt='prePage' />&nbsp;");
		}

		
		for(int i=(lpage-1)*this.pagesPerScreen+1; i<=pages && i<=lpage*this.pagesPerScreen; i++){
			if(i==(lpage-1)*this.pagesPerScreen+1){
				pageClass = " class='sta'";
			}else{
				pageClass = "";
			}
			if(i==this.currPage){
				results.append("<font class='f_b'>"+i+"</font>&nbsp;");
			}
			else{
				results.append("<font class='f_b'><a href='"+getPageCommonUrl()+"_currPage="+i+"'>"+i+"</a></font>&nbsp;");
			}
		}

		if(this.currPage<pages) {
	        if((this.currPage%5)==0)
	            tmpLpage = lpage + 1;
            else
                tmpLpage = lpage;

			results.append("<a href='"+getPageCommonUrl()+"_currPage="+(this.currPage+1)
                        +"&amp;lpage="+tmpLpage+"' ><img src='http://ui.dnt7.com/backoffice/images/btn/next_button.png' alt='nextPage' /></a>&nbsp;");
        }
		else{
			results.append("<img src='http://ui.dnt7.com/backoffice/images/btn/next_button.png' alt='nextPage' />&nbsp;");
		}

		// next button
		if(lpage<lpages){
			results.append("<a href='"+getPageCommonUrl()+"_currPage="+((lpage*this.pagesPerScreen)+1)+"'><img src='http://ui.dnt7.com/backoffice/images/btn/next_dubble_button.png' alt='nextPage' /></a>&nbsp;");
		}
		else{
			results.append("<img src='http://ui.dnt7.com/backoffice/images/btn/next_dubble_button.png' alt='nextPage' />&nbsp;");
		}

		return results.toString();

	}


	

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
