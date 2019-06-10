package game2d.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game2d.Handler;

public class UIManager {
  private Handler handler;
  private ArrayList<UIObject> objects;

  public UIManager(Handler handler) {
    this.handler = handler;
    objects = new ArrayList<UIObject>();
  }


  public void tick() {
    for (UIObject o : objects) {
      o.tick();
    }
  };

  public void render(Graphics g) {
    for (UIObject o : objects) {
      o.render(g);
    }
  };

  public void onMouseMove(MouseEvent e) {
    for (UIObject o : objects) {
      o.onMouseMove(e);
    }
  };

  public void onMouseRelease(MouseEvent e) {
    for (UIObject o : objects) {
      o.onMouseRelease(e);
    }
  };

  public void addObject(UIObject o) {
    objects.add(o);
  }

  public UIObject getObject(int i) {
    return objects.get(i);
  }

  public void removeObject(UIObject o) {
    objects.remove(o);
  }

  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  public Handler getHandler() {
    return handler;
  }
}
