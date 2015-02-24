/*============================================================================
 * @BusinessType : Common
 * @File : HTMLUtil.java
 * @FileName : Jsp관련각종유티리티를 담고 있음
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

import java.io.*;
import java.util.*;
import java.text.*;
import java.net.URLEncoder;
import java.net.URLDecoder;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.cesa.common.*;

/**
 * out, req, res 를 인자로 받으므로, 웹과 관련된
 * 모든 내용을 이 클래스에서 처리할 수 있다.
 *
 * @version 1.0
 */
public class HTMLUtil
{
	/**
	 * jsp에서 전달받은 <code>HttpServletRequest</code>
	 */
    private HttpServletRequest req;

	/**
	 * jsp에서 전달받은 <code>HttpServletResponse</code>
	 */
    private HttpServletResponse res;

	/**
	 * jsp에서 전달받은 <code>JspWriter</code>
	 */
    private JspWriter out;

	/**
	 * jsp에서 전달받은 <code>HttpSession</code>
	 */
    private HttpSession session;


	/**
	 * 생성자
	 */
    public HTMLUtil(){
        super();
    }

	/**
	 * 생성자
	 *
	 * @param req <code>HttpServletRequest</code>
	 * @param res <code>HttpServletResponse</code>
	 * @param out <code>JspWriter</code>
	 * @param session <code>HttpSession</code>
	 */
    public HTMLUtil(HttpServletRequest req,
                   HttpServletResponse res,
                   JspWriter out,
                   HttpSession session){

		synchronized(this){
			this.req = req;
			this.res = res;
			this.out = out;
			this.session = session;
		}
    }

	/**
	 * <pre>
	 * 페이지 네비게이터를 out 을 통해 출력한다.
	 * 너비,페이지당 출력글 갯수등은 Config에 선언된 값을 사용함
	 *
	 * Config.BORRD_LIST_CNT
	 * Config.BOARD_PAGE_CNT
	 *
	 * </pre>
	 *
	 * @param pageCommonUrl 프로그램 이름과 argument 까지
	 * @param totCount 전체 게시글 카운트
	 * @param currPage 현재 페이지 번호
	 */
	public void printDividedPage( String pageCommonUrl,
									int totCount,
									int currPage) throws Exception {

		SiteContext sc = SiteContext.getInstance();
		printDividedPage(pageCommonUrl,totCount,currPage,
				sc.getInt("front.list.cnt"),sc.getInt("front.page.cnt"));
	}

