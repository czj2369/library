package ui.userui;

import java.net.URL;

/**
 * 用户读者主界面
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jdbcUtil.JdbcUtil;

public class UserUI extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 加载布局文件
//		Parent root = FXMLLoader.load(getClass().getResource("UserUI.fxml"));
		URL location = getClass().getResource("UserUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
		initView(root,fxmlLoader);
		//Scene 相当于一个画布，是场景中的根节点
		Scene scene = new Scene(root);
		//Stage 就是窗口，primaryStage是程序启动后的主窗口，由框架传入
		//将scene场景绑定到主窗口中
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		//设置窗口名称
		primaryStage.setTitle("第七组图书馆系统");
		primaryStage.show();
	}

	private void initView(Parent root,FXMLLoader fxmlLoader) {
		Platform.runLater(new Runnable() {
			public void run() {
				UserUIController userUIController =  fxmlLoader.getController();
				userUIController.showNotice();
			}
		});
	}

}
