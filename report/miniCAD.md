# mini CAD
**姓名**: 范钊瑀

**学号**: 3190105838


## 思路与设计
### 布局设计

### 数据结构

#### `Pair`类

#### 图形类
`Shape`类是一个抽象类，它作为所有图形的父类，其中包含了所有图形共有的属性，包含有确定图形位置的p1和p2两个坐标点，以及确定图形颜色的color属性，和存在有`thickness`变量以用于保存绘制图形的粗细

```java
abstract class Shape implements Serializable{
    public Pair<Integer> p1 = new Pair<>(0, 0);
    public Pair<Integer> p2 = new Pair<>(0, 0);
    public Color color = Color.black;
    public double thickness = 1;

    public abstract void draw(Graphics g, Color c);

    public abstract boolean isInside(Pair<Integer> p);

    public abstract void move(Pair<Integer> p);

    public void resize(double ratio){
        Pair<Integer> center = new Pair<>((int)((p1.getX() + p2.getX()) / 2), (int)((p1.getY() + p2.getY()) / 2));
        Pair<Integer> new_p1 = new Pair<>((int)(center.getX() - (center.getX() - p1.getX()) * ratio), (int)(center.getY() - (center.getY() - p1.getY()) * ratio));
        Pair<Integer> new_p2 = new Pair<>((int)(center.getX() - (center.getX() - p2.getX()) * ratio), (int)(center.getY() - (center.getY() - p2.getY()) * ratio));
        p1 = new_p1;
        p2 = new_p2;
    }

    public void setP1(Pair<Integer> p1) {
        this.p1 = p1;
    }

    public void setP2(Pair<Integer> p2) {
        this.p2 = p2;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }

    public double getThickness(){
        return thickness;
    }

    public Pair<Integer> getTopLeftPoint(){
        return new Pair<>(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
    }

    public Pair<Integer> getBottomRightPoint(){
        return new Pair<>(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));
    }

}
```

### 


## 功能实现

### 放置图形

### 尺寸变化
#### 大小改变

#### 粗细改变