package ui.manageui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mysql.jdbc.ResultSetRow;

import domain.Book;
import domain.BookRecommen;
import domain.Bookedbook;
import domain.FreeBack;
import domain.Hbook;
import domain.Lendbook;
import domain.Outbook;
import domain.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdbcUtil.JdbcUtil;
import ui.login.Login;

public class ManagerUIController {
	@FXML private Label logoLabel;
	@FXML private ImageView logo;
	@FXML private BorderPane rootPane;//这是管理界面界面的根布局的id
	
	@FXML private Button cancellation;//注销按钮
	@FXML private Button inBook;//图书入库按钮
	@FXML private Button outBook;//图书出库按钮
	@FXML private Button notice;//编辑公告按钮
	@FXML private Button dealRecommened;//处理图书荐购按钮
	@FXML private Button userManange;//读者管理按钮
	@FXML private Button addUser;//添加用户按钮
	
	@FXML private Pane inBookUI;//图书入库界面
	@FXML private Pane outBookUI;//图书出库界面
	@FXML private Pane noticeUI;//编辑公告界面
	@FXML private Pane dealRecommenedUI;//处理图书荐购界面
	@FXML private Pane userManangeUI;//读者管理界面
	@FXML private Pane addUserUI;//添加读者界面
	@FXML private Pane watchFreeBackUI;//查看反馈界面
	@FXML private Pane stockUI;//库存界面
	@FXML private Pane infoUI;//信息统计界面
	@FXML private TabPane dealLendUI;//处理借阅界面
	
	@FXML private Button gitBook;//图书入库提交按钮
	@FXML private Button cancel;//图书入库取消按钮
	@FXML private TextField bookNumText;//索书号
	@FXML private TextField bookNameText;//书名
	@FXML private TextField bookAuthorText;//作者
	@FXML private TextField bookPressText;//出版社
	@FXML private TextField bookTypeText;//类型
	@FXML private TextField bookSumText;//入库数量
	
	@FXML private Button gitNotice;//编辑公告界面中的确定按钮
	@FXML private TextArea noticeContent;//编辑公告界面中的文本框
	
	@FXML private TableView recommenTable;//处理荐购界面搜索结果表格
	@FXML private TableColumn bookName;//处理荐购列书名
	@FXML private TableColumn bookAuthor;//处理荐购列作者
	@FXML private TableColumn bookPress;//处理荐购列出版信息
	@FXML private TableColumn bookReason;//处理荐购列荐购理由
	
	@FXML private TableView outBookTable;//出库界面搜索结果表格
	@FXML private TableColumn bookoutName;//出库界面列书名
	@FXML private TableColumn bookoutAuthor;//出库界面列作者
	@FXML private TableColumn bookoutPress;//出库界面列出版信息
	@FXML private TableColumn bookoutNum;//出库界面列索书号
	@FXML private TableColumn bookoutSum;//出库界面列库存量
	@FXML private TextField searchOutBook;//出库界面中搜索文本框
	@FXML private TextField outNum;//单类出库界面中的索书号
	@FXML private TextField outAmount;//单类出库界面中的数量
	@FXML private TextField outReason;//单类出库界面中的原因
	
	@FXML private TextField readerID;//添加用户中的读者证号文本框
	@FXML private TextField Name;//添加用户中的读者名字
	@FXML private TextField userName;//添加用户中的帐号
	@FXML private TextField password;//添加用户中的密码
	@FXML private RadioButton male;//添加用户中的男
	@FXML private RadioButton female;//添加用户中的女
	
	@FXML private TableView backTable;//查看反馈界面中表格
	@FXML private TableColumn backUser;//查看反馈界面中读者列
	@FXML private TableColumn backInfo;//查看反馈界面中反馈信息列
	
	@FXML private TableView stockTable;//库存界面的表格
	@FXML private TextField stockContent;//搜索关键字
	@FXML private Button stockBook;//搜索按钮
	@FXML private TableColumn bookNum;
	@FXML private TableColumn bookName1;
	@FXML private TableColumn bookAuthor1;
	@FXML private TableColumn bookPress1;
	@FXML private TableColumn bookType;
	@FXML private TableColumn bookPrice;
	@FXML private TableColumn bookRest;
	@FXML private TableColumn bookSum;
	
	@FXML private TableView userinfoTable;//读者管理界面读者信息查询的表格以及各列
	@FXML private TableColumn infoReaderID;
	@FXML private TableColumn infoUsername;
	@FXML private TableColumn infoPassword;
	@FXML private TableColumn infoName;
	@FXML private TableColumn infoSex;
	@FXML private TableColumn infoEmail;
	@FXML private TableColumn infoPhone;
	@FXML private TableColumn infoState;
	@FXML private TextField searchUserInfoText;//搜索读者信息文本框
	@FXML private TextField oldUserInfoText;//读者管理中读者信息修改中 要修改的读者证号
	@FXML private TextField newUserInfoText;//读者管理中读者信息修改中 新的读者证号
	@FXML private TextField newNameInfoText;//读者管理中读者信息修改中 新的读者名字
	@FXML private RadioButton newMale;//新性别中的男
	@FXML private RadioButton newFemale;//新性别中的女
	
	@FXML private TextField fineReaderID;//读者管理界面中罚款通知的文本框
	@FXML private TextField fineMoney;
	@FXML private TextField fineReason;
	
	@FXML private TextField lendBookNum;//处理借阅预约中的索书号文本框
	@FXML private TextField lendBookReaderID;//处理借阅预约中的读者证文本框
	@FXML private TableView bookedTable;//处理借阅预约中的表格以及各列
	@FXML private TableColumn bookedBookName;
	@FXML private TableColumn bookedBookReaderID;
	@FXML private TableColumn bookedBookTime;
	@FXML private TableColumn bookedBookNum;
	
	@FXML private TableColumn lendedbookNum;
	@FXML private TableColumn lendedbookName;
	@FXML private TableColumn lendedbookAuthor;
	@FXML private TableColumn lendedbookPress;
	@FXML private TableColumn lendedbookType;
	@FXML private TableColumn lendedbookTime;
	@FXML private TableView lendbookedTable;//图书归还表格
	@FXML private TextField searchReaderIDText;//图书归还界面中的文本框
	
