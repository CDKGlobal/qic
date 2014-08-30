package com.testing123.ui;
//
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.testing123.controller.ParameterManager;
//import com.testing123.controller.UIState;
//import com.testing123.dataObjects.DataPointSet;
//import com.testing123.ui.FlotOptions;
//import com.testing123.vaadin.GetData;
//
///**
// * Servlet implementation class QICServlet
// */
//@WebServlet("/QICServlet")
//public class QICServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	
//	private GetData data;
//	private UIState state;
//
//    /**
//     * Default constructor. 
//     */
//    public QICServlet() {
//        super();
//        this.data = new GetData();
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		state = new UIState();
//		if (request.getParameter("message") == null) {
//			state = new ParameterManager().getState(request.getParameter("st"), request.getParameter("end"),
//					request.getParameter("x"), request.getParameter("y"), request.getParameter("proj"), 
//					request.getParameter("auth"));
//			state.verifyState();
//			
//			DataPointSet queried = data.requestData(state);
//			PrintWriter out = response.getWriter();
//			out.println(queried.toString());
//		} else if (request.getParameter("message").equals("giveOptions")) {
//			PrintWriter out = response.getWriter();
//			out.println(FlotOptions.getString(state));
//		}
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//	}
//
//}
