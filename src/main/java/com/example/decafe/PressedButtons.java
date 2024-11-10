package com.example.decafe;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class PressedButtons {
  public BooleanProperty w = new SimpleBooleanProperty();
  public BooleanProperty a = new SimpleBooleanProperty();
  public BooleanProperty s = new SimpleBooleanProperty();
  public BooleanProperty d = new SimpleBooleanProperty();

  private BooleanBinding any = w.or(a).or(s).or(d);

  public void handleButtonPress(AnimationTimer timer) {
    any.addListener((observableValue, aBoolean, t1) -> { // if any key from the four keys is pressed
      if (!aBoolean) {
          timer.start();
      } else {
          timer.stop();
      }
    });
  }

  // key events if wasd-keys are pressed
  @FXML
  public void keyPressed(KeyEvent event) {
      switch (event.getCode()) {
          case W -> w.set(true);
          case A -> a.set(true);
          case S -> s.set(true);
          case D -> d.set(true);
      }
  }

  // key events if wasd-keys are released
  @FXML
  public void keyReleased(KeyEvent event) {
      switch (event.getCode()) {
          case W -> w.set(false);
          case A -> a.set(false);
          case S -> s.set(false);
          case D -> d.set(false);
      }
  }
}
