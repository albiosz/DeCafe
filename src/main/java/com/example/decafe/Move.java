package com.example.decafe;

public class Move {
  public static final String UP = "up";
  public static final String DOWN = "down";
  public static final String LEFT = "left";
  public static final String RIGHT = "right";
  public static final String NONE = "none";


  private String direction = "none";
  private double xVelocity = 0;
  private double yVelocity = 0;

  public String getDirection() {
    return direction;
  }

  public double getXVelocity() {
    return xVelocity;
  }

  public double getYVelocity() {
    return yVelocity;
  }


  public void setDirection(String direction) {
    this.direction = direction;
  }

  public Move(PressedButtons pressedButtons, double velocity) {
    setDirection(pressedButtons);
    setVelocity(pressedButtons, velocity);
  }

  public void setDirection(PressedButtons pressedButtons) {
    if (pressedButtons.w.get()) {
      direction = UP;
    }

    // if waiter should move down
    if (pressedButtons.s.get()) {
        direction = DOWN;
    }

    // if waiter should move left
    if (pressedButtons.a.get()) {
        direction = LEFT;
    }

    // if waiter should move right
    if (pressedButtons.d.get()) {
        direction = RIGHT;
    }
  }

  public void setVelocity(PressedButtons pressedButtons, double velocity) {
    if (pressedButtons.w.get()) {
      yVelocity = -velocity; // negative move because otherwise waiter would move down
    }

    // if waiter should move down
    if (pressedButtons.s.get()) {
        yVelocity = velocity;
    }

    // if waiter should move left
    if (pressedButtons.a.get()) {
        xVelocity = -velocity; // negative move because otherwise waiter would move right
    }

    // if waiter should move right
    if (pressedButtons.d.get()) {
        xVelocity = velocity;
    }
  }
}
