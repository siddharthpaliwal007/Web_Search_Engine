package searchEngine;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import helpers.DbHandler;
import helpers.Structures.UrlStruct;
import pageParser.PageParser;
import webCrawler.Crawler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Margaret, Siddharth, Srishti
 * @brief Class to act as the main search engine.
 *        Responsibilities -
 *        1. User input / output
 *        2. Delegating work to other classes.
 */


/**
 * Servlet implementation class allinone
 */
@WebServlet("/MainApplication")

public class MainApplication extends HttpServlet {

	public MainApplication() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String flag=request.getParameter("flag");
	PrintWriter p=response.getWriter();
	if(flag.equals("insert"))
		
	try{  
	
		response.setContentType("text/html");
		//PrintWriter p=response.getWriter();
		p.write("Procedure Started.");
		String ip=request.getParameter("ip");
		String port=request.getParameter("port");
		String user=request.getParameter("user");
		String password=request.getParameter("pass");
		String crawl=request.getParameter("crawl");

		
	    HashSet<String> keyset;
	    Map<String, List<String>> urlMap = new HashMap<String, List<String>>();
	    DbHandler db = new DbHandler(ip,port,user,password);
	
	    /** if user wants the application to crawl*/
	    if(crawl == "true")
	    {
	      /** create crawler object*/
	      Crawler webCrawler = new Crawler(10);
	      System.out.println(Instant.now());
	
	      /** start crawling*/
	      webCrawler.crawl("http://ask.uwindsor.ca");
	
	      /** start a thread to asynchronously perform insertion to database*/
	      new Thread( () -> {
	        db.insertion(webCrawler.getReferencedLinksMap());
	      }).start();
	
	      System.out.println("Crawling completed : " + Instant.now());
	      keyset = webCrawler.getUrls();
	    }
	    /** if crawling is disabled*/
	    else
	    {
	      /** get urls from db*/
	      urlMap = db.search();
	      keyset = new HashSet<String>(urlMap.keySet());
	    }
	
	    System.out.println("Parsing started : " + Instant.now());
	    PageParser.parse(keyset);
	    System.out.println("Parsing Completed : " + Instant.now());
	
	    response.sendRedirect("search.jsp");
	    
		}catch(Exception e){
			 System.out.println(e);	
		}
	
	
	else if (flag.equals("search")){
		
		  String keyword=request.getParameter("pattern");
	      TreeSet<UrlStruct> data = PageParser.search(keyword);
	      int count = 0;
	    
	      for(UrlStruct str : data.descendingSet())
	      {
	        if(++count > 10)
	          break;
	        response.getWriter().println(str.url + " : " + str.rank);
	      }   
	}
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

	