	@FXML private TableView hBookTable;//信息统计的表格以及各列
	@FXML private TableColumn hbookName;
	@FXML private TableColumn hbookAuthor;
	@FXML private TableColumn hbookPress;
	@FXML private TableColumn hbookNum;
	@FXML private TableColumn hbookType;
	@FXML private TableColumn hbookTimes;
	@FXML private Button historybutton; 
	
	@FXML private TableView userHlendTable;//信息统计的读者历史借阅以及各列
	@FXML private TableColumn userHname;
	@FXML private TableColumn userHauthor;
	@FXML private TableColumn userHpress;
	@FXML private TableColumn userHnum;
	@FXML private TableColumn userHlendtime;
	@FXML private TableColumn userHbacktime;
	@FXML private TextField userHreaderID;
	
	@FXML private TableView stockedTable;//库存界面的表格
	@FXML private TableColumn outedNum;
	@FXML private TableColumn outedName;
	@FXML private TableColumn outedReason;
	@FXML private TableColumn outedSum;
	@FXML private TableColumn outedTime;
	
	@FXML private ChoiceBox allhistory;
	
	@FXML //首页按钮逻辑
	public void returnIndex(ActionEvent event){
		logoLabel.setVisible(true);
		logo.setVisible(true);
		noticeUI.setVisible(true);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		inBookUI.setVisible(false);
		stockUI.setVisible(false);
		dealLendUI.setVisible(false);
		infoUI.setVisible(false);
	}
	
