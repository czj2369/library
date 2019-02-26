package ui.login;

/**
 * 登陆界面
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import jdbcUtil.JdbcUtil;
import ui.manageui.ManageUI;
import ui.userui.UserUI;

public class Login extends Application{
	private Button login;
	private TextField username;
	private TextField password;
	private Label tips;
	private RadioButton manage;
	private RadioButton reader;
	public static void main(String[] args) {
		Application.launch(Login.class,args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 加载布局文件
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		initView(root,primaryStage);
		//Scene 相当于一个画布，是场景中的根节点
		Scene scene = new Scene(root);
		//Stage 就是窗口，primaryStage是程序启动后的主窗口，由框架传入
		//讲scene场景绑定到主窗口中
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		//设置窗口名称
		primaryStage.setTitle("第七组图书馆系统");
		primaryStage.show();
	}

	private void initView(Parent parent,Stage stage){
		//获取登陆界面上的控件
		login = (Button)parent.lookup("#login");
		username = (TextField)parent.lookup("#username");
		password = (TextField)parent.lookup("#password");
		tips = (Label)parent.lookup("#tips");
		ToggleGroup group = new ToggleGroup();
		manage = (RadioButton)parent.lookup("#manage");
		reader = (RadioButton)parent.lookup("#reader");
		manage.setToggleGroup(group);
		reader.setToggleGroup(group);
		reader.setSelected(true);
		
		
		//登陆按钮事件
		login.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String sql = "";
				String nowUserSql = "update nowuser set nowUser=?,nowUserID=? where nowID=1";
				if(manage.isSelected()){
					sql = "select * from admin where adminUser=? and adminPass=?";
				}else if(reader.isSelected()){
					sql = "select * from user where username=? and password=?";
				}
				Connection conn = null;
				ResultSet rs = null;
				PreparedStatement ps = null;
				PreparedStatement ps2 = null;
				try {
					conn = JdbcUtil.getConn();
					ps = conn.prepareStatement(sql);
					ps2 = conn.prepareStatement(nowUserSql);
					ps.setString(1, username.getText());
					ps.setString(2, password.getText());
					rs = ps.executeQuery();
					if(reader.isSelected()){
						ps2.setString(1, username.getText());
						while(rs.next()){
							ps2.setString(2, rs.getString("readerID"));
						}
					}
					if(rs.first()){
						System.out.println("登陆成功");
						/*if(reader.isSelected()){
							ps2.executeUpdate();
						}*/
						tips.setText("");
						//如果选择管理员登陆并且成功登陆则启动进入管理员界面,否则进入读者界面
						if(manage.isSelected()){
							Platform.runLater(new Runnable() {
								public void run() {
									try {
										new ManageUI().start(new Stage());
										stage.hide();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
						}else{
							ps2.executeUpdate();
							Platform.runLater(new Runnable() {
								public void run() {
									try {
										new UserUI().start(new Stage());
										stage.hide();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
						}
					}else {
						System.out.println("登录失败");
						password.setText("");
						//tips.setText("登录失败，请重新确定帐号密码！");
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("登录失败");
						alert.setHeaderText(null);
						alert.setContentText("登录失败，请重新确定帐号密码！");

						alert.showAndWait();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					JdbcUtil.realease(conn, ps, rs);
					JdbcUtil.realease(conn, ps2, rs);
				}
				
			}
		});
	}
}
