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
    any.addListener((observableValue, isButtonPressed, t1) -> { // if any key from the four keys is pressed
      if (!isButtonPressed) {
          timer.start();
      } else {
          timer.stop();
      }
    });
  }

  // key events if wasd-keys are pressed
  @FXML
  public void keyPressed(KeyEvent event) {
    changeButtonState(event, true);
  }

  // key events if wasd-keys are released
  @FXML
  public void keyReleased(KeyEvent event) {
    changeButtonState(event, false);
  } 

  private void changeButtonState(KeyEvent event, boolean newState) {
    switch (event.getCode()) {
      case W -> w.set(newState);
      case A -> a.set(newState);
      case S -> s.set(newState);
      case D -> d.set(newState);
    }
  }

  public boolean isTwoDiagonalButtonsPressed() {
    return w.get() && a.get()
      || w.get() && d.get() 
      || s.get() && a.get() 
      || s.get() && d.get();
  }
}
