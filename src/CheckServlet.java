

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class CheckServlet
 */
@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	private static int WIDTH=60;  //��֤��ͼƬ���
	private static int HEIGHT=20; //��֤��ͼƬ�߶�
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		response.setContentType("image/jpeg");
		
		ServletOutputStream sos=response.getOutputStream();
		//���ò�Ҫ����ͼƬ
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires",0);
		//�����ڴ�ͼ�񲢻�ȡ������
		BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		//�����������֤��
		char [] rands = generateCheckCode();
		//����ͼ��
		drawBackground(g);
		drawRands(g,rands);
		//����ͼ����ƹ��̣����ͼ��
		g.dispose();
		//ͼ��������ͻ���
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image,"JPEG",bos);
		byte[] buf = bos.toByteArray();
		response.setContentLength(buf.length);
		//��������Ҳ����д�ɣ�bos.write(sos);
		bos.writeTo(sos);
		sos.write(buf);
		bos.close();
		sos.close();
		session.setAttribute("check_code",new String(rands));
		
	}
	
	private char[] generateCheckCode(){
		//������֤����ַ���
		String charts = "0123456789abcdefghijklmnopqrstuvwxyz";
		char [] rands= new char[4];
		for (int i =0 ; i<4; i++){
			int rand = (int )(Math.random()*36);
			String rand_1 = rand+"";
		    rands[i]=rand_1.charAt(0);
		}
		return rands;
	}
	
	private  void drawRands(Graphics g, char[] rands){
		g.setColor(Color.BLACK);
		g.setFont(new Font(null,Font.ITALIC|Font.BOLD,18));
		//�ڲ�ͬ�߶��������֤���ÿ���ַ�
		g.drawString(""+rands[0],1,17);
		g.drawString(""+rands[1],16,15);
		g.drawString(""+rands[2],31,18);
		g.drawString(""+rands[3],46,16);
		System.out.println(rands);
	}
	private void drawBackground(Graphics g){
		//���汳��
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//�������120�����ŵ�
 		for(int i =0; i<120;i++){
			int x = (int)(Math.random()*WIDTH);
			int y = (int)(Math.random()*HEIGHT);
			int red = (int)(Math.random()*255);
			int green = (int )(Math.random()*255);
			int blue= (int)(Math.random()*225);
			g.setColor(new Color(red,green,blue));
			g.drawOval(x,y,1,0);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