	/**
	 * 페이지 네비게이터를 out 을 통해 출력한다.
	 *
	 * @param pageCommonUrl 프로그램 이름과 argument 까지
	 * @param totCount 전체 게시글 카운트
	 * @param currPage 현재 페이지 번호
	 * @param articlesPerPage 페이지당 출력 글 갯수
	 * @param pagesPerScreen 스크린당 Page 갯수
	 * @param tableWidth 테이블 너비 ( 500, 100% )
	 */
	public void printDividedPage(String pageCommonUrl,
									int totCount,
									int currPage,
									int articlesPerPage,
									int pagesPerScreen) throws Exception {

		int pages  = (totCount-1)/articlesPerPage + 1;
        int lpages = (pages-1)/pagesPerScreen + 1;
		int lpage  = (currPage-1)/pagesPerScreen + 1;
		int tmpLpage;


        if(pageCommonUrl != null){
            if(pageCommonUrl.indexOf("?") == -1) pageCommonUrl += "?";
            else pageCommonUrl += "&";
        }
        else return;

		// 페이지를 출력한다.

		// prev button
		if(lpage>=2){
			out.println("<a href='"+pageCommonUrl+"_currPage="+(((lpage-2)*pagesPerScreen)+1)+"><img src='/images/front/button/page_prev01.gif' class='pointer' style='vertical-align:middle;' alt='firstPage' /></a>&nbsp;");
		}
		else{
			out.println("<img src='/images/front/button/page_prev01.gif' class='pointer' style='vertical-align:middle;' alt='firstPage' />&nbsp;");
		}

		if(currPage > 1) {
            if((currPage%5)==0)
	            tmpLpage = lpages - 1;
            else
                tmpLpage = lpages;

            out.println("<a href='"+pageCommonUrl+"_currPage="+(currPage-1)+"&amp;lpage="+tmpLpage+"' ><img src='/images/front/button/page_prev02.gif' class='pointer' style='vertical-align:middle;' alt='prevPage' /></a>&nbsp;");
        }
		else{
			out.println("<img src='/images/front/button/page_prev02.gif' class='pointer' style='vertical-align:middle;' alt='prevPage' />&nbsp;");
		}

		int pageCnt = 0;
		String pageClass = "";
		for(int i=(lpage-1)*pagesPerScreen+1; i<=pages && i<=lpage*pagesPerScreen; i++){
			if(i==(lpage-1)*pagesPerScreen+1){
				pageClass = " class='sta'";
			}else{
				pageClass = "";
			}
			if(i==currPage){
				out.println("<font style='font-size:12px; color:#4d4d4d;'>"+i+"</font>&nbsp;");
			}
			else{
				out.println("<a href='"+pageCommonUrl+"_currPage="+i+"' class='paging'>"+i+"</a>&nbsp;");
			}
		}

		if(currPage<pages) {
	        if((currPage%5)==0)
	            tmpLpage = lpage + 1;
            else
                tmpLpage = lpage;

            out.println("<a href='"+pageCommonUrl+"_currPage="+(currPage+1)
                        +"&amp;lpage="+tmpLpage+"' ><img src='/images/front/button/page_next01.gif' class='pointer' style='vertical-align:middle;' alt='nextPage' /></a>&nbsp;");
        }
		else{
			out.println("<img src='/images/front/button/page_next01.gif' class='pointer' style='vertical-align:middle;' alt='nextPage' />&nbsp;");
		}

		// next button
		if(lpage<lpages){
			out.println("<a href='"+pageCommonUrl+"_currPage="+((lpage*pagesPerScreen)+1)+"'><img src='/images/front/button/page_next02.gif' class='pointer' style='vertical-align:middle;' alt='lastPage' /></a>");
		}
		else{
			out.println("<img src='/images/front/button/page_next02.gif' class='pointer' style='vertical-align:middle;' alt='lastPage' />");
		}
	}

	/**
	 * 페이지 네비게이터를 out 을 통해 출력한다.
	 *
	 * @param pageCommonUrl 프로그램 이름과 argument 까지
	 * @param totCount 전체 게시글 카운트
	 * @param currPage 현재 페이지 번호
	 * @param articlesPerPage 페이지당 출력 글 갯수
	 * @param pagesPerScreen 스크린당 Page 갯수
	 * @param tableWidth 테이블 너비 ( 500, 100% )
	 */
	public void printDividedPage2(String pageCommonUrl,
									int totCount,
									int currPage,
									int articlesPerPage,
									int pagesPerScreen) throws Exception {

		int pages  = (totCount-1)/articlesPerPage + 1;
        int lpages = (pages-1)/pagesPerScreen + 1;
		int lpage  = (currPage-1)/pagesPerScreen + 1;
		int tmpLpage;


        if(pageCommonUrl == null) return;

		// 페이지를 출력한다.

		// prev button
		if(lpage>=2){
			out.println("<a href='"+pageCommonUrl+",\""+(((lpage-2)*pagesPerScreen)+1)+"\")'><img src='/images/front/button/page_prev01.gif' class='pointer' style='vertical-align:middle;' alt='firstPage' /></a>&nbsp;");
		}
		else{
			out.println("<img src='/images/front/button/page_prev01.gif' class='pointer' style='vertical-align:middle;' alt='firstPage' />&nbsp;");
		}

		if(currPage > 1) {
            if((currPage%5)==0)
	            tmpLpage = lpages - 1;
            else
                tmpLpage = lpages;

            out.println("<a href='"+pageCommonUrl+",\""+(currPage-1)+"\")'><img src='/images/front/button/page_prev02.gif' class='pointer' style='vertical-align:middle;' alt='prevPage' /></a>&nbsp;");
        }
		else{
			out.println("<img src='/images/front/button/page_prev02.gif' class='pointer' style='vertical-align:middle;' alt='prevPage' />&nbsp;");
		}

		int pageCnt = 0;
		String pageClass = "";
		for(int i=(lpage-1)*pagesPerScreen+1; i<=pages && i<=lpage*pagesPerScreen; i++){
			if(i==(lpage-1)*pagesPerScreen+1){
				pageClass = " class='sta'";
			}else{
				pageClass = "";
			}
			if(i==currPage){
				out.println("<font style='font-size:12px; color:#4d4d4d;'>"+i+"</font>&nbsp;");
			}
			else{
				out.println("<a href='"+pageCommonUrl+",\""+i+"\")' class='paging'>"+i+"</a>&nbsp;");
			}
		}

		if(currPage<pages) {
	        if((currPage%5)==0)
	            tmpLpage = lpage + 1;
            else
                tmpLpage = lpage;
            out.println("<a href='"+pageCommonUrl+",\""+(currPage+1)+"\")'><img src='/images/front/button/page_next01.gif' class='pointer' style='vertical-align:middle;' alt='nextPage' /></a>&nbsp;");
        }
		else{
			out.println("<img src='/images/front/button/page_next01.gif' class='pointer' style='vertical-align:middle;' alt='nextPage' />&nbsp;");
		}

		// next button
		if(lpage<lpages){
			out.println("<a href='"+pageCommonUrl+",\""+((lpage*pagesPerScreen)+1)+"\")'><img src='/images/front/button/page_next02.gif' class='pointer' style='vertical-align:middle;' alt='lastPage' /></a>");
		}
		else{
			out.println("<img src='/images/front/button/page_next02.gif' class='pointer' style='vertical-align:middle;' alt='lastPage' />");
		}
	}

