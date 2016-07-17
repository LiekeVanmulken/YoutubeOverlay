import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Optional;

/**
 * Created by wouter on 16-7-2016.
 */
public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setPrefSize(640, 390);

		Label lbl = new Label("Set your size and position now");
		root.getChildren().add(lbl);


		HBox input = new HBox();

		TextField tf = new TextField("http://www.youtube.com/embed/7xNEXsR-cSQ?autoplay=1");
		tf.setPromptText("Video link");
		input.getChildren().add(tf);

		Button btn = new Button("Start");
		input.getChildren().add(btn);
//		btn.setOnAction(e -> btnClicked(stage, tf.getText()));
		btn.setOnAction(e -> showBorder(stage, tf.getText(),true));

		root.getChildren().add(input);

		Label lbl1 = new Label("Use your \"F5\" key to show the border.");
		root.getChildren().add(lbl1);

		stage.setScene(new Scene(root));
		stage.show();
	}

	public void showBorder(Stage stage, String link, boolean showBorder) {
		double x = stage.getX();
		double y = stage.getY();
		double width = stage.getWidth();
		double height = stage.getHeight();
		WebView webView;
		if(link == null) {
			webView = (WebView) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
		}else {
			webView = new WebView();
			webView.getEngine().load(
					link
			);
		}
		stage.close();
		HBox root = new HBox();

		root.getChildren().add(webView);
		root.setPrefSize(width, height);

		Stage stage1 = new Stage();
		stage1.setX(x);
		stage1.setY(y);

		Scene scene = new Scene(root);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.F5) {
					showBorder(stage1,null ,!showBorder);
				}
				if (event.getCode() == KeyCode.F4) {
					stage1.close();
					try {
						webView.getEngine().load("");
						start(new Stage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (event.getCode() == KeyCode.ESCAPE) {
					stage1.setAlwaysOnTop(false);
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close the application ?");
					Optional<ButtonType> result = alert.showAndWait();
					if(result.get() == ButtonType.OK) {
						Platform.exit();
					}
					stage1.setAlwaysOnTop(true);
				}
			}
		});


		stage1.setScene(scene);
		stage1.setAlwaysOnTop(true);
		if (showBorder) {
			stage1.initStyle(StageStyle.DECORATED);
		} else {
			stage1.initStyle(StageStyle.UNDECORATED);
		}
		stage1.show();

	}
}

