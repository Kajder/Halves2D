package game2d.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public abstract class UIObject {

  protected int x, y, width, height;
  protected boolean hovering = false;
  protected Rectangle bounds;

  public UIObject(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.bounds = new Rectangle(x, y, width, height);
  }

  public abstract void tick();

  public abstract void render(Graphics g);

  public abstract void onClick();

  public void onMouseRelease(MouseEvent e) {
    if (hovering) {
      onClick();
    }
  }

  public void onMouseMove(MouseEvent e) {
    if (bounds.contains(e.getX(), e.getY())) {
      hovering = true;
    } else {
      hovering = false;
    }
  }

  //getters and setters
  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
    bounds.setBounds(x, y, width, height);
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
    bounds.setBounds(x, y, width, height);
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
    bounds.setBounds(x, y, width, height);
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
    bounds.setBounds(x, y, width, height);
  }

  public boolean isHovering() {
    return hovering;
  }

  public void setHovering(boolean hovering) {
    this.hovering = hovering;
  }
}