	/**
	 * <pre>
     * 페이지 네비게이터를 out 을 통해 출력한다.
     * 너비,페이지당 출력글 갯수등은 Config에 선언된 값을 사용함
     *
     * Config.BORRD_LIST_CNT
     * Config.BOARD_PAGE_CNT
     *
     * </pre>
     *
     * @param pageCommonUrl 프로그램 이름과 argument 까지
     * @param totCount 전체 게시글 카운트
     * @param currPage 현재 페이지 번호
     */
    public void printDividedPageAdmin( String pageCommonUrl,
	     							 int totCount,
         							 int currPage) throws Exception {


		SiteContext sc = SiteContext.getInstance();
         printDividedPageAdmin(pageCommonUrl,totCount,currPage,
	         	 sc.getInt("admin.list.cnt"), sc.getInt("admin.page.cnt"));
    }

	/**
     * 페이지 네비게이터를 out 을 통해 출력한다.
     *
     * @param pageCommonUrl 프로그램 이름과 argument 까지
     * @param totCount 전체 게시글 카운트
     * @param currPage 현재 페이지 번호
     * @param articlesPerPage 페이지당 출력 글 갯수
     * @param pagesPerScreen 스크린당 Page 갯수
     * @param tableWidth 테이블 너비 ( 500, 100% )
	*/
    public void printDividedPageAdmin(String pageCommonUrl,
	                                int totCount,
                                    int currPage,
                                    int articlesPerPage,
                                    int pagesPerScreen) throws Exception {

		int pages  = (totCount-1)/articlesPerPage + 1;
        int lpages = (pages-1)/pagesPerScreen + 1;
        int lpage  = (currPage-1)/pagesPerScreen + 1;
        int tmpLpage;

        if(pageCommonUrl != null){
            if(pageCommonUrl.indexOf("?") == -1) pageCommonUrl += "?";
            else pageCommonUrl += "&amp;";
        }
        else return;

        // 페이지를 출력한다.

        // prev button
        if(lpage>=2){
	        out.println("<a href='"+pageCommonUrl+"_currPage="+(((lpage-2)*pagesPerScreen)+1)+"'><img src='http://ui.dnt7.com/backoffice/images/btn/pre_dubble_button.png' alt='prePage' /></a>");
        }
        else{
            out.println("<img src='http://ui.dnt7.com/backoffice/images/btn/pre_dubble_button.png' alt='prePage' />");
        }

        if(currPage > 1) {
	        if((currPage%5)==0)
	            tmpLpage = lpages - 1;
            else
                tmpLpage = lpages;
            out.println("<a href='"+pageCommonUrl+"_currPage="+(currPage-1)+"&amp;lpage="+tmpLpage+"' ><img src='http://ui.dnt7.com/backoffice/images/btn/pre_button.png' alt='prePage'/></a>");
        }
        else{
            out.println("<img src='http://ui.dnt7.com/backoffice/images/btn/pre_button.png' alt='prePage' />");
        }

        // Print Pages

        int pageCnt = 0;
        for(int i=(lpage-1)*pagesPerScreen+1; i<=pages && i<=lpage*pagesPerScreen; i++){
            if(i==currPage){
	            out.println("<font class='f_b'>"+i+"</font>");
            }
            else{
                out.println("<font class='f_b'><a href='"+pageCommonUrl+"_currPage="+i+"'>"+i+"</a></font>");
            }
        }

        if(currPage<pages) {
	        if((currPage%5)==0)
	            tmpLpage = lpage + 1;
            else
                tmpLpage = lpage;
            out.println("<a href='"+pageCommonUrl+"_currPage="+(currPage+1)
                        +"&amp;lpage="+tmpLpage+"' ><img src='http://ui.dnt7.com/backoffice/images/btn/next_button.png' alt='nextPage' /></a>");
        }
        else{
            out.println("<img src='http://ui.dnt7.com/backoffice/images/btn/next_button.png' alt='nextPage' />");
        }

        // next button
        if(lpage<lpages){
	        out.println("<a href='"+pageCommonUrl+"_currPage="+((lpage*pagesPerScreen)+1)+"'><img src='http://ui.dnt7.com/backoffice/images/btn/next_dubble_button.png' alt='nextPage' /></a>");
        }
        else{
            out.println("<img src='http://ui.dnt7.com/backoffice/images/btn/next_dubble_button.png' alt='nextPage' />");
        }
    }


