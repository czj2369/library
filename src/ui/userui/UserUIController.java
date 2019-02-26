package ui.userui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.omg.CORBA.PRIVATE_MEMBER;

import domain.Book;
import domain.Bookedbook;
import domain.Fine;
import domain.Hbook;
import domain.Lendbook;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdbcUtil.JdbcUtil;
import ui.login.Login;

public class UserUIController {
	@FXML private Label logoText;
	@FXML private ImageView logoImg;
	@FXML private Button testButton;
	@FXML private Button search;//图书查询按钮
	@FXML private Button returnIndex;//首页按钮
	@FXML private Button cancellation;//注销按钮
	@FXML private Button readerInfo;//个人信息按钮
	@FXML private Button userfreeBack;//用户反馈按钮
	@FXML private Button finePay;//缴纳罚款按钮
	@FXML private Button loss;//挂失按钮
	@FXML private Button lend;//图书借阅界面
	@FXML private Button bookRecommend;//图书荐购按钮
	
	@FXML private Pane bookSearchUI;//图书查询界面
	@FXML private TabPane readerInfoUI;//个人信息界面
	@FXML private TitledPane noticeUI;//公告界面
	@FXML private Pane userfreebackUI;//用户反馈界面
	@FXML private Pane finePayUI;//缴纳罚款界面
	@FXML private Pane lossUI;//挂失界面
	@FXML private TabPane lendinfoUI;//借阅信息界面
	@FXML private Pane bookRecommendUI;//图书荐购界面
	@FXML private Pane bookBookUI;//预约界面
	
	@FXML private Button changeReaderInfo;//个人信息修改信息中的提交按钮
	@FXML private Label userID;//个人信息中帐号
	@FXML private Label userName;//个人信息中姓名
	@FXML private Label userSex;//个人信息中性别
	@FXML private Label userPhone;//个人信息中手机号
	@FXML private Label userEmail;//个人信息中邮箱
	@FXML private TextField userName1;//个人信息修改信息中的姓名文本框
	@FXML private TextField userSex1;//个人信息修改信息中的性别文本框
	@FXML private TextField userPhone1;//个人信息修改信息中手机号文本框
	@FXML private TextField userEmail1;//个人信息修改信息中的邮箱文本框
	@FXML private TextField userPassword;//个人信息修改信息中的密码文本框
	@FXML private Button refresh;//刷新按钮
	
	@FXML private Button send;//用户反馈中提交按钮
	@FXML private TextArea sendContext;//用户反馈中文本框
	@FXML private ChoiceBox cb;
	
	@FXML private Label noticeLabel;//主界面公告的label
	
	@FXML private TextField bookRecommendName;//图书荐购界面的文本框
	@FXML private TextField bookRecommendAuthor;//图书荐购界面的文本框
	@FXML private TextField bookRecommendPress;//图书荐购界面的文本框
	@FXML private TextArea bookRecommendReason;//图书荐购界面的文本框
	
	@FXML private Label userState;//挂失界面中的状态Label
	@FXML private Button lossButton;//挂失界面中的按钮
	
	@FXML private TableColumn bookBookNum;//预约书索书号
	@FXML private TableColumn bookBookName;//预约书名
	@FXML private TableView bookBookTable;//预约图书列表
	@FXML private TextField bookBookid;//预约书索书号
	
	@FXML private TableView searchTable;//搜索结果表格
	@FXML private TextField searchContent;//搜索关键字
	@FXML private Button searchBook;//搜索按钮
	@FXML private TableColumn bookNum;
	@FXML private TableColumn bookName;
	@FXML private TableColumn bookAuthor;
	@FXML private TableColumn bookPress;
	@FXML private TableColumn bookType;
	@FXML private TableColumn bookRest;
	@FXML private TableColumn bookSum;
	
	@FXML private TableColumn fineID;
	@FXML private TableColumn fineReason;
	@FXML private TableColumn fineTime;
	@FXML private TableColumn fineMoney;
	@FXML private TableColumn fineSend;
	@FXML private TableView finePayTable;
	
