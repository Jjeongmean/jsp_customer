package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

import DAO.CustomerDAO;

import DTO.Customer;


@WebServlet("/")
@MultipartConfig(maxFileSize=1024*1024*50, location="c:/Temp/img")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CustomerDAO dao; 
    private ServletContext ctx;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		//init은 서블릿 객체 생성시 딱 한번만 실행되므로 객체를 한번만 생성해 공유한다. 
		dao = new CustomerDAO();
		ctx = getServletContext(); //ServletContext: 웹 어플리케이션의 자원관리
	}

	//CustomerDAO객체의 getList 메소드 실행: 게시물 전체 목록을 가져온 후 request 객체에 넣어준다.
			public String getList(HttpServletRequest request) {
				List<Customer> list;
				
				try {
					list = dao.getList();
					request.setAttribute("customerList", list);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("error", "고객 리스트를 정상적으로 가져오지 못했습니다!");
				}
				
				return "index.jsp";
			}
	
	
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //request 객체 한글깨짐 방지
		
		String command = request.getServletPath();
		String site = null;
		
		System.out.println("command: " + command);
		
		switch(command) {
			case "/index": site = getList(request); break; //index.jsp로 이동
			case "/view": site = getView(request); break; //view.jsp로 이동
			case "/register": site = "register.jsp"; break; //register.jsp로 이동
			case "/insert": site = insertCustomer(request); break; //게시글 등록
			case "/edit": site = getViewForEdit(request); break; //edit.jsp로 이동
			case "/update": site = updateCustomer(request); break; //게시글 수정
			case "/delete": site = deleteCustomer(request); break; //게시글 삭제
		}
		
		/*
		 -공통점: 둘다 페이지를 이동한다.
		 redirect: 객체(request, response)를 가지고 이동하지 X, URL의 변화
		   *DB에 변화가 생기는 요청에 사용(insert, update, delete) - 글쓰기, 글수정, 글삭제, 회원가입..
		 
		 forward: 객체(request, response)를 가지고 이동, URL의 변화 X
		   *단순 조회에 사용(select) - 게시글 목록보기, 게시글 상세페이지, 검색..	
		 */
		
		// redirect:/index
		if(site.startsWith("redirect:/")) { //redirect 처리
			String rview = site.substring("redirect:/".length()); //index번호: 10
			response.sendRedirect(rview); //rview: /index
		} else { //forward 처리
			ctx.getRequestDispatcher("/" + site).forward(request, response);
		}
	}
	

	
		//회원정보의 상세 페이지를 가지고 와서 request 객체에 넣어준다.
		public String getView(HttpServletRequest request) {
			//쿼리 파라메터에 있는 id값을 가져온다.
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				//dao.updateViews(id); //조회수 증가
				Customer c = dao.getView(id);
				request.setAttribute("customer", c);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "게시글을 정상적으로 가져오지 못했습니다!");
			}
			
			return "view.jsp";
		}
	
		//수정할 고객정보의 기존 데이터를 가지고 와서 request 객체에 넣어준다.
		public String getViewForEdit(HttpServletRequest request) {
			//쿼리 파라메터에 있는 id값을 가져온다.
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				Customer c = dao.getView(id);
				request.setAttribute("customer", c);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "edit.jsp";
		}
	
	
		//회원정보을 등록해준다.
		public String insertCustomer(HttpServletRequest request) {
			Customer c = new Customer();
			try {
				BeanUtils.populate(c, request.getParameterMap());
				
				//1.이미지 파일 서버(c:/Temp/img) 컴퓨터에 저장
				Part part = request.getPart("file"); //파일에 대한 정보
				String fileName = getFilename(part); //파일명 얻음
				
				//fileName이 null이 아니고 length()도 0이 아니라면
				//업로드된 파일이 있는지 확인
				//"c:/Temp/img"에 있는 파일 업로드 하면 X
				if(fileName != null && !fileName.isEmpty()) {
					part.write(fileName); //서버에 파일 업로드
					
					//2.경로를 포함한 이미지 파일 이름을 Customer 객체에 저장
					c.setImg("/img/" +  fileName);
				} else {
					c.setImg(null); //업로드한 이미지가 없을 경우 null 저장
				}
				
				dao.insertCustomer(c);
				
			} catch (Exception e) {
				e.printStackTrace();
				
				try {
					//쿼리스트링의 한글깨짐을 방지하기 위해 UTF-8로 인코딩
					String encodeName = URLEncoder.encode("게시물이 정상적으로 등록되지 않았습니다!", "UTF-8");
					return "redirect:/index?error=" + encodeName;
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
			} 
			
			return "redirect:/index";
		}
		
		
		
		//고객 정보를 수정해준다.
		public String updateCustomer(HttpServletRequest request) {
			Customer c = new Customer();
			String origin_file = request.getParameter("origin_file");
			
			try {
				BeanUtils.populate(c, request.getParameterMap());
				
				//1.이미지 파일 서버(c:/Temp/img) 컴퓨터에 저장
				Part part = request.getPart("file"); //파일에 대한 정보
				String fileName = getFilename(part); //파일명 얻음
				
				//fileName이 null이 아니고 length()도 0이 아니라면
				//업로드된 파일이 있는지 확인
				if(fileName != null && !fileName.isEmpty()) {
					part.write(fileName); //서버에 파일 업로드
					
					//2.경로를 포함한 이미지 파일 이름을 Customer 객체에 저장
					c.setImg("/img/" +  fileName);
			
				} else { //업로드된 파일이 없을때
					if(origin_file == null || origin_file.equals("")) {
						c.setImg(null);
					} else {
						c.setImg(origin_file);
					}
				}
				
				dao.updateCustomer(c);
				
			} catch (Exception e) {
				e.printStackTrace();
				
				try {
					//쿼리스트링의 한글깨짐을 방지하기 위해 UTF-8로 인코딩
					String encodeName = URLEncoder.encode("게시물이 정상적으로 수정되지 않았습니다!", "UTF-8");
					return "redirect:/view?id="+ c.getId() +"&error=" + encodeName;
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			} 
			
			return "redirect:/view?id=" + c.getId();
		}
		
		//고객 정보을 삭제해준다.
		public String deleteCustomer(HttpServletRequest request) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				dao.deleteCustomer(id);
			} catch(Exception e) {
				e.printStackTrace();
				
				try {
					//쿼리스트링의 한글깨짐을 방지하기 위해 UTF-8로 인코딩
					String encodeName = URLEncoder.encode("게시물이 정상적으로 삭제되지 않았습니다!", "UTF-8");
					return "redirect:/index?error=" + encodeName;
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
			
			return "redirect:/index";
		}
		
			
		//파일에서 이미지명을 추출하는 메소드
		private String getFilename(Part part) {
			String fileName = null;
			
			//파일이름이 들어있는 헤더 영역을 가지고옴 
			String header = part.getHeader("content-disposition");
			
			//form-data; name="img"; filename="사진5.jpg"
			System.out.println("Header => " + header);
			
			//파일 이름이 들어있는 부분의 시작위치(인덱스 번호)를 가지고옴
			int start = header.indexOf("filename=");
			//쌍따옴표 사이의 이미지명 부분만 가지고 옴
			fileName = header.substring(start + 10, header.length() - 1);
			
			return fileName;
		}
}