	/**
	 * 파일 업로드 폼을 출력한다.
	 * @param inptName	file input의 이름
	 * @param del 기존에 등록된 파일명(없을시 공란)
	 *
	 */
	public void printFileUpload(String inptName, String file_name) throws Exception {
		printFileUpload(inptName, file_name, 350);
	}

	public void printFileUpload(String inptName, String file_name, int inptSize) throws Exception {
		String fileName = file_name;

		if( fileName==null ) fileName = "";
		out.println("<table cellspacing=0 cellpadding=0 border=0>");
		out.println("   <tr>");
		out.println("       <td style=\"padding:0px 0px 0px 0px;border-bottom:0px;\">");
		out.println("           <input type=hidden name=\"" + inptName + "_origin\" value=\"" + fileName + "\">");
		out.println("           <input type=text style='width:" + inptSize + "px;' name='" + inptName + "_show' value=\"" + fileName + "\" readonly  class='txt'>&nbsp;");
		out.println("       </td>");
		out.println("		<td style=\"padding:0px 0px 0px 0px;border-bottom:0px;\">");
		out.println("			<span style=\"width:65px;height:18px;background:url(/images/btn/bts_search.gif) no-repeat 0px 0px;cursor:hand;\">");
		out.println("			<div style='overflow:hidden;width:63px;height18px;'><input type=\"file\" name=\""+inptName+"\" style=\"width:0;height:18px;filter:alpha(opacity=0);border-width:0;cursor:hand;border:0px;\" onchange=\""+inptName+"_show.value=this.value;\">");
		out.println("		</td>");
		/*
		out.println("       <td>");
		out.println("           <span style='overflow:hidden; width:63px; height:18px; background-image:url(/images/btn/bts_search.gif);' OnMouseOver=\"this.style.backgroundImage='url(/images/btn/bts_search.gif)';\" OnMouseOut=\"this.style.backgroundImage='url(/images/btn/bts_search.gif)';\">    ");
		out.println("           <div height='22px' style='overflow:hidden;width:68px;height:22px;filter:alpha(opacity=0)'>");
		out.println("           <input type='file' name='" + inptName + "' style='width:0;' OnChange='" + inptName + "_show.value=this.value' style='cursor:hand;'>");
		out.println("           </div></span>");
		out.println("       </td>");
		*/
		out.println("   </tr>");
		out.println("</table>");
	}
}