	@FXML private TableColumn lendbookNum;
	@FXML private TableColumn lendbookName;
	@FXML private TableColumn lendedbookAuthor;
	@FXML private TableColumn lendbookPress;
	@FXML private TableColumn lendbookType;
	@FXML private TableColumn lendbookTime1;
	@FXML private TableColumn lendbookTime2;
	@FXML private TableView lendingTable;
	
	@FXML private TableColumn lendedbookNum;
	@FXML private TableColumn lendedbookName;
	@FXML private TableColumn lendededbookAuthor1;
	@FXML private TableColumn lendedbookPress;
	@FXML private TableColumn lendedbookType;
	@FXML private TableColumn lendedbookTime1;
	@FXML private TableColumn lendedbookTime2;
	@FXML private TableView lendedTable;
	
	@FXML private TableColumn bookedbookNum;
	@FXML private TableColumn bookedbookName;
	@FXML private TableColumn bookedbookAuthor;
	@FXML private TableColumn bookedbookPress;
	@FXML private TableColumn bookedbookType;
	@FXML private TableColumn bookedbookTime;
	@FXML private TableColumn getbookTime;
	@FXML private TableView bookedTable;
	
	@FXML private BorderPane rootPane;//这是用户界面的根布局的id
	
	
	//图书查询按钮逻辑
	@FXML
	private void onSearch(ActionEvent event){
		Platform.runLater(new Runnable() {
			public void run() {
				logoText.setVisible(false);
				logoImg.setVisible(false);
				noticeUI.setVisible(false);
				bookSearchUI.setVisible(true);
				readerInfoUI.setVisible(false);
				userfreebackUI.setVisible(false);
				finePayUI.setVisible(false);
				lossUI.setVisible(false);
				bookRecommendUI.setVisible(false);
				bookBookUI.setVisible(false);
				lendinfoUI.setVisible(false);
				cb.setItems(FXCollections.observableArrayList(
					    "书名", "作者 ", "出版社", "类型"));
				cb.setValue("书名");
			}
		});
	}
	//搜索图书按钮逻辑
	@FXML
	private void onSearchBook(ActionEvent event){

		ObservableList<Book> bookList = FXCollections.observableArrayList();
		String sql = "select * from book where bookName like ? or bookAuthor like ? or bookPress like ? or bookType like ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+searchContent.getText()+"%");
			ps.setString(2, "%"+searchContent.getText()+"%");
			ps.setString(3, "%"+searchContent.getText()+"%");
			ps.setString(4, "%"+searchContent.getText()+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBookNum(rs.getString("bookNum"));
				book.setBookName(rs.getString("bookName"));
				book.setBookAuthor(rs.getString("bookAuthor"));
				book.setBookPress(rs.getString("bookPress"));
				book.setBookType(rs.getString("bookType"));
				book.setBookRest(rs.getInt("bookRest"));
				book.setBookSum(rs.getInt("bookSum"));
				bookNum.setCellValueFactory(new PropertyValueFactory("bookNum"));
				bookName.setCellValueFactory(new PropertyValueFactory("bookName"));
				bookAuthor.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
				bookPress.setCellValueFactory(new PropertyValueFactory("bookPress"));
				bookType.setCellValueFactory(new PropertyValueFactory("bookType"));
				bookRest.setCellValueFactory(new PropertyValueFactory("bookRest"));
				bookSum.setCellValueFactory(new PropertyValueFactory("bookSum"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.realease(conn, ps, rs);
		}
		searchTable.setItems(bookList);
	}
	@FXML 
	public void onsys(ActionEvent event){
		System.out.println(bookNum.getCellData(searchTable.getSelectionModel().getSelectedIndex()));
	}
	//首页按钮逻辑
	@FXML
	public void returnIndex(ActionEvent event){
		logoText.setVisible(true);
		logoImg.setVisible(true);
		noticeUI.setVisible(true);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(false);
		userfreebackUI.setVisible(false);
		lossUI.setVisible(false);
		lendinfoUI.setVisible(false);
		bookRecommendUI.setVisible(false);
		bookBookUI.setVisible(false);
		finePayUI.setVisible(false);
	}
	//注销按钮逻辑
	@FXML
	public void cancellation(ActionEvent event){
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					new Login().start(new Stage());
					Stage stage = (Stage) rootPane.getScene().getWindow();
					stage.hide();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//个人信息按钮逻辑
	@FXML
	public void onReaderInfo(ActionEvent event){
		logoText.setVisible(false);
		logoImg.setVisible(false);
		noticeUI.setVisible(false);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(true);
		userfreebackUI.setVisible(false);
		lossUI.setVisible(false);
		lendinfoUI.setVisible(false);
		bookRecommendUI.setVisible(false);
		bookBookUI.setVisible(false);
		finePayUI.setVisible(false);
		String sql = "select * from user where username=(select nowUser from nowuser where nowID=1)";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				userID.setText(rs.getString("username"));
				userName.setText(rs.getString("name"));
				userPhone.setText(rs.getString("phone"));
				userSex.setText(rs.getString("sex"));
				userEmail.setText(rs.getString("email"));
				userName1.setText(rs.getString("name"));
				userPhone1.setText(rs.getString("phone"));
				userSex1.setText(rs.getString("sex"));
				userEmail1.setText(rs.getString("email"));
				userPassword.setText(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
	}
	
	@FXML //刷新按钮逻辑
	public void onRefresh(ActionEvent event){
		if (refresh.getText().equals("刷新")) {
			String sql = "select * from user where username=(select nowUser from nowuser where nowID=1)";
			Connection conn = JdbcUtil.getConn();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					userID.setText(rs.getString("username"));
					userName.setText(rs.getString("name"));
					userPhone.setText(rs.getString("phone"));
					userSex.setText(rs.getString("sex"));
					userEmail.setText(rs.getString("email"));
					userName1.setText(rs.getString("name"));
					userPhone1.setText(rs.getString("phone"));
					userSex1.setText(rs.getString("sex"));
					userEmail1.setText(rs.getString("email"));
					userPassword.setText(rs.getString("password"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				JdbcUtil.realease(conn, ps, rs);
			}
			refresh.setText("确定");
		}
	}
	
	@FXML //个人信息修改信息中的提交按钮逻辑
	public void onChangeReaderInfo(ActionEvent event){
		String sql = "Update user set name=?,password=?,sex=?,email=?,phone=? where userName=(select nowUser from nowuser where nowID=1)";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		try {
			if (userName1.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("个人信息");
				alert.setHeaderText(null);
				alert.setContentText("姓名为空，请重试！");
				alert.showAndWait();
			}else if (userSex1.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("个人信息");
				alert.setHeaderText(null);
				alert.setContentText("性别为空，请重试！");
				alert.showAndWait();
			}else if (!userSex1.getText().equals("男")&&!userSex1.getText().equals("女")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("个人信息");
				alert.setHeaderText(null);
				alert.setContentText("性别填写错误，请填写男或者女。");
				alert.showAndWait();
			}else if (userPhone1.getLength()!=11) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("个人信息");
				alert.setHeaderText(null);
				alert.setContentText("请正确填写手机号码，请重试！");
				alert.showAndWait();
			}else if (userPassword.getLength()<6||userPassword.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("个人信息");
				alert.setHeaderText(null);
				alert.setContentText("密码为空或者少于六位，请重试！");
				alert.showAndWait();
			}else{
				ps = conn.prepareStatement(sql);
				ps.setString(1, userName1.getText());
				ps.setString(3, userSex1.getText());
				ps.setString(5, userPhone1.getText());
				ps.setString(4, userEmail1.getText());
				ps.setString(2, userPassword.getText());
				int flag = ps.executeUpdate();
				if(flag == 1){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("个人信息");
					alert.setHeaderText(null);
					alert.setContentText("修改成功");
					refresh.setText("刷新");
					alert.showAndWait();
				}else{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("个人信息");
					alert.setHeaderText(null);
					alert.setContentText("修改失败，请重试！");
					alert.showAndWait();
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
	}
	
	@FXML //用户反馈按钮逻辑
	public void onUserfreeback(ActionEvent event){
		logoText.setVisible(false);
		logoImg.setVisible(false);
		noticeUI.setVisible(false);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(false);
		finePayUI.setVisible(false);
		lossUI.setVisible(false);
		bookRecommendUI.setVisible(false);
		lendinfoUI.setVisible(false);
		bookBookUI.setVisible(false);
		userfreebackUI.setVisible(true);
	}
	
	@FXML //用户反馈界面中提交按钮的逻辑
	public void onSend(ActionEvent event){
		String sql = "insert into freeback (content,user) values(?,?)";
		String sql2 = "select nowUser from nowuser where nowID=1";
		String user = "";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps2 = conn.prepareStatement(sql2);
			rs = ps2.executeQuery();
			while(rs.next()){
				user = rs.getString("nowUser");
			}
			ps.setString(1, sendContext.getText());
			ps.setString(2, user);
			int flag = ps.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("用户反馈");
				alert.setHeaderText(null);
				alert.setContentText("反馈成功提交！");
				alert.showAndWait();
				sendContext.setText("");
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("用户反馈");
				alert.setHeaderText(null);
				alert.setContentText("反馈提交失败，请重试！");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps2, rs);
			try {
				if(ps!=null){
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@FXML //缴纳罚款按钮逻辑
	public void onFinePay(ActionEvent event){
		logoText.setVisible(false);
		logoImg.setVisible(false);
		noticeUI.setVisible(false);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(false);
		userfreebackUI.setVisible(false);
		lossUI.setVisible(false);
		lendinfoUI.setVisible(false);
		bookRecommendUI.setVisible(false);
		bookBookUI.setVisible(false);
		finePayUI.setVisible(true);
		
		ObservableList<Fine> fineList = FXCollections.observableArrayList();
		String sql = "select * from finepay where userID=?";
		String sql2 ="select nowUserID from nowuser where nowID=1";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;//容器
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			ps = conn.prepareStatement(sql);
			ps2 = conn.prepareStatement(sql2);
		
			rs2 = ps2.executeQuery();
			rs2.next();
			 ps.setString(1, rs2.getString("nowUserID"));
				rs = ps.executeQuery();
			while(rs.next()){
				Fine fine = new Fine();
				fine.setUserID(rs2.getString("nowUserID"));
				fine.setFineReason(rs.getString("fineReason"));
				fine.setFineTime(rs.getString("fineTime"));
				fine.setFineMoney(rs.getString("fineMoney"));
				
				fineID.setCellValueFactory(new PropertyValueFactory("userID"));
				fineReason.setCellValueFactory(new PropertyValueFactory("fineReason"));
				fineTime.setCellValueFactory(new PropertyValueFactory("fineTime"));
				fineMoney.setCellValueFactory(new PropertyValueFactory("fineMoney"));
				fineList.add(fine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		finePayTable.setItems(fineList);
	}
	
	@FXML //挂失按钮逻辑
	public void onLoss(ActionEvent event){
		logoText.setVisible(false);
		logoImg.setVisible(false);
		noticeUI.setVisible(false);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(false);
		userfreebackUI.setVisible(false);
		finePayUI.setVisible(false);
		lendinfoUI.setVisible(false);
		bookRecommendUI.setVisible(false);
		bookBookUI.setVisible(false);
		lossUI.setVisible(true);
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select state from user where username=(select nowUser from nowuser where nowID=1)";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				userState.setText(rs.getString("state"));
			}
			if (userState.getText().equals("正常")) {
				lossButton.setText("挂失");
			}else{
				lossButton.setText("解除挂失");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		
	}
	
	@FXML //挂失界面挂失按钮逻辑
	public void onGitLoss(ActionEvent event){
		String sql = "update user set state=? where username=(select nowUser from nowuser where nowID=1)";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			if (lossButton.getText().equals("挂失")) {
				ps.setString(1, "挂失");
				ps.executeUpdate();
				userState.setText("挂失");
				lossButton.setText("解除挂失");
			}else{
				ps.setString(1, "正常");
				ps.executeUpdate();
				userState.setText("正常");
				lossButton.setText("挂失");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
		
		
	}
	@FXML //借阅信息按钮逻辑
	public void onLend(ActionEvent event){
		logoText.setVisible(false);
		logoImg.setVisible(false);
		noticeUI.setVisible(false);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(false);
		userfreebackUI.setVisible(false);
		finePayUI.setVisible(false);
		lossUI.setVisible(false);
		bookBookUI.setVisible(false);
		bookRecommendUI.setVisible(false);
		lendinfoUI.setVisible(true);
		
		ObservableList<Lendbook> lendList = FXCollections.observableArrayList();
		String sql1 = "select * from lendbook where readerID=?";
		String sql2 ="select nowUserID from nowuser where nowID=1";
		String sql3 ="select * from book ";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps1 = null;//容器
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		try {
			ps1 = conn.prepareStatement(sql1);
			ps2 = conn.prepareStatement(sql2);
			ps3 = conn.prepareStatement(sql3);
			rs2 = ps2.executeQuery();
			rs2.next();
			ps1.setString(1, rs2.getString("nowUserID"));
			rs1 = ps1.executeQuery();
			   
			rs3 = ps3.executeQuery();
				 
			while(rs1.next()){ 
                
            while(rs3.next()) {
              if((rs3.getString("bookNum")).equals(rs1.getString("bookNum"))){
				Lendbook lendbook=new Lendbook();
				lendbook.setLendbookName(rs3.getString("bookName"));
				lendbook.setLendbookNum(rs1.getString("bookNum"));
				lendbook.setLendbookAuthor(rs3.getString("bookAuthor"));
				lendbook.setLendbookPress(rs3.getString("bookPress"));
				lendbook.setLendbookType(rs3.getString("bookType"));
				lendbook.setLenddate(rs1.getDate("lendTime"));
				
	   
				Date date ;
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(rs1.getDate("lendTime")); //你自己的数据进行类型转换
				calendar.add(calendar.DATE,90);//把日期往后增加一天.整数往后推,负数往前移动
				date=(Date)calendar.getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        String a = format.format(date);
		      
				lendbook.setBackdate(a);
				
				lendbookNum.setCellValueFactory(new PropertyValueFactory("lendbookNum"));
				lendbookName.setCellValueFactory(new PropertyValueFactory("lendbookName"));
				lendedbookAuthor.setCellValueFactory(new PropertyValueFactory("lendbookAuthor"));
				lendbookPress.setCellValueFactory(new PropertyValueFactory("lendbookPress"));
				lendbookType.setCellValueFactory(new PropertyValueFactory("lendbookType"));
				lendbookTime1.setCellValueFactory(new PropertyValueFactory("lenddate"));
				lendbookTime2.setCellValueFactory(new PropertyValueFactory("backdate"));
				
				lendList.add(lendbook);}}
                rs3.beforeFirst();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps1, rs1);
			JdbcUtil.realease(conn, ps2, rs2);
			JdbcUtil.realease(conn, ps3, rs3);
		}
		lendingTable.setItems(lendList);
 
	//<------------------------------------------------------------------------->	
		
		ObservableList<Bookedbook> bookList = FXCollections.observableArrayList();
		sql1 = "select * from bookbook where userID=?";
		sql2 ="select nowUser from nowuser where nowID=1";
	    sql3 ="select * from book ";
	    conn = JdbcUtil.getConn();
		 ps1 = null;//容器
		 ps2 = null;
		 ps3 = null;
		 rs1 = null;
		 rs2 = null;
		 rs3 = null;
		try {
			ps1 = conn.prepareStatement(sql1);
			ps2 = conn.prepareStatement(sql2);
			ps3 = conn.prepareStatement(sql3);
			rs2 = ps2.executeQuery();
			rs2.next();
			ps1.setString(1, rs2.getString("nowUser"));
			rs1 = ps1.executeQuery();
			rs3 = ps3.executeQuery();
				 
			while(rs1.next()){
				while(rs3.next()) {
                		if((rs3.getString("bookNum")).equals(rs1.getString("bookBookNum")))
                		{
				Bookedbook bookedbook=new Bookedbook();
				
				bookedbook.setBookedbookNum(rs1.getString("bookBookNum"));
				bookedbook.setBookedbookName(rs3.getString("bookName"));
				bookedbook.setBookedbookAuthor(rs3.getString("bookAuthor"));
				bookedbook.setBookedbookPress(rs3.getString("bookPress"));
				bookedbook.setBookedbookType(rs3.getString("bookType"));
				bookedbook.setBookedDate(rs1.getDate("bookTime"));
				
	   
				Date date1 ;
				Calendar calendar1 =
				new GregorianCalendar();
				calendar1.setTime(rs1.getDate("bookTime")); //你自己的数据进行类型转换
				calendar1.add(calendar1.DATE,15);//把日期往后增加一天.整数往后推,负数往前移动
				date1=(Date)calendar1.getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        String a = format.format(date1);
		      
				bookedbook.setGetDate(a);
				
				bookedbookNum.setCellValueFactory(new PropertyValueFactory("bookedbookNum"));
				bookedbookName.setCellValueFactory(new PropertyValueFactory("bookedbookName"));
				bookedbookAuthor.setCellValueFactory(new PropertyValueFactory("bookedbookAuthor"));
				bookedbookPress.setCellValueFactory(new PropertyValueFactory("bookedbookPress"));
				bookedbookType.setCellValueFactory(new PropertyValueFactory("bookedbookType"));
				bookedbookTime.setCellValueFactory(new PropertyValueFactory("bookedDate"));
				getbookTime.setCellValueFactory(new PropertyValueFactory("getDate"));
				
				bookList.add(bookedbook);}}
                rs3.beforeFirst();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps1, rs1);
			JdbcUtil.realease(conn, ps2, rs2);
			JdbcUtil.realease(conn, ps3, rs3);
		}
		bookedTable.setItems(bookList);	
		
		//<------------------------------------------------------------------------->	
		ObservableList<Hbook> hbookList = FXCollections.observableArrayList();
		String sqlh = "select * from historybook where readerID=(select nowUserID from nowuser where nowID=1)";
	    conn = JdbcUtil.getConn();
		PreparedStatement psh = null;//容器
		ResultSet rsh = null;
		try {
			psh = conn.prepareStatement(sqlh);
			rsh = psh.executeQuery();
				 
			while(rsh.next()){
				Hbook hbook=new Hbook();

				hbook.setHbookNum(rsh.getString("hbookNum"));
				hbook.setHbookName(rsh.getString("hbookName"));
				hbook.setHbookAuthor(rsh.getString("hbookAuthor"));
				hbook.setHbookPress(rsh.getString("hbookPress"));
				hbook.setHbookType(rsh.getString("hbookType"));
				hbook.setHlendDate(rsh.getDate("hbooklendTime"));
				hbook.setHbackDate(rsh.getDate("hbookbackTime"));
				
				lendedbookNum.setCellValueFactory(new PropertyValueFactory("hbookNum"));
				lendedbookName.setCellValueFactory(new PropertyValueFactory("hbookName"));
				lendededbookAuthor1.setCellValueFactory(new PropertyValueFactory("hbookAuthor"));
				lendedbookPress.setCellValueFactory(new PropertyValueFactory("hbookPress"));
				lendedbookType.setCellValueFactory(new PropertyValueFactory("hbookType"));
				lendedbookTime1.setCellValueFactory(new PropertyValueFactory("hlendDate"));
				lendedbookTime2.setCellValueFactory(new PropertyValueFactory("hbackDate"));
				
				hbookList.add(hbook);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, psh, rsh);
		}
		lendedTable.setItems(hbookList);	
	}
	
	@FXML //图书荐购按钮逻辑
	public void onBookRecommend(ActionEvent event){
		logoText.setVisible(false);
		logoImg.setVisible(false);
		noticeUI.setVisible(false);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(false);
		userfreebackUI.setVisible(false);
		finePayUI.setVisible(false);
		lossUI.setVisible(false);
		lendinfoUI.setVisible(false);
		bookBookUI.setVisible(false);
		bookRecommendUI.setVisible(true);
	
	}
	@FXML //处理图书荐购中提交的逻辑
	public void OnGitRecommened(){
		String sql = "insert into bookrecommen (bookname,bookauthor,bookpress,bookreason) values (?,?,?,?)";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps =null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bookRecommendName.getText());
			ps.setString(2, bookRecommendAuthor.getText());
			ps.setString(3, bookRecommendPress.getText());
			ps.setString(4, bookRecommendReason.getText());
			int flag = ps.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书荐购");
				alert.setHeaderText(null);
				alert.setContentText("成功提交！");
				alert.showAndWait();
				sendContext.setText("");
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("图书荐购");
			alert.setHeaderText(null);
			alert.setContentText("提交失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	@FXML //预约按钮的逻辑
	public void bookBook(ActionEvent event){
		logoText.setVisible(false);
		logoImg.setVisible(false);
		noticeUI.setVisible(false);
		bookSearchUI.setVisible(false);
		readerInfoUI.setVisible(false);
		userfreebackUI.setVisible(false);
		finePayUI.setVisible(false);
		lossUI.setVisible(false);
		lendinfoUI.setVisible(false);
		bookRecommendUI.setVisible(false);
		bookBookUI.setVisible(true);
	
	}
	//主界面公告栏显示的内容
	public void showNotice() {
		String sql = "select noticeContent from notice where id=1";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				noticeLabel.setText("公告："+rs.getString("noticeContent"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		
	}
	
	@FXML //预约按钮逻辑
	public void bookbookinfo(ActionEvent event){
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		String sql = "select * from book where bookNum=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
            ps.setString(1, bookBookid.getText());
			rs = ps.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBookNum(rs.getString("bookNum"));
				book.setBookName(rs.getString("bookName"));
				bookBookNum.setCellValueFactory(new PropertyValueFactory("bookNum"));
				bookBookName.setCellValueFactory(new PropertyValueFactory("bookName"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		bookBookTable.setItems(bookList);
	}
	
	@FXML  //预约界面中预约按钮的逻辑
	public void onbookbook(ActionEvent event){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String sql1 = "select * from book where bookNum=?";
		String sql2 = "insert into bookbook (bookBookNum,bookBookName,userID,bookTime) values(?,?,?,?)";
		String sql3 ="select nowUser from nowuser where nowID=1";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			ps1 = conn.prepareStatement(sql1);
			ps2 = conn.prepareStatement(sql2);
			ps3 = conn.prepareStatement(sql3);
            ps1.setString(1, bookBookid.getText());
			rs = ps1.executeQuery();
			rs2 = ps3.executeQuery();
			while(rs.next()){
			ps2.setString(1, rs.getString("bookNum"));
			ps2.setString(2, rs.getString("bookName"));
			}
			while(rs2.next()){		
			ps2.setString(3, rs2.getString("nowUser"));
			}
			ps2.setString(4, df.format(new Date()));
			int flag = ps2.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书预约");
				alert.setHeaderText(null);
				alert.setContentText("预约成功！");
				alert.showAndWait();
				sendContext.setText("");
			}
			
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("图书预约");
			alert.setHeaderText(null);
			alert.setContentText("图书预约失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
			
		}finally {
			JdbcUtil.realease(conn, ps1, rs);
			JdbcUtil.realease(conn, ps2, rs);
			JdbcUtil.realease(conn, ps3, rs2);
		
		}
	
	}
}