	@FXML //图书入库按钮逻辑
	public void onInBookUI(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		stockUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		inBookUI.setVisible(true);
	}
	@FXML //图书入库提交按钮逻辑
	public void onInBook(ActionEvent event){
		String sql = "insert into book (bookName,bookAuthor,bookPress,bookType,bookSum,bookRest,bookNum) "
				+ "values (?,?,?,?,?,?,?)";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int success = 0;
		try {
			if (bookNameText.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书入库");
				alert.setHeaderText(null);
				alert.setContentText("图书名称为空，请检查重试");
				alert.showAndWait();
			}else if (bookAuthorText.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书入库");
				alert.setHeaderText(null);
				alert.setContentText("图书作者为空，请检查重试");
				alert.showAndWait();
			}else if (bookSumText.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书入库");
				alert.setHeaderText(null);
				alert.setContentText("图书数量为空，请检查重试");
				alert.showAndWait();
			}else if (bookTypeText.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书入库");
				alert.setHeaderText(null);
				alert.setContentText("图书类型为空，请检查重试");
				alert.showAndWait();
			}else if (bookNumText.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书入库");
				alert.setHeaderText(null);
				alert.setContentText("图书索书号为空，请检查重试");
				alert.showAndWait();
			}else{
				ps = conn.prepareStatement(sql);
				ps.setString(1, bookNameText.getText());
				ps.setString(2, bookAuthorText.getText());
				ps.setString(3, bookPressText.getText());
				ps.setString(4, bookTypeText.getText());
				ps.setInt(5, Integer.valueOf(bookSumText.getText()));
				ps.setInt(6, Integer.valueOf(bookSumText.getText()));
				ps.setString(7, bookNumText.getText());
				success = ps.executeUpdate();
				if(success == 1){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("图书入库");
					alert.setHeaderText(null);
					alert.setContentText("图书入库成功");
					alert.showAndWait();
					bookNameText.setText("");
					bookAuthorText.setText("");
					bookPressText.setText("");
					bookTypeText.setText("");
					bookSumText.setText("");
					bookNumText.setText("");
				}
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("图书入库");
			alert.setContentText("图书号索引号已存在，请检查重试");
			alert.setHeaderText(null);
			alert.showAndWait();
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
	}
	
	@FXML //图书入库界面取消按钮逻辑
	public void onCancel(ActionEvent event){
		logoLabel.setVisible(true);
		logo.setVisible(true);
		inBookUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		noticeUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		outBookUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		stockUI.setVisible(false);
	}
	
	@FXML //注销按钮逻辑
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
	
	@FXML //图书出库按钮逻辑
	public void onOutBook(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		stockUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		outBookUI.setVisible(true);
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		String sql = "select * from book";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConn();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBookNum(rs.getString("bookNum"));
				book.setBookName(rs.getString("bookName"));
				book.setBookAuthor(rs.getString("bookAuthor"));
				book.setBookPress(rs.getString("bookPress"));
				book.setBookSum(rs.getInt("bookSum"));
				bookoutNum.setCellValueFactory(new PropertyValueFactory("bookNum"));
				bookoutName.setCellValueFactory(new PropertyValueFactory("bookName"));
				bookoutAuthor.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
				bookoutPress.setCellValueFactory(new PropertyValueFactory("bookPress"));
				bookoutSum.setCellValueFactory(new PropertyValueFactory("bookSum"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.realease(conn, ps, rs);
		}
		outBookTable.setItems(bookList);
	}
	
	@FXML //图书出库 搜索按钮逻辑
	public void onGitSearchOutBook(ActionEvent event){
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		String sql = "select * from book where bookName like ? or bookAuthor like ? or bookPress like ? or bookType like ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+searchOutBook.getText()+"%");
			ps.setString(2, "%"+searchOutBook.getText()+"%");
			ps.setString(3, "%"+searchOutBook.getText()+"%");
			ps.setString(4, "%"+searchOutBook.getText()+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBookNum(rs.getString("bookNum"));
				book.setBookName(rs.getString("bookName"));
				book.setBookAuthor(rs.getString("bookAuthor"));
				book.setBookPress(rs.getString("bookPress"));
				book.setBookSum(rs.getInt("bookSum"));
				bookoutNum.setCellValueFactory(new PropertyValueFactory("bookNum"));
				bookoutName.setCellValueFactory(new PropertyValueFactory("bookName"));
				bookoutAuthor.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
				bookoutPress.setCellValueFactory(new PropertyValueFactory("bookPress"));
				bookoutSum.setCellValueFactory(new PropertyValueFactory("bookSum"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.realease(conn, ps, rs);
		}
		outBookTable.setItems(bookList);
	}
	
	@FXML //图书出库 出库按钮逻辑
	public void onGitOutBook(ActionEvent event){
		String sql = "delete from book where bookNum=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) bookoutNum.getCellData(outBookTable.getSelectionModel().getSelectedIndex()));
			int flag = ps.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书出库");
				alert.setHeaderText(null);
				alert.setContentText("出库成功");
				ObservableList<Book> bookList = FXCollections.observableArrayList();
				String sql2 = "select * from book";
				Connection conn2 = null;
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				conn2 = JdbcUtil.getConn();
				try {
					ps2 = conn2.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while(rs2.next()){
						Book book = new Book();
						book.setBookNum(rs2.getString("bookNum"));
						book.setBookName(rs2.getString("bookName"));
						book.setBookAuthor(rs2.getString("bookAuthor"));
						book.setBookPress(rs2.getString("bookPress"));
						book.setBookSum(rs2.getInt("bookSum"));
						bookoutNum.setCellValueFactory(new PropertyValueFactory("bookNum"));
						bookoutName.setCellValueFactory(new PropertyValueFactory("bookName"));
						bookoutAuthor.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
						bookoutPress.setCellValueFactory(new PropertyValueFactory("bookPress"));
						bookoutSum.setCellValueFactory(new PropertyValueFactory("bookSum"));
						bookList.add(book);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					JdbcUtil.realease(conn2, ps2, rs2);
				}
				outBookTable.setItems(bookList);
				alert.showAndWait();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("图书出库");
			alert.setHeaderText(null);
			alert.setContentText("出库失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
	}
	
	@FXML //单类图书出库出库按钮逻辑
	public void outOnceBook(ActionEvent event){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String sql1 = "insert into outhistory (outNum,outAmount,outReason,outTime,outName) values(?,?,?,?,?)";
		String sql2 = "update book set bookRest=bookRest-1 where bookNum=?";
		String sql3 = "select * from book where bookNum=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		try {
			ps3 = conn.prepareStatement(sql3);
			ps3.setString(1, outNum.getText());
			rs3 = ps3.executeQuery();
			while(rs3.next()){
				int sum = rs3.getInt("bookSum");
				int rest = rs3.getInt("bookRest");
				String bookname = rs3.getString("bookName");
				if(outNum.getText().equals("")){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("图书出库");
					alert.setHeaderText(null);
					alert.setContentText("出库索书号为空，请重试！");
					alert.showAndWait();
				}else if(outAmount.getText().equals("")){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("图书出库");
					alert.setHeaderText(null);
					alert.setContentText("出库数量为空，请重试！");
					alert.showAndWait();
				}else if(sum-rest-Integer.valueOf(outAmount.getText())<0){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("图书出库");
					alert.setHeaderText(null);
					alert.setContentText("出库数量错误，超过库存，请重试！");
					alert.showAndWait();
				}else if(Integer.valueOf(outAmount.getText())<0){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("图书出库");
					alert.setHeaderText(null);
					alert.setContentText("出库数量填写错误，请重试！");
					alert.showAndWait();
				}else if(outReason.getText().equals("")){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("图书出库");
					alert.setHeaderText(null);
					alert.setContentText("出库原因为空，请重试！");
					alert.showAndWait();
				}else{
					ps1 = conn.prepareStatement(sql1);
					ps2 = conn.prepareStatement(sql2);
					ps1.setString(1, outNum.getText());
					ps1.setInt(2, Integer.valueOf(outAmount.getText()));
					ps1.setString(3, outReason.getText());
					ps1.setString(4, df.format(new Date()));
					ps1.setString(5, bookname);
					ps2.setString(1, outNum.getText());
					int flag = ps1.executeUpdate();
					if(flag == 1){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("图书出库");
						alert.setHeaderText(null);
						alert.setContentText("出库成功");
						alert.showAndWait();
						outNum.setText("");
						outAmount.setText("");
						outReason.setText("");
					}
					ps2.executeUpdate();
				}	
			}

		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("图书出库");
			alert.setHeaderText(null);
			alert.setContentText("出库失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}
		
	}
	
	@FXML //编辑公告按钮逻辑
	public void onNotice(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		stockUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		noticeUI.setVisible(true);
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from notice";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				noticeContent.setText(rs.getString("noticeContent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
	}
	
	@FXML //编辑公告中的确定按钮逻辑
	public void onGitNotice(ActionEvent event){
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		String sql = "update notice set noticeContent=? where id=1";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, noticeContent.getText());
			int flag = ps.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("编辑公告");
				alert.setHeaderText(null);
				alert.setContentText("修改成功");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("编辑公告");
			alert.setHeaderText(null);
			alert.setContentText("修改失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
	}
	
	@FXML //处理图书荐购按钮逻辑
	public void onDealRecommened(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		infoUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		stockUI.setVisible(false);
		dealLendUI.setVisible(false);
		dealRecommenedUI.setVisible(true);
		ObservableList<BookRecommen> bookList = FXCollections.observableArrayList();
		String sql = "select * from bookrecommen";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConn();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				BookRecommen book = new BookRecommen();
				book.setBookName(rs.getString("bookName"));
				book.setBookAuthor(rs.getString("bookAuthor"));
				book.setBookPress(rs.getString("bookPress"));
				book.setBookReason(rs.getString("bookReason"));
				bookName.setCellValueFactory(new PropertyValueFactory("bookName"));
				bookAuthor.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
				bookPress.setCellValueFactory(new PropertyValueFactory("bookPress"));
				bookReason.setCellValueFactory(new PropertyValueFactory("bookReason"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.realease(conn, ps, rs);
		}
		recommenTable.setItems(bookList);
	}
	@FXML //处理图书荐购界面中确定按钮的逻辑
	public void onGitRecommen(ActionEvent event){
		//还没写逻辑
	}
	
	@FXML //读者管理按钮逻辑
	public void onUserManange(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		stockUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		userManangeUI.setVisible(true);
		
		ObservableList<User> userList = FXCollections.observableArrayList();
		String sql = "select * from user";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConn();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setReaderID(rs.getString("readerID"));
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setSex(rs.getString("sex"));
				user.setState(rs.getString("state"));
				infoName.setCellValueFactory(new PropertyValueFactory("name"));
				infoReaderID.setCellValueFactory(new PropertyValueFactory("readerID"));
				infoUsername.setCellValueFactory(new PropertyValueFactory("username"));
				infoPassword.setCellValueFactory(new PropertyValueFactory("password"));
				infoSex.setCellValueFactory(new PropertyValueFactory("sex"));
				infoPhone.setCellValueFactory(new PropertyValueFactory("phone"));
				infoState.setCellValueFactory(new PropertyValueFactory("state"));
				infoEmail.setCellValueFactory(new PropertyValueFactory("email"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.realease(conn, ps, rs);
		}
		userinfoTable.setItems(userList);
	}
	
	@FXML //读者管理中读者信息查询搜索按钮逻辑
	public void onSearchUserInfo(ActionEvent event){
		ObservableList<User> userList = FXCollections.observableArrayList();
		String sql = "select * from user where "
				+ "readerID like ? or username like ? or password like ? or name like ?"
				+ "or sex like ? or email like ? or phone like ? or state like ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+searchUserInfoText.getText()+"%");
			ps.setString(2, "%"+searchUserInfoText.getText()+"%");
			ps.setString(3, "%"+searchUserInfoText.getText()+"%");
			ps.setString(4, "%"+searchUserInfoText.getText()+"%");
			ps.setString(5, "%"+searchUserInfoText.getText()+"%");
			ps.setString(6, "%"+searchUserInfoText.getText()+"%");
			ps.setString(7, "%"+searchUserInfoText.getText()+"%");
			ps.setString(8, "%"+searchUserInfoText.getText()+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setReaderID(rs.getString("readerID"));
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setSex(rs.getString("sex"));
				user.setState(rs.getString("state"));
				infoName.setCellValueFactory(new PropertyValueFactory("name"));
				infoReaderID.setCellValueFactory(new PropertyValueFactory("readerID"));
				infoUsername.setCellValueFactory(new PropertyValueFactory("username"));
				infoPassword.setCellValueFactory(new PropertyValueFactory("password"));
				infoSex.setCellValueFactory(new PropertyValueFactory("sex"));
				infoPhone.setCellValueFactory(new PropertyValueFactory("phone"));
				infoState.setCellValueFactory(new PropertyValueFactory("state"));
				infoEmail.setCellValueFactory(new PropertyValueFactory("email"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.realease(conn, ps, rs);
		}
		userinfoTable.setItems(userList);
	}
	
	@FXML //读者管理中读者信息查询重置按钮逻辑
	public void onReset(ActionEvent event){
		searchUserInfoText.setText("");
	}
	
	@FXML //读者管理中读者信息修改中修改按钮逻辑
	public void onChangeUser(ActionEvent event){
		String sql = "update user set readerID=?,name=?,sex=? where readerID=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ToggleGroup group = new ToggleGroup();
		newMale.setToggleGroup(group);
		newFemale.setToggleGroup(group);
		try {
			if (newUserInfoText.getText().equals("")||newUserInfoText.getLength()!=11) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("读者信息");
				alert.setHeaderText(null);
				alert.setContentText("新的读者证号为空或者不是11位，请检查重试！");
				alert.showAndWait();
			}else if(newNameInfoText.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("读者信息");
				alert.setHeaderText(null);
				alert.setContentText("新的读者姓名为空，请检查重试！");
				alert.showAndWait();
			}else if (!newMale.isSelected()&&!newFemale.isSelected()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("读者信息");
				alert.setHeaderText(null);
				alert.setContentText("读者性别为空，请检查重试！");
				alert.showAndWait();
			}else if (oldUserInfoText.getText().equals("")||oldUserInfoText.getLength()!=11) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("读者信息");
				alert.setHeaderText(null);
				alert.setContentText("旧的读者证号为空或者不是11位，请检查重试！");
				alert.showAndWait();
			}else{
				String sex = "";
				if(male.isSelected()){
					sex = "男";
				}else if(female.isSelected()){
					sex = "女";
				}
				ps = conn.prepareStatement(sql);
				ps.setString(1, newUserInfoText.getText());
				ps.setString(2, newNameInfoText.getText());
				ps.setString(3, sex);
				ps.setString(4, oldUserInfoText.getText());
				int flag = ps.executeUpdate();
				if(flag == 1){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("读者信息");
					alert.setHeaderText(null);
					alert.setContentText("修改成功");
					oldUserInfoText.setText("");
					newUserInfoText.setText("");
					newNameInfoText.setText("");
					newMale.setSelected(false);
					newFemale.setSelected(false);
					alert.showAndWait();
				}else{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("读者信息");
					alert.setHeaderText(null);
					alert.setContentText("修改失败，请检查修改的图书证号是否存在！");
					alert.showAndWait();
				}
			}
			
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("读者信息");
			alert.setHeaderText(null);
			alert.setContentText("修改失败，新的读者证已存在！");
			alert.showAndWait();
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
	}
	
	@FXML //读者管理中读者信息修改中撤销按钮逻辑
	public void onReChange(ActionEvent event){
		oldUserInfoText.setText("");
		newUserInfoText.setText("");
		newNameInfoText.setText("");
		newMale.setSelected(false);
		newFemale.setSelected(false);
	}
	
	@FXML //读者管理中读者信息查询销户按钮逻辑
	public void onCloseUser(ActionEvent event){
		String sql = "delete from user where readerID=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) infoReaderID.getCellData(userinfoTable.getSelectionModel().getSelectedIndex()));
			int flag = ps.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("读者销户");
				alert.setHeaderText(null);
				alert.setContentText("销户成功");
				ObservableList<User> userList = FXCollections.observableArrayList();
				String sql2 = "select * from user";
				Connection conn2 = null;
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				conn2 = JdbcUtil.getConn();
				try {
					ps2 = conn2.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while(rs2.next()){
						User user = new User();
						user.setReaderID(rs2.getString("readerID"));
						user.setName(rs2.getString("name"));
						user.setUsername(rs2.getString("username"));
						user.setPassword(rs2.getString("password"));
						user.setPhone(rs2.getString("phone"));
						user.setEmail(rs2.getString("email"));
						user.setSex(rs2.getString("sex"));
						user.setState(rs2.getString("state"));
						infoName.setCellValueFactory(new PropertyValueFactory("name"));
						infoReaderID.setCellValueFactory(new PropertyValueFactory("readerID"));
						infoUsername.setCellValueFactory(new PropertyValueFactory("username"));
						infoPassword.setCellValueFactory(new PropertyValueFactory("password"));
						infoSex.setCellValueFactory(new PropertyValueFactory("sex"));
						infoPhone.setCellValueFactory(new PropertyValueFactory("phone"));
						infoState.setCellValueFactory(new PropertyValueFactory("state"));
						infoEmail.setCellValueFactory(new PropertyValueFactory("email"));
						userList.add(user);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					JdbcUtil.realease(conn, ps, rs2);
				}
				userinfoTable.setItems(userList);
				alert.showAndWait();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("读者销户");
			alert.setHeaderText(null);
			alert.setContentText("销户失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
	}
	
	@FXML //读者管理中缴纳罚款通知的提交按钮逻辑
	public void onGitFine(ActionEvent event){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String sql = "insert into finepay (userID,fineReason,fineTime,fineMoney) values (?,?,?,?)";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, fineReaderID.getText());
			ps.setString(2, fineReason.getText());
			ps.setString(3, df.format(new Date()));
			ps.setFloat(4, Float.valueOf(fineMoney.getText()));
			int flag = ps.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("罚款通知");
				alert.setHeaderText(null);
				alert.setContentText("通知成功");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("罚款通知");
			alert.setHeaderText(null);
			alert.setContentText("通知失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
		
		
	}
	
	@FXML //添加用户按钮逻辑
	public void onAddUser(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		stockUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		addUserUI.setVisible(true);
	}
	
	@FXML //添加用户中添加按钮逻辑
	public void onGitAdd(ActionEvent event){
		ToggleGroup group = new ToggleGroup();
		male.setToggleGroup(group);
		female.setToggleGroup(group);
		String sql = "insert into user (readerID,name,sex,username,password,state) values (?,?,?,?,?,?)";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		try {
			if (readerID.getText().equals("")||readerID.getLength()!=11) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("添加用户");
				alert.setHeaderText(null);
				alert.setContentText("读者证号为空或者不是11位，请检查重试！");
				alert.showAndWait();
			}else if(Name.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("添加用户");
				alert.setHeaderText(null);
				alert.setContentText("读者姓名为空，请检查重试！");
				alert.showAndWait();
			}else if (!male.isSelected()&&!female.isSelected()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("添加用户");
				alert.setHeaderText(null);
				alert.setContentText("读者性别为空，请检查重试！");
				alert.showAndWait();
			}else if (userName.getText().equals("")||userName.getLength()<=7||userName.getLength()>=25) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("添加用户");
				alert.setHeaderText(null);
				alert.setContentText("帐号为空或者长度不满足7~20位，请检查重试！");
				alert.showAndWait();
			}else if (password.getText().equals("")||password.getLength()<6) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("添加用户");
				alert.setHeaderText(null);
				alert.setContentText("密码为空或者密码少于六位，请检查重试！");
				alert.showAndWait();
			}else{
				ps = conn.prepareStatement(sql);
				String sex = "";
				if(male.isSelected()){
					sex = "男";
				}else if(female.isSelected()){
					sex = "女";
				}
				ps.setString(1, readerID.getText());
				ps.setString(2, Name.getText());
				ps.setString(4, userName.getText());
				ps.setString(3, sex);
				ps.setString(5, password.getText());
				ps.setString(6, "正常");
				int flag = ps.executeUpdate();
				if(flag == 1){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("添加用户");
					alert.setHeaderText(null);
					alert.setContentText("添加成功");
					alert.showAndWait();
					readerID.setText("");
					Name.setText("");
					userName.setText("");
					password.setText("");
				}
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("添加用户");
			alert.setHeaderText(null);
			alert.setContentText("读者证号或帐号已存在，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
		}
	}
	
	@FXML //查看反馈按钮逻辑
	public void onWatchFreeBack(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		stockUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		watchFreeBackUI.setVisible(true);
		ObservableList<FreeBack> freeList = FXCollections.observableArrayList();
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from freeback";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				FreeBack fb = new FreeBack();
				fb.setBackUser(rs.getString("user"));
				fb.setBackInfo(rs.getString("content"));
				backUser.setCellValueFactory(new PropertyValueFactory("backUser"));
				backInfo.setCellValueFactory(new PropertyValueFactory("backInfo"));
				freeList.add(fb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		backTable.setItems(freeList);
	}
	
	@FXML //库存按钮逻辑
	public void onStock(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(false);
		stockUI.setVisible(true);
		
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from book";
		try {
			ps = conn.prepareStatement(sql);
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
				bookName1.setCellValueFactory(new PropertyValueFactory("bookName"));
				bookAuthor1.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
				bookPress1.setCellValueFactory(new PropertyValueFactory("bookPress"));
				bookType.setCellValueFactory(new PropertyValueFactory("bookType"));
				bookRest.setCellValueFactory(new PropertyValueFactory("bookRest"));
				bookSum.setCellValueFactory(new PropertyValueFactory("bookSum"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		stockTable.setItems(bookList);
		
		
		//<--------------------------------------------------->
		ObservableList<Outbook> outList = FXCollections.observableArrayList();
		sql = "select * from outhistory";

		 conn = null;
		 ps = null;
	
		 rs = null;
		conn = JdbcUtil.getConn();
		try {
	
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

	
			while (rs.next()) {
	

				
				Outbook outbook = new Outbook();
				outbook.setOutNum(rs.getString("outNum"));
				outbook.setOutName(rs.getString("outName"));
				outbook.setOutReason(rs.getString("outReason"));
				outbook.setOutSum(rs.getString("outAmount"));
				outbook.setOutTime(rs.getString("outTime"));
				 
				outedNum.setCellValueFactory(new PropertyValueFactory("outNum"));
				outedName.setCellValueFactory(new PropertyValueFactory("outName"));
				outedReason.setCellValueFactory(new PropertyValueFactory("outReason"));
				outedSum.setCellValueFactory(new PropertyValueFactory("outSum"));
				outedTime.setCellValueFactory(new PropertyValueFactory("outTime"));
				outList.add(outbook);
				 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		stockedTable.setItems(outList);
	}
	
	@FXML //库存界面搜索按钮逻辑
	public void onStockSearchBook(ActionEvent event) {
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		String sql = "select * from book where bookName like ? or bookAuthor like ? or bookPress like ? or bookType like ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = JdbcUtil.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + stockContent.getText() + "%");
			ps.setString(2, "%" + stockContent.getText() + "%");
			ps.setString(3, "%" + stockContent.getText() + "%");
			ps.setString(4, "%" + stockContent.getText() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setBookNum(rs.getString("bookNum"));
				book.setBookName(rs.getString("bookName"));
				book.setBookAuthor(rs.getString("bookAuthor"));
				book.setBookPress(rs.getString("bookPress"));
				book.setBookType(rs.getString("bookType"));
				book.setBookRest(rs.getInt("bookRest"));
				book.setBookSum(rs.getInt("bookSum"));
				bookNum.setCellValueFactory(new PropertyValueFactory("bookNum"));
				bookName1.setCellValueFactory(new PropertyValueFactory("bookName"));
				bookAuthor1.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
				bookPress1.setCellValueFactory(new PropertyValueFactory("bookPress"));
				bookType.setCellValueFactory(new PropertyValueFactory("bookType"));
				bookRest.setCellValueFactory(new PropertyValueFactory("bookRest"));
				bookSum.setCellValueFactory(new PropertyValueFactory("bookSum"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		stockTable.setItems(bookList);
	}
	
	@FXML //信息统计按钮逻辑
	public void onInfo(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		stockUI.setVisible(false);
		dealLendUI.setVisible(false);
		infoUI.setVisible(true);
		allhistory.setItems(FXCollections.observableArrayList(
			    "一个月内", "半年内", "一年内" ));
		allhistory.setValue("一个月内");
	}
	
	@FXML //信息统计按钮逻辑
	public void Hbookbutton(ActionEvent event) {
		ObservableList<Hbook> hbookList = FXCollections.observableArrayList();
		String sql = "SELECT*,count(1) AS counts "
				+ "FROM historybook "
				+ "WHERE (UNIX_TIMESTAMP(hbookbackTime) - UNIX_TIMESTAMP(hbooklendTime))<=? "
				+ "GROUP BY hbookNum";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;//容器
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if(allhistory.getSelectionModel().getSelectedItem().equals("一个月内")){
				ps.setInt(1, 2592000);
			}
			if(allhistory.getSelectionModel().getSelectedItem().equals("半年内")){
				ps.setInt(1, 15552000);
			}
			if(allhistory.getSelectionModel().getSelectedItem().equals("一年内")){
				ps.setInt(1, 31536000);
			}
			rs = ps.executeQuery();	
			while(rs.next()){ 
				Hbook hbook=new Hbook();
				hbook.setHbookNum(rs.getString("hbookNum"));
				hbook.setHbookName(rs.getString("hbookName"));
				hbook.setHbookAuthor(rs.getString("hbookAuthor"));
				hbook.setHbookPress(rs.getString("hbookPress"));
				hbook.setHbookType(rs.getString("hbookType"));
				hbook.setHlendDate(rs.getDate("hbooklendTime"));
				hbook.setHbackDate(rs.getDate("hbookbackTime"));
				hbook.setHbookTimes(rs.getInt("counts"));
				
				hbookNum.setCellValueFactory(new PropertyValueFactory("hbookNum"));
				hbookName.setCellValueFactory(new PropertyValueFactory("hbookName"));
	   			hbookAuthor.setCellValueFactory(new PropertyValueFactory("hbookAuthor"));
	   			hbookPress.setCellValueFactory(new PropertyValueFactory("hbookPress"));
	   			hbookType.setCellValueFactory(new PropertyValueFactory("hbookType")); 
	   			hbookTimes.setCellValueFactory(new PropertyValueFactory("hbookTimes"));
	   			hbookList.add(hbook);
	   			}
			}catch (SQLException e) {
				e.printStackTrace();
	   		}finally {
	   			JdbcUtil.realease(conn, ps, rs);
	   			}
		hBookTable.setItems(hbookList);
		}
	   	
	
	@FXML //信息统计中读者借阅历史查看按钮逻辑
	public void gitUserHistory(ActionEvent event){
		ObservableList<Hbook> hbookList = FXCollections.observableArrayList();
		String sql = "select * from historybook where readerID=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;//容器
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userHreaderID.getText());
			rs = ps.executeQuery();	
			while(rs.next()){
				Hbook hbook=new Hbook();
				hbook.setHbookNum(rs.getString("hbookNum"));
				hbook.setHbookName(rs.getString("hbookName"));
				hbook.setHbookAuthor(rs.getString("hbookAuthor"));
				hbook.setHbookPress(rs.getString("hbookPress"));
				hbook.setHlendDate(rs.getDate("hbooklendTime"));
				hbook.setHbackDate(rs.getDate("hbookbackTime"));
				
				userHnum.setCellValueFactory(new PropertyValueFactory("hbookNum"));
				userHname.setCellValueFactory(new PropertyValueFactory("hbookName"));
	   			userHauthor.setCellValueFactory(new PropertyValueFactory("hbookAuthor"));
	   			userHpress.setCellValueFactory(new PropertyValueFactory("hbookPress"));
	   			userHlendtime.setCellValueFactory(new PropertyValueFactory("hlendDate")); 
	   			userHbacktime.setCellValueFactory(new PropertyValueFactory("hbackDate"));
	   			hbookList.add(hbook);
			}
			}catch (SQLException e) {
				e.printStackTrace();
	   		}finally {
	   			JdbcUtil.realease(conn, ps, rs);
	   			}
		userHlendTable.setItems(hbookList);
	}
	
	@FXML //图书借阅预约按钮逻辑
	public void onDealLend(ActionEvent event){
		logoLabel.setVisible(false);
		logo.setVisible(false);
		inBookUI.setVisible(false);
		outBookUI.setVisible(false);
		noticeUI.setVisible(false);
		dealRecommenedUI.setVisible(false);
		userManangeUI.setVisible(false);
		addUserUI.setVisible(false);
		watchFreeBackUI.setVisible(false);
		stockUI.setVisible(false);
		dealLendUI.setVisible(false);
		infoUI.setVisible(false);
		dealLendUI.setVisible(true);
		
		ObservableList<Bookedbook> bookList = FXCollections.observableArrayList();
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from bookbook";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Bookedbook book = new Bookedbook();
				book.setBookBookNum(rs.getString("bookBookNum"));
				book.setBookBookName(rs.getString("bookBookName"));
				book.setUserID(rs.getString("userID"));
				book.setBookTime(rs.getString("bookTime"));
				bookedBookReaderID.setCellValueFactory(new PropertyValueFactory("userID"));
				bookedBookName.setCellValueFactory(new PropertyValueFactory("bookBookName"));
				bookedBookNum.setCellValueFactory(new PropertyValueFactory("bookBookNum"));
				bookedBookTime.setCellValueFactory(new PropertyValueFactory("bookTime"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps, rs);
		}
		bookedTable.setItems(bookList);
	}
	
	@FXML //图书借阅预约界面中搜索按钮
	public void onSearchBackBook(ActionEvent event){
		ObservableList<Lendbook> lendList = FXCollections.observableArrayList();
		String sql = "select * from lendbook where readerID=?";
		String sql2 = "select * from book where bookNum=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps1 = null;//容器
		PreparedStatement ps2 = null;//容器
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			ps1 = conn.prepareStatement(sql);
			ps2 = conn.prepareStatement(sql2);
			ps1.setString(1, searchReaderIDText.getText());
			rs1 = ps1.executeQuery();
			while(rs1.next()){
				ps2.setString(1, rs1.getString("bookNum"));
				rs2 = ps2.executeQuery();	 
				while(rs2.next()){
					Lendbook lendbook=new Lendbook();
					lendbook.setLendbookName(rs2.getString("bookName"));
					lendbook.setLendbookNum(rs2.getString("bookNum"));
					lendbook.setLendbookAuthor(rs2.getString("bookAuthor"));
					lendbook.setLendbookPress(rs2.getString("bookPress"));
					lendbook.setLendbookType(rs2.getString("bookType"));
					lendbook.setLenddate(rs1.getDate("lendTime"));

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			      
					lendbook.setBackdate(df.format(new Date()));
					
					lendedbookNum.setCellValueFactory(new PropertyValueFactory("lendbookNum"));
					lendedbookName.setCellValueFactory(new PropertyValueFactory("lendbookName"));
					lendedbookAuthor.setCellValueFactory(new PropertyValueFactory("lendbookAuthor"));
					lendedbookPress.setCellValueFactory(new PropertyValueFactory("lendbookPress"));
					lendedbookType.setCellValueFactory(new PropertyValueFactory("lendbookType"));
					lendedbookTime.setCellValueFactory(new PropertyValueFactory("lenddate"));
					
					lendList.add(lendbook);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps1, rs1);
			JdbcUtil.realease(conn, ps2, rs2);
		}
		lendbookedTable.setItems(lendList);
	}
	
	@FXML //图书借阅预约中归还按钮逻辑
	public void onGitBack(ActionEvent event){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String sql = "insert into historybook "
				+ "(hbookNum,hbookName,hbookAuthor,hbookPress,hbookType,hbooklendTime,hbookbackTime) "
				+ "values (?,?,?,?,?,?,?)";
		String sql2 = "delete from lendbook where readerID=? and bookNum=?";
		String sql3 = "select * from book where bookNum=?";
		String sql4 = "select * from lendbook where readerID=? and bookNum=?";
		String sql8 = "update book set bookRest=bookRest+1 where bookNum=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps8 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			ps1 = conn.prepareStatement(sql);
			ps2 = conn.prepareStatement(sql2);
			ps3 = conn.prepareStatement(sql3);
			ps4 = conn.prepareStatement(sql4);
			ps8 = conn.prepareStatement(sql8);
			ps3.setString(1, (String) lendedbookNum.getCellData(lendbookedTable.getSelectionModel().getSelectedIndex()));
			ps4.setString(1, searchReaderIDText.getText());
			ps4.setString(2, (String) lendedbookNum.getCellData(lendbookedTable.getSelectionModel().getSelectedIndex()));
			rs = ps3.executeQuery();
			while(rs.next()){
				ps1.setString(1, rs.getString("bookNum"));
				ps1.setString(2, rs.getString("bookName"));
				ps1.setString(3, rs.getString("bookAuthor"));
				ps1.setString(4, rs.getString("bookPress"));
				ps1.setString(5, rs.getString("bookType"));
			}
			rs2 = ps4.executeQuery();
			while(rs2.next()){
				ps1.setString(6, rs2.getString("lendTime"));
				ps1.setString(7, df.format(new Date()));
			}
			ps1.executeUpdate();
			ps2.setString(1, searchReaderIDText.getText());
			ps2.setString(2, (String) lendedbookNum.getCellData(lendbookedTable.getSelectionModel().getSelectedIndex()));
			ps2.executeUpdate();
			ps8.setString(1, (String) lendedbookNum.getCellData(lendbookedTable.getSelectionModel().getSelectedIndex()));
			ps8.executeUpdate();
			
			ObservableList<Lendbook> lendList = FXCollections.observableArrayList();
			String sql5 = "select * from lendbook where readerID=?";
			String sql6 = "select * from book where bookNum=?";
			Connection conn1 = JdbcUtil.getConn();
			PreparedStatement ps5 = null;//容器
			PreparedStatement ps6 = null;//容器
			ResultSet rs3 = null;
			ResultSet rs4 = null;
			try {
				ps5 = conn.prepareStatement(sql5);
				ps6 = conn.prepareStatement(sql6);
				ps5.setString(1, searchReaderIDText.getText());
				rs3 = ps5.executeQuery();
				while(rs3.next()){
					ps6.setString(1, rs3.getString("bookNum"));
					rs4 = ps6.executeQuery();	 
					while(rs4.next()){
						Lendbook lendbook=new Lendbook();
						lendbook.setLendbookName(rs4.getString("bookName"));
						lendbook.setLendbookNum(rs4.getString("bookNum"));
						lendbook.setLendbookAuthor(rs4.getString("bookAuthor"));
						lendbook.setLendbookPress(rs4.getString("bookPress"));
						lendbook.setLendbookType(rs4.getString("bookType"));
						lendbook.setLenddate(rs3.getDate("lendTime"));

						lendedbookNum.setCellValueFactory(new PropertyValueFactory("lendbookNum"));
						lendedbookName.setCellValueFactory(new PropertyValueFactory("lendbookName"));
						lendedbookAuthor.setCellValueFactory(new PropertyValueFactory("lendbookAuthor"));
						lendedbookPress.setCellValueFactory(new PropertyValueFactory("lendbookPress"));
						lendedbookType.setCellValueFactory(new PropertyValueFactory("lendbookType"));
						lendedbookTime.setCellValueFactory(new PropertyValueFactory("lenddate"));
						
						lendList.add(lendbook);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				JdbcUtil.realease(conn, ps1, rs);
				JdbcUtil.realease(conn, ps2, rs2);
				JdbcUtil.realease(conn, ps3, rs3);
				JdbcUtil.realease(conn, ps4, rs4);
				JdbcUtil.realease(conn1, ps5, rs2);
				JdbcUtil.realease(conn, ps6, rs2);
			}
			lendbookedTable.setItems(lendList);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML //图书借阅预约中处理按钮逻辑
	private void onGitBook(ActionEvent event){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String sql = "insert into lendbook (bookNum,readerID,lendTime) values (?,?,?)";
		String sql2 = "delete from bookbook where userID=? and bookBookNum=?";
		String sql3 = "select * from bookbook";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps2 = conn.prepareStatement(sql2);
			ps3 = conn.prepareStatement(sql3);
			ps.setString(1, (String) bookedBookNum.getCellData(bookedTable.getSelectionModel().getSelectedIndex()));
			ps.setString(2, (String) bookedBookReaderID.getCellData(bookedTable.getSelectionModel().getSelectedIndex()));
			ps.setString(3, df.format(new Date()));
			int flag = ps.executeUpdate();
			if(flag == 1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("图书预约");
				alert.setHeaderText(null);
				alert.setContentText("处理预约成功");
				ps2.setString(2, (String) bookedBookNum.getCellData(bookedTable.getSelectionModel().getSelectedIndex()));
				ps2.setString(1, (String) bookedBookReaderID.getCellData(bookedTable.getSelectionModel().getSelectedIndex()));
				ps2.executeUpdate();
				alert.showAndWait();
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("图书预约");
			alert.setHeaderText(null);
			alert.setContentText("处理预约失败，请重试！");
			alert.showAndWait();
			e.printStackTrace();
		}
		ObservableList<Bookedbook> bookList = FXCollections.observableArrayList();
		try {
			rs = ps3.executeQuery();
			while(rs.next()){
				Bookedbook book = new Bookedbook();
				book.setBookBookNum(rs.getString("bookBookNum"));
				book.setBookBookName(rs.getString("bookBookName"));
				book.setUserID(rs.getString("userID"));
				book.setBookTime(rs.getString("bookTime"));
				bookedBookReaderID.setCellValueFactory(new PropertyValueFactory("userID"));
				bookedBookName.setCellValueFactory(new PropertyValueFactory("bookBookName"));
				bookedBookNum.setCellValueFactory(new PropertyValueFactory("bookBookNum"));
				bookedBookTime.setCellValueFactory(new PropertyValueFactory("bookTime"));
				bookList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
			JdbcUtil.realease(conn, ps2);
			JdbcUtil.realease(conn, ps3, rs);
		}
		bookedTable.setItems(bookList);
	}
	
	@FXML //图书借阅预约中确定借阅按钮逻辑
	private void onGitLend(ActionEvent event){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String sql = "insert into lendbook (bookNum,readerID,lendTime) values (?,?,?)";
		String sql2 = "update book set bookRest=bookRest-1 where bookNum=?";
		String sql3 = "select state from user where readerID=?";
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		try {
			ps3 = conn.prepareStatement(sql3);
			ps3.setString(1, lendBookReaderID.getText());
			rs = ps3.executeQuery();
			while(rs.next()){
				if (rs.getString("state").equals("正常")) {
					try {
						ps2 = conn.prepareStatement(sql2);
						ps = conn.prepareStatement(sql);
						ps.setString(1, lendBookNum.getText());
						ps.setString(2, lendBookReaderID.getText());
						ps.setString(3, df.format(new Date()));
						int flag = ps.executeUpdate();
						if(flag == 1){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("图书借阅");
							alert.setHeaderText(null);
							alert.setContentText("借阅成功");
							ps2.setString(1, lendBookNum.getText());
							ps2.executeUpdate();
							lendBookNum.setText("");
							lendBookReaderID.setText("");
							alert.showAndWait();
						}
					} catch (SQLException e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("图书借阅");
						alert.setHeaderText(null);
						alert.setContentText("借阅失败，请重试！");
						alert.showAndWait();
						e.printStackTrace();
					}
				}else{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("图书借阅");
					alert.setHeaderText(null);
					alert.setContentText("当前帐号状态为挂失，无法进行借阅！");
					alert.showAndWait();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.realease(conn, ps);
			JdbcUtil.realease(conn, ps3, rs);
			JdbcUtil.realease(conn, ps2);
		}
		
	}
}
