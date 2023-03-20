package main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Servlet implementation class Wikipedia
 */
@WebServlet("/Wikipedia")
public class Wikipedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Wikipedia() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		try {
			String word=request.getParameter("word");
			String arr[]=word.split(" ");
			for(int i=0;i<arr.length;i++) {
				arr[i]=arr[i].substring(0,1).toUpperCase()+arr[i].substring(1);
			}
			word=String.join("_",arr);
			System.out.println(word);
			URLConnection connection = new URL("https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch="+word+"&prop=info&inprop=url&utf8=&format=json").openConnection();
			InputStream is = connection.getInputStream();
			Scanner sc = new Scanner(is);
			String inline="";
			while(sc.hasNext())
			{
			inline+=sc.nextLine();
			}
			JSONParser parser=new JSONParser();
			JSONObject obj=new JSONObject();
			obj=(JSONObject) parser.parse(inline);
			JSONArray newArr=new JSONArray();
			newArr=(JSONArray)((JSONObject)obj.get("query")).get("search");
			String res="";
			for(int i=0;i<newArr.size();i++) {
				String title=((JSONObject)newArr.get(i)).get("title")+"";
				String url="https://en.wikipedia.org/wiki/"+title.replace(" ", "_");
				res+="<h1 class=\"head\"><a href="+url+" target=\"_blank\" >"+((JSONObject)newArr.get(i)).get("title")+"</a></h1><br><h3 class=\"subject\">"+((JSONObject)newArr.get(i)).get("snippet")+"</h3><hr>";
			}
			response.getWriter().append(res);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response.getWriter().append("sorry");
		}
	}

}
