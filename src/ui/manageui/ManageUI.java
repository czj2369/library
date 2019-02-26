package ui.manageui;
/**
 * 管理员主界面
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManageUI extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 加载布局文件
		Parent root = FXMLLoader.load(getClass().getResource("ManageUI.fxml"));
		initView(root);
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

	private void initView(Parent root) {
		// TODO Auto-generated method stub
		
	}
	
}
